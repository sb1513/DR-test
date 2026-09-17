<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const user = ref({
  nickName:"",
  userName:"",
  userPwd:"",
  rePwd:"",
  email:"",
})
const errMessage = ref("")
const router = useRouter()

function send(){
  errMessage.value=''
  if(!user.value.nickName.trim()){
    errMessage.value="昵称不能为空"
    alert(errMessage.value)
  }else if(!user.value.userName.trim()){
    errMessage.value="用户名不能为空"
    alert(errMessage.value)
  }else if(!user.value.email.trim()){
    errMessage.value="邮箱不能为空"
    alert(errMessage.value)
  }else if(!user.value.userPwd.trim()){
    errMessage.value="密码不能为空"
    alert(errMessage.value)
  }else if(user.value.userPwd.trim()!==user.value.rePwd.trim()){
    errMessage.value="密码不相同"
    alert(errMessage.value)
  }else{
    axios({
      method:'post',
      url:'/user/register',
      data:user.value
    }).then(res=>{
      alert(res.data.msg)
      router.push({name:"HomePage-index"})
    })
  }
}
</script>

<template>
  <div class="register-page">
    <div class="background-shape shape-one"></div>
    <div class="background-shape shape-two"></div>

    <div class="register-container">
      <div class="brand">
        <div class="logo-icon">✦</div>
        <span>AI Friends</span>
      </div>

      <div class="register-card">
        <div class="card-header">
          <div class="welcome">CREATE ACCOUNT</div>
          <h1>创建你的账户</h1>
          <p>加入 AI Friends，认识属于你的 AI 伙伴</p>
        </div>

        <form @submit.prevent="send">
          <div class="form-row">
            <div class="form-group">
              <label>昵称</label>
              <div class="input-box">
                <span class="input-icon">♙</span>
                <input v-model="user.nickName" type="text" placeholder="请输入昵称"/>
              </div>
            </div>

            <div class="form-group">
              <label>用户名</label>
              <div class="input-box">
                <span class="input-icon">＠</span>
                <input v-model="user.userName" type="text" placeholder="请输入用户名"/>
              </div>
            </div>
          </div>

          <div class="form-group">
            <label>邮箱</label>
            <div class="input-box">
              <span class="input-icon">✉</span>
              <input v-model="user.email" type="email" placeholder="请输入邮箱"/>
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>密码</label>
              <div class="input-box">
                <span class="input-icon">⌑</span>
                <input v-model="user.userPwd" type="password" placeholder="请输入密码"/>
              </div>
            </div>

            <div class="form-group">
              <label>确认密码</label>
              <div class="input-box">
                <span class="input-icon">✓</span>
                <input v-model="user.rePwd" type="password" placeholder="再次输入密码"/>
              </div>
            </div>
          </div>

          <div v-if="errMessage" class="error-message">{{ errMessage }}</div>

          <button type="submit" class="register-main-button">
            <span>创建账户</span>
            <span>→</span>
          </button>
        </form>

        <div class="divider">
          <span>已经拥有账户？</span>
        </div>

        <RouterLink :to="{name:'LoginPage-index'}" class="login-button">
          登录已有账户
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
.register-page{min-height:100vh;background:#f7f8fc;display:flex;align-items:center;justify-content:center;position:relative;overflow:hidden;color:#171923;font-family:Inter,-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif}
.background-shape{position:absolute;border-radius:50%;pointer-events:none}
.shape-one{width:450px;height:450px;background:rgba(121,103,240,.1);top:-180px;left:-130px}
.shape-two{width:500px;height:500px;background:rgba(180,92,246,.09);right:-190px;bottom:-230px}
.register-container{width:100%;max-width:650px;padding:30px 20px;position:relative;z-index:2}
.brand{display:flex;justify-content:center;align-items:center;gap:10px;margin-bottom:22px;font-size:20px;font-weight:800;letter-spacing:-.5px}
.logo-icon{width:38px;height:38px;border-radius:12px;display:flex;align-items:center;justify-content:center;color:white;background:linear-gradient(135deg,#6366f1,#a855f7);box-shadow:0 9px 22px rgba(99,102,241,.25)}
.register-card{background:rgba(255,255,255,.95);border:1px solid #e8e9f0;border-radius:25px;padding:35px 42px;box-shadow:0 25px 70px rgba(35,38,65,.09);backdrop-filter:blur(20px)}
.card-header{text-align:center;margin-bottom:28px}
.welcome{font-size:10px;letter-spacing:2px;font-weight:800;color:#7779db;margin-bottom:9px}
.card-header h1{font-size:30px;letter-spacing:-1px;margin:0 0 8px;font-weight:800}
.card-header p{font-size:13px;color:#8a8f9d;line-height:1.6;margin:0}
.form-row{display:grid;grid-template-columns:1fr 1fr;gap:15px}
.form-group{margin-bottom:17px}
.form-group label{display:block;font-size:12px;font-weight:700;margin-bottom:7px;color:#383b46}
.input-box{height:48px;border:1px solid #e2e4eb;border-radius:11px;background:#fafbfc;display:flex;align-items:center;padding:0 12px;transition:.2s}
.input-box:focus-within{border-color:#7774e8;background:white;box-shadow:0 0 0 4px rgba(99,102,241,.08)}
.input-icon{width:24px;color:#9499a7;font-size:17px;text-align:center;margin-right:7px;flex:none}
.input-box input{width:100%;height:100%;border:0;outline:0;background:transparent;font-size:13px;color:#252832;min-width:0}
.input-box input::placeholder{color:#b0b4be}
.error-message{font-size:12px;color:#df5353;background:#fff3f3;border:1px solid #ffdede;border-radius:9px;padding:10px 12px;margin:-2px 0 14px}
.register-main-button{width:100%;height:51px;border:0;border-radius:12px;background:linear-gradient(100deg,#6366f1,#8b5cf6);color:white;font-size:14px;font-weight:700;display:flex;align-items:center;justify-content:space-between;padding:0 18px;cursor:pointer;box-shadow:0 10px 22px rgba(99,102,241,.22);transition:.25s}
.register-main-button span:last-child{font-size:19px}
.register-main-button:hover{transform:translateY(-2px);box-shadow:0 14px 28px rgba(99,102,241,.3)}
.register-main-button:active{transform:translateY(0)}
.divider{display:flex;align-items:center;gap:12px;color:#a0a4af;font-size:11px;margin:23px 0 14px}
.divider:before,.divider:after{content:"";height:1px;background:#eceef2;flex:1}
.login-button{height:47px;border:1px solid #e0e2e9;border-radius:11px;display:flex;align-items:center;justify-content:center;text-decoration:none;color:#505461;font-size:13px;font-weight:700;transition:.2s}
.login-button:hover{background:#f7f7ff;border-color:#cfd0f5;color:#6366d9}
.home-link{display:block;text-align:center;text-decoration:none;color:#9296a2;font-size:12px;margin-top:18px;transition:.2s}
.home-link:hover{color:#6366d9}
.footer{text-align:center;color:#adb1bb;font-size:10px;margin-top:20px}
@media(max-width:650px){.register-container{padding:20px 15px}.register-card{padding:30px 25px}.form-row{grid-template-columns:1fr}.card-header h1{font-size:27px}}
</style>