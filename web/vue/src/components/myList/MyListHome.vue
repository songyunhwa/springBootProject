<template>

  <recommend class="right-banner"></recommend>
  <Banner></Banner>

  <router-link to="/wished">
    <button>찜한 장소</button>
  </router-link>

  <my-list></my-list>

  <!-- 하단 스크롤 바-->
  <div class="scoll-menu">
    <img src="src/assets/images/scroll_up.png" v-on:click="scrollUp"/>
  </div>
</template>

<script>
import Recommend from "@/components/banner/Recommend";
import Banner from "@/components/banner/Banner";
import MyList from "./MyList";

export default {
  name: 'MyListHome',
  components: {MyList, Recommend, Banner},
  data: () => ({
    username: '',
    password: '',
    url: '',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    views : '0', // 하루 접속량,
  }),
  created() {
    this.username = this.$cookies.get('username');
    this.url = this.resourceHost + '/history';
  },
  methods: {
    scrollUp(){
      window.scrollTo(0,0);
    },
  }
}
</script>
<style>

    .my-list {
        height:200px;
    }
</style>