<template>

  <button @click="putList">저장하기</button>
  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
  {{ this.files }}
</template>
<script>
import axios from 'axios'
export default {
  name: 'Editor',
  components: {
  },
  props: {
    select_place: Object,
  },
  data: () => ({
    files: '',
    showModal: false,
    modal: {
      header: '',
      body: '',
      footer: ''
    },
    content_files : new Array(),
    content:''
  }),
  created() {
    this.url = this.resourceHost + '/myList';
  },
  methods: {
    putList() {
      //let content = this.$refs.toastuiEditor.invoke("getHtml");
      let content = '';
      return axios
          .post(this.url + '?placeId='+this.select_place.id + '&content=' + content)
          .then(() => {
            this.modal.body = '등록했습니다.';
            this.onToggleModal();
          })
          .catch(({error}) => {
            this.modal.body = '등록하지 못했습니다.';
            this.onToggleModal();
            console.log(error);
          })
    },
    putFile() {
      let formData = new FormData();

      if(this.content_files.length === 0) {
        return;
      }

      this.content_files.forEach(file => {
        formData.append("image", file);
      })

      axios.post(this.url + 'review/image', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        console.log(data.data);
      }).catch((error) => {
        console.log(error);
      })
    },
    inputFile(e) {
      let files = e.target.value;
      let filesArr = Array.prototype.slice.call(files);

      this.content_files.slice(0, this.content_files.length);
      this.files = '';

      filesArr.forEach(function(file , i) {
        this.content_files.push(file);
        console.log(file);
        this.file = '<div onclick="this.deleteFile(\''+i+'\')">' + file.name +'</div>'
      })

    },
    deleteFile(i) {
      this.content_files.slice(i, 1);
    },
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
      } else {
        this.showModal = true;
      }
    }
  }
}
</script>
<style>

</style>