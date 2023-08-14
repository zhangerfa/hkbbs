import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

import Components from "unplugin-vue-components/vite";
import { VantResolver } from "unplugin-vue-components/resolvers";
import { viteMockServe } from "vite-plugin-mock";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Components({
      resolvers: [VantResolver()],
    }),
    viteMockServe({
      localEnabled: false, // 是否应用于本地
      prodEnabled: false, // 是否应用于生产
      supportTs: true, // 打开后 可以读取 ts 文件模块 请注意 打开后将无法监视.js 文件
      watchFiles: true, // 监视文件更改 这样更改mock的时候，不需要重新启动编译
      mockPath: "./src/utils/mock", //解析路径
    }),
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
  server: {
    proxy: {
      // 代理所有/api的请求
      "/api": {
        target: "http://zhangerfa.site/",
        // 跨域配置
        changeOrigin: true,
        //重写路径
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
