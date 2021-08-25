<!-- 유투브 리스트에 있는 유투브 -->
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
    <div class="tail" @click="onTogglePlaceModal">수정</div>
  </div>

  <Review :select_place="select_place" ref="review"></Review>
  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
  <PlaceModal v-show="showPlaceModal" :select_modal="modal" @close="onTogglePlaceModal" ref="placeModal"></PlaceModal>
</template>
<script>
import axios from "axios";
import Modal from "@/views/Modal";
import PlaceModal from "@/views/PlaceModal";
import Review from "@/components/Review";

export default {
  name: 'Youtube',
  components: { Modal , Review, PlaceModal},
  props: {
    select_place: Object
  },
  data: () => ({
    username: '',
    password: '',
    url: 'http://localhost:9000/api/v1/',
    showModal: false,
    showPlaceModal: false,
    modal: {
      header:'',
      body:'',
      footer:''
    },
    select: '',
  }),
  computed: {
    placeName() {
      return this.$route.params.id;
    }
  },
  created() {
    this.username = this.$cookies.get('username');
  },
  methods: {
    setRecommend() {
      if(this.$cookies.get('username')==null){
        this.modal.body = '로그인을 해야합니다.';
        this.onToggleModal();
        return;
      }

      return axios
          .post(this.url + 'recommend?userName=' + this.username + '&id=' + this.select_place.id)
          .then((data) => {
            this.modal.body = data.data;
            this.onToggleModal();
          })
          .catch(({error}) => {
            this.modal.body = '추천을 실패했습니다.'
            this.onToggleModal();
            console.log(error);

          })
    },
    setWished() {
      if(this.$cookies.get('username')==null){
        this.modal.body = '로그인을 해야합니다.';
        this.onToggleModal();
        return;
      }

      return axios
          .post(this.url + 'wished?userName=' + this.username + '&id=' + this.select_place.id)
          .then((data) => {
            this.modal.body = data.data;
            this.onToggleModal();
            this.$emit('putWished', this.select_place);
          })
          .catch(({error}) => {
            this.modal.body = '찜을 실패했습니다.';
            this.onToggleModal();
            console.log(error);
          })
    },
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
        this.$emit('click', this.select_place.id);
      } else {
        this.showModal = true;
      }
    },
    onTogglePlaceModal() {
      if (this.showPlaceModal) {
        this.showPlaceModal = false;
      } else {
        this.showPlaceModal = true;
        this.$refs.placeModal.modifyYoutube(this.select_place);
      }
    },
    selectPlace() {
      this.select = this.select_place;
    },
    getReview(id) {
      this.$refs.review.getReview(id);
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