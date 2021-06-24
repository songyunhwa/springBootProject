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

</style>