<template>
  <input type="file" @change="putFile" id="file" hidden>
  <div id="file-byte"></div>
  <quill-editor
      id="quill-editor"
      v-model:value="content"
      :disabled="disabled"
      :options="editorOption"
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
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from 'vue3-quill'
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
    content:'',
    disabled: false,
    editorOption: {
      modules: {
        toolbar: {
          container: [
            ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
            ['blockquote', 'code-block'],

            [{ 'header': 1 }, { 'header': 2 }],               // custom button values
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
            [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
            [{ 'direction': 'rtl' }],                         // text direction

            [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

            [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
            [{ 'font': [] }],
            [{ 'align': [] }],

            ['image']
          ],
          handlers: {
            image: function() {
              document.getElementById('file').click()
            }
          }
        },
      }
    },
  }),
  created() {
    this.url = this.resourceHost;
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
    },
    putList() {
      //let content = this.$refs.toastuiEditor.invoke("getHtml");
      let form = {
        placeId : this.place_id,
        content : this.content,
        text: this.text
      }
      return axios
          .post(this.url + '/myList', form)
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
      let file = e.target.files[0];

      var formData = new FormData();
      formData.append("image", file);

      axios.post(this.url + '/review/image', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        const fileName = data.data;
        console.log(fileName);
        axios.get('http://localhost:9000/api/v1/review/image/' + fileName)
                .then((data=> {
                  var img = new Image();
                  let ext = fileName.slice(fileName.lastIndexOf(".")+1);
                  img.src =  "data:image/" + ext + ";base64," + data.data;
                  this.content += img.innerHTML;
                  console.log(img.innerHTML);
                  console.log(img.innerText);
                  console.log(data.data);

                }))

      }).catch((error) => {
        console.log(error);
      })
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
      //this.quillEditor.setup(this.content);
      //console.log("setPlace" + this.content);
    }
  }
}
</script>
<style>

</style>