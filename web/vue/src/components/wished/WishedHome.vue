<!-- 찜한 창 홈 -->
<template>
  <recommend class="right-banner"></recommend>

  <div class="banner">
    <button @click="this.$router.push({path: '/'});">홈</button>

    <div class="title">
      당다라당당에 오신걸 환영합니다! {{ username }}
      오늘 방문자수: {{this.views}}
    </div>

  </div>
  <wished-list></wished-list>
</template>

<script>
import axios from "axios";
import Recommend from "@/components/banner/Recommend";
import WishedList from "@/components/wished/Wished";
export default {
  name: 'Home',
  components: {WishedList, Recommend},
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

.title {
  font-size: 14px;
  color: #7C7877;
  padding: 10px 10px;
  float: left;
}

button {
  color: #444444;
  background: #F3F3F3;
  border: 1px #DADADA solid;
  padding: 5px 10px;
  font-weight: bold;
  font-size: 9pt;
  outline: none;
  float: left;
  margin: 10px 10px;
}

button:hover {
  border: 1px #C6C6C6 solid;
  box-shadow: 1px 1px 1px #EAEAEA;
  color: #333333;
  background: #F7F7F7;
}

button:active {
  box-shadow: inset 1px 1px 1px #DFDFDF;
}

.right-banner{
  float:right;
  width: 20%;
}
</style>