import { defineStore } from 'pinia'
import type { AppState } from '@/stores/interface'
// 应用仓库
const useAppStore = defineStore('useAppStore', {
  state: (): AppState => ({
    isCollapse: false,
    device: 'desktop',
    size: 'default'
  }),
  actions: {
    changeCollapse(isCollapse: boolean) {
      this.isCollapse = isCollapse
    },
    toggle() {
      this.isCollapse = !this.isCollapse
    },
    toggleDevice(device: string) {
      this.device = device
    }
  }
})

export default useAppStore
