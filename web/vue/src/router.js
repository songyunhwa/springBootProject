import Home from "@/components/home/Home";
import Login from './components/user/Login'
import Logout from "@/components/user/Logout";
import WishedHome from "@/components/wished/WishedHome";
import myList from "@/components/myList/myList";
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
        path: "/myList",
        component: myList,
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