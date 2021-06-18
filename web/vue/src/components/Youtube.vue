<template>
  <div @setInput="setInput">
  </div>
</template>
<script>
export default {
  name: 'Youtube',
  data: () => ({
    email: '',
    password: '',
    place: {
      name: '',
      area: '',
      number : '',
      subCategory: '',
      recommend:'',
      view:'',
      youtube: ''
    },
  }),
  methods: {
    setInput(place){
      this.place = place;
      console.log(this.place);
    }
  }
}
</script>
<style>

</style>