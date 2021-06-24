<template>
  <ul style="list-style: none;">
    <div>추천 리스트</div>
    <li v-for="place in this.places"
        v-bind:key="place" @click="selectPlace(place)">
      <div class="recommend">
        {{ place.name }}
        {{ place.subCategory }}
      </div>
    </li>
  </ul>

</template>
<script>
import axios from "axios";

export default {
  name: 'Recommend',
  components: {},
  data: () => ({
    url: 'http://localhost:9000/api/v1/recommend', //
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
    getYoutube() {
      return axios
          .get(this.url + "?userName=" + this.email)
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
  }
}
</script>
<style>
.recommend {
  background: beige;
  font-size: 15px;
  align-content: center;
}
</style>