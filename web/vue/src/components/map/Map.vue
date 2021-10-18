<template>
  <!--
  <div>
    <ul style="list-style: none;">
      <li v-for="city in this.cities"
          v-bind:key="city"> @click="getPlace(city.name)"
        {{city}}
      </li>
    </ul>
  </div>-->
  <div>
    <input style="float:left; width: 200px; height:30px;" name="address" v-model="address" placeholder="지역을 입력하세요"/>
    <button @click="getPlace">검색</button>
  </div>
  <div class="map-list-left" style="margin-top: 50px;">
    <youtube-list ref="youtube_list" @selectPlace="selectPlace"></youtube-list>
  </div>
  <div class="map-list-mid">
    <div>
      <naver-maps
          :height="height"
          :width="width"
          :mapOptions="mapOptions"
          :initLayers="initLayers"
          @load="onLoad">

        <div v-for="mark in markers" :key="mark">
          <naver-marker :lat="mark.location.lat" :lng="mark.location.lng" @click="onMarkerClicked(mark)"
                        @load="onMarkerLoad"/>
        </div>
      </naver-maps>
    </div>
    <div style="margin-top: 10px;">
      <table>
        <tr>
          <th>이름</th>
          <td>{{ marker.name }}</td>
        </tr>
        <tr>
          <th>주소</th>
          <td>{{ marker.location.address }}</td>
        </tr>
      </table>
    </div>
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
      height: 500,
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
      initLayers: ['BACKGROUND', 'BACKGROUND_DETAIL', 'POI_KOREAN', 'TRANSIT', 'ENGLISH', 'CHINESE', 'JAPANESE'],
      url: '',
      address: '서울특별시',
      isOpen: false,
      markers: [{
        name: '',
        location: {
          address: '',
          lat: '',
          lng: '',
        }
      }],
      marker_list: [],
      marker: {
        name: '',
        location: {
          address: '',
          lat: '',
          lng: '',
        }
      },
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
      cities: [{
        0:'서울특별시'
      }],
    }
  },
  created() {
    this.url = this.resourceHost;
    //this.getCities();
    //this.getPlace();
  },
  computed: {},
  methods: {
    onLoad(vue) {
      this.map = vue;
    },
    onMarkerClicked(mark) {
      this.isOpen = !this.isOpen;
      this.marker = mark;
      console.log(this.marker);

    },
    onMarkerLoad(vue) {
      this.marker = vue.marker;
      this.marker_list.push(this.marker);
    },
    initMarker() {
      while(this.markers.length !== 0){
        this.markers.pop();
      }
      this.marker = {
        name: '',
        location: {
          address: '',
          lat: '',
          lng: '',
        }
      };
    },
    getPlace(city) {
      return axios
          .get(this.url + '/location?address=' + city)
          .then((data) => {
            this.initMarker();

            this.places = data.data;
            this.$refs.youtube_list.setPlace(this.places);

            let sumlat = 0;
            let sumlng = 0;
            let sumcnt = 0;
            this.places.forEach(place => {
              place.location.forEach(location => {
                let p = {
                  name: place.name,
                  location: {
                    address: location.address,
                    lat: location.lat,
                    lng: location.lng,
                  }
                }
                this.markers.push(p);

                sumlat += location.lat;
                sumlng += location.lng;
                sumcnt++;
              })
            })
            this.mapOptions.lat = sumlat / sumcnt;
            this.mapOptions.lng = sumlng / sumcnt;
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    getCities() {
      return axios
          .get(this.url + '/city')
          .then((data) => {
            this.cities=data.data;
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace() {
      /*
      this.mapOptions.lat = place.location[0].lat;
      this.mapOptions.lng = place.location[0].lng;*/
    }
  },

}
</script>
<style scoped>

.map-list-left {
  list-style: none;
  border-collapse: separate;
  border-spacing: 1px;
  text-align: left;
  line-height: 1.5;
  position: absolute;
  width: 30%;
}

.map-list-mid {
  position: absolute;
  left: 30%;
  width: 50%;
  text-align: center;

}

place-name {
  color: black;
}

</style>