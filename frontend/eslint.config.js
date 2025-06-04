import js from "@eslint/js";
import pluginVue from "eslint-plugin-vue";
import { defineConfig } from "eslint/config";
import globals from "globals";
import tseslint from "typescript-eslint";

export default defineConfig([
  {
    files: ["**/*.{js,mjs,cjs,ts,mts,cts,vue}"],
    plugins: { js },
    extends: ["js/recommended"],
  },
  {
    files: ["**/*.{js,mjs,cjs,ts,mts,cts,vue}"],
    languageOptions: { globals: { ...globals.browser, ...globals.node } },
  },
  tseslint.configs.recommended,
  pluginVue.configs["flat/recommended"],
  {
    files: ["**/*.vue"],
    languageOptions: { parserOptions: { parser: tseslint.parser } },
  },
  {
    rules: {
      // 通用规则
      "no-console": process.env.NODE_ENV === "production" ? "error" : "warn",
      "no-debugger": process.env.NODE_ENV === "production" ? "error" : "warn",
      "no-unused-vars": "warn",
      "no-undef": "warn",
      // Vue 规则
      "vue/multi-word-component-names": [
        "error",
        {
          ignores: ["index"],
        },
      ], // 允许单字组件名
      "vue/max-attributes-per-line": [
        "error",
        {
          singleline: 5,
          multiline: 5,
        },
      ],
      "vue/singleline-html-element-content-newline": [
        "error",
        {
          ignoreWhenNoAttributes: true,
          ignoreWhenEmpty: true,
          ignores: ["pre", "textarea", "h1"],
          externalIgnores: [],
        },
      ],
    },
  },
  { ignores: ["node_modules/", "dist/", "public/", "*.d.ts"] },
]);
