import { createRouter,createWebHistory} from 'vue-router';
import Home from "../pages/Home/Home.vue";
import Dog from "../pages/chat/dog.vue";
import Cat from "../pages/chat/cat.vue";
import Tiger from "../pages/chat/tiger.vue";

 const  routes= [
     {
         path:  '/',
         name: 'HomePage-index',
         component: Home
     },
     {
        path: '/cat',
        name: 'CatChatPage-index',
        component: Cat
     },
     {
        path: '/dog',
        name: 'DogChatPage-index',
        component: Dog
     },
     {
        path: '/tiger',
        name: 'TigerChatPage-index',
        component: Tiger
     },
    ]
const router = createRouter({
    routes,
    history: createWebHistory(),
})

export default router;