<template>
  <button @click="getPlace">검색</button>

  <div class="map-list-right">
    <div>
      <naver-maps
          :height="height"
          :width="width"
          :mapOptions="mapOptions"
          :initLayers="initLayers"
          @load="onLoad">

        <div v-for="marker in marker_list" :key="marker">
          <naver-marker :lat="marker.lat" :lng="marker.lng" @click="onMarkerClicked(markers.index)"
                        @load="onMarkerLoad"/>
          <naver-info-window :isOpen="isOpen" :marker="marker" @load="onInfoLoad">
            <h3>{{ test }}</h3>
          </naver-info-window>
        </div>

        <naver-marker :lat="mapOptions.lat" :lng="mapOptions.lng" @click="onMarkerClicked"/>
      </naver-maps>
    </div>
  </div>

  <div class="map-list-left">
    <youtube-list ref="youtube_list" @selectPlace="selectPlace"></youtube-list>
  </div>

</template>

<script>
import YoutubeList from "@/components/youtube/YoutubeList";
import axios from "axios";

export default {
  name: 'Map',
  components: {YoutubeList},
  data() {
    return {
      width: 800,
      height: 800,
      mark: null,
      count: 1,
      map: null,
      isCTT: false,
      mapOptions: {
        lat: 37,
        lng: 127,
        zoom: 10,
        zoomControl: true,
        mapTypeControl: true,
      },
      //https://github.com/Shin-JaeHeon/vue-naver-maps/issues/4 비교
      initLayers: ['BACKGROUND', 'BACKGROUND_DETAIL', 'POI_KOREAN', 'TRANSIT', 'ENGLISH', 'CHINESE', 'JAPANESE'],
      url: '',
      address: '서울특별시',
      isOpen: false,
      marker: null,
      marker_list: [],
      markers: [{
        name: '',
        location: [{
          address: '',
          lat: '',
          lng: '',
        }],
      }],
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
    }
  },
  created() {
    this.url = this.resourceHost;
    this.getPlace();
  },
  computed: {
    hello() {
      return `Hello, World! × ${this.count}`;
    }
  },
  methods: {
    onLoad(vue) {
      this.map = vue;
      },
    onMarkerClicked() {
      this.info = !this.info;
    },
    onMarkerLoad(vue) {
      this.mark = vue.marker;
      this.marker_list.push(this.marker);
    },
    onInfoLoad() {

    },
    getPlace() {
      return axios
          .get(this.url + '/location?address=' + this.address)
          .then((data) => {
            this.places = data.data;
            this.$refs.youtube_list.setPlace(this.places);

            this.places.forEach(place => {
                let p = {
                  name : place.name,
                  location : [{
                    lat: place.location.lat,
                    lng: place.location.lng,
                  }]
                }
                this.markers.push(p);
            })
            console.log(this.markers);

          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.mapOptions.lat = place.location[0].lat;
      this.mapOptions.lng = place.location[0].lng;
    }
  },
  mounted() {
    setInterval(() => this.count++, 1000);
  },

}
</script>
<style scoped>
.info-window-container {
  padding: 10px;
  width: 300px;
  height: 100px;
}

.map-list-left {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  position: absolute;
  width: 30%;
}

.map-list-right {
  position: absolute;
  left: 30%;
  width: 49%;
  text-align: center;

}

place-name {
  color: black;
}

</style>