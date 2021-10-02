<template>
    <div class="banner">
        <div v-if="this.username&&this.username.length>0">
            <router-link to="/logout">
                <button>로그아웃</button>
            </router-link>
        </div>
        <div v-else>
            <router-link to="/login">
                <button>로그인</button>
            </router-link>
        </div>

        <button @click="this.$router.push({path: '/'});">홈</button>
        <button @click="goUrl('wished')">찜한 장소</button>
        <button @click="goUrl('myList')">내 일기장으로 가기</button>
        <button  @click="onTogglePlaceModal">
            맛집 추가
        </button>

        <div class="title">
            당다라당당에 오신걸 환영합니다! {{ username }}
            오늘 방문자수: {{ this.views }}
        </div>
    </div>

    <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
    <PlaceModal v-show="showPlaceModal" :select_modal="modal" @close="onTogglePlaceModal" @putPlace="onToggleModal"
                ref="placeModal"></PlaceModal>

</template>

<script>
    import axios from "axios";
    import Modal from "@/modal/Modal";
    import PlaceModal from "@/modal/PlaceModal";

    export default {
        name: 'Banner',
        components: {Modal, PlaceModal},
        data: () => ({
            usename: '',
            role: '',
            url: '',
            input: '', // 입력하는 값
            msg: '',   // 실질적으로 넘어가는 값
            views: '0', // 하루 접속량,
            showModal: false,
            showPlaceModal : false,
            modal: {
                header: '',
                body: '',
                footer: ''
            }
        }),
        created() {
            this.username = this.$cookies.get('username');
            this.role = this.$cookies.get('role');
            this.url = this.resourceHost + '/history';
            this.getLoginHistory();
        },
        methods: {
            onToggleModal() {
                if (this.showModal) {
                    this.showModal = false;
                } else {
                    this.showModal = true;
                }
            },
            onTogglePlaceModal() {

                if (this.showPlaceModal) {
                    this.showPlaceModal = false;
                } else {
                    this.showPlaceModal = true;
                   // this.$refs.placeModal.initialization();
                }
            },
            getLoginHistory() {
                return axios
                    .get(this.url)
                    .then(({data}) => {
                        this.views = data;
                    })
                    .catch(() => {

                    })
            },
            scrollUp() {
                window.scrollTo(0, 0);
            },
            goUrl(path) {
                if (this.username === null || this.username.length == 0) {
                    this.modal.body = "로그인을 해주세요.";
                    this.showModal = true;
                    return;
                }
                this.$router.push(path);
            }
        }
    }
</script>
<style>

    .banner {
        height: 50px;
    }

    .title {
        font-size: 14px;
        color: #7C7877;
        padding: 10px 10px;
        float: left;
    }


</style>