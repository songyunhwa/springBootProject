import App from './App.vue'
import {createApp} from 'vue'
import router from './router'
import VueCookies from "vue3-cookies";
import GAuth from 'vue-google-oauth2'

const gauthOption = {
    clientId: '942313148186-02jviab6j8v2po06op58129tttdmci28.apps.googleusercontent.com',
    scope: 'profile email',
    prompt: 'select_account'
}

createApp(App).use(router).use(VueCookies).use(GAuth, gauthOption).mount('#app');
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