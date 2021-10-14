<template>
  <div id="map" style="width:100%;height:400px;"></div>
</template>

<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=4lvfzjnn4n&callback=initMap"></script>
<script>
export default {
  name: 'Map',
  components: {},
  data: () => ({
    map : null,
  }),
  created() {
    this.initMap();
  },
  methods: {
    initMap() {
      this.map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(37.3595704, 127.105399),
        zoom: 10
      });
    }
  }
}

</script>
