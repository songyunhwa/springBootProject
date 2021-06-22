<template>
  <div class="right">
    <Youtube :select_place="select"></Youtube>
  </div>
  <div class="left">
    <ul class="type04">
      <li v-for="place in this.places"
          v-bind:key="place" @click="selectPlace(place)">
        <name>{{ place.name }}</name>
        {{ place.subCategory }}
        <div class="hr">{{ place.youtube }}</div>
      </li>
    </ul>
  </div>
</template>
<script>
import axios from "axios";
import Youtube from "@/components/Youtube";

export default {
  name: 'YoutubeList',
  components: {Youtube},
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/place',
    places: [{
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: ''
    }],
    select: {
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: ''
    },
    object: [],
  }),
  created() {
    this.getYoutube();
  },
  methods: {
    getYoutube() {
      return axios
          .get(this.url)
          .then(({data}) => {
            this.places = data;
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.select = place;
    }
  }
}
</script>
<style>

youtube {
  font-size: 10px;
}

ul.type04 {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  margin: 20px 10px;
}

name {
  width: 150px;
  color: #F0E5DE;
}

li {
  padding: 5px 0px 5px 5px;
  margin-bottom: 10px;
  border-bottom: 1px solid #efefef;
  font-size: 20px;
  color: #ABD0CE;
}

.left {
  background: #7C7877;
  width: 50%;
  position: fixed;
  margin-top:20px;
}

.right {
  right: -20px;
  width: 50%;
  height: 100%;
  position: fixed;
  background: #D9D4CF; /*#ABD0CE #F0E5DE #D9D4CF; #7C7877;*/
  text-align: center;
  margin-top:20px;
  margin-right:20px;
  margin-bottom: 10px;
  font-size: 20px;
  color: #7C7877;
}
</style>