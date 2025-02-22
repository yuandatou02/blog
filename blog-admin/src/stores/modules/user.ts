import { defineStore } from 'pinia'
import type { UserState } from '@/stores/interface'
import { removeToken, setToken } from '@/utils/token.ts'
import { login, logout } from '@/api/login'
import type { LoginForm } from '@/api/login/types.ts'
import { getUserInfo } from '@/api/user'
//创建用户仓库
const useUserStore = defineStore('useUserStore', {
  state: (): UserState => ({
    id: null,
    avatar: '',
    roleList: [],
    permissionList: []
  }),
  actions: {
    LogIn(LoginForm: LoginForm) {
      return new Promise((resolve, reject) => {
        login(LoginForm)
          .then(({ data }) => {
            if (data.flag) {
              setToken(data.data)
              resolve(data)
            } else {
              reject(data.msg)
            }
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    LogOut() {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            this.id = null
            this.avatar = ''
            this.roleList = []
            this.permissionList = []
            removeToken()
            resolve(null)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    GetInfo() {
      return new Promise((resolve, reject) => {
        getUserInfo()
          .then(({ data }) => {
            if (data.flag) {
              this.id = data.data.id
              this.avatar = data.data.avatar
              this.roleList = data.data.roleList
              this.permissionList = data.data.permissionList
            }
            resolve(data)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }
  },
  getters: {}
})
// 暴露用户仓库
export default useUserStore
