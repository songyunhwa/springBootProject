<template>
  <div v-show="this.showModal">
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">

            <div class="modal-header">
              <slot name="header">
                <h3>장소 추가하기</h3>
              </slot>
            </div>
            <div class="modal-body">
                <table>
                  <thead>
                  <tr>
                    <th>장소이름</th>
                    <td><input v-model="this.place.name" style="margin: 5px 10px;padding: 5px 10px;"></td>
                  </tr>
                  <tr>
                    <th>지역</th>
                    <td><input v-model="this.place.area" style="margin: 5px 10px;padding: 5px 10px;"></td>
                  </tr>
                  <tr>
                    <th>번호</th>
                    <td><input v-model="this.place.number" style="margin: 5px 10px;padding: 5px 10px;"></td>
                  </tr>
                  <tr>
                    <th>카테고리</th>
                    <td><input v-model="this.place.category" style="margin: 5px 10px;padding: 5px 10px;"></td>
                  </tr>
                  </thead>
                  <tbody>

                  </tbody>
                </table>
              <div class="modal-default-button">
                <button class="modal-default-button" @click="$emit('close' , this.place);">
                  확인
                </button>
              </div>

            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Modal',
  props: {
    select_modal: Object,
  },
  data : () => ({
    place: {
      name: '',
      area: '',
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
  }),
  methods:{
  }
}
</script>
<style>
</style>
