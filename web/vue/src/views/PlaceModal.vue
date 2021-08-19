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
                <div><input v-model="place.name" style="margin: 5px 10px;padding: 5px 10px;"></div>
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
                <div><input v-model="place.subCategory" style="margin: 5px 10px;padding: 5px 10px;"></div>
              </div>
              <div>
                <ul>
                  <li v-for="(youtube, index) in place.youtube" v-bind:key="youtube">
                    {{ youtube.title }}
                    <button @click="place.youtube.splice(index, 1);">삭제</button>
                  </li>
                </ul>
              </div>

              <div>
                <button @click="addYoutube?addYoutube=false:addYoutube=true;">유투브 추가</button>
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
                <div>
                  <button class="modal-default-button" @click="putYoutube">
                    추가
                  </button>
                </div>
              </div>

              <div class="modal-default-button">
                <button @click="this.putPlace">
                  확인
                </button>
                <button @click="this.$emit('close')">
                  취소
                </button>
              </div>
              <div>
                {{result}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'PlaceModal',
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
    addYoutube: false,
    showModal: false,
    url: '',
    result: '결과',
  }),
  methods: {
    putYoutube(){
      if(this.youtube.videoId!=null&&this.youtube.channelTitle!=null&&this.youtube.title!=null&&this.youtube.url!=null) {
        this.place.youtubes.push(this.youtube);
        this.youtube.videoId = null;
        this.youtube.channelTitle = null;
        this.youtube.title = null;
        this.youtube.url = null;
      }else {
        this.result = '칸을 채워주세요';
      }
    },
    putPlace() {
      this.url = 'http://localhost:9000/api/v1/place';
      console.log(this.place);
      return axios
          .post(this.url ,this.place)
          .then(({data}) => {
            console.log(data);
            this.$emit('close');
          })
          .catch(({error}) => {
            console.log("error");
            this.result = error.response.data;
          })


    },
  }
}
</script>
<style>
</style>
