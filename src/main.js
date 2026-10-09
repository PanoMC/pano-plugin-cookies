import { PanoPlugin, viewComponent } from '@panomc/sdk';
import { derived } from 'svelte/store';
import { _ as i18n } from '@panomc/sdk/utils/language';
import { api } from '@panomc/sdk/plugin-api';
import { showToast } from '@panomc/sdk/toasts';

const pluginId = 'pano-plugin-cookies';

// this is to render plugin translations
export const _ = derived(i18n, ($_fn) => {
  return (key, options) => $_fn(`plugins.${pluginId}.${key}`, options);
});

// Success/failure colouring for this plugin's toasts, matching the panel. showToast from
// @panomc/sdk/toasts is the host panel's ToastContainer `show`, whose signature is
// (text, params, toastComponent, options): passing undefined for toastComponent keeps the
// host's DefaultToast, and options.variant maps to Bootstrap's text-success / text-danger.
// These live here rather than in @panomc/sdk/toasts because this plugin is pinned to
// @panomc/sdk 1.0.0-dev.39, which predates the variants; they can be dropped for a direct
// SDK import once that pin moves. On an older panel build the extra argument is ignored and
// the toast renders neutral, so this degrades instead of breaking.
export function showSuccessToast(text, params = {}) {
  return showToast(text, params, undefined, { variant: 'success' });
}

export function showErrorToast(text, params = {}) {
  return showToast(text, params, undefined, { variant: 'danger' });
}

export default class CookiesPlugin extends PanoPlugin {
  onLoad() {
    const pano = this.pano;

    if (pano.isPanel) {
      // Load config when visiting the plugin detail page
      pano.ui.addon.onLoad(async (data, event) => {
        if (data.addon.id !== pluginId) return;

        try {
          const config = await api.panel.get({
            path: '/config',
            request: event,
          });
          data.addon.config = config;
        } catch (e) {
          console.error('[pano-plugin-cookies] Failed to load config', e);
        }
      });

      // Register settings component in plugin detail
      pano.ui.hook.register({
        name: `panel:plugin-detail:content:${pluginId}`,
        component: viewComponent(() => import('./panel/components/view/CookiesSettings.svelte')),
        permission: `pano.plugin.${pluginId}.manage.cookies`,
      });
    } else {
      // Theme side
      pano.ui.app.onLoad(async (data, event) => {
        try {
          const config = await api.get({
            path: '/config',
            request: event,
          });
          data.cookiesConfig = config;
        } catch (e) {
          console.error('[pano-plugin-cookies] Failed to load config', e);
        }
      });
      // The banner mounts on `theme:top` through `export const view` in CookieBanner.svelte.
    }
  }

  onContextUpdate(ctx) { }

  onUnload() { }
}
