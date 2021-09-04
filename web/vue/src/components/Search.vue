<!-- 검색에 쓰이는 디저트 종류 리스트 화면 -->
<template>
  <ul style="list-style: none; float:left;">
    <li v-for="dessert in this.desserts"
        v-bind:key="dessert" @click="getDessert">
      <div v-on:click="selectDessert(dessert.subCategory)">{{ dessert.included }}</div>
    </li>
  </ul>
</template>
<script>
import axios from "axios";

export default {
  name: 'Search',
  props: {
    select_place: Object
  },
  data: () => ({
    input: '',
    url:  '',
    desserts: [{
      id: '',
      included: '',
      subCategory: '',
    }]
  }),
  created(){
    this.url = this.resourceHost;
  },
  computed: {},
  methods: {
    getDessert() {
      return axios
          .get(
              this.url + '/dessert/list'
          )
          .then((data) => {
            this.desserts = data;
          })
          .catch(({error}) => {
            console.log(error);
          });
    },
    selectDessert(subCategory) {
      this.$emit('click', subCategory);
    },

  }
}
</script>