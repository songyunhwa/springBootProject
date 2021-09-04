import App from './App.vue'
import {createApp} from 'vue'
import router from './router'
import VueCookies from "vue3-cookies";
import store from "store";

const app = createApp(App).use(router).use(VueCookies).use(store);

// store, foo를 글로벌 변수에 넣는 모습
//app.config.globalProperties.$store = store;
app.config.globalProperties.resourceHost = "http://localhost:9000/api/v1";

app.mount('#app');
//this.$cookies.config("1d")
/*
import Vue from 'vue'

import router from "./router"

new Vue({
    el: '#app',
    render: h => h(App),
    router
})
*/