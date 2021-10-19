<!-- 왼쪽에 있는 유투브 리스트 -->
<template>
  <ul style="list-style: none;">
    <li v-for="(place, i) in this.places"
        v-bind:key="place" @click="selectPlace(place, i)">

      <input type="checkbox" name="check" @click="selectPlace(place, i)" style="margin-right: 10px; float:left;"/>
      <place_name>{{ place.name }}</place_name>
      {{ place.subCategory }}
      <div>{{ place.youtubers }}</div>

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
    select: [],
    object: [],
  }),
  created() {
    this.url = this.resourceHost + '/places';
  },
  methods: {
    setPlace(params) {
      if (Array.isArray(params)) {
        while (this.places.length > 0) {
          this.places.splice(0, this.places.length);
        }
        this.places = params;
      } else {
        this.getYoutube();
      }
    },
    getYoutube(params) {

      if (params && params.length > 0) {
        this.url = this.resourceHost + '/dessert?subCategory=' + params;
      } else {
        this.url = this.resourceHost + '/places';
      }
      var checkbox = document.getElementsByName('check');
      return axios
          .get(this.url)
          .then(({data}) => {

            this.places = data;
            this.places.forEach((place, i) => {

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

              checkbox[i].checked = false;
            })
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })

    },
    selectPlace(place, index) {
      var checkbox = document.getElementsByName('check');
      var isCancel =false;
      for (var i = 0; i < this.places.length; i++) {
        if (checkbox[i].checked === true && index == i) {
          isCancel = true;
        }
        checkbox[i].checked = false;
      }
      // 체크 취소할 때
      if(isCancel) {
        this.$emit("initCheckbox");
        return;
      }
      // 체크할 때
      checkbox[index].checked = true;
      this.$emit("selectPlace", place);
    },
    initCheckbox() {
      var checkbox = document.getElementsByName('check');
      for (var i = 0; i < this.places.length; i++) {
        checkbox[i].checked = false;
      }
    }
  }
}
</script>
<style>
place_name {
  width: 250px;
  color: #F0E5DE;
  float: left;
}

</style>