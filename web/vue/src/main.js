import App from './App.vue'
import {createApp} from 'vue'
import router from './router'
import VueCookies from "vue3-cookies";
import store from "store";

const app = createApp(App).use(router).use(VueCookies).use(store);

// resourceHost를 글로벌 변수에 넣는 모습
//app.config.globalProperties.$store = store;
app.config.globalProperties.resourceHost = "http://localhost:9000/api/v1";
//app.config.globalProperties.resourceHost = "http://15.152.18.84:9000/api/v1";
app.config.globalProperties.googleLoginHost = "http://15.152.18.84:9000";
app.mount('#app');

