<script setup>
import {ref, computed} from "vue";
import {useRouter} from "vue-router";
import axios from "../../axios/index.js";

const router = useRouter()
const curUser = ref(JSON.parse(sessionStorage.getItem("cur_user") || "null"))

const isLogin = computed(() => !!curUser.value)
const userNickname = computed(() => curUser.value?.nickName || curUser.value?.nickname || curUser.value?.userNickname || curUser.value?.userName || "用户")

const logout = () => {
  sessionStorage.removeItem("cur_user")
  sessionStorage.removeItem("token")
  curUser.value = null
  alert("登出成功")
  router.push("/")
}

const removeUser = () => {
  if(!curUser.value){
    return
  }

  if(confirm("确认注销账户?")){
    axios({
      method: 'post',
      url: `/user/user_delete`,
      data: {
        id: curUser.value.id
      }
    }).then(res => {
      alert(res.data.msg)
      sessionStorage.removeItem("cur_user")
      sessionStorage.removeItem("token")
      curUser.value = null
      router.push("/")
    })
  }
}
</script>

<template>
  <div class="home">
    <header class="navbar">
      <div class="logo">
        <div class="logo-icon">✦</div>
        <span>AI Friends</span>
      </div>

      <nav class="nav-links">
        <RouterLink :to="{name:'DogChatPage-index'}" class="nav-item">Dog</RouterLink>
        <RouterLink to="/cat" class="nav-item">Cat</RouterLink>
        <RouterLink to="/tiger" class="nav-item">Tiger</RouterLink>
        <RouterLink :to="{name:'FavsPage-index'}" class="nav-item">收藏</RouterLink>
      </nav>

      <div class="user-actions">
        <template v-if="!isLogin">
          <RouterLink :to="{name:'LoginPage-index'}" class="login-btn">登录</RouterLink>
          <RouterLink :to="{name:'RegisterPage-index'}" class="register-btn">注册</RouterLink>
        </template>

        <div v-else class="user-info">
          <div class="user-avatar">{{ userNickname.charAt(0) }}</div>
          <span>{{ userNickname }}</span>
        </div>
      </div>
    </header>

    <main class="main">
      <section class="hero">
        <div class="badge">✦ AI FRIENDS</div>
        <h1>和你的 <span>AI 朋友</span><br/>开始聊天吧</h1>
        <p>选择一个你喜欢的伙伴，开始一段属于你们的专属对话。</p>
      </section>

      <section class="chat-grid">
        <RouterLink :to="{name:'DogChatPage-index'}" class="chat-card dog">
          <div class="card-glow"></div>
          <div class="animal-icon">🐶</div>
          <div class="card-content">
            <h2>Dog</h2>
            <p>忠诚、活泼的 AI 伙伴</p>
            <span class="start">开始聊天 →</span>
          </div>
        </RouterLink>

        <RouterLink to="/cat" class="chat-card cat">
          <div class="card-glow"></div>
          <div class="animal-icon">🐱</div>
          <div class="card-content">
            <h2>Cat</h2>
            <p>可爱、傲娇的 AI 伙伴</p>
            <span class="start">开始聊天 →</span>
          </div>
        </RouterLink>

        <RouterLink to="/tiger" class="chat-card tiger">
          <div class="card-glow"></div>
          <div class="animal-icon">🐯</div>
          <div class="card-content">
            <h2>Tiger</h2>
            <p>勇敢、霸气的 AI 伙伴</p>
            <span class="start">开始聊天 →</span>
          </div>
        </RouterLink>
      </section>

      <section class="bottom-panel">
        <RouterLink v-if="isLogin" :to="{name:'FavsPage-index'}" class="panel-item">
          <div class="panel-icon">♡</div>
          <div>
            <strong>我的收藏</strong>
            <span>查看收藏的精彩对话</span>
          </div>
          <b>→</b>
        </RouterLink>

        <RouterLink v-if="isLogin" :to="{name:'UserUpdatePage-index'}" class="panel-item">
          <div class="panel-icon">⚙</div>
          <div>
            <strong>账户设置</strong>
            <span>修改密码与账户信息</span>
          </div>
          <b>→</b>
        </RouterLink>

        <button v-if="isLogin" @click="logout" class="panel-item logout">
          <div class="panel-icon">↪</div>
          <div>
            <strong>退出登录</strong>
            <span>安全退出当前账户</span>
          </div>
          <b>→</b>
        </button>

        <button v-if="isLogin" @click="removeUser" class="panel-item delete">
          <div class="panel-icon">⌫</div>
          <div>
            <strong>注销账户</strong>
            <span>永久删除当前账户</span>
          </div>
          <b>→</b>
        </button>
      </section>
    </main>
  </div>
</template>

<style scoped>
*{box-sizing:border-box}
.home{min-height:100vh;background:#f7f8fc;color:#171923;font-family:Inter,-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif}
.navbar{height:72px;padding:0 7%;display:flex;align-items:center;justify-content:space-between;background:rgba(255,255,255,.82);backdrop-filter:blur(18px);border-bottom:1px solid #eceef3;position:sticky;top:0;z-index:20}
.logo{display:flex;align-items:center;gap:10px;font-size:20px;font-weight:800;letter-spacing:-.5px}
.logo-icon{width:36px;height:36px;border-radius:11px;display:flex;align-items:center;justify-content:center;color:white;background:linear-gradient(135deg,#6366f1,#a855f7);box-shadow:0 8px 20px rgba(99,102,241,.25)}
.nav-links{display:flex;gap:8px;margin-left:80px}
.nav-item{padding:9px 17px;border-radius:10px;color:#686d7a;text-decoration:none;font-size:14px;font-weight:600;transition:.2s}
.nav-item:hover{color:#5b5ce2;background:#f0f0ff}
.user-actions{display:flex;align-items:center;gap:10px}
.login-btn,.register-btn{padding:9px 18px;border-radius:10px;text-decoration:none;font-size:14px;font-weight:600;transition:.2s}
.login-btn{color:#555b69}
.login-btn:hover{background:#f0f1f5}
.register-btn{color:white;background:#171923;box-shadow:0 5px 15px rgba(23,25,35,.16)}
.register-btn:hover{transform:translateY(-1px);background:#30323c}
.user-info{display:flex;align-items:center;gap:9px;padding:6px 12px 6px 6px;border:1px solid #e7e8ef;border-radius:25px;background:white;color:#383b46;font-size:14px;font-weight:700;box-shadow:0 4px 14px rgba(30,35,55,.05)}
.user-avatar{width:34px;height:34px;border-radius:50%;display:flex;align-items:center;justify-content:center;color:white;background:linear-gradient(135deg,#6366f1,#a855f7);font-size:14px;font-weight:800}
.main{max-width:1120px;margin:auto;padding:70px 30px 80px}
.hero{text-align:center;margin-bottom:55px}
.badge{display:inline-flex;padding:7px 13px;border-radius:30px;background:#eeeefe;color:#6264d9;font-size:11px;font-weight:800;letter-spacing:1.5px;margin-bottom:20px}
.hero h1{font-size:52px;line-height:1.12;letter-spacing:-2.5px;margin:0;font-weight:850}
.hero h1 span{background:linear-gradient(90deg,#6366f1,#a855f7);-webkit-background-clip:text;color:transparent}
.hero p{margin:20px auto 0;color:#777d8b;font-size:16px;max-width:500px;line-height:1.7}
.chat-grid{display:grid;grid-template-columns:repeat(3,1fr);gap:20px}
.chat-card{height:300px;border-radius:24px;padding:28px;position:relative;overflow:hidden;text-decoration:none;color:#171923;background:white;border:1px solid #e9ebf1;box-shadow:0 8px 30px rgba(25,30,50,.05);transition:.3s}
.chat-card:hover{transform:translateY(-7px);box-shadow:0 20px 45px rgba(25,30,50,.11)}
.card-glow{position:absolute;width:190px;height:190px;border-radius:50%;right:-70px;top:-70px;filter:blur(2px);opacity:.7}
.dog .card-glow{background:#ffe6bc}
.cat .card-glow{background:#e4ddff}
.tiger .card-glow{background:#ffd6cc}
.animal-icon{font-size:68px;position:relative;margin-bottom:30px;filter:drop-shadow(0 10px 12px rgba(0,0,0,.08))}
.card-content{position:relative}
.card-content h2{font-size:25px;margin:0 0 7px;font-weight:800}
.card-content p{color:#858b99;margin:0;font-size:14px}
.start{display:inline-block;margin-top:27px;font-size:13px;font-weight:700;color:#6264d9}
.bottom-panel{margin-top:45px;display:grid;grid-template-columns:repeat(4,1fr);gap:12px}
.panel-item{min-height:76px;border:1px solid #e8eaf0;border-radius:16px;background:white;padding:15px;display:flex;align-items:center;gap:12px;text-decoration:none;color:#292c35;text-align:left;cursor:pointer;font-family:inherit;transition:.2s}
.panel-item:hover{border-color:#d9daf4;background:#fafaff;transform:translateY(-2px)}
.panel-icon{width:38px;height:38px;border-radius:11px;background:#f2f2f8;display:flex;align-items:center;justify-content:center;font-size:19px;flex:none}
.panel-item strong{display:block;font-size:13px;margin-bottom:3px}
.panel-item span{display:block;font-size:11px;color:#9398a4}
.panel-item b{margin-left:auto;color:#a2a6b1;font-size:16px}
.logout:hover .panel-icon{background:#eef0ff;color:#6366f1}
.delete:hover{border-color:#ffd9d9;background:#fffafa}
.delete:hover .panel-icon{background:#fff0f0;color:#e05252}
@media(max-width:850px){.nav-links{margin-left:0}.chat-grid{grid-template-columns:1fr}.chat-card{height:220px}.bottom-panel{grid-template-columns:repeat(2,1fr)}.hero h1{font-size:42px}}
@media(max-width:600px){.navbar{padding:0 20px}.nav-links{display:none}.main{padding:45px 18px}.hero h1{font-size:36px}.hero{margin-bottom:35px}.bottom-panel{grid-template-columns:1fr}.user-actions .login-btn{display:none}.user-info{padding-right:9px}.user-info span{max-width:90px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}}
</style>