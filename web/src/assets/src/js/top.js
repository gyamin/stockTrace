import { createApp } from 'vue'

const Top = {
  data() {
    return {
      message: 'Hello Vue!!'
    }
  },
  delimiters: ["[[","]]"]
}

createApp(Top).mount('#app-test')