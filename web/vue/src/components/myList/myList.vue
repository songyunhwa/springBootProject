<template>
  <ul>
    <li v-for="place in this.places"
        v-bind:key="place" @click="selectPlace(place)">
      <table>
        <tr>
          <th>이름</th>
          <td>{{ place.name }}</td>
        </tr>
        <tr>
          <th>지역</th>
          <td>{{ place.area }}</td>
        </tr>
        <tr>
          <th>카테고리</th>
          <td>{{ place.subCategory }}</td>
        </tr>
      </table>
      <ul>
        <li v-for="review in place.reviews"
            v-bind:key="review">
          <!--<div v-if="review.fileName">
            <img
                :src="require(`C:\\Users\\82107\\Downloads\\yhwasongtest\\web\\vue\\src\\assets\\images/${review.fileName}.png`)"
                class="review-img"/>
          </div>-->

          <div>{{ review.contents }}</div>
        </li>
      </ul>
    </li>
  </ul>
  <div v-if="this.places.length == 0">
    현재 추가된 맛집이 없습니다.
  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'myList',
  components: {},
  data: () => ({
    title: '맛집리스트',
    username: '',
    places: [{
      name: '',
      area: '',
      subCategory: '',
      review: [{
        contents: '',
        fileName: ''
      }],
    }],
    select: {
      name: '',
      subCategory: '',
      review: [{
        contents: '',
        fileName: ''
      }],
    },
  }),
  state: {
    accessToken: null,
  },
  created() {
    this.url = this.resourceHost + '/myList';
    this.places.splice(0, this.places.length);

    this.getMyList();
  },
  methods: {
    selectPlace(place) {
      this.select = place;
    },
    getMyList() {
      axios.defaults.withCredentials = true;
      return axios
          .get(this.url)
          .then((data) => {

            data.data.forEach(data => {
              this.places.push(data);
            });
            console.log(this.places);
          })
          .catch(({error}) => {
            console.log(error);
          })
    },
  }
}
</script>
<style>

</style>