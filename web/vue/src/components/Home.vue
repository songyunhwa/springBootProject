<template>
  <!-- 배너 -->
  <recommend class="right-banner"></recommend>
  <Banner></Banner>

  <!-- 유투브 -->
  <youtube-list :msg="input" ref="list"></youtube-list>

  <!-- 하단 스크롤 바 -->
  <div class="scoll-menu">
    <img src="../assets/images/scroll_up.png" v-on:click="scrollUp"/>
  </div>
</template>

<script>
import YoutubeList from "@/components/YoutubeList";
import Recommend from "@/components/Recommend";
import Banner from "@/components/Banner";
export default {
  name: 'Home',
  components: {YoutubeList, Recommend, Banner},
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/history',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    views : '0', // 하루 접속량,
  }),
  created() {
    this.email = this.$cookies.get('email');
  },
  methods: {
    scrollUp(){
      window.scrollTo(0,0);
    }
  }
}
</script>
<style>

button {
  color: #444444;
  background: #F3F3F3;
  border: 1px #DADADA solid;
  padding: 5px 10px;
  font-weight: bold;
  font-size: 9pt;
  outline: none;
  float: left;
  margin: 10px 10px;
}

button:hover {
  border: 1px #C6C6C6 solid;
  box-shadow: 1px 1px 1px #EAEAEA;
  color: #333333;
  background: #F7F7F7;
}

button:active {
  box-shadow: inset 1px 1px 1px #DFDFDF;
}

.right-banner{
  float:right;
  width: 20%;
}

.scoll-menu{
  position: fixed;
  right:10px;
  bottom:10px;
  height:100px;
  width:100px;
  text-decoration: none;

}
</style>