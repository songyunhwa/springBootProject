import Home from './views/Home.vue';
import Router  from "vue-router";

const routes = [
    {
        path: "/",
        component: Home,
    }
];



const router = Router({
    routes,
});

export default router;