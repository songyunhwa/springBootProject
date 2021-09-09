<template>
    <div>
        <ul>
            <li v-for="place in this.places"
                v-bind:key="place" @click="selectPlace(place)">
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
                </table>
                <Viewer v-if="place.review.content != null" :initialValue="place.review.content"/>
            </li>
        </ul>
    </div>
    <div v-if="this.places.length == 0">
        현재 추가된 맛집이 없습니다.
    </div>
    <div>
        <Editor></Editor>
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
                name: '',
                area: '',
                subCategory: '',
                review: [{
                    content: '',
                    fileName: ''
                }],
            }],
            select: {
                name: '',
                subCategory: '',
                review: [{
                    content: '',
                    fileName: ''
                }],
            },
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
            },
            getMyList() {
                axios.defaults.withCredentials = true;
                return axios
                    .get(this.url)
                    .then((data) => {

                        data.data.forEach(data => {
                            this.places.push(data);
                        });
                        console.log(this.places);
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