<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const loginName = ref("")
const loginPwd = ref("")
const errMessage = ref("")
const router = useRouter()

function send(){
  errMessage.value=''
  if(!loginName.value.trim()){
    errMessage.value="用户名不能为空"
    alert(errMessage.value)
  }else if(!loginPwd.value.trim()){
    errMessage.value="密码不能为空"
    alert(errMessage.value)
  }else{
    axios({
      method:'post',
      url:'/user/login',
      data:{
        userName:loginName.value,
        userPwd:loginPwd.value,
      }
    }).then(res=>{
      alert(res.data.msg)
      errMessage.value=res.data.msg
      let user=res.data.data.user
      let token=res.data.data.token
      sessionStorage.setItem("cur_user",JSON.stringify(user))
      sessionStorage.setItem("token",token)
      router.push({name:"HomePage-index"})
    })
  }
}
</script>

<template>
  <div class="login-page">
    <div class="background-shape shape-one"></div>
    <div class="background-shape shape-two"></div>
    <div class="login-container">
      <div class="brand">
        <div class="logo-icon">✦</div>
        <span>AI Friends</span>
      </div>

      <div class="login-card">
        <div class="card-header">
          <div class="welcome">WELCOME BACK</div>
          <h1>欢迎回来</h1>
          <p>登录你的 AI Friends 账户，继续你的聊天旅程</p>
        </div>

        <form @submit.prevent="send">
          <div class="form-group">
            <label>用户名</label>
            <div class="input-box">
              <span class="input-icon">♙</span>
              <input v-model="loginName" type="text" placeholder="请输入用户名" autocomplete="username"/>
            </div>
          </div>

          <div class="form-group">
            <label>密码</label>
            <div class="input-box">
              <span class="input-icon">⌑</span>
              <input v-model="loginPwd" type="password" placeholder="请输入密码" autocomplete="current-password"/>
            </div>
          </div>

          <div v-if="errMessage" class="error-message">{{ errMessage }}</div>

          <button type="submit" class="login-button">
            <span>登录</span>
            <span>→</span>
          </button>
        </form>

        <div class="divider">
          <span>还没有账户？</span>
        </div>

        <RouterLink :to="{name:'RegisterPage-index'}" class="register-button">
          创建新账户
        </RouterLink>

        <RouterLink :to="{name:'HomePage-index'}" class="home-link">
          ← 返回首页
        </RouterLink>
      </div>

      <div class="footer">© 2026 AI Friends · Your AI companions</div>
    </div>
  </div>
</template>

<style scoped>
*{box-sizing:border-box}
.login-page{min-height:100vh;background:#f7f8fc;display:flex;align-items:center;justify-content:center;position:relative;overflow:hidden;color:#171923;font-family:Inter,-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif}
.background-shape{position:absolute;border-radius:50%;filter:blur(2px);pointer-events:none}
.shape-one{width:430px;height:430px;background:rgba(121,103,240,.11);top:-170px;left:-120px}
.shape-two{width:500px;height:500px;background:rgba(180,92,246,.09);right:-180px;bottom:-220px}
.login-container{width:100%;max-width:450px;padding:30px 20px;position:relative;z-index:2}
.brand{display:flex;justify-content:center;align-items:center;gap:10px;margin-bottom:25px;font-size:20px;font-weight:800;letter-spacing:-.5px}
.logo-icon{width:38px;height:38px;border-radius:12px;display:flex;align-items:center;justify-content:center;color:white;background:linear-gradient(135deg,#6366f1,#a855f7);box-shadow:0 9px 22px rgba(99,102,241,.25)}
.login-card{background:rgba(255,255,255,.94);border:1px solid #e8e9f0;border-radius:25px;padding:38px 40px;box-shadow:0 25px 70px rgba(35,38,65,.09);backdrop-filter:blur(20px)}
.card-header{text-align:center;margin-bottom:30px}
.welcome{font-size:10px;letter-spacing:2px;font-weight:800;color:#7779db;margin-bottom:10px}
.card-header h1{font-size:31px;letter-spacing:-1px;margin:0 0 9px;font-weight:800}
.card-header p{font-size:13px;color:#8a8f9d;line-height:1.6;margin:0}
.form-group{margin-bottom:19px}
.form-group label{display:block;font-size:13px;font-weight:700;margin-bottom:8px;color:#383b46}
.input-box{height:50px;border:1px solid #e2e4eb;border-radius:12px;background:#fafbfc;display:flex;align-items:center;padding:0 14px;transition:.2s}
.input-box:focus-within{border-color:#7774e8;background:white;box-shadow:0 0 0 4px rgba(99,102,241,.08)}
.input-icon{width:25px;color:#9499a7;font-size:19px;text-align:center;margin-right:8px}
.input-box input{width:100%;height:100%;border:0;outline:0;background:transparent;font-size:14px;color:#252832}
.input-box input::placeholder{color:#b0b4be}
.error-message{font-size:12px;color:#df5353;background:#fff3f3;border:1px solid #ffdede;border-radius:9px;padding:10px 12px;margin:-5px 0 15px}
.login-button{width:100%;height:51px;border:0;border-radius:12px;background:linear-gradient(100deg,#6366f1,#8b5cf6);color:white;font-size:14px;font-weight:700;display:flex;align-items:center;justify-content:space-between;padding:0 18px;cursor:pointer;box-shadow:0 10px 22px rgba(99,102,241,.22);transition:.25s}
.login-button span:last-child{font-size:19px}
.login-button:hover{transform:translateY(-2px);box-shadow:0 14px 28px rgba(99,102,241,.3)}
.login-button:active{transform:translateY(0)}
.divider{display:flex;align-items:center;gap:12px;color:#a0a4af;font-size:11px;margin:25px 0 15px}
.divider:before,.divider:after{content:"";height:1px;background:#eceef2;flex:1}
.register-button{height:48px;border:1px solid #e0e2e9;border-radius:11px;display:flex;align-items:center;justify-content:center;text-decoration:none;color:#505461;font-size:13px;font-weight:700;transition:.2s}
.register-button:hover{background:#f7f7ff;border-color:#cfd0f5;color:#6366d9}
.home-link{display:block;text-align:center;text-decoration:none;color:#9296a2;font-size:12px;margin-top:20px;transition:.2s}
.home-link:hover{color:#6366d9}
.footer{text-align:center;color:#adb1bb;font-size:10px;margin-top:22px}
@media(max-width:500px){.login-container{padding:20px 15px}.login-card{padding:30px 25px}.card-header h1{font-size:28px}}
</style>
