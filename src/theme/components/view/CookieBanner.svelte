<style>
  .cookie-banner-bar {
    position: fixed;
    left: 0;
    right: 0;
    z-index: 1050;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
    border-top: 3px solid transparent;
    transition: all 0.3s ease;
  }
  .cookie-banner-bar.top {
    top: 0;
    border-top: none;
    border-bottom: 3px solid var(--primary);
  }
  .cookie-banner-bar.bottom {
    bottom: 0;
  }

  .cookie-banner-floating,
  .cookie-banner-modal {
    position: fixed;
    z-index: 1050;
    width: 350px;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    transition: all 0.3s ease;
  }

  .cookie-banner-floating.bottom-left {
    bottom: 20px;
    left: 20px;
  }
  .cookie-banner-floating.bottom-right {
    bottom: 20px;
    right: 20px;
  }
  .cookie-banner-floating.top-left {
    top: 20px;
    left: 20px;
  }
  .cookie-banner-floating.top-right {
    top: 20px;
    right: 20px;
  }
  .cookie-banner-floating.top {
    top: 20px;
    left: 50%;
    transform: translateX(-50%);
  }
  .cookie-banner-floating.bottom {
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
  }

  .cookie-banner-modal {
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
  }

  @media (max-width: 576px) {
    .cookie-banner-floating,
    .cookie-banner-modal {
      width: 90%;
      left: 5%;
      right: 5%;
      transform: none;
    }
  }

  .cookie-banner-custom {
    position: fixed;
    z-index: 1050;
    transition: all 0.3s ease;
  }

  .message {
    font-size: 0.9rem;
    line-height: 1.4;
  }
</style>

{#if show && config?.enabled}
  {#if config.design === 'BAR'}
    <div
      class="cookie-banner-bar {config.position.toLowerCase()}"
      style="background: {config.backgroundColor}; color: {config.textColor}; border-color: {config.primaryColor}">
      {#if config.useCustomContent}
        {@html config.customContent}
      {:else}
        <div
          class="container d-flex flex-column flex-md-row align-items-center justify-content-between py-2">
          <div class="message mb-2 mb-md-0">
            {config.message}
            {#if config.linkText && config.linkUrl}
              <a
                href={config.linkUrl}
                target="_blank"
                style="color: {config.primaryColor}"
                class="ms-1">
                {config.linkText}
              </a>
            {/if}
          </div>
          {#if config.buttonText}
            <button
              class="btn"
              style="background: {config.primaryColor}; color: #fff"
              on:click={accept}>
              {config.buttonText}
            </button>
          {/if}
        </div>
      {/if}
    </div>
  {:else}
    <div
      class="cookie-banner-{config.design.toLowerCase()} {config.position
        .toLowerCase()
        .replace('_', '-')}"
      style="background: {config.backgroundColor}; color: {config.textColor}; --primary: {config.primaryColor}">
      {#if config.useCustomContent}
        {@html config.customContent}
      {:else}
        <div class="content">
          <div class="message mb-3">
            {config.message}
            {#if config.linkText && config.linkUrl}
              <div class="mt-2">
                <a href={config.linkUrl} target="_blank" style="color: {config.primaryColor}">
                  {config.linkText}
                </a>
              </div>
            {/if}
          </div>
          {#if config.buttonText}
            <div class="actions text-end">
              <button
                class="btn w-100"
                style="background: {config.primaryColor}; color: #fff"
                on:click={accept}>
                {config.buttonText}
              </button>
            </div>
          {/if}
        </div>
      {/if}
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
