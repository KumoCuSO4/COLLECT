/*
 * @Description:
 * @Author: lz
 * @Date: 2022-03-31 09:29:24
 * @LastEditTime: 2022-04-02 22:59:00
 * @LastEditors: lz
 */
import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/home',
    component: Home,
    children: [
      {
        path: '',
        redirect: 'square'
      },
      {
        path: 'square',
        component: () => import('../views/Square.vue'),
        children: [
          {
            path: '',
            name: 'task',
            component: () => import('../views/Task.vue')
          },
          {
            path: 'task-detail/:id',
            name: 'task-detail',
            component: () => import('../views/TaskDetail.vue')
          },
          {
            path: 'report-detail/:id',
            name: 'report-detail',
            component: () => import('../views/ReportDetail.vue')
          }
        ]
      },
      {
        path: 'accept',
        name: 'accept',
        component: () => import('../views/Accept.vue')
      },
      {
        path: 'issue',
        name: 'issue',
        component: () => import('../views/Issue.vue')
      },
      {
        path: 'create',
        name: 'create',
        component: () => import('../views/Create.vue')
      },
      {
        path: 'history',
        name: 'history',
        component: () => import('../views/History.vue')
      },
      {
        path:'person',
        name:'person',
        component:()=>import('@/views/Person.vue')
      },
      {
        path:'settings',
        name:'settings',
        component:()=>import('@/views/Settings.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
