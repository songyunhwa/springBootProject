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

    <button @click="onToggleModal">
      나만 아는 맛집
    </button>


    <div class="title">
      당다라당당에 오신걸 환영합니다! {{ email }}
      오늘 방문자수: {{ this.views }}
    </div>

    <div class="search">
      <input v-model="input" style="margin: 5px 10px;padding: 5px 10px;" @keyup.enter="this.$emit('search', input)">
      <button @click="this.$emit('search', input)">검색</button>
    </div>

    <!-- 아래는 맛집 추가할 때 쓰는 Modal - success 알림창 / placeModal  추가창 -->
    <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
    <PlaceModal v-show="showPlaceModal" :select_modal="modal" @close="onTogglePlaceModal" @putPlace="onToggleModal" ref="placeModal"></PlaceModal>
  </div>
</template>

<script>
import axios from "axios";
import PlaceModal from "@/views/PlaceModal";

export default {
  name: 'Banner',
  components: {PlaceModal},
  data: () => ({
    email: '',
    role: '',
    url: 'http://localhost:9000/api/v1/history',
    input: '', // 입력하는 값
    msg: '',   // 실질적으로 넘어가는 값
    views: '0', // 하루 접속량,
    showModal: false,
    showPlaceModal: false,
    modal: {
      header: '',
      body: '',
      footer: ''
    }
  }),
  created() {
    this.email = this.$cookies.get('email');
    this.role = this.$cookies.get('role');
    this.getLoginHistory();
  },
  methods: {
    getLoginHistory() {
      return axios
          .get(this.url)
          .then(({data}) => {
            this.views = data;
          })
          .catch(() => {

          })
    },
    scrollUp() {
      window.scrollTo(0, 0);
    },
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
      } else {
        this.modal.body = "맛집이 저장되었습니다.";
        this.showModal = true;
      }
    },
    onTogglePlaceModal() {
      if (this.showPlaceModal) {
        this.showPlaceModal = false;
      } else {
        this.showPlaceModal = true;
        this.$refs.placeModal.initialization();
      }
    },
  }
}
</script>
<style>

.banner {
  height: 50px;
}

.title {
  font-size: 14px;
  color: #7C7877;
  padding: 10px 10px;
  float: left;
}


</style>