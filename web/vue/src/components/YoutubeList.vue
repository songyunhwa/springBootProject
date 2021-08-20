<!-- 왼쪽에 있는 유투브 리스트 -->
<template>
  <div class="youtube-list-right">
    <Youtube :select_place="select" ref="youtube" v-on:click="selectPlace"></Youtube>
  </div>


  <div class="youtube-list-left">
    <ul style="list-style: none;">
      <li v-for="place in this.places"
          v-bind:key="place" @click="selectPlace(place.id)">

        <name>{{ place.name }}</name>
        {{ place.subCategory }}
       <!-- <hr style="border: none; margin-top:0px;">-->
        {{ place.youtubers }}

      </li>
    </ul>
  </div>
</template>
<script>
import axios from "axios";
import Youtube from "@/components/Youtube";
export default {
  name: 'YoutubeList',
  components: { Youtube },
  props: {
    msg: Object,
  },
  data: () => ({
    email: '',
    password: '',
    url: 'http://localhost:9000/api/v1/places',
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
        url: '',
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
    this.getYoutube();
  },
  methods: {
    getYoutube(params) {

      if (this.msg && this.msg.length > 0) {
        this.url = 'http://localhost:9000/api/v1/places/' + this.msg;
      } else if(params && params.length > 0 ) {
        this.url = 'http://localhost:9000/api/v1/dessert?subCategory=' + params;
      } else {
        this.url = 'http://localhost:9000/api/v1/places';
      }


      return axios
          .get(this.url)
          .then(({data}) => {
            this.places = data;

            this.places.forEach(place => {
              // 만약 정보가 없으면 - 로 바꾸기

              place.area = place.area && place.area.length > 0 ? place.area : "-";
              place.number = place.number && place.number.length > 0 ? place.number : "-";

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

              //처음 들어갈 때 - 리스트 맨 앞으로 설정
              if (this.place == null) {
                this.select = this.places[0];
                this.$refs.youtube.getReview(this.select.id);

              }
            })
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(id){
      this.$refs.youtube.getReview(id);

      this.url = 'http://localhost:9000/api/v1/place?id=' + id;
      return axios
          .get(this.url)
          .then(({data}) => {
            this.select = data[0];
            this.select.area = this.select.area && this.select.area.length > 0 ? this.select.area : "-";
            this.select.number = this.select.number && this.select.number.length > 0 ? this.select.number : "-";

            // 유투브 설정
            let titles = [];
            let youtubes = [];

            this.select.youtubers = '';
            data[0].youtube.forEach(youtube => {
              if (!titles.includes(youtube.channelTitle)) {
                titles.push(youtube.channelTitle);

                youtube.url = "https://www.youtube.com/watch?v=" + youtube.videoId;
                this.select.youtubers += "#" + youtube.channelTitle;
                youtubes.push(youtube);
                }
            })

            // 한번 더 겹치는 거 없는지 filtering
            this.select.youtube = youtubes;
          })
      }
  }
}
</script>
<style>

.youtube-list-left {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  background: #7C7877;
  position: absolute;
  width: 30%;
}

name {
  width: 150px;
  color: #F0E5DE;
}


.youtube-list-right {
  position: absolute;
  left : 30%;
  width: 49%;
  background: #D9D4CF; /*#ABD0CE #F0E5DE #D9D4CF; #7C7877;*/
  text-align: center;
  margin-right: 20px;
  font-size: 20px;
  color: #7C7877;
}


</style>