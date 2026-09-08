<script setup>
import {useRouter} from "vue-router";
import Home from "./Home.vue";
import axios from "../../axios/index.js";

const router = useRouter()

const logout=()=>{
  sessionStorage.removeItem("cur_user")
  sessionStorage.removeItem("token")
  alert("登出成功")
  router.push("/")
}

const removeUser = () =>{
  if(confirm("确认注销账户?")){
    axios({
      method: 'post',
      url: `/user/user_delete`,
      data: {
        id: JSON.parse(sessionStorage.getItem("cur_user")).id
      }
    }).then(res => {
      alert(res.data.msg)
      sessionStorage.removeItem("cur_user")
      sessionStorage.removeItem("token")
    })
  }
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
    <RouterLink :to="{name: 'UserUpdatePage-index'}">
      <button class="btn btn-neutral btn-ghost">修改密码</button>
    </RouterLink>
    <RouterLink :to="{name: 'RegisterPage-index'}">
      <button class="btn btn-neutral btn-ghost">注册</button>
    </RouterLink>
    <button @click="logout()" class="">登出</button>
    <button @click="removeUser()" class="">注销</button>
  </div>
</template>

<style scoped>
button{
  display: inline-block;
  cursor: pointer;
  transition: 0.2s;
}
</style>