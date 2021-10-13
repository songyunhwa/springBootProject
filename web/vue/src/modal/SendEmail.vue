<template>
  <div v-show="this.showModal">
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">

            <div class="modal-header">
              <slot name="header">
                <h3>{{ title }}</h3>
              </slot>
            </div>

            <div class="modal-body">
              <table style=".td : width: 500px; text-align: center;">
                <slot name="body">
                  <tr>
                    <td>이름</td>
                    <td>
                      <input name="username" v-model="username"/>
                    </td>
                  </tr>
                  <tr>
                    <td>이메일</td>
                    <td>
                      <input name="email" v-model="email"/>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <button @click="sendEmail" :disabled="username.length == 0||email.length == 0">
                        메일 보내기
                      </button>
                    </td>
                    <td>
                      <button @click="$emit('close')">
                        취소
                      </button>
                    </td>
                  </tr>
                </slot>
              </table>
              <div style="color:red; margin-top: 10px;">{{ result }}</div>

            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'SendModal',
  props: {
    select_modal: Object,
  },
  data: () => ({
    title: '비밀번호 찾기',
    username: '',
    email: '',
    url: '',
    result: '',
  }),
  created() {
    this.url = this.resourceHost;
  },
  methods: {
    sendEmail() {
      return axios
          .get(this.url + '/sendEmail?email=' + this.email + '&username=' + this.username)
          .then(() => {
            this.$emit('close');
          })
          .catch((error) => {
            console.log("error");
            console.log(error);
            this.result = "메일 보내기 실패했습니다." + error.response.data;
          })
    }
  }
}
</script>
<style>


</style>
