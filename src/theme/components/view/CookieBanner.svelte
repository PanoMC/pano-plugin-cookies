{#if show && config?.enabled}
  {#if config.design === 'BAR'}
    <div
      class="cookies-cookie-banner {BAR_POSITION_CLASSES[config.position] ?? ''} start-0 end-0 border-top border-bottom shadow-sm p-3"
      style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
        '#212529'}; border-color: {config.primaryColor} !important; z-index: {BANNER_Z};">
      {#if config.useCustomContent}
        {@html config.customContent}
      {:else}
        <div class="container text-center text-md-start">
          <div class="row align-items-center">
            <div class="col-md-9 mb-3 mb-md-0">
              <p class="mb-0">
                {config.message}
                {#if config.linkText && config.linkUrl}
                  <a
                    href={config.linkUrl}
                    target="_blank"
                    style="color: {config.primaryColor}; text-decoration: underline;"
                    class="ms-1">
                    {config.linkText}
                  </a>
                {/if}
              </p>
            </div>
            <div class="col-md-3 text-md-end">
              {#if config.buttonText}
                <button
                  class="cookies-cookie-banner__action btn btn-primary px-4"
                  style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                  use:clickHandler>
                  {config.buttonText}
                </button>
              {/if}
            </div>
          </div>
        </div>
      {/if}
    </div>
  {:else if config.design === 'FLOATING'}
    <div
      class="cookies-cookie-banner p-4"
      style="position: fixed; {floatingOffset(config.position)} z-index: {BANNER_Z}; max-width: 400px;">
      <div
        class="card shadow-lg border-0"
        style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
          '#212529'};">
        <div class="cookies-cookie-banner__body card-body p-4">
          {#if config.useCustomContent}
            {@html config.customContent}
          {:else}
            <p class="card-text mb-4">
              {config.message}
              {#if config.linkText && config.linkUrl}
                <a
                  href={config.linkUrl}
                  target="_blank"
                  style="color: {config.primaryColor}; text-decoration: underline;"
                  class="d-block mt-2">
                  {config.linkText}
                </a>
              {/if}
            </p>
            {#if config.buttonText}
              <button
                class="cookies-cookie-banner__action-2 btn btn-primary w-100 py-2"
                style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                use:clickHandler>
                {config.buttonText}
              </button>
            {/if}
          {/if}
        </div>
      </div>
    </div>
  {:else if config.design === 'MODAL'}
    <div
      class="cookies-cookie-banner vw-100 vh-100 position-fixed top-0 start-0 d-flex align-items-center justify-content-center p-3"
      style="background: rgba(0,0,0,0.5); z-index: 9999;">
      <div style="width: 100%; max-width: 450px;">
        <div
          class="card shadow-lg border-0"
          style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
            '#212529'};">
          <div class="cookies-cookie-banner__body-2 card-body p-4 text-center">
            {#if config.useCustomContent}
              {@html config.customContent}
            {:else}
              <h5 class="cookies-cookie-banner__title card-title mb-3">Çerez Politikası</h5>
              <p class="card-text mb-4">
                {config.message}
                {#if config.linkText && config.linkUrl}
                  <a
                    href={config.linkUrl}
                    target="_blank"
                    style="color: {config.primaryColor}; text-decoration: underline;"
                    class="d-block mt-2">
                    {config.linkText}
                  </a>
                {/if}
              </p>
              {#if config.buttonText}
                <button
                  class="cookies-cookie-banner__action-3 btn btn-primary w-100 py-2"
                  style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                  use:clickHandler>
                  {config.buttonText}
                </button>
              {/if}
            {/if}
          </div>
        </div>
      </div>
    </div>
  {:else}
    <div
      class="cookies-cookie-banner position-fixed p-3 shadow"
      style="background: {config.backgroundColor}; color: {config.textColor}; z-index: {BANNER_Z}; bottom: 1rem; right: 1rem;">
      {@html config.customContent}
    </div>
  {/if}
{/if}

<script module>
  export const view = { hook: 'theme:top', skipLoad: true };
</script>

<script>
  import {page} from '@panomc/sdk/svelte';

  const browser = typeof window !== 'undefined';
  const accepted = browser ? localStorage.getItem('pano-cookies-accepted') : true;

  // Below the Bootstrap offcanvas / modal backdrop (1040), so an open cart offcanvas or modal stays reachable above the banner; the blocking MODAL design keeps 9999.
  const BANNER_Z = 1039;

  // The bar sits on the top or the bottom edge; the class names are written out in full so the build sees them.
  const BAR_POSITION_CLASSES = { TOP: 'fixed-top', BOTTOM: 'fixed-bottom' };

  // The settings come from the page data (set in src/main.js); the optional prop lets the view catalogue pass its own.
  let { config: configProp = null } = $props();
  let config = $derived(configProp ?? $page.data.cookiesConfig);
  let show = $state(!accepted);

  function accept() {
    if (browser) {
      localStorage.setItem('pano-cookies-accepted', 'true');
    }
    show = false;
  }

  function floatingOffset(position) {
    switch (position) {
      case 'TOP':
        return 'top: 0; left: 50%; transform: translateX(-50%);';
      case 'TOP_LEFT':
        return 'top: 0; left: 0;';
      case 'TOP_RIGHT':
        return 'top: 0; right: 0;';
      case 'BOTTOM_LEFT':
        return 'bottom: 0; left: 0;';
      case 'BOTTOM_RIGHT':
        return 'bottom: 0; right: 0;';
      case 'BOTTOM':
      default:
        return 'bottom: 0; left: 50%; transform: translateX(-50%);';
    }
  }

  function clickHandler(node) {
    node.addEventListener('click', accept);
    return {
      destroy() {
        node.removeEventListener('click', accept);
      }
    };
  }
</script>
