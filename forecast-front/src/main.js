import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css'; // or 'ant-design-vue/dist/antd.less'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Antd)


//路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    return next()
  }
  //获取token
  const tokenStr = window.localStorage.getItem('token')
  if (!tokenStr) {
    return next('/login')
  }
  next()
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
