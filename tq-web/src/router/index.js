import Vue from 'vue'
import Router from 'vue-router'
import Redis from '@/components/Redis'

Vue.use(Router)

export default new Router({
  mode:'history',
  base:'/tqmanager/',
  routes: [
    {
      path: '/redis',
      name: 'Redis',
      component: Redis
    }
  ]
})
