<!-- 찜한 창 -->
<template>
  <div class="youtube-list-right">
    <Youtube :select_place="select" @putWished="deletePlace"></Youtube>
  </div>
  <div class="youtube-list-left">
    <ul>
      <li v-for="place in this.places"
          v-bind:key="place" @click="selectPlace(place)">

        <name>{{ place.name }}</name>
        {{ place.subCategory }}
        <hr style="border: none; margin-top:0px;">
        {{ place.youtubers }}
      </li>
    </ul>
    <button  class="wished-tail" @click="prevPage" :disabled="this.currentPage.page === 0">이전</button>
    <button class="wished-tail"  @click="nextPage" :disabled="this.currentPage.page === this.currentPage.totalPages">다음</button>
  </div>
</template>
<script>
import axios from "axios";
import Youtube from "@/components/youtube/Youtube";

export default {
  name: 'WishedList',
  components: {Youtube},
  data: () => ({
    username: '',
    password: '',
    url: '',
    places: [{
      name: '',
      area: {
        address: '',
        lat: '',
        lng: '',
      },
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtubers: '',
      youtube: [{
        videoId: '',
        channelTitle: '',
        title: '',
        url: '',
      }],
    }],
    select: {
      id: '',
      name: '',
      area: {
        address: '',
        lat: '',
        lng: '',
      },
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: Object,
    },
    object: [],
    currentPage: {
      page: 0,
      size: 3,
      totalElements: 0,
      totalPages: 0,
    },
  }),
  created() {
    this.username = this.$cookies.get('username');
    this.url = this.resourceHost + '/wished';
    this.getWished();

  },
  methods: {
    prevPage() {
      this.currentPage.page -= 1;
      this.getWished();
    },
    nextPage() {
      this.currentPage.page += 1;
      this.getWished();

    },
    getWished() {

      return axios
          .get(this.url + '?page=' + this.currentPage.page + '&size=' + this.currentPage.size + '&sort=id,DESC')
          .then(({data}) => {

            this.currentPage.totalElements = data.page.totalElements;
            this.currentPage.totalPages = data.page.totalPages-1; // 0부터 시작하기 때문에 totalPage -1

            this.places = data.placeModels;
            this.places.forEach(place => {
              // 유투브 설정
              let titles = [];
              let youtubes = [];

              place.youtubers = '';
              place.youtube.forEach(youtube => {
                if (!titles.includes(youtube.channelTitle)) {
                  titles.push(youtube.channelTitle);

                  youtube.url = "https://www.youtube.com/watch?v=" + youtube.videoId;
                  place.youtubers += "#" + youtube.channelTitle;
                  youtubes.push(youtube);
                }

                // 한번 더 겹치는 거 없는지 filtering
                place.youtube = youtubes;
              })
            })
            //처음 들어갈 때 - 리스트 맨 앞으로 설정
            if (this.places.length > 0) {
              this.select = this.places[0];
              this.$refs.youtube.getReview(this.select.id);
            }
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.select = place;
    },
    deletePlace(place) {
      console.log("deletePlace", place);
      for (let i = 0; i < this.places.length; i++) {
        if (this.places[i].name === place.name && this.places[i].subCategory === place.subCategory) {
          this.places.splice(i, 1);
          return;
        }
      }
    },
  }
}
</script>
<style>
li {
  font-size: 20px;
  color: #ABD0CE;
  border-bottom: 1px solid #DFDFDF;
  margin-top: 10px;
  margin-bottom: 10px;
}

.wished-tail {
  font-size: 17px;
  background-color: #7C7877;
  color: lightgray;
  float: right;
  margin-right: 20px;
  border: 0px;
  outline : 0px;
}


wished-tail:disabled {
  color: #7C7877;
}
</style>