<script setup>
import {useRouter} from "vue-router";
import Home from "./Home.vue";

const router = useRouter()

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
    <br/>
    <RouterLink :to="{name: 'LoginPage-index'}">
      <button class="btn btn-neutral btn-ghost">登录</button>
    </RouterLink>
    <RouterLink :to="{name: 'RegisterPage-index'}">
      <button class="btn btn-neutral btn-ghost">注册</button>
    </RouterLink>
  </div>
</template>

<style scoped>

</style>