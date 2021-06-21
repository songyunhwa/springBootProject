import Login from './components/Login'
import Home from './views/Home'
import Logout from "@/components/Logout";
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
        path: "/logout",
        component: Logout,
    }
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;