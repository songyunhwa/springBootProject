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
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400&display=swap');
#app {
  font-family:'Noto Sans KR', sans-serif;
  #font-family: "jua";
  font-size: 20px;
  height:500px;
}

@font-face {
  font-family: "jua";
  src: url("fonts/BMJUA_ttf.ttf");
}


</style>