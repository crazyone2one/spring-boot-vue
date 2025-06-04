import vue from "@vitejs/plugin-vue";
import path from "path";
import UnoCSS from "unocss/vite";
import { defineConfig, loadEnv } from "vite";

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), "");
  return {
    // vite 配置
    plugins: [vue(), UnoCSS()],
    resolve: {
      alias: [
        {
          find: /\/@\//,
          replacement: path.resolve(__dirname, ".", "src") + "/",
        },
      ],
      extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"], // 自动匹配文件后缀名
    },
    server: {
      proxy: {
        "/api": {
          target: "http://localhost:3000",
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ""),
        },
      },
    },
    define: {
      __APP_ENV__: JSON.stringify(env.APP_ENV),
    },
  };
});
