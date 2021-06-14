import App from './App.vue'
import { createApp } from 'vue'
import router from './router'
import VueCookies from "vue3-cookies";

createApp(App).use(router).use(VueCookies).mount('#app')
this.$cookies.config("1d")
/*
import Vue from 'vue'

import router from "./router"

new Vue({
    el: '#app',
    render: h => h(App),
    router
})
*/