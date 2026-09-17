<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "../../axios/index.js";

const searchQuery = ref("");
const ans = ref("");
const loading = ref(false);
const router = useRouter();
const sid = ref(1)

const systemPrompt = "你是一只狗，你聪明伶俐，听主人的话";

function handlenewchat() {
  sid.value = crypto.randomUUID()
  alert("新对话创建成功")
  searchQuery.value = ''
  ans.value = ''
}

function handlefavs() {
  if (!ans.value) {
    alert("收藏失败");
    return;
  }

  const user = JSON.parse(sessionStorage.getItem("cur_user"));
  if (!user) {
    alert("请先登录");
    router.push({ name: "LoginPage-index" });
    return;
  }

  axios({
    method: "post",
    url: "/user/favs/newfavs",
    data: {
      userId: parseInt(user.id),
      favAsk: searchQuery.value + "--" + systemPrompt,
      favAns: ans.value
    }
  }).then(res => {
    alert(res.data.msg);
  });
}

const send = () => {
  if (!searchQuery.value.trim() || loading.value) return;

  loading.value = true;
  ans.value = "";

  axios.post('/ai/chat', {"systemPrompt": systemPrompt,"ask": searchQuery.value,"sid":sid.value}
  ).then(res => {
    ans.value = res.data.data;
    console.log(res.data);
  }).finally(() => {
    loading.value = false;
  });
};
</script>

<template>
  <div class="dog-page">
    <div class="bg-decoration decoration-one"></div>
    <div class="bg-decoration decoration-two"></div>

    <header class="topbar">
      <div class="brand">
        <div class="brand-icon">🐶</div>
        <div>
          <div class="brand-name">AI Friends</div>
          <div class="brand-subtitle">Your lovely AI companion</div>
        </div>
      </div>

      <RouterLink :to="{ name: 'HomePage-index' }" class="home-btn">
        <span>←</span>
        <span>Home</span>
      </RouterLink>
    </header>

    <main class="chat-container">
      <section class="chat-card">
        <div class="chat-header">
          <div class="character-info">
            <div class="dog-avatar">
              🐶
              <span class="online-dot"></span>
            </div>
            <div>
              <h1>Dog Chat</h1>
              <p>聪明伶俐的狗狗正在等你</p>
            </div>
          </div>

          <div class="status">
            <span></span>
            Online
          </div>
        </div>

        <div class="conversation">
          <div v-if="!searchQuery && !ans && !loading" class="welcome">
            <div class="welcome-icon">🐾</div>
            <h2>汪！你好呀！</h2>
            <p>和你的 AI 狗狗聊聊天吧</p>
            <div class="suggestions">
              <button @click="searchQuery = '你今天开心吗？'">你今天开心吗？</button>
              <button @click="searchQuery = '陪我聊聊天吧'">陪我聊聊天吧</button>
              <button @click="searchQuery = '你喜欢吃什么？'">你喜欢吃什么？</button>
            </div>
          </div>

          <div v-if="searchQuery" class="message user-message">
            <div class="message-avatar">你</div>
            <div class="message-content">
              <div class="message-name">You</div>
              <div class="bubble user-bubble">{{ searchQuery }}</div>
            </div>
          </div>

          <div v-if="loading" class="message ai-message">
            <div class="message-avatar dog-small">🐶</div>
            <div class="message-content">
              <div class="message-name">Dog</div>
              <div class="bubble ai-bubble typing">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>

          <div v-if="ans && !loading" class="message ai-message">
            <div class="message-avatar dog-small">🐶</div>
            <div class="message-content">
              <div class="message-name">Dog</div>
              <div class="bubble ai-bubble">{{ ans }}</div>
            </div>
          </div>
        </div>

        <div class="action-area">
          <button class="favorite-btn" :disabled="!ans || loading" @click="handlefavs">
            <span>♡</span>
            收藏这段对话
          </button>
        </div>

        <div class="action-area">
          <button class="favorite-btn" :disabled="!ans || loading" @click="handlenewchat">
            新对话
          </button>
        </div>

        <div class="input-area">
          <form @submit.prevent="send" class="input-form">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="和狗狗说点什么..."
              :disabled="loading"
            />
            <button type="submit" :disabled="!searchQuery.trim() || loading" class="send-btn">
              <span v-if="!loading">➤</span>
              <span v-else class="spinner"></span>
            </button>
          </form>
          <div class="input-tip">AI 可能会产生不准确的信息，请结合实际情况判断</div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.dog-page {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #fafaff 0%, #f4f2ff 48%, #f8faff 100%);
  color: #27233a;
}

.bg-decoration {
  position: absolute;
  border-radius: 50%;
  filter: blur(5px);
  pointer-events: none;
}

.decoration-one {
  width: 420px;
  height: 420px;
  top: -180px;
  left: -140px;
  background: rgba(139, 92, 246, 0.13);
}

.decoration-two {
  width: 360px;
  height: 360px;
  right: -130px;
  bottom: -150px;
  background: rgba(59, 130, 246, 0.1);
}

.topbar {
  height: 76px;
  padding: 0 7%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  z-index: 5;
  background: rgba(255, 255, 255, 0.76);
  border-bottom: 1px solid rgba(120, 100, 180, 0.1);
  backdrop-filter: blur(18px);
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-icon {
  width: 43px;
  height: 43px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 14px;
  font-size: 24px;
  background: linear-gradient(135deg, #8b5cf6, #6366f1);
  box-shadow: 0 8px 22px rgba(99, 102, 241, 0.24);
}

.brand-name {
  font-size: 18px;
  font-weight: 800;
  color: #27233a;
}

.brand-subtitle {
  margin-top: 1px;
  font-size: 11px;
  color: #9994aa;
}

.home-btn {
  display: flex;
  align-items: center;
  gap: 7px;
  padding: 10px 17px;
  color: #625d76;
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  border: 1px solid #e8e4f1;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.85);
  transition: all 0.2s ease;
}

.home-btn:hover {
  color: #6d4aff;
  border-color: #d8d0ff;
  transform: translateY(-1px);
  box-shadow: 0 7px 18px rgba(88, 67, 160, 0.1);
}

.chat-container {
  width: min(920px, 92%);
  margin: 38px auto;
  position: relative;
  z-index: 2;
}

.chat-card {
  min-height: 700px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid rgba(221, 216, 237, 0.9);
  border-radius: 28px;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 24px 70px rgba(70, 54, 120, 0.12);
  backdrop-filter: blur(20px);
}

.chat-header {
  min-height: 94px;
  padding: 18px 25px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #eeeaf5;
}

.character-info {
  display: flex;
  align-items: center;
  gap: 14px;
}

.dog-avatar {
  width: 58px;
  height: 58px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  border-radius: 18px;
  background: linear-gradient(135deg, #fff7ed, #fef3c7);
  box-shadow: inset 0 0 0 1px rgba(245, 158, 11, 0.12);
}

.online-dot {
  width: 11px;
  height: 11px;
  position: absolute;
  right: 1px;
  bottom: 1px;
  border: 2px solid white;
  border-radius: 50%;
  background: #22c55e;
}

.character-info h1 {
  margin: 0;
  font-size: 21px;
  font-weight: 800;
  color: #28233a;
}

.character-info p {
  margin: 4px 0 0;
  color: #9892a9;
  font-size: 13px;
}

.status {
  display: flex;
  align-items: center;
  gap: 7px;
  padding: 7px 11px;
  border-radius: 20px;
  background: #f4fbf6;
  color: #43a466;
  font-size: 12px;
  font-weight: 600;
}

.status span {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #22c55e;
}

.conversation {
  flex: 1;
  min-height: 390px;
  padding: 32px 30px;
  overflow-y: auto;
}

.welcome {
  min-height: 330px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.welcome-icon {
  width: 82px;
  height: 82px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 18px;
  border-radius: 25px;
  font-size: 40px;
  background: linear-gradient(135deg, #fff7ed, #fef3c7);
  box-shadow: 0 14px 30px rgba(245, 158, 11, 0.12);
}

.welcome h2 {
  margin: 0;
  font-size: 25px;
  font-weight: 800;
  color: #302a43;
}

.welcome p {
  margin: 8px 0 22px;
  color: #9993aa;
  font-size: 14px;
}

.suggestions {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 9px;
}

.suggestions button {
  padding: 9px 14px;
  border: 1px solid #e7e2f0;
  border-radius: 20px;
  background: white;
  color: #777087;
  cursor: pointer;
  transition: all 0.2s;
}

.suggestions button:hover {
  color: #6d4aff;
  border-color: #d7ceff;
  background: #faf9ff;
  transform: translateY(-1px);
}

.message {
  display: flex;
  gap: 12px;
  margin-bottom: 25px;
}

.user-message {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 38px;
  height: 38px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 13px;
  background: #eeeaff;
  color: #7256df;
  font-size: 12px;
  font-weight: 700;
}

.dog-small {
  font-size: 21px;
  background: linear-gradient(135deg, #fff7ed, #fef3c7);
}

.message-content {
  max-width: 72%;
}

.user-message .message-content {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.message-name {
  margin: 0 0 6px 3px;
  color: #aaa4b8;
  font-size: 11px;
  font-weight: 600;
}

.bubble {
  padding: 13px 17px;
  line-height: 1.7;
  font-size: 14px;
  white-space: pre-wrap;
  word-break: break-word;
}

.user-bubble {
  border-radius: 18px 5px 18px 18px;
  background: linear-gradient(135deg, #7c5cff, #6950e8);
  color: white;
  box-shadow: 0 8px 20px rgba(105, 80, 232, 0.17);
}

.ai-bubble {
  border-radius: 5px 18px 18px 18px;
  background: #f7f6fa;
  color: #4d4859;
  border: 1px solid #eeebf3;
}

.typing {
  display: flex;
  align-items: center;
  gap: 5px;
  width: 65px;
  height: 43px;
}

.typing span {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #a69fc0;
  animation: typing 1.2s infinite;
}

.typing span:nth-child(2) {
  animation-delay: 0.15s;
}

.typing span:nth-child(3) {
  animation-delay: 0.3s;
}

@keyframes typing {
  0%, 60%, 100% {
    transform: translateY(0);
    opacity: 0.45;
  }
  30% {
    transform: translateY(-4px);
    opacity: 1;
  }
}

.action-area {
  display: flex;
  justify-content: flex-end;
  padding: 0 28px 15px;
}

.favorite-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 13px;
  border: 1px solid #e4def4;
  border-radius: 10px;
  background: #faf9ff;
  color: #755bd8;
  font-size: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.favorite-btn span {
  font-size: 18px;
  line-height: 1;
}

.favorite-btn:hover:not(:disabled) {
  background: #f1edff;
  transform: translateY(-1px);
}

.favorite-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.input-area {
  padding: 18px 25px 17px;
  border-top: 1px solid #eeeaf5;
  background: rgba(250, 249, 253, 0.8);
}

.input-form {
  display: flex;
  gap: 10px;
  padding: 6px;
  border: 1px solid #e5e0ed;
  border-radius: 17px;
  background: white;
  box-shadow: 0 6px 20px rgba(70, 54, 120, 0.06);
  transition: all 0.2s;
}

.input-form:focus-within {
  border-color: #c9bcff;
  box-shadow: 0 8px 25px rgba(112, 83, 224, 0.1);
}

.input-form input {
  flex: 1;
  min-width: 0;
  padding: 11px 14px;
  outline: none;
  border: none;
  background: transparent;
  color: #332e40;
  font-size: 14px;
}

.input-form input::placeholder {
  color: #b3adbd;
}

.send-btn {
  width: 43px;
  height: 43px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 13px;
  background: linear-gradient(135deg, #8061f4, #6549dc);
  color: white;
  font-size: 19px;
  cursor: pointer;
  box-shadow: 0 7px 15px rgba(105, 80, 232, 0.2);
  transition: all 0.2s;
}

.send-btn:hover:not(:disabled) {
  transform: translateY(-1px) scale(1.02);
  box-shadow: 0 9px 20px rgba(105, 80, 232, 0.27);
}

.send-btn:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

.spinner {
  width: 17px;
  height: 17px;
  border: 2px solid rgba(255, 255, 255, 0.4);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.input-tip {
  margin-top: 9px;
  text-align: center;
  color: #aaa5b4;
  font-size: 10px;
}

@media (max-width: 650px) {
  .topbar {
    height: 68px;
    padding: 0 5%;
  }

  .brand-subtitle {
    display: none;
  }

  .brand-name {
    font-size: 16px;
  }

  .brand-icon {
    width: 39px;
    height: 39px;
    font-size: 21px;
  }

  .home-btn {
    padding: 8px 11px;
  }

  .chat-container {
    width: 94%;
    margin: 18px auto;
  }

  .chat-card {
    min-height: calc(100vh - 105px);
    border-radius: 21px;
  }

  .chat-header {
    padding: 15px 17px;
  }

  .dog-avatar {
    width: 48px;
    height: 48px;
    font-size: 27px;
  }

  .character-info h1 {
    font-size: 18px;
  }

  .character-info p {
    font-size: 11px;
  }

  .status {
    display: none;
  }

  .conversation {
    padding: 25px 17px;
  }

  .message-content {
    max-width: 80%;
  }

  .bubble {
    font-size: 13px;
  }

  .input-area {
    padding: 13px 14px;
  }

  .action-area {
    padding: 0 17px 12px;
  }
}
</style>