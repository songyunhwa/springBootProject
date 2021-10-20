<template>
  <!--
    <ul>
      <li v-for="city in this.cities"
          v-bind:key="city">
        {{city}}
      </li>
    </ul>-->
  <div>
    <input class="address_input" name="address" v-model="address" placeholder="지역을 입력하세요"/>
    <button @click="getPlace">검색</button>
    <button @click="initCheckbox">모두 보기</button>
  </div>
  <div class="map-list-left">
    <youtube-list ref="youtube_list" @selectPlace="selectPlace" @initCheckBox="initCheckbox"></youtube-list>
  </div>
  <div class="map-list-right">
    <div>
      <naver-maps
          :height="height"
          :width="width"
          :mapOptions="mapOptions"
          :initLayers="initLayers"
          @load="onLoad">

        <div v-for="(mark, i) in markers" :key="mark">
          <naver-marker :lat="mark.location.lat" :lng="mark.location.lng" @click="onMarkerClicked(mark, i)"
                        @load="onMarkerLoad"/>
        </div>
      </naver-maps>
    </div>
    <div style="margin-top: 10px; color:#7C7877;">
      <table>
        <tr>
          <th>이름</th>
          <td>{{ markers[0].name }}</td>
        </tr>
        <tr>
          <th>주소</th>
          <div v-if="markers[0].location">{{ markers[0].location.address }}</div>
        </tr>

        <tr>
          <th>번호</th>
          <td>{{ markers[0].number }}</td>
        </tr>
        <tr>
          <th>카테고리</th>
          <td>{{ markers[0].subCategory }}</td>
        </tr>
        <tr>
          <th>추천수</th>
          <td>{{ markers[0].recommend }}</td>
        </tr>
        <tr>
          <th>조회수</th>
          <td>{{ markers[0].view }}</td>
        </tr>
        <tr>
          <th>관련 유투버</th>
          <td>
            <ul style="list-style: none;">
              <li v-for="youtube in markers[0].youtube" v-bind:key="youtube">
                # {{ youtube.channelTitle }}
              </li>
            </ul>
          </td>
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
      address: '서울',
      isOpen: false,
      marker_list: [],
      marker: {
        name: '',
        location: {
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
      },
      markers: [{
        name: '',
        location: {
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
        id: '',
        name: '',
      }],
    }
  },
  created() {
    this.url = this.resourceHost;
    //this.getCities();
    this.getPlace();
  },
  computed: {},
  methods: {
    onLoad(vue) {
      this.map = vue;
    },
    onMarkerClicked(mark, i) {
      this.isOpen = !this.isOpen;
      this.marker = mark;

      this.$refs.youtube_list.selectPlace(mark, i);
    },
    onMarkerLoad(vue) {
      this.marker = vue.marker;
      this.marker_list.push(this.marker);
    },
    initMarker() {
      while (this.markers.length !== 0) {
        this.markers.pop();
      }
      this.marker = {
        name: '',
        location: {
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
      };
    },
    initMapPlace() {
      while(this.places.length >0)  {
        this.places.pop();
      }
      this.places= [{
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
      }];
    },
    getPlace() {
      return axios
          .get(this.url + '/location?address=' + this.address)
          .then((data) => {
            if (data.data.length > 0) {
              this.places = data.data;
              this.$refs.youtube_list.setPlace(this.places);

              this.places.forEach(place => {
                place.youtubers="";
                place.youtube.forEach(youtube => {
                  if(place.youtubers.indexOf(youtube.channelTitle) === -1) {
                    place.youtubers += "#" + youtube.channelTitle;
                  }
                });
              })

              this.initMarker();
              this.setMarkers();
            }else {
             this.initMapPlace();
              this.initMarker();
              this.setMarkers();
            }
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    selectPlace(place) {
      this.initMarker();
      let sumlat = 0;
      let sumlng = 0;
      let sumcnt = 0;
      let marker = place;
      place.location.forEach(location => {

        marker.location.address = location.address;
        marker.location.lat = location.lat;
        marker.location.lng = location.lng;
        this.markers.push(marker);

        sumlat += location.lat;
        sumlng += location.lng;
        sumcnt++;
      })
      if (sumlat / sumcnt > 0) {
        this.map.setCenter(sumlat / sumcnt, sumlng / sumcnt);
      }
    },
    getCities() {
      return axios
          .get(this.url + '/city')
          .then((data) => {
            this.cities = data.data;
            console.log(this.cities);
          })
          .catch(({error}) => {
            console.log("error");
            console.log(error);
          })
    },
    initCheckbox() {
      this.initMarker();
      this.setMarkers();
      this.$refs.youtube_list.initCheckbox();
    },
    setMarkers() {
      let sumlat = 0;
      let sumlng = 0;
      let sumcnt = 0;
      this.places.forEach(place => {
        let marker = place;
        place.location.forEach(location => {

          marker.location.address = location.address;
          marker.location.lat = location.lat;
          marker.location.lng = location.lng;
          this.markers.push(marker);

          sumlat += location.lat;
          sumlng += location.lng;
          sumcnt++;
        })
      })
      if (sumlat / sumcnt > 0) {
        this.map.setCenter(sumlat / sumcnt, sumlng / sumcnt);
      }
    }
  },

}
</script>
<style scoped>

.map-list-left {
  list-style: none;
  text-align: left;
  line-height: 1.5;
  position: absolute;
  width: 40%;
  margin-top: 50px;
}

.map-list-right {
  position: absolute;
  right: 0px;
  width: 60%;
  text-align: center;
}


.address_input {
  float: left;
  width: 200px;
  height: 20px;
  margin-top: 10px;
  margin-left: 20px;
}

</style>