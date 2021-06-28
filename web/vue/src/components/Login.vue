<template>
  <div>
    <h2>{{this.title}}</h2>
    <form @submit="onSubmit">
      <input placeholder="Enter your ID" v-model="email">
      <input placeholder="Enter your password" v-model="password">
    </form>
    {{ result }}
    <button type="button" @click="Click">확인</button>
  </div>

  <button v-if="this.title==='로그인'" @click="ChangeUrl">회원가입</button>
  <button v-if="this.title==='회원가입'" @click="ChangeUrl">로그인</button>
  <button @click="this.$router.push({path: '/'});">홈</button>

</template>

<script>
import axios from 'axios'

const resourceHost = "http://localhost:9000/api/v1"
export default {
  name: 'Login',
  data: () => ({
    title: '로그인',
    email: '',
    password: '',
    url: '',
    result: '',
    userModel: {
      email: '',
      password: '',
      authority: 'ROLE_USER',
    }
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
    Click(){
      if(this.title == '로그인')
        this.Login();
      else
        this.SignUp();
    },

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
            console.log(data);
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

    ChangeUrl(){
      if(this.title == '로그인') {
        this.title = '회원가입';
        this.url = resourceHost + '/user';
      }else {
        this.title = '로그인';
      }
    },

    SignUp(){
      if (!this.email || !this.password) {
        alert("Your email or password is empty.");
      }

      this.userModel.email = this.email;
      this.userModel.password = this.password;

      return axios
          .post(this.url, this.userModel)
          .then(({data}) => {
            console.log(data);


          })
          .catch(() => {

          })
    },
  },

}
</script>
