import { PanoPlugin, viewComponent } from '@panomc/sdk';
import { derived } from 'svelte/store';
import { _ as i18n } from '@panomc/sdk/utils/language';
import ApiUtil from '@panomc/sdk/utils/api';

const pluginId = 'pano-plugin-cookies';

// this is to render plugin translations
export const _ = derived(i18n, ($_fn) => {
  return (key, options) => $_fn(`plugins.${pluginId}.${key}`, options);
});

export default class CookiesPlugin extends PanoPlugin {
  onLoad() {
    const pano = this.pano;

    if (pano.isPanel) {
      // Load config when visiting the plugin detail page
      pano.ui.addon.onLoad(async (data, event) => {
        if (data.addon.id !== pluginId) return;

        try {
          const config = await ApiUtil.get({
            path: '/api/panel/cookies/config',
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
      const cookieBannerComponent = viewComponent(() => import('./theme/components/view/CookieBanner.svelte'));

      pano.ui.app.onLoad(async (data, event) => {
        try {
          const config = await ApiUtil.get({
            path: '/api/cookies/config',
            request: event,
          });
          data.cookiesConfig = config;
        } catch (e) {
          console.error('[pano-plugin-cookies] Failed to load config', e);
        }
      });

      pano.ui.hook.register({
        name: 'theme:top',
        component: cookieBannerComponent,
        skipLoad: true,
      });
    }
  }

  onContextUpdate(ctx) { }

  onUnload() { }
}
