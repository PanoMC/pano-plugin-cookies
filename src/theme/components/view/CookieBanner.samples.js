// Sample data of CookieBanner for the view catalogue (doc 02 section 7). Pure data: import only view helpers and
// relative .js fixtures.
/** @type {string[]} */
export const notApplicable = ['error', 'loading'];

const config = {
  enabled: true,
  message: 'We use cookies to ensure you get the best experience on our website.',
  buttonText: 'Got it!',
  linkText: 'Learn more',
  linkUrl: 'https://www.cookiesandyou.com/',
  design: 'BAR',
  position: 'BOTTOM',
  primaryColor: '#007bff',
  textColor: '#ffffff',
  backgroundColor: '#343a40',
  useCustomContent: false,
  customContent: '',
};

/** @type {import('@panomc/plugin-kit').Samples} */
export default {
  filled: {
    props: { config },
  },
  empty: {
    props: { config: { ...config, enabled: false } },
  },
};
