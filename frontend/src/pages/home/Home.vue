<script setup>
import {useRouter} from "vue-router";
import Home from "./Home.vue";

const router = useRouter()

const logout=()=>{
  sessionStorage.removeItem("cur_user")
  sessionStorage.removeItem("token")
  alert("登出成功")
  router.push("/")
}

</script>

<template>
  <div>
    <div class="text-3xl">Chat</div>
    <RouterLink :to="{name: 'DogChatPage-index'}">
      <button class="btn btn-neutral btn-ghost">Dog</button>
    </RouterLink>
    <RouterLink to="/cat">
      <button class="btn btn-neutral btn-ghost">Cat</button>
    </RouterLink>
    <RouterLink to="/tiger">
      <button class="btn btn-neutral btn-ghost">Tiger</button>
    </RouterLink>
    <RouterLink :to="{name: 'FavsPage-index'}">
      <button class="btn btn-neutral btn-ghost">收藏</button>
    </RouterLink>
    <br/>
    <RouterLink :to="{name: 'LoginPage-index'}">
      <button class="btn btn-neutral btn-ghost">登录</button>
    </RouterLink>
    <RouterLink :to="{name: 'RegisterPage-index'}">
      <button class="btn btn-neutral btn-ghost">注册</button>
    </RouterLink>
    <button @click="logout()" class="">登出</button>
  </div>
</template>

<style scoped>
button{
  display: inline-block;
  cursor: pointer;
  transition: 0.2s;
}
</style>