<template>
    <div>
        <ul>
            <li v-for="(place) in this.places"
                v-bind:key="place">
                <table>
                    <tr>
                        <th>이름</th>
                        <td>{{ place.name }}</td>
                    </tr>
                    <tr>
                        <th>지역</th>
                        <td>{{ place.area }}</td>
                    </tr>
                    <tr>
                        <th>카테고리</th>
                        <td>{{ place.subCategory }}</td>
                    </tr>
                    <div>{{place.text}}</div>
                    <tr>
                        <th>
                            <div class="tail" style="font-size: 15px; color:gray" @click="selectPlace(place)">변경</div>
                        </th>
                        <th>
                            <div class="tail" style="font-size: 15px; color:gray" @click="deletePlace(place)">삭제</div>
                        </th>
                    </tr>
                </table>
            </li>
        </ul>
    </div>
    <div v-if="this.places.length == 0">
        현재 추가된 맛집이 없습니다.
    </div>
    <div v-show="showEditor">
        <Editor ref="editor" @getPlace="getMyList"></Editor>
    </div>

    <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>

</template>
<script>
    import axios from 'axios'
    import Editor from "./Editor";
    import Modal from "@/modal/Modal";

    export default {
        name: 'myList',
        components: {Editor, Modal},
        data: () => ({
            title: '맛집리스트',
            username: '',
            places: [{
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                text: '',
                fileId: '',
                fileName: ''
            }],
            select: {
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                text: '',
                fileId: '',
                fileName: ''
            },
            showEditor: false,
            showModal: false,
            modal: {
                header: '',
                body: '',
                footer: ''
            }
        }),
        state: {
            accessToken: null,
        },
        created() {
            this.url = this.resourceHost + '/myList';
            this.places.splice(0, this.places.length);

            this.getMyList();
        },
        methods: {
            onToggleModal() {
                if (this.showModal) {
                    this.showModal = false;
                } else {
                    this.showModal = true;
                }
            },
            selectPlace(place) {
                this.showEditor = !this.showEditor;
                this.$refs.editor.setPlace(place);
            },
            getMyList() {
                axios.defaults.withCredentials = true;
                this.showEditor = false;
                return axios
                    .get(this.url)
                    .then((data) => {
                        while(this.places.length!==0) {
                            this.places.pop();
                        }
                        data.data.forEach((place) => {
                            this.places.push(place);
                        })
                    })
                    .catch(({error}) => {
                        console.log("error => " + error);
                    })

            },
            deletePlace(place) {
                return axios
                    .delete(this.url + '?placeId=' + place.placeId)
                    .then(() => {
                        this.modal.body = "삭제됐습니다."
                        this.onToggleModal();
                    })
                    .catch(({error}) => {
                        this.modal.body = "삭제를 실패했습니다."
                        this.onToggleModal();
                        console.log("error => " + error);
                    })
            },
        }
    }
</script>
<style>

</style>