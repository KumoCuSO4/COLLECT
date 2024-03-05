/*
 * @Description:
 * @Author: lz
 * @Date: 2022-03-31 09:29:24
 * @LastEditTime: 2022-05-26 11:52:03
 * @LastEditors: 张志浩
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'ant-design-vue/dist/antd.css'
import Axios from 'axios'
import Driver from "driver.js"
import "driver.js/dist/driver.min.css"
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.prototype.$driver = new Driver({
  className: 'scoped-class',        // className to wrap driver.js popover
  animate: true,                    // Whether to animate or not
  opacity: 0.75,                    // Background opacity (0 means only popovers and without overlay)
  padding: 10,                      // Distance of element from around the edges
  allowClose: false,                 // Whether the click on overlay should close or not
  overlayClickNext: false,          // Whether the click on overlay should move next
  doneBtnText: '完成',              // Text on the final button
  closeBtnText: '关闭',            // Text on the close button for this step
  stageBackground: '#ffffff',       // Background color for the staged behind highlighted element
  nextBtnText: '下一步',              // Next button text for this step
  prevBtnText: '上一步',          // Previous button text for this step
  showButtons: true,               // Do not show control buttons in footer
  keyboardControl: true,            // Allow controlling through keyboard (escape to close, arrow keys to move)
  scrollIntoViewOptions: {},        // We use `scrollIntoView()` when possible, pass here the options for it if you want any
  onHighlightStarted: (Element) => {}, // Called when element is about to be highlighted
  onHighlighted: (Element) => {},      // Called when element is fully highlighted
  onDeselected: (Element) => {},       // Called when element has been deselected
  onReset: (Element) => {},            // Called when overlay is about to be cleared
  onNext: (Element) => {},                    // Called when moving to next step on any step
  onPrevious: (Element) => {},                // Called when moving to previous step on any step
});

Vue.use(ElementUI)
Vue.use(Antd)
Vue.config.productionTip = false
Axios.defaults.baseURL = 'http://121.5.6.104:8081'
// Axios.defaults.baseURL = '/api'
Axios.defaults.timeout = 50000
Axios.defaults.headers = { 'Content-Type': 'application/json' }
Vue.prototype.$axios = Axios

// 角色分配

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
