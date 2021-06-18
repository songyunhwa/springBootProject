<template>
    <div>
      <h3>당다라당당에 오신걸 환영합니다! {{email}}</h3>
    </div>
  <ul>
    <li v-for="place in this.places"
    v-bind:key="place" v-on:click="onEmit(place)"
    >
      <name>{{place.name}}</name> {{place.subCategory}} <hr>
      <youtube>{{place.youtube}}</youtube>
    </li>
  </ul>
</template>
<script>
import axios from "axios";
export default {
  name:'Home',
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/place',
    places: [{
      name: '',
      area: '',
      number : '',
      subCategory: '',
      recommend:'',
      view:'',
      youtube: ''
    }],
    object:[],
  }),
  created() {
    this.email = this.$cookies.get('email');
    this.getYoutube();
  },
  methods: {
    getYoutube(){
      return axios
          .get(this.url)
          .then(({data}) => {
            this.places = data;
          })
          .catch(({error})=>{
            console.log("error");
            console.log(error);
          })
    },
    onEmit(place){
      this.$emit("setInput", place);

      this.$router.push({ path: '/youtube' });
    }
  }
}
</script>
<style>
  ul{
    list-style-type: none;
  }
  li{
    font-weight: lighter;
    height: 50px;
    line-height: 50px;
  }
  name{
    font-style: oblique;
  }
  category{
    color:rosybrown;
  }
  youtube{
    font-size:10px;
  }

</style>