import Home from "@/components/banner/Home";
import Login from './components/user/Login'
import Logout from "@/components/user/Logout";
import WishedHome from "@/components/wished/WishedHome";
import MyListHome from "@/components/myList/MyListHome";
import Password from "@/components/user/Password";
import { createWebHistory, createRouter } from "vue-router";
import Map from "@/components/map/Map";

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
        component: MyListHome,
    },
    {
        path: "/logout",
        component: Logout,
    },
    {
        path: "/password/:email",
        component: Password,
    },
    {
        path: "/map",
        component:  Map
    }
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;