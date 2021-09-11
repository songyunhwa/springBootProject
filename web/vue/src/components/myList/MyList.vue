<template>
    <div>
        <ul>
            <li v-for="place in this.places"
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
                    <tr>
                        {{place.content}}
                    </tr>
                    <tr>
                        <th>
                        <div class="tail" style="font-size: 15px; color:gray" @click="selectPlace(place)">변경</div>
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
        <Editor  :select_place="select" @getPlace="getMyList"></Editor>
    </div>
</template>
<script>
    import axios from 'axios'
    import Editor from "./Editor";

    export default {
        name: 'myList',
        components: {Editor},
        data: () => ({
            title: '맛집리스트',
            username: '',
            places: [{
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                fileId: '',
                fileName: ''
            }],
            select: {
                id: '',
                name: '',
                area: '',
                subCategory: '',
                content: '',
                fileId: '',
                fileName: ''
            },
            showEditor : false
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
            selectPlace(place) {
                this.select = place;
                this.showEditor=!this.showEditor;
            },
            getMyList() {
                axios.defaults.withCredentials = true;
                this.showEditor = false;
                return axios
                    .get(this.url)
                    .then((data) => {
                        this.places = data.data;
                    })
                    .catch(({error}) => {
                        console.log(error);
                    })
            },
        }
    }
</script>
<style>

</style>