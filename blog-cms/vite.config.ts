import {fileURLToPath, URL} from "node:url";

import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";
import {createSvgIconsPlugin} from "vite-plugin-svg-icons";
import IconsResolver from "unplugin-icons/resolver";

import path from "node:path";

const pathSrc = path.resolve(__dirname, "src");
// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueDevTools(),
        AutoImport({
            resolvers: [
                ElementPlusResolver({importStyle: "sass"}),
                // Auto import icon components
                // 自动导入图标组件
                IconsResolver({
                    prefix: "Icon",
                }),
            ],
            dts: path.resolve(pathSrc, "auto-imports.d.ts"),
        }),
        Components({
            resolvers: [
                // Auto register icon components
                // 自动注册图标组件
                IconsResolver({
                    enabledCollections: ["ep"],
                }),
                ElementPlusResolver({importStyle: "sass"}),
            ],
            dts: path.resolve(pathSrc, "components.d.ts"),
        }),
        createSvgIconsPlugin({
            // 指定需要缓存的图标文件夹
            iconDirs: [path.resolve(process.cwd(), "src/assets/icons")],
            // 指定symbolId格式
            symbolId: "icon-[dir]-[name]",
        }),
    ],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        hmr: true,
        port: 1314,
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ""),
            },
        },
    },
});
