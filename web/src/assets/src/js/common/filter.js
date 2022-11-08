function toYen(val) {
  if (!val) return ''
  return '\u00a5' + val.toString()
}

function roundOff(val, digits) {
  if (typeof parseFloat(val) !== "number") return ''
  return Math.round(val * Math.pow(10, digits) ) / Math.pow(10, digits);
}

export default {toYen, roundOff}