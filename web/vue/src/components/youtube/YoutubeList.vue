<!-- 왼쪽에 있는 유투브 리스트 -->
<template>
  <ul style="list-style: none;">
    <li v-for="place in this.places"
        v-bind:key="place" @click="selectPlace(place)">

      <place-name>{{ place.name }}</place-name>
      {{ place.subCategory }}
      <!-- <hr style="border: none; margin-top:0px;">-->
      {{ place.youtubers }}

    </li>
  </ul>
</template>
<script>
import axios from "axios";

export default {
  name: 'YoutubeList',
  components: {},
  props: {
    msg: Object,
  },
  data: () => ({
    username: '',
    password: '',
    url: '',
    places: [{
      name: '',
      location: [{
        address: '',
        lat: '',
        lng: '',
      }],
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
    object: [],
  }),
  created() {
    this.url = this.resourceHost + '/places';
    this.getYoutube();
  },
  methods: {
    getYoutube(params) {

      if (this.msg && this.msg.length > 0) {
        this.url = this.resourceHost + '/place/' + this.msg;
      } else if (params && params.length > 0) {
        this.url = this.resourceHost + '/dessert?subCategory=' + params;
      } else {
        this.url = this.resourceHost + '/places';
      }

      return axios
          .get(this.url)
          .then(({data}) => {
            if (this.places == null) {
              this.places = data;
              this.places.forEach(place => {

                // 유투브 이름과 url 설정
                let titles = []; // 유투버

                place.youtubers = '';
                place.youtube.forEach(youtube => {
                  if (!titles.includes(youtube.channelTitle)) {
                    titles.push(youtube.channelTitle);
                    place.youtubers += "#" + youtube.channelTitle;
                  }
                  youtube.url = "https://www.youtube.com/watch?v=" + youtube.videoId;

                })
              })
            }
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.$emit("selectPlace", place);
    },
    setPlace(places) {
      this.places = places;
    }
  }
}
</script>
<style>

name {
  width: 150px;
  color: #F0E5DE;
}


</style>