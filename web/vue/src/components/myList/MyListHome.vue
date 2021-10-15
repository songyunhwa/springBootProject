<template>
  <Banner></Banner>


  <my-list></my-list>

</template>

<script>
import Banner from "@/components/banner/Banner";
import MyList from "./MyList";

export default {
  name: 'MyListHome',
  components: {MyList, Banner},
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
</style>