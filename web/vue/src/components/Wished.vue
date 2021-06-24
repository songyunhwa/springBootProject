<template>
  <div class="youtube-list-right">
    <Youtube :select_place="select"></Youtube>
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
  </div>
</template>
<script>
import axios from "axios";
import Youtube from "@/components/Youtube";
export default {
  name: 'WishedList',
  components: {Youtube},
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/wished', //
    places: [{
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtubers: '',
      youtube: [{
        videoId: '',
        channelTitle: '',
        title: '',
        url : '',
      }],
    }],
    select: {
      id: '',
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: Object,
    },
    object: [],
  }),
  created() {
    this.email = this.$cookies.get('email');
    this.getYoutube();
  },
  methods: {
    getYoutube() {
      return axios
          .get(this.url+"?userName="+this.email)
          .then(({data}) => {
            this.places = data;


            this.places.forEach(place => {
              // 만약 정보가 없으면 - 로 바꾸기

              place.area =  place.area&&place.area.length>0?   place.area : "-";
              place.number = place.number&&place.number.length>0 ? place.number : "-";

              // 유투브 설정
              let titles = [];
              let youtubes = [];

              place.youtubers = '';
              place.youtube.forEach(youtube => {
                if (!titles.includes(youtube.channelTitle)) {
                  titles.push(youtube.channelTitle);

                  youtube.url = "https://www.youtube.com/watch?v="+youtube.videoId;
                  place.youtubers += "#" + youtube.channelTitle;
                  youtubes.push(youtube);
                }

                // 한번 더 겹치는 거 없는지 filtering
                place.youtube = youtubes;
              })

              //처음 들어갈 때 - 리스트 맨 앞으로 설정
              if(this.place == null) {
                this.select = this.places[0];
              }
            })
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.select = place;
    }
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

</style>