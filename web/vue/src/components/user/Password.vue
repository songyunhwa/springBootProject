<template>
  <div><h2>{{ title }}</h2></div>
  <Form v-slot="{ errors }">
    <table style=".td : width: 500px; text-align: center;">
      <tr>
        <td>이메일</td>
        <td>
          <Field name="email" v-model="email" :disabled="true"/>
        </td>
      </tr>
      <tr>
        <td>비밀번호</td>
        <td>
          <Field name="password" rules="required|alpha_dash" v-model="password"/>
          <span v-if="errors.password"><div style="color:red;">비밀번호를 적어주세요. ** 알파벳, 숫자 밑 대시만이 가능합니다.</div> </span>
        </td>
      </tr>
      <tr>
        <td>비밀번호 확인</td>
        <td>
          <Field name="checkPassword" rules="required" v-model="checkPassword"/>
          <span v-if="errors.checkPassword"><div style="color:red;">비밀번호를 적어주세요.</div> </span>
          <span v-if="!errors.checkPassword&&checkPassword!==password"><div style="color:#ff0000;">같은 비밀번호가 아닙니다..</div> </span>
        </td>
      </tr>
      <tr>
        <td>
          <button @click="ChangePassword" :disabled="errors.password||checkPassword!==password">확인</button>
        </td>
      </tr>
    </table>
    <div style="color:red; margin-top: 10px;">{{ result }}</div>
  </Form>

  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
</template>

<script>
import axios from 'axios'
import {Form, Field, defineRule} from 'vee-validate';
import {required, email, alpha_dash} from '@vee-validate/rules';
import Modal from "@/modal/Modal";

defineRule('required', required);
defineRule('email', email);
defineRule('alpha_dash', alpha_dash);

export default {
  name: 'Password',
  components: {
    Form,
    Field,
    Modal
  },
  data: () => ({
    title: '비밀번호 변경',
    username: '',
    email: '',
    password: '',
    checkPassword: '',
    url: '',
    showModal: false,
    modal: {
      header: '',
      body: '',
      footer: ''
    },
    userModel: {
      username: '',
      email: '',
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
    console.log("created");
    this.email = this.$route.query.email;
    this.url = this.resourceHost;
  },
  methods: {
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
        // 홈으로 이동
        this.$router.push({path: '/'});
      } else {
        this.showModal = true;
      }
    },
    ChangePassword() {
      let form = {
        username: 'not_used',
        email: this.email,
        password: this.password,
      }

      axios
          .post(this.url + '/changePassword', form)
          .then((data) => {
            this.result = data.data;
            this.modal.body = '비밀번호를 변경했습니다.';
            this.onToggleModal();

          })
          .catch((error) => {

            this.modal.body = '비밀번호를 변경하지 못했습니다.' + error.response.data;
            this.onToggleModal();
            console.log(error);
          })
    }
  }
}
</script>

<style scoped>

</style>