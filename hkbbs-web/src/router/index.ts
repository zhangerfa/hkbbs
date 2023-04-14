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
  },
  {
    path: "/treehole",
    name: "TreeHole",
    component: TreeHole,
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
];
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
