import Home from "@/components/Home";
import Login from './components/Login'
import Logout from "@/components/Logout";
import WishedHome from "@/components/WishedHome";
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
        path: "/wished",
        component: WishedHome,
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