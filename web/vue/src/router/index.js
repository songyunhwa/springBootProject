import Vue from 'vue'
import Router from 'vue-router'
import Name from '@/components/Name'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login
        }
    ]
})