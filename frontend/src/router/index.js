import { createRouter,createWebHistory} from 'vue-router';
import Home from "../pages/home/Home.vue";
import Dog from "../pages/chat/dog.vue";
import Cat from "../pages/chat/cat.vue";
import Tiger from "../pages/chat/tiger.vue";
import Register from "../pages/user/Register.vue";
import Login from "../pages/user/Login.vue";
import Favs from "../pages/favs/Favs.vue";
import Update from "../pages/user/Update.vue";

 const  routes= [
     {
         path:  '/',
         name: 'HomePage-index',
         component: Home
     },
     {
        path: '/cat',
        name: 'CatChatPage-index',
        component: Cat,
        meta: {isLogin:true}
     },
     {
        path: '/dog',
        name: 'DogChatPage-index',
        component: Dog,
        meta: {isLogin: true}
     },
     {
        path: '/tiger',
        name: 'TigerChatPage-index',
        component: Tiger,
        meta: {isLogin: true}
     },
     {
        path: '/register',
        name: 'RegisterPage-index',
        component: Register
     },
     {
        path: '/login',
        name: 'LoginPage-index',
        component: Login
     },
     {
        path: '/user_update',
        name: 'UserUpdatePage-index',
        component: Update,
        meta: {isLogin: true}
     },
     {
        path: '/favs',
        name: 'FavsPage-index',
        component: Favs,
        meta: {isLogin: true}
     },
    ]
const router = createRouter({
    routes,
    history: createWebHistory(),
})

router.beforeEach((to,from)=>{
  if(!to.meta.isLogin || sessionStorage.getItem("token")){
      return
  }
  alert("请登录")
  return ("/login")
})

export default router;