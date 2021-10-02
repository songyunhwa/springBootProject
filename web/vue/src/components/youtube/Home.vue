<template>
    <!-- 추천 배너 -->
    <recommend class="right-banner"></recommend>

    <!-- 배너 -->
    <Banner @search="getYoutube"></Banner>

    <!-- 검색 -->
    <div class="search">
        <input v-model="input" style="margin: 5px 10px;padding: 5px 10px;" @keyup.enter="getYoutube(input)">
        <button @click="getYoutube(input)">검색</button>
    </div>

    <!-- 유투브 -->
    <youtube-list :msg="input" ref="youtube_list"></youtube-list>

    <!-- 하단 스크롤 바
    <div class="scoll-menu">
        <img  :src="'../../assets/images/scroll_up.png'" v-on:click="scrollUp"/>
    </div>
-->
</template>

<script>
    import YoutubeList from "@/components/youtube/YoutubeList";
    import Recommend from "@/components/banner/Recommend";
    import Banner from "@/components/banner/Banner";

    export default {
        name: 'Home',
        components: { YoutubeList, Recommend, Banner},
        data: () => ({
            username: '',
            password: '',
            url: '',
            input: '', // 입력하는 값
            msg: '',   // 실질적으로 넘어가는 값
            views: '0', // 하루 접속량,
        }),
        created() {
            this.username = this.$cookies.get('username');
            this.url = this.resourceHost + '/history';
        },
        methods: {
            scrollUp() {
                window.scrollTo(0, 0);
            },
            getYoutube(param) {
                console.log(param);
                this.$refs.youtube_list.getYoutube(param);
            }
        }
    }
</script>
<style>

    button {
        color: #444444;
        background: #F3F3F3;
        border: 1px #DADADA solid;
        padding: 5px 10px;
        font-weight: bold;
        font-size: 9pt;
        outline: none;
        float: left;
        margin: 10px 10px;
    }

    button:hover {
        border: 1px #C6C6C6 solid;
        box-shadow: 1px 1px 1px #EAEAEA;
        color: #333333;
        background: #F7F7F7;
    }

    button:active {
        box-shadow: inset 1px 1px 1px #DFDFDF;
    }

    .right-banner {
        float: right;
        width: 20%;
    }

    .scoll-menu {
        position: fixed;
        right: 10px;
        bottom: 10px;
        height: 100px;
        width: 100px;
        text-decoration: none;

    }

    .search {
        float: right;
    }
</style>