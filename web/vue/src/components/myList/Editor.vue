<template>
    <input type="file" @change="putFile" id="file" hidden>
  <quill-editor
          id="quill-editor"
          ref="quillEdits"
          v-model:value="content"
          :disabled="disabled"
          @blur="onEditorBlur($event)"
          @focus="onEditorFocus($event)"
          @ready="onEditorReady($event)"
          @change="onEditorChange($event);"
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
      this.text = text;

      //state.content = html
    },
    putList() {
      //let content = this.$refs.toastuiEditor.invoke("getHtml");
      let form = {
        placeId : this.place_id,
        content : this.content,
          text: this.text
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
    putFile(e) {
        console.log("들어옴");
        console.log(e.target);
        this.content_files = e.target.files[0];

        var formData = new FormData();
        formData.append("file", this.content_files);
        formData.append("name", this.content_files.name);

      axios.post(this.url + 'review/image', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        console.log(data);
          //this code to set your position cursor
          const range = this.$refs.quillEdit.quill.getSelection()
//this code to set image on your server to quill editor
          this.$refs.quillEdit.quill.insertEmbed(range.index , 'image', `http://your.api/${data}`)

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