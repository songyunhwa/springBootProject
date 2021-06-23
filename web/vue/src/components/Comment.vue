<template>


  <youtube-list></youtube-list>
</template>
<script>
import YoutubeList from "@/components/YoutubeList";
export default {
  name: 'Comment',
  components: {YoutubeList},
  props: {
  },
  data: () => ({
        input: ''
      }
  ),
  computed: {
  },
  methods: {

  }
}
</script>
<style>

</style>