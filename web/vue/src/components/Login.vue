<template>
  <div><h2>{{ this.title }}</h2></div>
  <Form @submit="Click" v-slot="{ errors }">
    <table style="width: 300px; text-align: center;">
      <tr>
        <td>이메일</td>
        <Field name="email" rules="required" v-model="this.email"/>
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
    <button @click="handleClickGetAuth" :disabled="!isLoaded">get auth code</button>
  </div>
</template>
<script>
import axios from 'axios'
import {Form, Field, defineRule} from 'vee-validate';
import {required, email, alpha_dash} from '@vee-validate/rules';

defineRule('required', required);
defineRule('email', email);
defineRule('alpha_dash', alpha_dash);

const resourceHost = "http://localhost:9000/api/v1"
export default {
  name: 'Login',
  components: {
    Form,
    Field,
  },
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
    },
    errors: [],
    isLoaded: false,
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
    Click() {
      if (this.title == '로그인')
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
      axios.defaults.withCredentials = true;
      return axios
          .get(this.url)
          .then(({data}) => {
            console.log(data);

            if (this.$cookies.get('email') == null) {
              this.$cookies.set('email', data.username);
              this.$cookies.set('role', data.role);
            }
            this.$router.push({path: '/'});
          })
          .catch((error) => {
            this.result = error;
            if (this.$cookies.get('sessionId') != null)
              this.$cookies.remove('sessionId');
          })
    },

    ChangeUrl() {
      this.result = "";
      if (this.title == '로그인') {
        this.title = '회원가입';
        this.url = resourceHost + '/user';
      } else {
        this.title = '로그인';
      }
    },
    SignUp() {
      this.userModel.email = this.email;
      this.userModel.password = this.password;

      return axios
          .post(this.url, this.userModel)
          .then(() => {
            this.title = '로그인';
            this.result = '회원가입 되었습니다.';
          })
          .catch((error) => {
            this.result = error.response.data.split("java.lang.Exception:")[1];
          })
    },
    handleClickGetAuth() {
      this.$gAuth.getAuthCode()
          .then(authCode => {
            //on success
            return this.$http.post('http://localhost:9000/auth/google', {
              code: authCode,
              redirect_uri: 'postmessage'
            })
          })
          .then(response => {
            //and then
            console.log(response);
          })
          .catch(error => {
            //on fail do something
            console.log(error);
          })
    }
  }
}
</script>
