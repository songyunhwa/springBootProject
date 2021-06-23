<template>
  <div class="right">
    <Youtube :select_place="select"></Youtube>
  </div>
  <div class="left">
    <ul class="type04">
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
    url: 'http://localhost:9000/api/v1/wished',
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

youtube {
  list-style: none;
  font-size: 10px;
}

ul.type04 {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  margin-top: 0px;
}

name {
  width: 150px;
  color: #F0E5DE;
}

li {
  padding: 5px 0px 5px 5px;
  margin-bottom: 10px;
  border-bottom: 1px solid #efefef;
  font-size: 20px;
  color: #ABD0CE;
}

.left {
  background: #7C7877;
  width: 50%;
}

.right {
  right: -20px;
  width: 50%;;
  position: absolute;
  background: #D9D4CF; /*#ABD0CE #F0E5DE #D9D4CF; #7C7877;*/
  text-align: center;
  margin-right: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  color: #7C7877;
}
</style>