import { createApp } from 'vue'

const Login = {
  data() {
    return {
      greeting: 'Hello'
    }
  },
  template: `
    <p>{{greeting}}</p>
  `
}

let app = createApp(Login).mount('#vue-app')
