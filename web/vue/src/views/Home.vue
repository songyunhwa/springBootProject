<template>
  <div>
    <h3>당다라당당에 오신걸 환영합니다! {{ email }}</h3>
  </div>

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
  <router-link to="/name">
    <button>찜한 장소</button>
  </router-link>

  <youtube-list></youtube-list>
</template>

<script>
import YoutubeList from "@/components/YoutubeList";
export default {
  name: 'Home',
  components: {YoutubeList},
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/place',
    object: [],
  }),
  created() {
    this.email = this.$cookies.get('email');
  },
  methods: {
  }
}
</script>
<style>

</style>