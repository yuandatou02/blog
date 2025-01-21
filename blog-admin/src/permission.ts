import NProgress from 'nprogress'
import router from '@/router'
import useStore from '@/stores'
import { getToken } from '@/utils/token.ts'
import { isReLogin } from '@/utils/request.ts'
// 进度条配置
NProgress.configure({
  easing: 'ease',
  speed: 500,
  showSpinner: false,
  trickleSpeed: 200,
  minimum: 0.3
})
// 白名单路由
const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  // 进度条开始
  NProgress.start()
  const { user, permission } = useStore()
  // 判断是否有token
  if (getToken()) {
    //登录页面则跳转到首页
    if (to.path === '/login') {
      next('/')
      // 进度条结束
      NProgress.done()
    } else {
      // 不是登录页面则判断是否已经获取完用户信息
      if (user.roleList.length === 0) {
        // 没有获取完用户信息则从新登录并获取用户信息
        isReLogin.show = true
        user.GetInfo().then(() => {
          isReLogin.show = false
          permission.generateRoutes().then((accessRoutes) => {
            accessRoutes.forEach((route) => {
              router.addRoute(route)
            })
            next({ ...to, replace: true })
          }).catch((err) => {
            user.LogOut().then(() => {
              ElMessage.error(err)
              next({ path: '/login' })
            })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 未登录可以访问白名单页面(登录页面)
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
