<template>
  <div>
    <h2>Log In</h2>
    <form @submit="onSubmit">
      <input placeholder="Enter your ID" v-model="email">
      <input placeholder="Enter your password" v-model="password">
      <button type="button" @click="Login">Login</button>
    </form>
    {{ result }}
  </div>
</template>

<script>
import axios from 'axios'

const resourceHost = "http://localhost:9000/api/v1"
export default {
  name: 'Login',
  data: () => ({
    email: '',
    password: '',
    url: '',
    result: ''
  }),
  state: {
    accessToken: null,
  },
  getters: {},
  mutations: {
    login(state, {accessToken}) {
      state.accessToken = accessToken
      localStorage.accessToken = accessToken
    },
  },
  methods: {
    Login() {
      if (this.$cookies.get('sessionId') != null) {
        this.url = `${resourceHost}/login/check?id=${this.$cookies.get('sessionId')}`
      } else {
        if (!this.email || !this.password) {
          alert("Your email or password is empty.");
        }
        this.url = `${resourceHost}/login?email=${this.email}&password=${this.password}`
      }
      return axios
          .get(this.url)
          .then(({data}) => {
            if (this.$cookies.get('sessionId') == null) {
              this.$cookies.set('sessionId', data.sessionId);
              this.$cookies.set('email', data.username);
            }
            this.$router.push({path: '/'});

          })
          .catch(() => {
            this.result = "로그인에 실패했습니다. 다시 시도해주세요"
            if (this.$cookies.get('sessionId') != null)
              this.$cookies.remove('sessionId');
          })
    },
  },

}
</script>
