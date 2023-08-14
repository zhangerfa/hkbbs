import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

const Login = () => import("../pages/Login/Login.vue");
const Register = () => import("../pages/Register/Register.vue");
const CardWall = () => import("../pages/CardWall/CardWall.vue");
const Post = () => import("../pages/Post/Post.vue");
const TreeHole = () => import("../pages/TreeHole/TreeHole.vue");
const Notice = () => import("../pages/Notice/Notice.vue");
const Chat = () => import("../pages/Notice/ChatPanel.vue");
const Profile = () => import("../pages/Profile/Profile.vue");

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      hasTabbar: false,
    },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: {
      hasTabbar: false,
    },
  },
  {
    path: "/cardwall",
    name: "CardWall",
    component: CardWall,
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
    path: "/chat/:id",
    name: "Chat",
    component: Chat,
    meta: {
      hasTabbar: false,
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
    path: "/post/:type",
    name: "Post",
    component: Post,
    meta: {
      hasTabbar: true,
    },
  },
];
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
