<!-- An HTML code field in the look of the panel's code editor, without a library: a transparent textarea on top of a
     highlighted copy of the same text, with line numbers.
     Styles are inline: the panel does not load a plugin's plugin.css on plugin pages, so a <style> block would not apply. -->
<div
  class="form-control d-flex p-0 overflow-hidden"
  style="height: {height}; --hcf-tag: {colors.tag}; --hcf-attr: {colors.attr}; --hcf-value: {colors.value}; --hcf-bracket: {colors.bracket}; --hcf-comment: {colors.comment};">
  <div
    bind:this={gutter}
    aria-hidden="true"
    style="flex: none; min-width: 2.75rem; padding: {PADDING_Y} 0.5rem {PADDING_Y} 0; overflow: hidden; text-align: right; color: var(--bs-secondary-color); border-right: 1px solid var(--bs-border-color); background: var(--bs-tertiary-bg); user-select: none; {FONT}">
    {#each lineNumbers as number (number)}
      <div>{number}</div>
    {/each}
  </div>
  <div style="position: relative; flex: 1; min-width: 0;">
    <pre
      bind:this={highlight}
      aria-hidden="true"
      style="{LAYER} overflow: hidden; color: var(--bs-body-color); pointer-events: none; background: transparent;"><code
        style="color: inherit; font-size: inherit; white-space: inherit;">{@html highlighted}</code
      ></pre>
    <textarea
      {id}
      spellcheck="false"
      autocomplete="off"
      autocapitalize="off"
      wrap="off"
      style="{LAYER} overflow: auto; resize: none; color: transparent; caret-color: var(--bs-body-color); background: transparent; outline: none;"
      bind:value
      on:scroll={syncScroll}
      on:keydown={onKeydown}></textarea>
  </div>
</div>

<script>
  import { onMount } from 'svelte';

  export let value = '';
  export let id = undefined;
  export let height = '320px';

  const PADDING_Y = '0.5rem';
  const FONT = 'font-family: var(--bs-font-monospace); font-size: 0.85rem; line-height: 1.5;';
  const LAYER = `position: absolute; inset: 0; margin: 0; padding: ${PADDING_Y} 0.75rem; border: 0; white-space: pre; tab-size: 2; ${FONT}`;

  const LIGHT = { tag: '#22863a', attr: '#6f42c1', value: '#032f62', bracket: '#6a737d', comment: '#6a737d' };
  const DARK = { tag: '#e06c75', attr: '#d19a66', value: '#98c379', bracket: '#abb2bf', comment: '#7f848e' };

  let gutter;
  let highlight;
  let dark = false;

  $: colors = dark ? DARK : LIGHT;
  $: text = value ?? '';
  $: lineNumbers = Array.from({ length: text.split('\n').length }, (_, i) => i + 1);
  // A trailing newline needs one more line in the copy, or the last line of the textarea has nothing under it.
  $: highlighted = highlightHtml(text) + '\n';

  // The panel switches its theme with data-bs-theme on <html>.
  onMount(() => {
    const root = document.documentElement;
    const read = () => (dark = root.getAttribute('data-bs-theme') === 'dark');
    const observer = new MutationObserver(read);

    read();
    observer.observe(root, { attributes: true, attributeFilter: ['data-bs-theme'] });

    return () => observer.disconnect();
  });

  function escape(part) {
    return part.replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;');
  }

  function span(kind, part) {
    const italic = kind === 'comment' ? ' font-style: italic;' : '';

    return `<span style="color: var(--hcf-${kind});${italic}">${escape(part)}</span>`;
  }

  function highlightTag(tag) {
    const match = tag.match(/^(<\/?)([^\s/>]*)([\s\S]*?)(\/?>)?$/);

    if (!match) {
      return escape(tag);
    }

    const [, open, name, rest, close = ''] = match;
    const attributes = rest.replace(
      /([^\s=]+)(\s*=\s*)?("[^"]*"|'[^']*'|[^\s"'>]+)?|(\s+)/g,
      (part, attrName, equals, attrValue, space) => {
        if (space) return space;

        return (
          span('attr', attrName) + (equals ? escape(equals) : '') + (attrValue ? span('value', attrValue) : '')
        );
      },
    );

    return span('bracket', open) + span('tag', name) + attributes + span('bracket', close);
  }

  function highlightHtml(source) {
    let out = '';
    const pattern = /<!--[\s\S]*?(?:-->|$)|<\/?[a-zA-Z][^>]*>?/g;
    let last = 0;
    let match;

    while ((match = pattern.exec(source))) {
      out += escape(source.slice(last, match.index));
      out += match[0].startsWith('<!--') ? span('comment', match[0]) : highlightTag(match[0]);
      last = pattern.lastIndex;
    }

    return out + escape(source.slice(last));
  }

  function syncScroll(event) {
    highlight.scrollTop = event.currentTarget.scrollTop;
    highlight.scrollLeft = event.currentTarget.scrollLeft;
    gutter.scrollTop = event.currentTarget.scrollTop;
  }

  function onKeydown(event) {
    if (event.key !== 'Tab' || event.ctrlKey || event.altKey || event.metaKey) {
      return;
    }

    event.preventDefault();

    const input = event.currentTarget;
    const { selectionStart: start, selectionEnd: end } = input;

    value = text.slice(0, start) + '  ' + text.slice(end);

    requestAnimationFrame(() => {
      input.selectionStart = input.selectionEnd = start + 2;
    });
  }
</script>
