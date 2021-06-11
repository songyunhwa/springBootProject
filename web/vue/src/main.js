import App from './App.vue'
import { createApp } from 'vue'
import router from './router'

createApp(App).user(router).mount('#app')

/*
import Vue from 'vue'

import router from "./router"

new Vue({
    el: '#app',
    render: h => h(App),
    router
})
*/