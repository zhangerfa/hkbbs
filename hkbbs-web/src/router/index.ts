import {createRouter, createWebHashHistory} from 'vue-router'
const routes = [
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        component: () => import('../pages/Login/Login.vue')
    }
]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
