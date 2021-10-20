<template>
  <!-- 추천 배너 -->
  <recommend class="right-banner"></recommend>

  <!-- 배너 -->
  <Banner></Banner>

  <!-- 검색 -->
  <div class="search">
    <input v-model="input" style="margin: 5px 10px;padding: 5px 10px;" @keyup.enter="getYoutubes(input)">
    <button @click="getYoutubes(input)">검색</button>
  </div>

  <!-- 유투브 -->
  <div class="youtube-list-right">
    <Youtube class="youtube-li" :select_place="select" ref="youtube" v-on:click="selectPlace" @getYoutube="getYoutube"></Youtube>
  </div>
  <div class="youtube-list-left">
    <youtube-list :msg="input" ref="youtube_list" @selectPlace="selectPlace"></youtube-list>
  </div>

  <!-- 하단 스크롤 바 -->
  <div class="scoll-menu">
    <img
        :src="require(`@/assets/images/scroll_up.png`)"
        v-on:click="scrollUp"/>
  </div>
</template>

<script>
import YoutubeList from "@/components/youtube/YoutubeList";
import Recommend from "@/components/banner/Recommend";
import Banner from "@/components/banner/Banner";
import Youtube from "@/components/youtube/Youtube";
export default {
  name: 'Home',
  components: {YoutubeList, Youtube, Recommend, Banner},
  data: () => ({
    username: '',
    password: '',
    url: '',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    views: '0', // 하루 접속량,
    select: {
      id: '',
      name: '',
      location: [{
        address: '',
        lat: '',
        lng: '',
      }],
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: [{
        videoId: '',
        channelTitle: '',
        title: '',
        url: '',
      }],
    },
  }),
  created() {

  },
  mounted() {
    this.username = this.$cookies.get('username');
    this.url = this.resourceHost + '/history';
    this.getYoutubes("");
  },
  methods: {
    scrollUp() {
      window.scrollTo(0, 0);
    },
    getYoutubes(param) {
      // 초기 화면이나 검색할 때 사용 ( 모든 리스트 불러오기)
      this.$refs.youtube_list.setPlace(param);
    },
    selectPlace(place) {
      // place 선택
      this.$refs.youtube.getReview(place.id);
      this.select = place;
    },
    getYoutube(placeId) {
      // 유투브 수정했을때
      this.$refs.youtube_list.getYoutube(placeId);
    }
  }
}
</script>
<style>
.youtube-list-left {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  background: #7C7877;
  position: absolute;
  width: 30%;
}

.youtube-list-right {
  position: absolute;
  left: 30%;
  width: 49%;
  background: #D9D4CF; /*#ABD0CE #F0E5DE #D9D4CF; #7C7877;*/
  text-align: center;
  margin-right: 20px;
  font-size: 20px;
  color: #7C7877;
  border-bottom: 1px solid #DFDFDF;
}

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

.right-banner {
  float: right;
  width: 20%;
}

.scoll-menu {
  position: fixed;
  right: 10px;
  bottom: 10px;
  height: 100px;
  width: 100px;
  text-decoration: none;

}

.search {
  float: right;
}

li {
  font-size: 20px;
  color: #ABD0CE;
  margin-top: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid #DFDFDF;

}
</style>