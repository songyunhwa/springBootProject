<!-- 배너 -->
<template>
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
    <wished-list></wished-list>
  </div>
</template>

<script>
import WishedList from "@/components/Wished";

export default {
  name: 'WishedHome',
  components: {WishedList, },
  data: () => ({
    email: '',
    password: '',
    url: '',
    object: [],
  }),
  created() {
    this.email = this.$cookies.get('email');
  },

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

</style>