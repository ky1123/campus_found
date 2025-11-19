import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '../views/UserView.vue'
import BackendTestView from '../views/BackendTestView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/users',
    name: 'users',
    component: UserView
  },
  {
    path: '/backend-test',
    name: 'backend-test',
    component: BackendTestView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
