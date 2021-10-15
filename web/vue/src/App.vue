<template>
  <div id="app" class="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {},
  methods: {}
}
</script>
<style>
#app {
  font-family: "jua";
  font-size: 20px;
  height:500px;
}

@font-face {
  font-family: "jua";
  src: url("fonts/BMJUA_ttf.ttf");
}


</style>