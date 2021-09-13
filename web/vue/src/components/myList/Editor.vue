<template>
  <quill-editor
          v-model:value="content"
          :disabled="disabled"
          @blur="onEditorBlur($event)"
          @focus="onEditorFocus($event)"
          @ready="onEditorReady($event)"
          @change="onEditorChange($event)"
  />
  <button @click="putList">저장하기</button>
  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
  {{ this.files }}
</template>
<script>
import axios from 'axios'
import { quillEditor } from 'vue3-quill'
//import customQuillModule from 'customQuillModule'
//Quill.register('modules/customQuillModule', customQuillModule)

export default {
  name: 'Editor',
  components: {
    quillEditor
  },
  props: {
  },
  data: () => ({
    files: '',
    showModal: false,
    modal: {
      header: '',
      body: '',
      footer: ''
    },
    place_id: '',
    content_files : new Array(),
    content:'',
    disabled: false,
  }),
  created() {
    this.url = this.resourceHost + '/myList';
  },
  methods: {
    onEditorBlur (quill) {
      console.log('editor blur!', quill)
    },
    onEditorFocus(quill) {
      console.log('editor focus!', quill)
    },
    onEditorReady (quill) {
      console.log('editor ready!', quill)
    },
    onEditorChange ({ quill, html, text }) {
      console.log('editor change!', quill, html, text)
      this.content = html;
      //state.content = html
    },
    putList() {
      //let content = this.$refs.toastuiEditor.invoke("getHtml");
      let form = {
        placeId : this.place_id,
        content : this.content
      }
      return axios
          .post(this.url, form)
          .then(() => {
            this.modal.body = '등록했습니다.';
            this.onToggleModal();
            this.$emit('getPlace');
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
    },
    setPlace(place){
      this.place_id=place.id;
      this.content=place.content
      this.quillEditor.setup(this.content);
      console.log("setPlace" + this.content);
    }
  }
}
</script>
<style>

</style>