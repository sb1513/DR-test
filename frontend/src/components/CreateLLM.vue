<script setup>
import {ref} from 'vue'
import {useRouter} from "vue-router";
import axios from "../axios/index.js";

const props=defineProps({
  title:{
    type:String,
    required:true
  },
  systemprompt:{
    type:String,
    required:true
  }
});

const searchQuery=ref('')
const ans=ref('')
const loading=ref(false)
const router=useRouter()
const sid = ref(1)

function handlenewchat() {
  sid.value = crypto.randomUUID()
  alert("新对话创建成功")
  searchQuery.value = ''
  ans.value = ''
}

function handlefavs(){
  if(!ans.value){
    alert("收藏失败")
    return
  }
  let user_id=JSON.parse(sessionStorage.getItem("cur_user")).id
  axios({
    method:'post',
    url:'/user/favs/newfavs',
    data:{
      userId:parseInt(user_id),
      favAsk:searchQuery.value+"--"+props.systemprompt,
      favAns:ans.value,
    }
  }).then(res=>{
    alert(res.data.msg)
  })
}

const send = () => {
  if (!searchQuery.value.trim() || loading.value) return;

  loading.value = true;
  ans.value = "";

  axios.post('/ai/chat', {"systemPrompt": props.systemprompt,"ask": searchQuery.value,"sid":sid.value}
  ).then(res => {
    ans.value = res.data.data;
    console.log(res.data);
  }).finally(() => {
    loading.value = false;
  });
};
</script>

<template>
  <div class="chat-page">
    <header class="chat-header">
      <RouterLink :to="{name:'HomePage-index'}" class="back-btn">←</RouterLink>
      <div class="character">
        <div class="avatar">{{ title.includes('Dog')?'🐶':title.includes('Cat')?'🐱':title.includes('Tiger')?'🐯':'✦' }}</div>
        <div>
          <h1>{{ title }}</h1>
          <div class="online"><i></i> AI 伙伴在线</div>
        </div>
      </div>
      <div class="header-actions">
        <button @click="handlefavs" class="header-fav" :disabled="!ans || loading">♡ 收藏</button>
        <button @click="handlenewchat" class="header-fav" :disabled="!ans || loading">新对话</button>
        <RouterLink :to="{name:'HomePage-index'}" class="home-link">Home</RouterLink>
      </div>
    </header>

    <main class="chat-main">
      <div v-if="!searchQuery && !ans && !loading" class="welcome">
        <div class="welcome-avatar">{{ title.includes('Dog')?'🐶':title.includes('Cat')?'🐱':title.includes('Tiger')?'🐯':'✦' }}</div>
        <div class="welcome-badge">AI FRIEND</div>
        <h2>你好，我是 {{ title }}</h2>
        <p>有什么想和我聊聊的吗？输入你的问题，开始我们的对话吧。</p>
      </div>

      <div v-if="searchQuery" class="message user-message">
        <div class="message-avatar">YOU</div>
        <div class="bubble user-bubble">{{ searchQuery }}</div>
      </div>

      <div v-if="loading" class="message ai-message">
        <div class="message-avatar ai-avatar">{{ title.includes('Dog')?'🐶':title.includes('Cat')?'🐱':title.includes('Tiger')?'🐯':'✦' }}</div>
        <div class="bubble ai-bubble typing">
          <span></span><span></span><span></span>
        </div>
      </div>

      <div v-if="ans" class="message ai-message">
        <div class="message-avatar ai-avatar">{{ title.includes('Dog')?'🐶':title.includes('Cat')?'🐱':title.includes('Tiger')?'🐯':'✦' }}</div>
        <div class="bubble ai-bubble">
          <div class="answer-title">{{ title }}</div>
          <div class="answer-text">{{ ans }}</div>
        </div>
      </div>
    </main>

    <div class="input-area">
      <form class="input-wrapper" @submit.prevent="send">
        <input v-model="searchQuery" type="text" :placeholder="`和 ${title} 说点什么...`" :disabled="loading"/>
        <button type="submit" :disabled="!searchQuery.trim() || loading" class="send-btn">
          <span v-if="!loading">↑</span>
          <span v-else class="loader"></span>
        </button>
      </form>
      <div class="input-tip">AI 可能会产生错误信息，请核实重要内容</div>
    </div>
  </div>
</template>

<style scoped>
*{box-sizing:border-box}
.chat-page{min-height:100vh;background:#f7f8fc;color:#171923;font-family:Inter,-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif;display:flex;flex-direction:column}
.chat-header{height:72px;display:flex;align-items:center;padding:0 6%;background:rgba(255,255,255,.9);backdrop-filter:blur(18px);border-bottom:1px solid #e8e9ef;position:sticky;top:0;z-index:10}
.back-btn{width:38px;height:38px;border-radius:11px;background:#f2f3f7;display:flex;align-items:center;justify-content:center;color:#686c78;text-decoration:none;font-size:19px;margin-right:15px;transition:.2s}
.back-btn:hover{background:#ededff;color:#6366d9}
.character{display:flex;align-items:center;gap:11px}
.avatar{width:40px;height:40px;border-radius:13px;background:#f0efff;display:flex;align-items:center;justify-content:center;font-size:23px;box-shadow:inset 0 0 0 1px #e4e2fa}
.character h1{font-size:15px;margin:0 0 3px;font-weight:800}
.online{font-size:10px;color:#8d929f;display:flex;align-items:center;gap:5px}
.online i{width:6px;height:6px;border-radius:50%;background:#5cc78a;box-shadow:0 0 0 3px rgba(92,199,138,.12)}
.header-actions{margin-left:auto;display:flex;align-items:center;gap:8px}
.header-fav,.home-link{height:37px;border:1px solid #e2e4eb;border-radius:10px;background:white;padding:0 13px;color:#6d717d;font-size:11px;font-weight:700;cursor:pointer;text-decoration:none;display:flex;align-items:center;transition:.2s}
.header-fav:hover:not(:disabled){border-color:#d1d0f3;color:#6366d9;background:#fafaff}
.header-fav:disabled{opacity:.4;cursor:not-allowed}
.home-link:hover{background:#f3f3ff;color:#6366d9}
.chat-main{width:100%;max-width:850px;flex:1;margin:auto;padding:50px 25px 160px}
.welcome{text-align:center;margin-top:10vh}
.welcome-avatar{width:80px;height:80px;border-radius:25px;background:#eeedff;display:flex;align-items:center;justify-content:center;font-size:43px;margin:0 auto 20px;box-shadow:0 15px 35px rgba(99,102,241,.1)}
.welcome-badge{display:inline-block;font-size:9px;letter-spacing:2px;font-weight:800;color:#7778da;background:#eeedff;padding:6px 11px;border-radius:20px;margin-bottom:12px}
.welcome h2{font-size:25px;letter-spacing:-.8px;margin:0 0 9px;font-weight:800}
.welcome p{max-width:430px;margin:auto;color:#9397a4;font-size:13px;line-height:1.7}
.message{display:flex;gap:12px;margin-bottom:28px;animation:appear .25s ease}
@keyframes appear{from{opacity:0;transform:translateY(7px)}to{opacity:1;transform:translateY(0)}}
.user-message{justify-content:flex-end}
.message-avatar{width:32px;height:32px;border-radius:10px;display:flex;align-items:center;justify-content:center;flex:none;font-size:9px;font-weight:800;color:#8a8e99;background:#e9ebf0}
.ai-avatar{font-size:18px;background:#eeedff}
.bubble{max-width:75%;padding:13px 16px;border-radius:16px;font-size:13px;line-height:1.75}
.user-bubble{color:white;background:linear-gradient(135deg,#6366f1,#8465e9);border-bottom-right-radius:5px;box-shadow:0 8px 20px rgba(99,102,241,.16)}
.ai-bubble{background:white;border:1px solid #e7e8ee;border-bottom-left-radius:5px;color:#383b45;box-shadow:0 7px 25px rgba(30,35,55,.045)}
.answer-title{font-size:10px;color:#706fd3;font-weight:800;letter-spacing:.8px;margin-bottom:7px}
.answer-text{white-space:pre-wrap}
.typing{display:flex;gap:5px;padding:17px 19px}
.typing span{width:6px;height:6px;background:#9698a5;border-radius:50%;animation:bounce 1.2s infinite}
.typing span:nth-child(2){animation-delay:.15s}
.typing span:nth-child(3){animation-delay:.3s}
@keyframes bounce{0%,60%,100%{transform:translateY(0);opacity:.45}30%{transform:translateY(-5px);opacity:1}}
.input-area{position:fixed;left:0;right:0;bottom:0;background:linear-gradient(transparent,#f7f8fc 22%);padding:35px 20px 20px;z-index:5}
.input-wrapper{max-width:800px;height:62px;margin:auto;background:white;border:1px solid #dedfe8;border-radius:17px;padding:7px 8px 7px 19px;display:flex;align-items:center;box-shadow:0 12px 40px rgba(30,35,55,.1);transition:.2s}
.input-wrapper:focus-within{border-color:#aaa9e5;box-shadow:0 14px 45px rgba(99,102,241,.12),0 0 0 4px rgba(99,102,241,.05)}
.input-wrapper input{height:100%;flex:1;border:0;outline:0;background:transparent;font-size:14px;color:#272a34;min-width:0}
.input-wrapper input::placeholder{color:#a9adb8}
.input-wrapper input:disabled{opacity:.6}
.send-btn{width:46px;height:46px;border:0;border-radius:13px;background:linear-gradient(135deg,#6366f1,#8b5cf6);color:white;font-size:24px;display:flex;align-items:center;justify-content:center;cursor:pointer;box-shadow:0 7px 17px rgba(99,102,241,.22);transition:.2s}
.send-btn:hover:not(:disabled){transform:translateY(-2px);box-shadow:0 10px 22px rgba(99,102,241,.3)}
.send-btn:disabled{opacity:.4;cursor:not-allowed;box-shadow:none}
.loader{width:17px;height:17px;border:2px solid rgba(255,255,255,.4);border-top-color:white;border-radius:50%;animation:spin .7s linear infinite}
@keyframes spin{to{transform:rotate(360deg)}}
.input-tip{text-align:center;color:#a4a8b2;font-size:9px;margin-top:8px}
@media(max-width:600px){.chat-header{padding:0 15px}.header-fav{font-size:0;width:37px;padding:0;justify-content:center}.header-fav:first-letter{font-size:18px}.home-link{font-size:0;width:37px;padding:0;justify-content:center}.home-link:after{content:"⌂";font-size:17px}.chat-main{padding:35px 15px 145px}.bubble{max-width:86%}.welcome{margin-top:7vh}.welcome h2{font-size:22px}.input-area{padding:30px 12px 15px}.input-wrapper{height:57px;border-radius:15px}.send-btn{width:43px;height:43px}}
</style>