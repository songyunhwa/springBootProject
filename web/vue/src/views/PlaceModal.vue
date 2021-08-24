<template>
  <div v-show="this.showModal">
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">

            <div class="modal-header">
              <slot name="header">
                <h3>장소 추가하기</h3>
              </slot>
            </div>
            <div class="modal-body">
              <div>
                <div>장소이름</div>
                <div><input v-model="place.name" style="margin: 5px 10px;padding: 5px 10px;" :disabled="isModify"></div>
              </div>
              <div>
                <div>지역</div>
                <div><input v-model="place.area" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
              <div>
                <div>번호</div>
                <div><input v-model="place.number" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
              <div>
                <div>카테고리</div>
                <div>
                  <select name="category" v-model="place.subCategory" class="select-category" :disabled="isModify">
                    <option v-for="(category, index) in categorys" v-bind:key="index"> {{ category.included }}</option>
                  </select>
                </div>
              </div>
              <div>
                <ul>
                  <li v-for="(youtube) in place.youtubes" v-bind:key="youtube">
                    {{ youtube.title }}
                  </li>
                </ul>
              </div>

              <div v-if="!addYoutube">
                <button @click="addYoutube?addYoutube=false:addYoutube=true;">유투브 추가</button>
              </div>
            </div>

            <div v-if="addYoutube">
              <div>
                <div>유투버 이름</div>
                <div><input v-model="youtube.channelTitle" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
              <div>
                <div>제목</div>
                <div><input v-model="youtube.title" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
              <div>
                <div>비디오 아이디</div>
                <div><input v-model="youtube.videoId" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
            </div>

            <div style="color:red; margin-top: 10px;">{{ result }}</div>
            <div class="modal-default-button">
              <button class="modal-default-button" @click="putYoutube" v-if="addYoutube">
                유투브 추가
              </button>
              <button class="modal-default-button" @click="popPlaceYoutubes">
                전체 유투브 삭제
              </button>
              <button @click="this.putPlace">
                확인
              </button>
              <button @click="this.$emit('close')">
                취소
              </button>
            </div>
          </div>
        </div>
      </div>
    </transition>
    <Modal v-show="showResultModal" :select_modal="modal" @close="onToggleResultModal"></Modal>

  </div>
</template>

<script>
import axios from "axios";
import Modal from "@/views/Modal";

export default {
  name: 'PlaceModal',
  components: {Modal},
  props: {
    select_modal: Object,
  },
  data: () => ({
    place: {
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '1',
      youtubers: '',
      youtubes: [{
        videoId: '',
        channelTitle: '',
        title: '',
        url: '',
      }],
    },
    youtube: {
      videoId: '',
      channelTitle: '',
      title: '',
      url: '',
    },
    categorys: [{
      id: '',
      subCategory: '',
      included: '',
    }],
    addYoutube: false,
    showModal: false,
    showResultModal: false,
    isModify: false,
    modal: {
      header: '',
      body: '',
      footer: ''
    },
    url: '',
    result: '',
  }),
  methods: {
    initialization() {
      while (this.place.youtubes.length !== 0) {
        this.place.youtubes.pop();
      }
      this.isModify = false;
      this.place.name = '';
      this.place.area = '';
      this.place.number = '';
      this.place.subCategory = '';
      this.addYoutube = false;
      this.result = '';

      this.getCategory();
    },
    modifyYoutube(place) {
      while (this.place.youtubes.length !== 0) {
        this.place.youtubes.pop();
      }

      this.isModify = true;
      this.place.name = place.name;
      this.place.area = place.area;
      this.place.number = place.number;
      this.place.subCategory = place.subCategory;
      this.addYoutube = false;

      // 유투브 집어넣기
      if(place.youtube!=null) {
        for(let youtube of place.youtube) {
          this.youtube.url = youtube.url;
          this.youtube.title = youtube.title;
          this.youtube.channelTitle = youtube.channelTitle;
          this.youtube.videoId = youtube.videoId;
          this.putYoutube();
        }
      }
      console.log(place);
      console.log(this.place);

      this.getCategory();
    },
    putYoutube() {
      if (this.youtube.videoId.length > 0 &&
          this.youtube.channelTitle.length > 0 &&
          this.youtube.title.length > 0) {

        let addYoutube = {
          videoId: this.youtube.videoId,
          channelTitle: this.youtube.channelTitle,
          title: this.youtube.title,
          url: this.youtube.url,
        };
        this.place.youtubes.push(addYoutube);

        this.youtube.videoId = '';
        this.youtube.channelTitle = '';
        this.youtube.title = '';
      } else {
        this.result = '유투브 정보를 채워주세요';
      }
    },
    putPlace() {
      if (this.place.name.length === 0) {
        this.result = '장소의 이름을 적어주세요.';
        return;
      }
      if (this.place.subCategory.length === 0) {
        this.result = '카테고리를 적어주세요.';
        return;
      }
      if (this.place.youtubes.length === 0) {
        this.result = '유투브가 하나 이상이어야 합니다.';
        return;
      }

      return axios
          .post('http://localhost:9000/api/v1/admin/place', this.place)
          .then(() => {
            this.$emit('putPlace');
            this.$emit('close');
          })
          .catch(({error}) => {
            console.log(error);
            this.modal.body = "관리자로 다시 로그인해주십시오.";
            this.onToggleResultModal();
          })


    },
    getCategory() {
      this.url = 'http://localhost:9000/api/v1/category';
      return axios
          .get(this.url)
          .then(({data}) => {
            this.categorys = data;
          })
          .catch((error) => {
            console.log(error);
          })
    },
    onToggleResultModal() {
      if (this.showResultModal) {
        this.showResultModal = false;
      } else {
        this.showResultModal = true;
      }
    },
    popPlaceYoutubes(){
      while (this.place.youtubes.length !== 0) {
        this.place.youtubes.pop();
      }
    }
  }
}
</script>
<style>
.select-category{
  width: 180px; height: 30px; padding-left: 5px; margin-left: 10px;
}
</style>
