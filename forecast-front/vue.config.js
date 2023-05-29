const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    historyApiFallback: true,
    allowedHosts: 'all',
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '/api',
        },
      },
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    },
  },
})
