import { defineStore } from 'pinia'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import router from '@/router'

interface User { email: string }

export const useUserStore = defineStore('user', {
    state: () => ({ user: null as User | null }),
    actions: {
        async login(email: string, password: string) {
            const res = await request.post('/auth/login', { email, password })
            localStorage.setItem('token', res.data.token)
            this.user = { email }
            ElMessage.success('登录成功')
            router.push('/')
        },
        async register(email: string, password: string) {
            const res = await request.post('/auth/register', { email, password })
            localStorage.setItem('token', res.data.token)
            this.user = { email }
            ElMessage.success('注册成功')
            router.push('/')
        },
        logout() {
            localStorage.removeItem('token')
            this.user = null
            router.push('/login')
        }
    }
})