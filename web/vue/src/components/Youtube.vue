<template>
  <div class="youtube">


    <h3>{{ select_place.name }}</h3>
    <div>지역 : {{ select_place.area }}</div>
    <div>번호 : {{ select_place.number }}</div>
    <div>카테고리 : {{ select_place.subCategory }}</div>
    <div>추천수 : {{ select_place.recommend }}</div>
    <div>조회수 : {{ select_place.view }}</div>

    <div>관련 유투브</div>
    <ul>
      <li v-for="youtube in select_place.youtube" v-bind:key="youtube"
          class="youtube-ul">
        <div class="youtube-li">제목 : {{ youtube.title }}</div>
        <div class="youtube-li">유투버 : {{ youtube.channelTitle }}</div>
        <a :href="youtube.url" target="_blank" style="color: rosybrown">바로가기</a>
      </li>
    </ul>
    <div class="tail" @click="setWished">찜</div>
    <div class="tail" @click="setRecommend">추천</div>
  </div>


</template>
<script>
import axios from "axios";

export default {
  name: 'Youtube',
  props: {
    select_place: Object
  },
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/',
  }),
  computed: {
    placeName() {
      return this.$route.params.id;
    }
  },
  created() {
    this.email = this.$cookies.get('email');
  },
  methods: {
    setPlace(place){
      this.$props.select_place = place;
    },
    setRecommend() {
      return axios
          .post(this.url + 'recommend?userName=' + this.email + '&id=' + this.select_place.id)
          .then(() => {

          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    setWished() {
      return axios
          .post(this.url + 'wished?userName=' + this.email + '&id=' + this.select_place.id)
          .then(() => {

          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
  }
}
</script>
<style>
.youtube-ul {
  list-style: none;
  text-align: left;
  padding-bottom: 10px;
}

.youtube-li {
  color: rosybrown;
  margin-bottom: 10px;
}

.tail {
  float: right;
  margin-right: 40px;
}
</style>