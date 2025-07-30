import axios from 'axios'


const request = axios.create({
    baseURL: import.meta.env.VITE_API_BASE,
    timeout: 10000,
    headers: { 'Content-Type': 'application/json' }
})

// 请求拦截器
request.interceptors.request.use(cfg => {
    const token = localStorage.getItem('token')
    if (token) cfg.headers.Authorization = `Bearer ${token}`
    return cfg;
})


export default request;