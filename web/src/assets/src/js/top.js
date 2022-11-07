import { createApp } from 'vue'
import Filter from './common/filter'

const Top = {
  data() {
    return {
      top_100: data["top_100"],
      bottom_100: data["bottom_100"]
    }
  },
  computed: {
    // toYen: () => (num) => {
    //   if (!num) return ''
    //   return '\u00a5' + num.toString()
    // }

    // toYen: function () {
    //   return function (num) {
    //     return Filter.toYen(num)
    //   }
    // }

    toYen: () => (num) => Filter.toYen(num)
  },
  delimiters: ["[[","]]"]
}

let app = createApp(Top).mount('#vue-top')