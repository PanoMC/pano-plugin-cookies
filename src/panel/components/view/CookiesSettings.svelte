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
              bind:checked={config.useCustomContent} />
          </div>
        </div>
      </div>

      {#if config.useCustomContent}
        <div class="mb-3">
          <label class="form-label" for="customContent">
            {$_('settings.custom-content')}
            <small class=" d-block">{$_('settings.custom-content-desc')}</small>
          </label>
          <Editor
            bind:content={config.customContent}
            showHtml={true}
            showPreview={true}
            contentStyles={'min-height: 200px;'} />
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

      <!-- Design -->
      <div class="row mb-3">
        <label class="col-md-6 col-form-label" for="design">
          {$_('settings.design')}
        </label>
        <div class="col-md-6">
          <select class="form-select" id="design" bind:value={config.design}>
            <option value="BAR">{$_('settings.design-bar')}</option>
            <option value="FLOATING">{$_('settings.design-floating')}</option>
            <option value="MODAL">{$_('settings.design-modal')}</option>
          </select>
        </div>
      </div>

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
  import { onMount } from 'svelte';
  import { _, showSuccessToast, showErrorToast } from '../../../main';
  import { Editor } from '@panomc/sdk/components/panel';
  import ApiUtil from '@panomc/sdk/utils/api';

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

  let initialConfig = JSON.parse(JSON.stringify(config));

  $: hasChanges = JSON.stringify(config) !== JSON.stringify(initialConfig);

  $: if (config.design === 'BAR' && !['TOP', 'BOTTOM'].includes(config.position)) {
    config.position = 'BOTTOM';
  }

  async function saveConfig() {
    if (saving) return;
    saving = true;
    try {
      await ApiUtil.put({
        path: '/api/panel/cookies/config',
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
