<template>
    <input type="file" @change="uploadFile" id="file" hidden>
    <div id="file-byte"></div>
    <quill-editor
            id="quill-editor"
            v-model:value="place.content"
            :disabled="disabled"
            :options="editorOption"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @ready="onEditorReady($event)"
            @change="onEditorChange($event);"
    />
    <ul>
        <li v-for="file in file_name" v-bind:key="file">
            <div>{{file}}
                <div style="float: right; margin-right: 100px;" @click="removeFile(file)">x</div>
            </div>
        </li>
        {{file_name.size}}
    </ul>
    <!--<button class="review-register" @click="uploadFile">등록</button>
    <input name="image" id="image" type="file"/>-->

    <button @click="putList">저장하기</button>
    <button @click="$emit('close')">취소</button>
    <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>

</template>
<script>
    import axios from 'axios'
    import "quill/dist/quill.core.css";
    import "quill/dist/quill.snow.css";
    import "quill/dist/quill.bubble.css";
    import {quillEditor} from 'vue3-quill'

    export default {
        name: 'Editor',
        components: {
            quillEditor
        },
        props: {},
        data: () => ({
            files: '',
            showModal: false,
            modal: {
                header: '',
                body: '',
                footer: ''
            },
            place: {
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                text: '',
                file: [{
                    fileName: ''
                }],
            },
            file_id: [],
            file_name: [],
            disabled: false,
            editorOption: {
                modules: {
                    toolbar: {
                        container: [
                            ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                            ['blockquote', 'code-block'],

                            [{'header': 1}, {'header': 2}],               // custom button values
                            [{'list': 'ordered'}, {'list': 'bullet'}],
                            [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
                            [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                            [{'direction': 'rtl'}],                         // text direction

                            [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
                            [{'header': [1, 2, 3, 4, 5, 6, false]}],

                            [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                            [{'font': []}],
                            [{'align': []}],

                            ['image']
                        ],
                        handlers: {
                            image: function () {
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
            onEditorBlur(quill) {
                console.log('editor blur!', quill)
            },
            onEditorFocus(quill) {
                console.log('editor focus!', quill)
            },
            onEditorReady(quill) {
                console.log('editor ready!', quill)
            },
            onEditorChange({quill, html, text}) {
                console.log('editor change!', quill, html, text)
                this.place.content = html;
                this.place.text = text;
            },
            uploadFile(e) {
                const formData = new FormData();
                console.log(e.target);
                formData.append("image", e.target.files[0]);
                axios.post(this.url + '/review/image', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((data) => {
                    console.log(data.data);
                    this.file_id.push(data.data.fileId);
                    this.file_name.push(data.data.fileName);
                    console.log("upload " + this.file_id);
                }).catch((error) => {
                    console.log(error);
                })


            },
            putList() {
                //let content = this.$refs.toastuiEditor.invoke("getHtml");
                let form = {
                    placeId: this.place.id,
                    content: this.place.content,
                    text: this.place.text,
                    fileId: this.file_id,
                }
                console.log(form);
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
                return new Promise((resolve, reject) => {
                    let reader = new FileReader()
                    // convert the file to base64 text
                    reader.readAsDataURL(file)
                    // on reader load somthing...
                    reader.onload = (event) => {
                        this.content += '<img src="' + event.target.result + '">';
                    }
                    reader.onerror = (error) => {
                        reject(error)
                    }
                })
            },
            removeFile(file) {
                for (let i = 0; i < this.file_name.length; i++) {

                    if (this.file_name[i] == file) {
                        if (i === 0) {
                            this.file_name.shift();
                            this.file_id.shift();
                        } else {
                            this.file_name.splice(i);
                            this.file_id.splice(i);
                        }
                    }
                }
            },
            onToggleModal() {
                if (this.showModal) {
                    this.showModal = false;
                } else {
                    this.showModal = true;
                }
            }
            ,
            setPlace(place) {
                this.place = place;

                while (this.file_name.length != 0) {
                    this.file_name.pop();
                    this.file_id.pop();
                }
                if (place.file.length > 0) {
                    place.file.forEach(file => {
                        this.file_name.push(file.fileName);
                        this.file_id.push(file.fileId);
                    });
                }

                //this.quillEditor.setup(this.content);
                //console.log("setPlace" + this.content);
            }
        }
    }
</script>
<style>

</style>