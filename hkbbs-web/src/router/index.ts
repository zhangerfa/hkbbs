import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

const Login = () => import("../pages/Login/Login.vue");
const Post = () => import("../pages/Post/Post.vue");
const TreeHole = () => import("../pages/TreeHole/TreeHole.vue");
const Notice = () => import("../pages/Notice/Notice.vue");
const Profile = () => import("../pages/Profile/Profile.vue");

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/post",
  },
  {
    path: "/post",
    name: "Post",
    component: Post,
    meta: {
      hasTabbar: true,
    },
  },
  {
    path: "/treehole",
    name: "TreeHole",
    component: TreeHole,
    meta: {
      hasTabbar: true,
    },
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    meta: {
      hasTabbar: true,
    },
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    meta: {
      hasTabbar: true,
    },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      hasTab: false,
    },
  },
];
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
