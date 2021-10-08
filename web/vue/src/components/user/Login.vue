<template>
  <div><h2>{{ this.title }}</h2></div>
  <Form @submit="Click" v-slot="{ errors }">
    <table style="text-align: center;">
      <tr v-if="this.title==='회원가입'">
        <td>이름</td>
        <td>
          <Field name="username" rules="required" v-model="this.username"/>
          <span v-if="errors.username"><div style="color:red;">이름을 적어주세요.</div> </span></td>
      </tr>
      <tr>
        <td>이메일</td>
        <td>
          <Field name="email" rules="required" v-model="this.email"/>
          <span v-if="errors.email"><div style="color:red;">이메일을 적어주세요.</div> </span></td>
      </tr>
      <tr>
        <td>비밀번호</td>
        <td>
          <Field name="password" rules="required|alpha_dash" v-model="this.password"/>
        </td>
      </tr>
      <tr></tr>
    </table>
    <span v-if="errors.password" style="color:red;">비밀번호를 적어주세요.
        ** 알파벳, 숫자 밑 대시만이 가능합니다.</span>
    <tr></tr>
    <input type="submit" value="확인" style="margin-left: 10px;" :disabled="errors.username||errors.email||errors.password">
  </Form>

  <div style="margin-top: 10px;">
    <button v-if="this.title==='로그인'" @click="ChangeUrl">회원가입으로 가기</button>
    <button v-if="this.title==='회원가입'" @click="ChangeUrl">로그인으로 가기</button>
    <button @click="onToggleEmailModal">비밀번호 찾기</button>
    <button @click="this.$router.push({path: '/'});">홈</button>
  </div>
  <div>

    <a href="http://localhost:9000/oauth2/authorization/google">
      <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
      Sign in with google</a>
  </div>

  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
  <SendEmail v-show="showEmailModal" :select_modal="modal" @close="onToggleEmailModal"></SendEmail>
</template>
<script>
import axios from 'axios'
import {Form, Field, defineRule} from 'vee-validate';
import {required, email, alpha_dash} from '@vee-validate/rules';
import Modal from "@/modal/Modal";
import SendEmail from "@/modal/SendEmail";

defineRule('required', required);
defineRule('email', email);
defineRule('alpha_dash', alpha_dash);

export default {
  name: 'Login',
  components: {
    Form,
    Field,
    Modal,
    SendEmail
  },
  data: () => ({
    title: '로그인',
    username: '',
    email: '',
    password: '',
    url: '',
    userModel: {
      username: '',
      email: '',
      password: '',
      role: 'ROLE_USER',
    },
    showModal: false,
    showEmailModal: false,
    modal: {
      header: '',
      body: '',
      footer: ''
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
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
      } else {
        this.showModal = true;
      }
    },
    onToggleEmailModal() {
      if (this.showEmailModal) {
        this.showEmailModal = false;
      } else {
        this.showEmailModal = true;
      }
    },

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
        if (!this.email || !this.password) {
          alert("Your email or password is empty.");
        }
        this.url = this.resourceHost + `/login?username=${this.email}&password=${this.password}`
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
            this.modal.body = '로그인 실패했습니다.' + error.response.data;
            this.onToggleModal();

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
      let form = {
        username: this.username,
        email: this.email,
        password: this.password,

      }

      return axios
          .post(this.resourceHost + '/user', form)
          .then(() => {
            this.title = '로그인';
            this.modal.body = '회원가입 되었습니다.';
            this.onToggleModal();

          })
          .catch((error) => {
            this.modal.body = '회원가입 실패했습니다.' + error.response.data;
            this.onToggleModal();

          })
    },
  }
}
</script>
<style>

</style>