function toYen(num) {
  if (!num) return ''
  return '\u00a5' + num.toString()
}

export default {toYen}