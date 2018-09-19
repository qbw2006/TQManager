// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Redis from './components/Redis'
import router from './router'
import axios from 'axios'  
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'


const host = process.env.NODE_ENV === "development" ? "" : "http://10.1.6.37:8088";
const instance = axios.create({
    baseURL: host
})

Vue.prototype.$http=instance;  
Vue.config.productionTip = false

// 配置axios发送请求时携带cookie
axios.defaults.withCredentials = true

Vue.use(ElementUI)

/* eslint-disable no-new */
//new Vue({
//el: '#app',
//router,
//components: { App },
//template: '<App/>'
//})


new Vue({
  el: '#redis',
  router,
  components: { Redis },
  template: '<Redis/>'
})
