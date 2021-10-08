<template>
  <div v-show="this.showModal">
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">

            <div class="modal-header">
              <slot name="header">
                <h3>{{title}}</h3>
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
                  <div class="modal-default-button" style="float:right;">
                    <button @click="sendEmail">
                      메일 보내기
                    </button>
                    <button @click="$emit('close')">
                      취소
                    </button>
                  </div>
                  <div style="color:red; margin-top: 10px;">{{ result }}</div>
                </slot>
              </table>
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
    result : '',
  }),
  created() {
    this.url = this.resourceHost;
  },
  methods: {
    sendEmail() {
      return axios
          .get(this.url + '/sendEmail?email=' + this.email +'&username=' + this.username )
          .then(() => {
            this.$emit('close');
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
            this.result = error.response;
          })
    }
  }
}
</script>
<style>

</style>
