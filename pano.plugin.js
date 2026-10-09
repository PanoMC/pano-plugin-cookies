// Plugin-level options of the Pano plugin kit (@panomc/plugin-kit). The namespace is `cookies`
// (the plugin id minus `pano-plugin-`); the view lives where it was, so no file moved.
export default {
  viewDirs: ['src/theme/components/view'],
  styles: {
    // The banner is drawn from admin settings (background, text and button colours, the floating offset, the stacking
    // order above the page) and these are inline values by nature; a layered class could neither carry them nor beat
    // Bootstrap's unlayered `.btn-primary` colours.
    styleAttrAllow: ['CookieBanner'],
  },
};
