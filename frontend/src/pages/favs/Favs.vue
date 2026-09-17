<script setup>
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const favs=ref([])
const router=useRouter()
const showDetailId=ref([])
const editAsk=ref("")
const editAns=ref("")
const editFavId=ref(null)
const searchQuery=ref("")
const pageNum=ref(1)
const pageTotal=ref(1)
const pageSize=ref(0)

function changePage(num){
  pageNum.value=num
  loadFavs()
}

function loadFavs(){
  axios({
    method:'get',
    url:`/user/favs/list?pageNum=${pageNum.value}&pageSize=5&find=${searchQuery.value}`,
  }).then(res=>{
    let pg=res.data.data
    favs.value=pg.records
    pageTotal.value=pg.pages
    pageSize.value=pg.total
  })
}

function handleDetail(fav){
  const index=showDetailId.value.indexOf(fav.favId)
  if(index===-1) showDetailId.value.push(fav.favId)
  else showDetailId.value.splice(index,1)
}

const Edit=(fav)=>{
  if(!showDetailId.value.includes(fav.favId)) showDetailId.value.push(fav.favId)
  editAsk.value=fav.favAsk
  editAns.value=fav.favAns
  editFavId.value=fav.favId
}

const saveEdit=(fav)=>{
  if(confirm("确定要保存吗?")) handleUpdate(fav)
}

const cancelEdit=()=>{
  editAsk.value=""
  editAns.value=""
  editFavId.value=null
}

function handleUpdate(fav){
  axios({
    method:'post',
    url:`/user/favs/update`,
    data:{
      favId:fav.favId,
      userId:fav.userId,
      favAsk:editAsk.value,
      favAns:editAns.value,
    }
  }).then(res=>{
    alert(res.data.msg)
    editAsk.value=""
    editAns.value=""
    editFavId.value=null
    loadFavs()
  }).catch(err=>{
    console.log(err.message)
  })
}

function handleDelete(fav){
  if(confirm("确认要删除吗?")){
    axios({
      method:'post',
      url:`/user/favs/delete`,
      data:fav,
    }).then(res=>{
      alert(res.data.msg)
      loadFavs()
    })
  }
}

onMounted(()=>loadFavs())
</script>

<template>
  <div class="page">
    <header class="navbar">
      <RouterLink :to="{name:'HomePage-index'}" class="brand">
        <div class="logo">✦</div>
        <span>AI Friends</span>
      </RouterLink>
      <div class="nav-title">我的收藏</div>
      <RouterLink :to="{name:'HomePage-index'}" class="home-btn">← 首页</RouterLink>
    </header>

    <main class="main">
      <div class="page-header">
        <div>
          <div class="eyebrow">MY COLLECTION</div>
          <h1>我的收藏</h1>
          <p>保存你与 AI 伙伴之间的精彩对话</p>
        </div>
        <div class="count-card">
          <strong>{{ pageSize }}</strong>
          <span>条收藏</span>
        </div>
      </div>

      <form class="search-box" @submit.prevent="loadFavs">
        <span class="search-icon">⌕</span>
        <input v-model="searchQuery" type="text" placeholder="搜索收藏内容..."/>
        <button type="submit">搜索</button>
      </form>

      <div v-if="!favs.length" class="empty">
        <div class="empty-icon">♡</div>
        <h2>还没有收藏哦</h2>
        <p>收藏一些有趣的 AI 对话，它们会出现在这里</p>
        <RouterLink :to="{name:'HomePage-index'}">去首页看看 →</RouterLink>
      </div>

      <div v-else class="fav-list">
        <div v-for="(fav,index) in favs" :key="fav.favId" class="fav-card">
          <div class="fav-top">
            <div class="number">{{ String((pageNum-1)*5+index+1).padStart(2,'0') }}</div>
            <div class="question">
              <span class="question-label">提问</span>
              <h3>{{ fav.favAsk.split('--')[0] }}</h3>
            </div>
            <button class="detail-btn" @click="handleDetail(fav)">
              {{ showDetailId.includes(fav.favId) ? "收起 ↑" : "详细 ↓" }}
            </button>
          </div>

          <div v-if="showDetailId.includes(fav.favId)" class="detail">
            <div v-if="editFavId===fav.favId" class="edit-area">
              <div class="edit-group">
                <label>提问内容</label>
                <input v-model="editAsk" type="text"/>
              </div>
              <div class="edit-group">
                <label>AI 回答</label>
                <textarea v-model="editAns" rows="8"></textarea>
              </div>
              <div class="edit-actions">
                <button class="save-btn" @click="saveEdit(fav)">保存修改</button>
                <button class="cancel-btn" @click="cancelEdit">取消</button>
              </div>
            </div>

            <div v-else class="conversation">
              <div class="info-row">
                <span>对象</span>
                <strong>{{ fav.favAsk.split('--')[1] }}</strong>
              </div>
              <div class="conversation-block">
                <div class="message-label user-label">YOU</div>
                <div class="message">{{ fav.favAsk.split('--')[0] }}</div>
              </div>
              <div class="conversation-block">
                <div class="message-label ai-label">AI FRIEND</div>
                <div class="answer">{{ fav.favAns }}</div>
              </div>
            </div>
          </div>

          <div class="card-bottom">
            <span class="fav-id">#{{ fav.favId }}</span>
            <div class="actions">
              <button class="edit-btn" @click="Edit(fav)">✎ 编辑</button>
              <button class="delete-btn" @click="handleDelete(fav)">⌫ 删除</button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="favs.length" class="pagination">
        <button :disabled="pageNum===1" @click="changePage(pageNum-1)">‹</button>
        <button v-for="p in pageTotal" :key="p" :class="{active:p===pageNum}" @click="changePage(p)">{{ p }}</button>
        <button :disabled="pageNum===pageTotal" @click="changePage(pageNum+1)">›</button>
      </div>

      <div v-if="favs.length" class="page-info">{{ pageNum }} / {{ pageTotal }} 页 · 共 {{ pageSize }} 条收藏</div>
    </main>
  </div>
</template>

<style scoped>
*{box-sizing:border-box}
.page{min-height:100vh;background:#f7f8fc;color:#171923;font-family:Inter,-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif}
.navbar{height:72px;padding:0 7%;display:flex;align-items:center;border-bottom:1px solid #e9ebf1;background:rgba(255,255,255,.88);backdrop-filter:blur(18px);position:sticky;top:0;z-index:10}
.brand{display:flex;align-items:center;gap:10px;color:#171923;text-decoration:none;font-size:19px;font-weight:800}
.logo{width:36px;height:36px;border-radius:11px;display:flex;align-items:center;justify-content:center;color:white;background:linear-gradient(135deg,#6366f1,#a855f7);box-shadow:0 8px 20px rgba(99,102,241,.22)}
.nav-title{margin:auto;font-size:14px;font-weight:650;color:#777c89}
.home-btn{padding:8px 15px;border-radius:10px;color:#626672;text-decoration:none;font-size:13px;font-weight:650;transition:.2s}
.home-btn:hover{background:#f0f0ff;color:#6366d9}
.main{max-width:950px;margin:auto;padding:55px 25px 70px}
.page-header{display:flex;align-items:end;justify-content:space-between;margin-bottom:30px}
.eyebrow{font-size:10px;letter-spacing:2px;font-weight:800;color:#7779db;margin-bottom:9px}
.page-header h1{font-size:37px;letter-spacing:-1.5px;margin:0 0 7px;font-weight:850}
.page-header p{margin:0;color:#8b909d;font-size:13px}
.count-card{height:70px;min-width:105px;padding:12px 18px;border-radius:16px;background:white;border:1px solid #e7e9ef;text-align:center;box-shadow:0 7px 25px rgba(30,35,55,.04)}
.count-card strong{display:block;font-size:23px;color:#6366d9}
.count-card span{font-size:10px;color:#969aa6}
.search-box{height:53px;background:white;border:1px solid #e4e6ed;border-radius:14px;display:flex;align-items:center;padding:5px 6px 5px 16px;margin-bottom:22px;box-shadow:0 6px 22px rgba(30,35,55,.035);transition:.2s}
.search-box:focus-within{border-color:#b9b8ee;box-shadow:0 0 0 4px rgba(99,102,241,.07)}
.search-icon{font-size:23px;color:#969ba8;margin-right:9px}
.search-box input{flex:1;border:0;outline:0;background:transparent;font-size:13px;color:#292c35}
.search-box input::placeholder{color:#adb1ba}
.search-box button{height:41px;border:0;border-radius:10px;padding:0 20px;background:#171923;color:white;font-size:12px;font-weight:700;cursor:pointer;transition:.2s}
.search-box button:hover{background:#30323d}
.empty{text-align:center;background:white;border:1px solid #e8e9ef;border-radius:20px;padding:65px 20px}
.empty-icon{width:62px;height:62px;border-radius:20px;background:#f0efff;color:#7775dc;display:flex;align-items:center;justify-content:center;font-size:29px;margin:0 auto 17px}
.empty h2{font-size:20px;margin:0 0 7px}
.empty p{color:#969aa6;font-size:13px;margin:0 0 20px}
.empty a{font-size:13px;color:#6366d9;text-decoration:none;font-weight:700}
.fav-list{display:flex;flex-direction:column;gap:14px}
.fav-card{background:white;border:1px solid #e5e7ed;border-radius:18px;overflow:hidden;box-shadow:0 6px 24px rgba(30,35,55,.035);transition:.25s}
.fav-card:hover{border-color:#dcdcf0;box-shadow:0 10px 30px rgba(30,35,55,.07)}
.fav-top{min-height:82px;display:flex;align-items:center;padding:15px 20px;gap:16px}
.number{width:38px;height:38px;border-radius:11px;background:#f1f0ff;color:#7372dc;display:flex;align-items:center;justify-content:center;font-size:11px;font-weight:800;flex:none}
.question{flex:1;min-width:0}
.question-label{font-size:9px;color:#9297a4;font-weight:800;letter-spacing:1px}
.question h3{font-size:14px;margin:4px 0 0;font-weight:700;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}
.detail-btn{border:0;background:#f5f5f9;color:#737681;border-radius:9px;padding:8px 12px;font-size:11px;font-weight:700;cursor:pointer;transition:.2s}
.detail-btn:hover{background:#ededff;color:#6565d7}
.detail{padding:0 20px 20px;border-top:1px solid #f0f1f4}
.conversation{padding:20px 5px 3px}
.info-row{display:flex;gap:15px;align-items:center;background:#fafafe;border-radius:10px;padding:10px 13px;margin-bottom:17px;font-size:12px}
.info-row span{color:#969aa6}
.info-row strong{color:#6366d9}
.conversation-block{margin-bottom:18px}
.message-label{font-size:9px;font-weight:800;letter-spacing:1.3px;margin-bottom:7px}
.user-label{color:#777b86}
.ai-label{color:#7771dc}
.message{background:#f6f7f9;border-radius:10px;padding:12px 14px;font-size:13px;line-height:1.7;color:#3c404a}
.answer{background:#f4f2ff;border-radius:10px;padding:13px 14px;font-size:13px;line-height:1.8;color:#3d4050;white-space:pre-wrap}
.edit-area{padding:20px 5px 3px}
.edit-group{margin-bottom:15px}
.edit-group label{display:block;font-size:11px;font-weight:700;color:#666a76;margin-bottom:7px}
.edit-group input,.edit-group textarea{width:100%;border:1px solid #e1e3ea;border-radius:10px;background:#fafbfc;padding:11px 13px;outline:0;font-family:inherit;font-size:13px;resize:vertical;transition:.2s}
.edit-group input:focus,.edit-group textarea:focus{background:white;border-color:#8a89df;box-shadow:0 0 0 3px rgba(99,102,241,.07)}
.edit-actions{display:flex;gap:9px}
.save-btn,.cancel-btn{border:0;border-radius:9px;padding:9px 16px;font-size:11px;font-weight:700;cursor:pointer}
.save-btn{background:#6366d9;color:white}
.cancel-btn{background:#f0f1f4;color:#6f737d}
.card-bottom{height:48px;padding:0 20px;border-top:1px solid #f0f1f4;display:flex;align-items:center;justify-content:space-between}
.fav-id{font-size:10px;color:#b0b3bc}
.actions{display:flex;gap:5px}
.actions button{border:0;border-radius:8px;padding:7px 11px;font-size:10px;font-weight:700;cursor:pointer;transition:.2s}
.edit-btn{background:#f2f2ff;color:#6769d2}
.edit-btn:hover{background:#e8e8ff}
.delete-btn{background:#fff2f2;color:#d65d5d}
.delete-btn:hover{background:#ffe7e7}
.pagination{display:flex;justify-content:center;align-items:center;gap:6px;margin-top:30px}
.pagination button{width:34px;height:34px;border:1px solid #e2e4eb;border-radius:9px;background:white;color:#737783;font-size:12px;cursor:pointer;transition:.2s}
.pagination button:hover:not(:disabled){border-color:#c8c8ed;color:#6565d7;background:#f8f8ff}
.pagination button.active{background:#6366d9;border-color:#6366d9;color:white;box-shadow:0 6px 15px rgba(99,102,241,.2)}
.pagination button:disabled{opacity:.35;cursor:not-allowed}
.page-info{text-align:center;color:#999da8;font-size:10px;margin-top:12px}
@media(max-width:600px){.navbar{padding:0 18px}.nav-title{display:none}.main{padding:35px 15px}.page-header{align-items:start}.page-header h1{font-size:30px}.count-card{min-width:80px;height:62px}.fav-top{padding:13px 14px;gap:10px}.fav-top .number{width:32px;height:32px}.detail{padding:0 14px 15px}.card-bottom{padding:0 14px}.home-btn{padding:7px 10px}.question h3{font-size:13px}}
</style>
