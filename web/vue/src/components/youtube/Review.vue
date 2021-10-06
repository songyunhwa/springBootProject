<!-- 댓글창 -->
<template>
    <div style="margin-left: 50px;">
        <table style="margin-left: 50px;">
            <thead>
            <tr>
                <th>댓글</th>
            </tr>
            </thead>
            <tbody>
            <div>
                <td><textarea type="text" rows="5" style="width:500px;" v-model="this.input"/></td>
            </div>
            <button class="review-register" @click="putReview">등록</button>
            <input name="image" id="image" type="file" @change="uploadFile"/>
            </tbody>
        </table>

        <ul>
            <li v-for="review in reviews" v-bind:key="review" class="review">
                <table>
                    <tbody>
                    <td>
                        <div>
                            {{ review.userName }} {{ review.contents }}
                        </div>
                        <div v-if="review.fileName">
                            <img
                                    :src="image_path+ review.fileName"
                                    class="review-img"/>
                        </div>
                    </td>
                    <div v-show="!review.modify">
                        <button @click="onToggle(review)">수정</button>
                        <button @click="deleteReview(review.id, review)">삭제</button>
                    </div>
                    </tbody>
                </table>


                <div v-if="review.modify">
                    <div>
                        <textarea type="text" rows="5" v-model="review.input"/>
                    </div>
                    <div style="margin-left: 0px;">
                        <button @click="modifyReview(review.id, review);onToggle(review);">수정</button>
                        <button @click="deleteReview(review.id, review)">삭제</button>
                    </div>
                </div>

            </li>
        </ul>
    </div>

    <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
</template>
<script>
    import axios from "axios";
    import Modal from "@/modal/Modal";

    export default {
        name: 'Review',
        components: {
            Modal
        },
        props: {
            select_place: Object
        },
        data: () => ({
                input: '',
                url: '',
                image_path: '', //'@/assets/e1e35ffd2e6fa5d6b0d5bf9f53a6182b.png',
                fileId: '',
                reviews: [{
                    id: '',
                    placeName: '',
                    placeId: '',
                    userName: '',
                    userId: '',
                    contents: '',
                    star: '',
                    prevId: '',
                    fileId: '',
                    fileName: '',

                    // 아래부터는 사용자 입력
                    modify: false,
                    input: '',
                    input_star: '',
                }],
                showModal: false,
                modal: {
                    header: '',
                    body: '',
                    footer: ''
                }
            }
        ),

        created() {
            this.onReviewTimeout();
            this.url = this.resourceHost + '/';
            this.image_path = this.imagePath;
            this.imageSrc = this.resourceImg;
        },
        methods: {
            getReview(id) {
                return axios
                    .get(this.url + 'review?id=' + id)
                    .then((reviews) => {
                        this.reviews = reviews.data;
                    })
                    .catch(({error}) => {
                        console.log(error);
                    });
            },
            putReview() {
                var params = {
                    id: '',
                    placeName: this.select_place.name,
                    placeId: this.select_place.id,
                    userName: this.$cookies.get('username'),
                    userId: '',
                    contents: this.input,
                    star: '',
                    prevId: '',
                    fileId: this.fileId,
                };
              console.log("this.fileId => " + this.fileId);
                return axios
                    .post(
                        this.url + 'review',
                        params
                    )
                    .then(() => {
                        this.input = null;
                        this.getReview(this.select_place.id);
                    })
                    .catch(({error}) => {
                        console.log(error);
                    });
            }
            ,
            modifyReview(id, review) {
                if (review.userName !== this.$cookies.get('username')) {
                    this.modal.body = '수정 불가능합니다.'
                    this.onToggleModal();
                    return;
                }

                review.contents = review.input;
                review.star = '0';
                return axios
                    .post(
                        this.url + 'review/' + id,
                        review
                    )
                    .then((data) => {
                        console.log(data);
                    })
                    .catch(({error}) => {
                        console.log(error);
                    })
            }
            ,
            deleteReview(id, review) {
                if (review.userName !== this.$cookies.get('username')) {
                    this.modal.body = '삭제 불가능합니다.'
                    this.onToggleModal();
                    return;
                }

                return axios
                    .delete(this.url + 'review/' + id)
                    .then(() => {
                        this.getReview(this.select_place.id);
                    })
                    .catch(({error}) => {
                        console.log(error);
                    })
            }
            ,
            uploadFile(e) {
                if (this.$cookies.get('username') == null) {
                    this.modal.body = '로그인을 해야합니다.'
                    this.onToggleModal();
                    return;
                }
                const formData = new FormData();
                formData.append("image", e.target.files[0]);
                axios.post(this.url + 'review/image', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((data) => {
                    this.fileId = data.data.fileId;
                    console.log("this.fileId => " + this.fileId);
                }).catch((error) => {
                    console.log(error);
                })

            }
            ,
            onToggle(review) {
                if (review.userName !== this.$cookies.get('username')) {
                    this.modal.body = '수정 불가능합니다.'
                    this.onToggleModal();
                    return;
                }

                // 현재 input을 저장되어있던 값으로 변환
                review.input = review.contents;

                if (review.modify) {
                    review.modify = false;
                } else {
                    review.modify = true
                }
            }
            ,
            onToggleModal() {
                if (this.showModal) {
                    this.showModal = false;
                } else {
                    this.showModal = true;
                }
            }
            ,
            onReviewTimeout() {
                //setTimeout('getReview(this.placeId)', 10000);
            }
        }
    }
</script>
<style>

    .review {
        list-style: none;
        color: #7C7877;
    }

    .review-img {
        width: 200px;
        height: 150px;
        object-fit: cover;
    }

    .review-register {
        float: right;
        margin-top: 0px;
    }
</style>