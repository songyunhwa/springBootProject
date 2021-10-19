<!-- 추천 리스트 창 -->
<template>
  <ul class="recommend">
    <div>추천 리스트</div>
    <li v-for="place in this.places"
        v-bind:key="place" @click="selectPlace(place)"
        style="font-size: 15px;   color: rosybrown;">
        {{ place.name }}
        {{ place.subCategory }}
    </li>
  </ul>

</template>
<script>
import axios from "axios";
export default {
  name: 'Recommend',
  components: {},
  data: () => ({
    url: '', //
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
  }),
  created() {
    this.url = this.resourceHost + '/recommend';
    this.getRecommend();
  },
  methods: {
    getRecommend() {
      if(this.username===undefined){
        this.username = 'test1';
      }

      return axios
          .get(this.url)
          .then(({data}) => {
            this.places = data;
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      console.log("들어옴");
      this.select = place;
    },
    selectDessert(params){
      this.getYoutube(params);
    }
  }
}
</script>
<style>
.recommend {
  list-style: none;
  font-size: 15px;
  align-content: center;
}
</style>