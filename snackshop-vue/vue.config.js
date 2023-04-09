const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer:{
    port:8888,
    //是否启动时打开浏览器

    //设置代理
    proxy: {
      //代理的根路径
      '/':{
        //代理后端路径
        target: 'http://localhost:8080',
        ws: false,
        changeOrigin: true,
        pathReWrite: {
          '^/': '/'
        }
      }
    }
  }
});

