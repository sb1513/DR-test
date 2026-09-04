<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";

const user = ref({
  nickName: "",
  userName: "",
  userPwd: "",
  rePwd: "",
  email: "",
})
const errMessage = ref("")

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!user.value.nickName.trim()){
    errMessage.value="昵称不能为空"
  }else if(!user.value.userName.trim()){
    errMessage.value="用户名不能为空"
  }else if(!user.value.email.trim()){
    errMessage.value="邮箱不能为空"
  }else if(!user.value.userPwd.trim()){
    errMessage.value="密码不能为空"
  }else if(user.value.userPwd.trim()!==user.value.rePwd.trim()){
    errMessage.value="密码不相同"
  }else{
    axios({
      method: 'post',
      url: 'http://localhost:8080/user/register',
      data: user.value
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
    <h1>注册</h1>
    <div>
      昵称:<input type="text" v-model="user.nickName" placeholder="请输入昵称"/><br/>
    </div>
    <div>
      用户名:<input type="text" v-model="user.userName" placeholder="请输入用户名"/><br/>
    </div>
    <div>
      密码:<input type="password" v-model="user.userPwd" placeholder="请输入密码"/><br/>
    </div>
    <div>
      确认密码:<input type="password" v-model="user.rePwd" placeholder="请确认密码"/><br/>
    </div>
    <div>
      emal:<input type="text" v-model="user.email" placeholder="请输入邮箱"/><br/>
    </div>
    <div>
      <button @click="send">注册</button>
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