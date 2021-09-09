<template>
  <div class="banner">
    <div v-if="this.username&&this.username.length>0">
      <router-link to="/logout">
        <button>로그아웃</button>
      </router-link>
    </div>
    <div v-else>
      <router-link to="/login">
        <button>로그인</button>
      </router-link>
    </div>

    <button @click="this.$router.push({path: '/'});">홈</button>

    <div class="title">
      당다라당당에 오신걸 환영합니다! {{ username }}
      오늘 방문자수: {{ this.views }}
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'Banner',
  components: {},
  data: () => ({
    usename: '',
    role: '',
    url: '',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    views: '0', // 하루 접속량,
  }),
  created() {
    this.username = this.$cookies.get('username');
    this.role = this.$cookies.get('role');
    this.url = this.resourceHost + '/history';
    this.getLoginHistory();
  },
  methods: {
    getLoginHistory() {
      return axios
          .get(this.url)
          .then(({data}) => {
            this.views = data;
          })
          .catch(() => {

          })
    },
    scrollUp() {
      window.scrollTo(0, 0);
    },

  }
}
</script>
<style>

.banner {
  height: 50px;
}

.title {
  font-size: 14px;
  color: #7C7877;
  padding: 10px 10px;
  float: left;
}


</style>