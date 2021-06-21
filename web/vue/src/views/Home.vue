<template>
  <div class="side_menu">
    <Youtube :select_place="select"></Youtube>
  </div>

  <div>
    <h3>당다라당당에 오신걸 환영합니다! {{ email }}</h3>
  </div>

  <div v-if="this.email&&this.email.length>0">
    <router-link to="/logout">
      <button>로그아웃</button>
    </router-link>
  </div>
  <div v-else>
    <router-link to="/login">
      <button>로그인</button>
    </router-link>
  </div>
  <router-link to="/name">
    <button>찜한 장소</button>
  </router-link>


  <ul>
    <li v-for="place in this.places"
        v-bind:key="place" @click="selectPlace(place)"
    >
      <name>{{ place.name }}</name>
      {{ place.subCategory }}
      <hr>
      {{ place.youtube }}
    </li>
  </ul>
</template>
<script>
import axios from "axios";
import Youtube from "@/components/Youtube";

export default {
  name: 'Home',
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
    this.email = this.$cookies.get('email');
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

html, body {
  width: 100%; height: 100%;
}

ul {
  list-style-type: none;
}

li {
  font-weight: lighter;
  height: 50px;
  line-height: 50px;
}

name {
  font-style: oblique;
}

category {
  color: rosybrown;
}

youtube {
  font-size: 10px;
}

.side_menu {
  float: right;
  width: 13%;
  height: 100%;
}

</style>