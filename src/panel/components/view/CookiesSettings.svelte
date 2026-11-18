{#if addon?.id === 'pano-plugin-cookies'}
  <div class="card">
    <div class="card-header">
      {$_('settings.title')}
    </div>
    <div class="card-body">
      <!-- Enabled -->
      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="enabled">
          <span class="d-block">
            {$_('settings.enabled')}
          </span>
          <small>
            {$_('settings.enabled-desc')}
          </small>
        </label>
        <div class="col-md-6 d-flex align-items-center">
          <div class="form-check form-switch">
            <input
              class="form-check-input"
              type="checkbox"
              id="enabled"
              bind:checked={config.enabled} />
          </div>
        </div>
      </div>

      <!-- Design -->
      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="design">
          {$_('settings.design')}
        </label>
        <div class="col-md-6">
          <select class="form-select" id="design" bind:value={config.design}
            disabled={config.useCustomContent}>
            <option value="BAR">{$_('settings.design-bar')}</option>
            <option value="FLOATING">{$_('settings.design-floating')}</option>
            <option value="MODAL">{$_('settings.design-modal')}</option>
          </select>
        </div>
      </div>

      <!-- Custom Content Toggle -->
      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="useCustomContent">
          {$_('settings.use-custom-content')}
          <small class=" d-block">{$_('settings.use-custom-content-desc')}</small>
        </label>
        <div class="col-md-6 d-flex align-items-center">
          <div class="form-check form-switch">
            <input
              class="form-check-input"
              type="checkbox"
              id="useCustomContent"
              bind:checked={config.useCustomContent}
              on:change={onCustomContentToggle} />
          </div>
        </div>
      </div>

      {#if config.useCustomContent}
        <div class="mb-3">
          <div class="d-flex flex-wrap align-items-start justify-content-between gap-2 mb-2">
            <label class="form-label mb-0" for="customContent">
              {$_('settings.custom-content')}
              <small class=" d-block">{$_('settings.custom-content-desc')}</small>
            </label>
            <div class="d-flex flex-wrap gap-2">
              <button
                type="button"
                class="btn btn-link btn-sm text-decoration-none"
                on:click={() => (isPreview = !isPreview)}>
                <i class="fas {isPreview ? 'fa-code' : 'fa-eye'} me-1"></i>
                {isPreview ? $_('settings.custom-content-edit') : $_('settings.custom-content-preview')}
              </button>
              <button
                type="button"
                class="btn btn-sm btn-outline-secondary"
                on:click={loadDefaultTemplate}>
                {$_('settings.load-default-template')}
              </button>
            </div>
          </div>
          <!-- A code field and a preview, not the rich-text editor: that one rewrites the HTML into its own
               schema and drops div, button, class and data-* as soon as the page opens. -->
          {#if isPreview}
            <!-- The same boxes CookieBanner.svelte draws around the custom HTML, without the fixed position. -->
            <div class="form-control overflow-auto p-3 bg-body-tertiary" style="min-height: 200px;">
              {#if config.design === 'BAR'}
                <div
                  class="border-top border-bottom shadow-sm p-3"
                  style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
                    '#212529'}; border-color: {config.primaryColor} !important;">
                  {@html config.customContent}
                </div>
              {:else if config.design === 'MODAL'}
                <div class="d-flex justify-content-center p-3" style="background: rgba(0,0,0,0.5);">
                  <div style="width: 100%; max-width: 450px;">
                    <div
                      class="card shadow-lg border-0"
                      style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
                        '#212529'};">
                      <div class="card-body p-4 text-center">
                        {@html config.customContent}
                      </div>
                    </div>
                  </div>
                </div>
              {:else}
                <div class="p-4" style="max-width: 400px;">
                  <div
                    class="card shadow-lg border-0"
                    style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
                      '#212529'};">
                    <div class="card-body p-4">
                      {@html config.customContent}
                    </div>
                  </div>
                </div>
              {/if}
            </div>
          {:else}
            <textarea
              class="form-control font-monospace"
              id="customContent"
              rows="10"
              spellcheck="false"
              bind:value={config.customContent}></textarea>
          {/if}
        </div>
      {:else}
        <!-- Message -->
        <div class="row mb-3">
          <label class="col-md-6 col-form-label" for="message">
            {$_('settings.message')}
            <small class=" d-block">{$_('settings.message-desc')}</small>
          </label>
          <div class="col-md-6">
            <textarea class="form-control" id="message" rows="2" bind:value={config.message}
            ></textarea>
          </div>
        </div>

        <!-- Button Text -->
        <div class="row mb-3">
          <label class="col-md-6 col-form-label" for="buttonText">
            {$_('settings.button-text')}
            <small class=" d-block">{$_('settings.button-text-desc')}</small>
          </label>
          <div class="col-md-6">
            <input
              class="form-control"
              id="buttonText"
              type="text"
              bind:value={config.buttonText} />
          </div>
        </div>

        <!-- Link Text -->
        <div class="row mb-3">
          <label class="col-md-6 col-form-label" for="linkText">
            {$_('settings.link-text')}
            <small class=" d-block">{$_('settings.link-text-desc')}</small>
          </label>
          <div class="col-md-6">
            <input class="form-control" id="linkText" type="text" bind:value={config.linkText} />
          </div>
        </div>

        <!-- Link URL -->
        <div class="row mb-3">
          <label class="col-md-6 col-form-label" for="linkUrl">
            {$_('settings.link-url')}
            <small class=" d-block">{$_('settings.link-url-desc')}</small>
          </label>
          <div class="col-md-6">
            <input class="form-control" id="linkUrl" type="text" bind:value={config.linkUrl} />
          </div>
        </div>
      {/if}

      <!-- Position -->
      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="position">
          {$_('settings.position')}
        </label>
        <div class="col-md-6">
          <select class="form-select" id="position" bind:value={config.position}>
            {#if config.design === 'BAR'}
              <option value="TOP">{$_('settings.position-top')}</option>
              <option value="BOTTOM">{$_('settings.position-bottom')}</option>
            {:else}
              <option value="TOP_LEFT">{$_('settings.position-top-left')}</option>
              <option value="TOP_RIGHT">{$_('settings.position-top-right')}</option>
              <option value="BOTTOM_LEFT">{$_('settings.position-bottom-left')}</option>
              <option value="BOTTOM_RIGHT">{$_('settings.position-bottom-right')}</option>
              <option value="TOP">{$_('settings.position-top')}</option>
              <option value="BOTTOM">{$_('settings.position-bottom')}</option>
            {/if}
          </select>
        </div>
      </div>

      <hr />

      <!-- Colors -->
      <h5 class="mb-3">{$_('settings.colors.title')}</h5>

      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="primaryColor">
          {$_('settings.colors.primary')}
        </label>
        <div class="col-md-6">
          <input
            class="form-control form-control-color w-100"
            id="primaryColor"
            type="color"
            bind:value={config.primaryColor} />
        </div>
      </div>

      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="textColor">
          {$_('settings.colors.text')}
        </label>
        <div class="col-md-6">
          <input
            class="form-control form-control-color w-100"
            id="textColor"
            type="color"
            bind:value={config.textColor} />
        </div>
      </div>

      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="backgroundColor">
          {$_('settings.colors.background')}
        </label>
        <div class="col-md-6">
          <input
            class="form-control form-control-color w-100"
            id="backgroundColor"
            type="color"
            bind:value={config.backgroundColor} />
        </div>
      </div>

      <div class="mt-4">
        <button class="btn btn-secondary" on:click={saveConfig} disabled={saving || !hasChanges}>
          {#if saving}
            <span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"
            ></span>
          {/if}
          {$_('generic.save')}
        </button>
      </div>
    </div>
  </div>
{/if}

<script>
  import { _, showSuccessToast, showErrorToast } from '../../../main';
  import { api } from '@panomc/sdk/plugin-api';

  export let addon;

  let config = addon?.config || {
    enabled: true,
    message: '',
    buttonText: '',
    linkText: '',
    linkUrl: '',
    design: 'BAR',
    position: 'BOTTOM',
    primaryColor: '#007bff',
    textColor: '#ffffff',
    backgroundColor: '#343a40',
  };
  let saving = false;
  let isPreview = false;

  let initialConfig = JSON.parse(JSON.stringify(config));

  $: hasChanges = JSON.stringify(config) !== JSON.stringify(initialConfig);

  $: if (config.design === 'BAR' && !['TOP', 'BOTTOM'].includes(config.position)) {
    config.position = 'BOTTOM';
  }

  function escapeHtml(value) {
    return String(value ?? '')
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;');
  }

  /**
   * The built-in notice of the chosen design as plain HTML, filled with the current texts and colours, so a custom
   * notice can start from it. Buttons work through data-cookies-accept / data-cookies-close (see CookieBanner.svelte).
   */
  function buildDefaultTemplate(c) {
    const color = escapeHtml(c.primaryColor || '#007bff');
    const message = escapeHtml(c.message || 'This website uses cookies to give you the best experience.');
    const buttonText = escapeHtml(c.buttonText || 'Accept');
    const link =
      c.linkText && c.linkUrl
        ? `<a href="${escapeHtml(c.linkUrl)}" target="_blank" style="color: ${color}; text-decoration: underline;" class="ms-1">${escapeHtml(c.linkText)}</a>`
        : '';
    const button = (classes) =>
      `<button type="button" class="${classes}" style="background-color: ${color}; border-color: ${color};" data-cookies-accept>${buttonText}</button>`;

    if (c.design === 'BAR') {
      return `<div class="container text-center text-md-start">
  <div class="row align-items-center">
    <div class="col-md-9 mb-3 mb-md-0">
      <p class="mb-0">${message} ${link}</p>
    </div>
    <div class="col-md-3 text-md-end">
      ${button('btn btn-primary px-4')}
    </div>
  </div>
</div>`;
    }

    return `<p class="mb-4">${message} ${link}</p>
${button('btn btn-primary w-100 py-2')}`;
  }

  function loadDefaultTemplate() {
    config.customContent = buildDefaultTemplate(config);
  }

  // While the custom HTML is still the untouched default template, it follows the design, texts and colour it was
  // built from; once it is edited by hand it is left alone.
  let lastTemplate = null;

  $: syncTemplate(buildDefaultTemplate(config));

  function syncTemplate(template) {
    if (lastTemplate !== null && config.customContent === lastTemplate && template !== lastTemplate) {
      config.customContent = template;
    }

    lastTemplate = template;
  }

  function onCustomContentToggle() {
    if (config.useCustomContent && !config.customContent?.trim()) {
      loadDefaultTemplate();
    }
  }

  async function saveConfig() {
    if (saving) return;
    saving = true;
    try {
      await api.panel.put({
        path: '/config',
        body: config,
      });
      addon.config = config;
      initialConfig = JSON.parse(JSON.stringify(config));
      await showSuccessToast(`plugins.${addon.id}.toasts.save-success`);
    } catch (e) {
      console.error('Failed to save cookies config', e);
      await showErrorToast(`plugins.${addon.id}.toasts.save-error`);
    } finally {
      saving = false;
    }
  }
</script>
