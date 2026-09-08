<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const loginPwd = ref("")
const newPwd = ref('')
const errMessage = ref("")
const router = useRouter()

function send(){
  if(confirm("确认修改密码?")){
      //用axios向后端发送请求
    errMessage.value=''
    if(!loginPwd.value.trim()){
      errMessage.value="密码不能为空"
      alert(errMessage.value)
    }else if(!newPwd.value){
      errMessage.value="新密码不能为空"
      alert(errMessage.value)
    }else{
      axios({
        method: 'post',
        url: '/user/user_update',
        data: {
          loginPwd: loginPwd.value,
          newPwd: newPwd.value,
        }
      }).then(res=>{
        alert(res.data.msg)
        errMessage.value=res.data.msg
        router.push({name: "HomePage-index"})
      })
    }
    //console.log(errMessage)
  }

}

</script>

<template>
  <div class="daisyui">
    <h1>修改密码</h1>
    <div>
      原密码:<input type="password" v-model="loginPwd" placeholder="请输入原密码"/><br/>
    </div>
    <div>
      新密码:<input type="password" v-model="newPwd" placeholder="请输入新密码"/><br/>
    </div>
    <div>
      <button @click="send">修改</button>
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