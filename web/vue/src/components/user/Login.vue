<template>
  <div><h2>{{ this.title }}</h2></div>
  <Form @submit="Click" v-slot="{ errors }">
    <table style="width: 300px; text-align: center;">
      <tr>
        <td>이메일</td>
        <Field name="username" rules="required" v-model="this.username"/>
        <span v-if="errors.email"><div style="color:red;">이메일을 적어주세요.</div> </span>
      </tr>
      <tr>
        <td>비밀번호</td>
        <Field name="password" rules="required|alpha_dash" v-model="this.password"/>
        <span v-if="errors.password"><div style="color:red;">비밀번호를 적어주세요. ** 알파벳, 숫자 밑 대시만이 가능합니다.</div> </span>
      </tr>
      <tr></tr>
      <tr><input type="submit" value="확인" v-if="!errors.email&&!errors.password"></tr>
    </table>
    <div style="color:red; margin-top: 10px;">{{ result }}</div>
  </Form>

  <div style="margin-top: 10px;">
    <button v-if="this.title==='로그인'" @click="ChangeUrl">회원가입으로 가기</button>
    <button v-if="this.title==='회원가입'" @click="ChangeUrl">로그인</button>
    <button @click="this.$router.push({path: '/'});">홈</button>
  </div>
  <div>

    <a href="http://localhost:9000/oauth2/authorization/google">
      <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
      Sign in with google</a>
  </div>

</template>
<script>
import axios from 'axios'
import {Form, Field, defineRule} from 'vee-validate';
import {required, email, alpha_dash} from '@vee-validate/rules';

defineRule('required', required);
defineRule('email', email);
defineRule('alpha_dash', alpha_dash);

export default {
  name: 'Login',
  components: {
    Form,
    Field,
  },
  data: () => ({
    title: '로그인',
    username: '',
    password: '',
    url: '',
    result: '',
    userModel: {
      username: '',
      password: '',
      role: 'ROLE_USER',
    },
    errors: [],
    isLoaded: false,
  }),
  state: {
    accessToken: null,
  },
  created() {
    let url = 'http://localhost:9000/auth/google/callback';

    if (this.$route.query.code) {
      axios
          .get(url + '?code=' + this.$route.query.code)
          .then(({data}) => {
            this.$route.query = '';
            console.log(data);
            if (this.$cookies.get('username') == null) {
              this.$cookies.set('username', data[0].username);
              this.$cookies.set('role', data[0].role);
            }
            this.$router.push({path: '/'});

          })
          .catch((error) => {
            this.$route.query = '';

            this.result = error;
            if (this.$cookies.get('sessionId') != null)
              this.$cookies.remove('sessionId');
          })

    }
  },
  mutations: {
    login(state, {accessToken}) {
      state.accessToken = accessToken
      localStorage.accessToken = accessToken
    },
  },
  methods: {
    Click() {
      if (this.title == '로그인')
        this.Login();
      else
        this.SignUp();
    },

    Login() {
      if (this.$cookies.get('sessionId') != null) {
        this.url = this.resourceHost + `/login/check?id=${this.$cookies.get('sessionId')}`
      } else {
        if (!this.username || !this.password) {
          alert("Your email or password is empty.");
        }
        this.url = this.resourceHost + `/login?username=${this.username}&password=${this.password}`
      }
      axios.defaults.withCredentials = true;
      return axios
          .get(this.url)
          .then(({data}) => {
            console.log("login => " + data.token);
            if (this.$cookies.get('username') == null) {
              this.$cookies.set('username', data.username);
              this.$cookies.set('role', data.role);
            }
            this.$router.push({path: '/'});
          })
          .catch(error => {
            console.log(error.response);
            this.result = error.response.data;
            if (this.$cookies.get('SESSION') != null)
              this.$cookies.remove('SESSION');
          })
    },

    ChangeUrl() {
      this.result = "";
      if (this.title === '로그인') {
        this.title = '회원가입';
      } else {
        this.title = '로그인';
      }
    },
    SignUp() {
      this.userModel.username = this.username;
      this.userModel.password = this.password;

      return axios
          .post(this.resourceHost + '/user', this.userModel)
          .then(() => {
            this.title = '로그인';
            this.result = '회원가입 되었습니다.';
          })
          .catch((error) => {
            this.result = error.response.data;
          })
    },
  }
}
</script>
<style>

</style>