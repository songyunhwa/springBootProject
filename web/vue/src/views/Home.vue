<!-- 배너 -->
<template>
  <recommend class="right-banner"></recommend>

  <div class="banner">
    <div v-if="this.email&&this.email.length>0">
      <router-link to="/logout">
        <button>로그아웃</button>
      </router-link>
    </div>
    <div v-else>
      <router-link to="/login">
        <button>로그인</button>
      </router-link>
    </div>
    <router-link to="/wished">
      <button>찜한 장소</button>
    </router-link>

    <div class="title">
      당다라당당에 오신걸 환영합니다! {{ email }}
    </div>

    <div class="search">
      <input v-model="input" style="margin: 5px 10px;padding: 5px 10px;">
      <button @click="setMsg">검색</button>
    </div>

  </div>
  <youtube-list :msg="input" ref="list"></youtube-list>
</template>

<script>
import YoutubeList from "@/components/YoutubeList";
import Recommend from "@/components/Recommend";
export default {
  name: 'Home',
  components: {YoutubeList, Recommend},
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/place',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    object: [],
  }),
  created() {
    this.email = this.$cookies.get('email');
  },
  methods: {
    setMsg(){
      this.$refs.list.getYoutube();
    }
  }
}
</script>
<style>

.banner {
  height: 50px;
}

.title {
  font-size: 18px;
  color: #7C7877;
  padding: 10px 10px;
  float: left;
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

.search{
  float: right;
}

.right-banner{
  float:right;
  width: 20%;
}
</style>