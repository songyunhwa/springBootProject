import Login from './components/Login'
import Home from './views/Home'
import Youtube from './components/Youtube'
import { createWebHistory, createRouter } from "vue-router";

const routes = [
    {
        path: "/",
        component: Home,
    },
    {
        path: "/login",
        component: Login,
    },
    {
        path: "/youtube",
        component: Youtube,
    }
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;