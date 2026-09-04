<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";

const loginName = ref("")
const loginPwd = ref("")
const errMessage = ref("")

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!loginName.value.trim()){
    errMessage.value="用户名不能为空"
  }else if(!loginPwd.value.trim()){
    errMessage.value="密码不能为空"
  }else{
    axios({
      method: 'post',
      url: 'http://localhost:8080/user/login',
      data: {
        userName: loginName.value,
        userPwd: loginPwd.value,
      }
    }).then(res=>{
      alert(res.data.msg)
      errMessage.value=res.data.msg
    })
  }
  console.log(errMessage)
}

</script>

<template>
  <div class="daisyui">
    <h1>登录</h1>
    <div>
      用户名:<input type="text" v-model="loginName" placeholder="请输入用户名"/><br/>
    </div>
    <div>
      密码:<input type="password" v-model="loginPwd" placeholder="请输入密码"/><br/>
    </div>
    <div>
      <button @click="send">登录</button>
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">
      <button>首页</button>
    </RouterLink>
  </div>
</template>

<style scoped>
.daisyui{
}

</style>