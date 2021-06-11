<template>
    <div>
        <h1>Welcome to Home!</h1>
        <img src="../assets/logo.png">
    </div>
</template>
<script>
export default {
name:'Login'
}
</script>

<!--
const Login = {
<template>
  <div>
      <h2>Log In</h2>
      <form @submit="onSubmit">
          <input placeholder="Enter your ID" v-model="username">
          <input placeholder="Enter your password" v-model="password">
          <button type="submit">Login</button>
      </form>
  </div>
</template>

<script>
const resourceHost = "http://localhost:9000/api/v1"
export default {
  name: 'Login',
  data: () => ({
    username: '',
    password: ''
  }),
  state : {
  accessToken :null,
  },
  getters: {},
  mutations: {
    login(state, { accessToken }) {
      state.accessToken = accessToken
       localStorage.accessToken = accessToken
    },
    logout(state) {
      state.accessToken = null
    },
  },
  methods: {
      login({ commit }, { email, password }) {
            return axios
              .post(`${resourceHost}/login`, { username, password })
              .then(({ data }) => {commit("LOGIN", data)
              // http 요청 헤더
               axios.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`;
              })
          },
          LOGOUT({ commit }) {
            commit("LOGOUT")
          },

  },
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
}
</script>

<style>

</style>
-->