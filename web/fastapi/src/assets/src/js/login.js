import { createApp } from 'vue'

const Login = {
  data() {
    return {
      message: ''
    }
  },
  delimiters: ["[[","]]"]
}

let app = createApp(Login).mount('#vue-app')
