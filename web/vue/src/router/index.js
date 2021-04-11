import Vue from 'vue';
import VueRouter from 'vue-router';
import HelloWorld from "../components/HelloWorld";

Vue.use(VueRouter);

export const router = new VueRouter({
    routes:[
        {
            //  path : url 주소
            path: '/hello',
            // component: url 주소로 갔을 때 표시될 컴포넌트
            component: HelloWorld
        }
    ]
});