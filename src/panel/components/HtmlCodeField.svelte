<!-- An HTML code field in the look of the panel's code editor, without a library: a transparent textarea on top of a
     highlighted copy of the same text, with line numbers. -->
<div class="html-code-field form-control" style="--html-code-height: {height};">
  <div class="html-code-field__gutter" bind:this={gutter} aria-hidden="true">
    {#each lineNumbers as number (number)}
      <div>{number}</div>
    {/each}
  </div>
  <div class="html-code-field__body">
    <pre class="html-code-field__highlight" bind:this={highlight} aria-hidden="true"><code
        >{@html highlighted}</code
      ></pre>
    <textarea
      {id}
      class="html-code-field__input"
      spellcheck="false"
      autocomplete="off"
      autocapitalize="off"
      wrap="off"
      bind:value
      on:scroll={syncScroll}
      on:keydown={onKeydown}></textarea>
  </div>
</div>

<script>
  export let value = '';
  export let id = undefined;
  export let height = '320px';

  let gutter;
  let highlight;

  $: text = value ?? '';
  $: lineNumbers = Array.from({ length: text.split('\n').length }, (_, i) => i + 1);
  // A trailing newline needs one more line in the copy, or the last line of the textarea has nothing under it.
  $: highlighted = highlightHtml(text) + '\n';

  function escape(part) {
    return part.replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;');
  }

  function span(kind, part) {
    return `<span class="html-code-field__${kind}">${escape(part)}</span>`;
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

<style>
  .html-code-field {
    --html-code-font: var(--bs-font-monospace);
    --html-code-size: 0.85rem;
    --html-code-line: 1.5;
    --html-code-tag: #22863a;
    --html-code-attr: #6f42c1;
    --html-code-value: #032f62;
    --html-code-bracket: #6a737d;
    --html-code-comment: #6a737d;
    --html-code-text: var(--bs-body-color);
    --html-code-gutter: var(--bs-secondary-color);

    display: flex;
    height: var(--html-code-height);
    padding: 0;
    overflow: hidden;
  }

  :global([data-bs-theme='dark']) .html-code-field {
    --html-code-tag: #e06c75;
    --html-code-attr: #d19a66;
    --html-code-value: #98c379;
    --html-code-bracket: #abb2bf;
    --html-code-comment: #7f848e;
  }

  .html-code-field__gutter {
    flex: none;
    min-width: 2.75rem;
    padding: 0.5rem 0.5rem 0.5rem 0;
    overflow: hidden;
    text-align: right;
    color: var(--html-code-gutter);
    border-right: 1px solid var(--bs-border-color);
    background: var(--bs-tertiary-bg);
    user-select: none;
  }

  .html-code-field__gutter,
  .html-code-field__highlight,
  .html-code-field__input {
    font-family: var(--html-code-font);
    font-size: var(--html-code-size);
    line-height: var(--html-code-line);
  }

  .html-code-field__body {
    position: relative;
    flex: 1;
    min-width: 0;
  }

  .html-code-field__highlight,
  .html-code-field__input {
    position: absolute;
    inset: 0;
    margin: 0;
    padding: 0.5rem 0.75rem;
    border: 0;
    white-space: pre;
    tab-size: 2;
    overflow: auto;
  }

  .html-code-field__highlight {
    color: var(--html-code-text);
    pointer-events: none;
    scrollbar-width: none;
  }

  .html-code-field__highlight code {
    color: inherit;
    font-size: inherit;
    white-space: inherit;
  }

  .html-code-field__highlight::-webkit-scrollbar {
    display: none;
  }

  .html-code-field__input {
    resize: none;
    color: transparent;
    caret-color: var(--html-code-text);
    background: transparent;
    outline: none;
  }

  .html-code-field__input::selection {
    background: rgba(var(--bs-primary-rgb), 0.25);
  }

  .html-code-field__highlight :global(.html-code-field__tag) {
    color: var(--html-code-tag);
  }

  .html-code-field__highlight :global(.html-code-field__attr) {
    color: var(--html-code-attr);
  }

  .html-code-field__highlight :global(.html-code-field__value) {
    color: var(--html-code-value);
  }

  .html-code-field__highlight :global(.html-code-field__bracket) {
    color: var(--html-code-bracket);
  }

  .html-code-field__highlight :global(.html-code-field__comment) {
    color: var(--html-code-comment);
    font-style: italic;
  }
</style>
