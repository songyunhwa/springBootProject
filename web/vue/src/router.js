import Login from './components/Login'
import Home from './views/Home'
import { createWebHistory, createRouter } from "vue-router";

const routes = [
    {
        path: "/",
        component: Home,
    },
    {
        path: "/login",
        component: Login,
    }
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;