<template>

  <div class="map-banner">
    <Banner></Banner>
  </div>
  <Map></Map>
</template>

<script>
import Banner from "@/components/banner/Banner";
import Map from "@/components/map/Map";

export default {
  name: 'MapHome',
  components: {Banner, Map},
  data: () => ({
    username: '',
    password: '',
    url: '',
    views: '0', // 하루 접속량
  }),
  created() {
  },
  methods: {
  }
}
</script>
<style>

.map-banner {
}
</style>