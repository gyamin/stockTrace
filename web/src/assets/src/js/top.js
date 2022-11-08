import { createApp } from 'vue'
import Filter from './common/filter'

const Top = {
  data() {
    return {
      days_results: data["days_results"],
      top_100: data["top_100"],
      bottom_100: data["bottom_100"]
    }
  },
  computed: {
    roundOff: () => (val) => Filter.roundOff(val,2),
    toYen: () => (val) => Filter.toYen(val),
  },
  delimiters: ["[[","]]"]
}

let app = createApp(Top).mount('#vue-top')