<!-- 왼쪽에 있는 유투브 리스트 -->
<template>
  <ul style="list-style: none;">
    <li v-for="(place, i) in this.places" :class="{'map-youtube-list': isMap === true}"
        v-bind:key="place" @click="selectPlace(place, i)">

      <input type="checkbox" name="check" @click="selectPlace(place, i)" style="margin-right: 10px; float:left;"/>
      <place_name>{{ place.name }}</place_name>
      {{ place.subCategory }}
      <div class="youtuber-name">{{ place.youtubers }}</div>

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
    id: '',
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
    isMap: '',
  }),
  created() {
    this.url = this.resourceHost + '/places';
  },
  methods: {
    setPlace(params) {
      if (Array.isArray(params)) {
        this.isMap = true;  //Map 화면일때

        while (this.places.length > 0) {
          this.places.splice(0, this.places.length);
        }
        this.places = params;
      } else {
        this.isMap = false; // 홈화면일때

        this.getYoutubes();
      }
    },
    getYoutubes(params) {

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
              /// 유투브 이름 설정
              place.youtubers = "";
              place.youtube.forEach(youtube => {
                if (place.youtubers.indexOf(youtube.channelTitle) === -1) {
                  place.youtubers += "#" + youtube.channelTitle;
                }
              });
              if (checkbox[i] != null) {
                checkbox[i].checked = false;
              }
            })
          })
          .catch((error) => {
            console.log("error");
            console.log(error);
          })

    },
    selectPlace(place, index) {
      var checkbox = document.getElementsByName('check');
      var isCancel = false;
      for (var i = 0; i < this.places.length; i++) {
        if (checkbox[i].checked === true && index == i) {
          isCancel = true;
        }
        checkbox[i].checked = false;
      }
      // 체크 취소할 때
      if (isCancel) {
        this.$emit("initCheckbox");
        return;
      }
      // 체크할 때
      checkbox[index].checked = true;
      this.$emit("selectPlace", place);
    },
    getYoutube(placeId) {
      return axios
          .get(this.resourceHost + '/place?id=' + placeId)
          .then((data) => {
            for(let i=0; i<this.places.length; i++){
              if(this.places[i].id === data.data[0].id) {
                this.places[i] = data.data[0];

                // 유투브 이름 설정
                this.places[i].youtubers = "";
                this.places[i].youtube.forEach((youtube)=>{
                  if (this.places[i].youtubers.indexOf(youtube.channelTitle) === -1) {
                    this.places[i].youtubers += "#" + youtube.channelTitle;
                  }
                })
                this.$emit("selectPlace", this.places[i]);
                break;
              }
            }
          })
          .catch(({error}) => {
            console.log(error);
          })
    },
    initCheckbox() {
      var checkbox = document.getElementsByName('check');
      for (var i = 0; i < this.places.length; i++) {
        if (checkbox[i].checked)
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

.map-youtube-list {
  border: 1px #DADADA solid;
  padding: 5px 10px;
  width: 200px;
  height: 150px;
  float: left;
  margin: 10px 10px 10px 10px;
}

.youtuber-name {
  font-size: 15px;
}

</style>