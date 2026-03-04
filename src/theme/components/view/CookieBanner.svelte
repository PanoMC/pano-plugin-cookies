{#if show && config?.enabled}
  {#if config.design === 'BAR'}
    <div
      class="fixed-{config.position.toLowerCase()} start-0 end-0 border-top border-bottom shadow-sm p-3"
      style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
        '#212529'}; border-color: {config.primaryColor} !important; z-index: 9999;">
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
                  class="btn btn-primary px-4"
                  style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                  onclick={accept}>
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
      class="fixed-{config.position.toLowerCase().replace('_', '-')} p-4"
      style="z-index: 9999; max-width: 400px;">
      <div
        class="card shadow-lg border-0"
        style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
          '#212529'};">
        <div class="card-body p-4">
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
                class="btn btn-primary w-100 py-2"
                style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                onclick={accept}>
                {config.buttonText}
              </button>
            {/if}
          {/if}
        </div>
      </div>
    </div>
  {:else if config.design === 'MODAL'}
    <div
      class="vw-100 vh-100 position-fixed top-0 start-0 d-flex align-items-center justify-content-center p-3"
      style="background: rgba(0,0,0,0.5); z-index: 9999;">
      <div style="width: 100%; max-width: 450px;">
        <div
          class="card shadow-lg border-0"
          style="background: {config.backgroundColor || '#fff'}; color: {config.textColor ||
            '#212529'};">
          <div class="card-body p-4 text-center">
            {#if config.useCustomContent}
              {@html config.customContent}
            {:else}
              <h5 class="card-title mb-3">Çerez Politikası</h5>
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
                  class="btn btn-primary w-100 py-2"
                  style="background-color: {config.primaryColor}; border-color: {config.primaryColor}"
                  onclick={accept}>
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
      class="position-fixed p-3 shadow"
      style="background: {config.backgroundColor}; color: {config.textColor}; z-index: 9999; bottom: 1rem; right: 1rem;">
      {@html config.customContent}
    </div>
  {/if}
{/if}

<script>
  import { page } from '@panomc/sdk/svelte';

  const browser = typeof window !== 'undefined';
  const accepted = browser ? localStorage.getItem('pano-cookies-accepted') : true;

  let config = $derived($page.data.cookiesConfig);
  let show = $state(!accepted);

  function accept() {
    if (browser) {
      localStorage.setItem('pano-cookies-accepted', 'true');
    }
    show = false;
  }
</script>
