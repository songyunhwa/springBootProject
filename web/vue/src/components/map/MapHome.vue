<template>
  <recommend class="map-list-right"></recommend>

  <div class="banner">
    <button @click="this.$router.push({path: '/'});">홈</button>

    <div class="title">
      당다라당당에 오신걸 환영합니다! {{ username }}
      오늘 방문자수: {{this.views}}
    </div>
  </div>

  <Map></Map>
</template>

<script>
import axios from "axios";
import Recommend from "@/components/banner/Recommend";
import Map from "@/components/map/Map";

export default {
  name: 'MapHome',
  components: {Recommend, Map},
  data: () => ({
    username: '',
    password: '',
    url: '',
    views : '0', // 하루 접속량
  }),
  created() {
    this.username = this.$cookies.get('username');
    this.url = this.resourceHost + '/history';
    this.getLoginHistory();
  },
  methods: {
    getLoginHistory(){
      return axios
          .get(this.url)
          .then(({data}) => {
            this.views = data;
          })
          .catch(() => {

          })
    }
  }
}
</script>
<style>

.map-list-right {
  float:right;
  width: 15%;
}
</style>