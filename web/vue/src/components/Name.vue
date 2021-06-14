<template>
  <ul id="name" class="demo">
    <li v-for="item in items" :key="item.message">
      {{ item.message }}
    </li>
  </ul>
</template>
<script>
Vue.createApp({
data() {
return {
items: [{ message: 'Foo' }, { message: 'Bar' }]
}
}
}).mount('#name')
</script>