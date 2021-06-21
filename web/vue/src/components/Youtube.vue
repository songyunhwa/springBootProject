<template>
  <div class="youtube">
    <h3>{{ select_place.name }}</h3>
    <div>지역 : {{ select_place.area }}</div>
    <div>번호 : {{ select_place.number }}</div>
    <div>카테고리 : {{ select_place.subCategory }}</div>
    <div>추천수 : {{ select_place.recommend }}</div>
    <div>조회수 : {{ select_place.view }}</div>
    <div>관련 유투버 : {{ select_place.youtube }}</div>
  </div>


</template>
<script>
export default {
  name: 'Youtube',
  props: {
    select_place: Object
  },
  data: () => ({
    email: '',
    password: '',
    place: {
      name: '',
      area: '',
      number: '',
      subCategory: '',
      recommend: '',
      view: '',
      youtube: ''
    },
  }),
  computed: {
    placeName() {
      return this.$route.params.id;
    }
  },
  methods: {}
}
</script>
<style>
 .youtube{
   width: 50px;
   height: 100%;
 }
</style>