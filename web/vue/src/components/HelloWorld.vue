<template>
  <div class="hello">
    <h1>{{ msg }}</h1>

  </div>
  <button type = "button" @click= "getName()">보기</button>
  <div v-if='view === true'>
    <p> 이름 : {{name}}</p>
  </div>
</template>
<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data(){
    return{
      view : false,
      name : '',
    }
  },
  methods:{
    getName: function () {
      const base_url = 'http://localhost:9000/api/main'
      this.$http.get(`${base_url}`).then((result) => {
        this.name = result.data.name
        this.view = true
      });
    }
  }
}
</script>

<!-- scoped CSS -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
