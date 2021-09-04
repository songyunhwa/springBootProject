<template>
  <div>
    <h2>로그아웃</h2>
    <button type="button" @click="LOGOUT">확인</button>
    <button @click="this.$router.push({path: '/'});">홈</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Logout',
  data: () => ({
    username: '',
    password: '',
    url: ''
  }),
    created() {
        this.url = this.resourceHost + '/history';
    },
  state: {
    accessToken: null,
  },
  methods: {
    LOGOUT() {
      this.url = this.url + '/logout';
      return axios
          .get(this.url)
          .then(() => {
            this.$cookies.remove('username');
            this.$cookies.remove('role');

            this.$router.push({ path: '/' });
          })
          .catch((e)=>{
            console.log(e);
          })
    },

  },

}
</script>
<!--



redirect() {
        const { search } = window.location
        const tokens = search.replace(/^\?/, "").split("&")
        const { returnPath } = tokens.reduce((qs, tkn) => {
          const pair = tkn.split("=")
          qs[pair[0]] = decodeURIComponent(pair[1])
          return qs
        }, {})

        // 리다이렉트 처리
        this.$router.push(returnPath)
      },
-->