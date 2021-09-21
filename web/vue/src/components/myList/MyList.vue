<template>
    <div v-if="!showEditor">
        <ul>
            <li v-for="(place) in this.places"
                v-bind:key="place">
                <table class="list-table">
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
                    <tr v-if="place.text!=null">{{place.text.substring(0,50)}}</tr>
                    <tr>
                        <th>
                            <div class="list-tail" @click="selectPlace(place)">변경</div>
                        </th>
                        <th>
                            <div class="list-tail" @click="deletePlace(place)">삭제</div>
                        </th>
                    </tr>
                </table>
            </li>
        </ul>
    </div>

    <div v-show="showEditor">

        <table class="list-table">
            <tr>
                <th>이름</th>
                <td>{{ select.name }}</td>
            </tr>
            <tr>
                <th>지역</th>
                <td>{{ select.area }}</td>
            </tr>
            <tr>
                <th>카테고리</th>
                <td>{{ select.subCategory }}</td>
            </tr>
            <ul>
                <li v-for="file in select.file"
                    v-bind:key="file"
                    style="float:left; margin-left:10px;">
                    <img
                            :src="require(`C:\\Users\\82107\\Downloads\\yhwasongtest\\web\\vue\\src\\assets\\images/${file.fileName}`)"
                            class="review-img"/>
                    <!--<img src='../../assets/images/${{file.fileName}}'>-->
                </li>
            </ul>
        </table>

        <Editor ref="editor" @getPlace="getMyList" @close="showEditor=false"></Editor>
    </div>

    <div v-if="this.places.length == 0">
        현재 추가된 맛집이 없습니다.
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
                file: [{
                    fileId: '',
                    fileName: ''
                }],
            }],
            select: {
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                text: '',
                file: [{
                    fileId: '',
                    fileName: ''
                }],
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
                this.select = place;
                this.$refs.editor.setPlace(place);
            },
            getMyList() {
                axios.defaults.withCredentials = true;
                this.showEditor = false;
                return axios
                    .get(this.url)
                    .then((data) => {
                        while (this.places.length !== 0) {
                            this.places.pop();
                        }
                        data.data.forEach((place) => {
                            this.places.push(place);
                        })

                        this.select = this.places[0];
                    })
                    .catch(({error}) => {
                        console.log("error => " + error);
                    })

            },
            deletePlace(place) {
                return axios
                    .delete(this.url + '?placeId=' + place.id)
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
    .list-tail {
        font-size: 15px;
        color: gray;
    }

    .list-table {
        color: #ABD0CE;
        list-style-type: none;
    }

</style>