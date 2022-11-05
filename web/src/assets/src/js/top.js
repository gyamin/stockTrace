import { createApp } from 'vue'

const Top = {
  data() {
    return {
      top_100: data["top_100"],
      bottom_100: data["bottom_100"]
    }
  },
  delimiters: ["[[","]]"]
}


let app = createApp(Top).mount('#vue-app')