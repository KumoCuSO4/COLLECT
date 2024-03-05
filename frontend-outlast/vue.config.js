/*
 * @Description:
 * @Author: lz
 * @Date: 2022-04-01 09:01:08
 * @LastEditTime: 2022-05-25 20:36:19
 * @LastEditors: 张志浩
 */
module.exports = {
  // publicPath: '/task',
  transpileDependencies: ['vuetify'],
  devServer: {
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      '/api': {
        // 此处的写法，目的是为了 将 /api 替换成接口地址
        target: 'http://121.5.6.104:8081',
        secure: false,
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
