<script setup>
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const favs = ref([])
const router = useRouter()
const showDetailId = ref([])
const editAsk = ref("")
const editAns = ref("")
const editFavId = ref(null)
const searchQuery = ref("")
const pageNum = ref(1)//当前页数
const pageTotal = ref(1)// 总页数
const pageSize = ref(0)//结果数量

function changePage(num){
  pageNum.value=num
  loadFavs()
}

function loadFavs(){
  //let user_id = sessionStorage.getItem("token")
  axios({
      method: 'get',
      url: `/user/favs/list?pageNum=${pageNum.value}&pageSize=${5}&find=${searchQuery.value}`,
    }).then(res=>{
      let pg = res.data.data
      favs.value=pg.records
      pageTotal.value=pg.pages
      pageSize.value=pg.total
      //console.log(favs.value.length)
      //alert(res.data.msg)
      //console.log(res.data.code)
    })
}

function handleDetail(fav){
  const index = showDetailId.value.indexOf(fav.favId);

  if (index === -1) {
    showDetailId.value.push(fav.favId);
  } else {
    showDetailId.value.splice(index, 1);
  }
}

const Edit = (fav) => {
  if(!showDetailId.value.includes(fav.favId)){
    showDetailId.value.push(fav.favId)
  }
  editAsk.value=fav.favAsk
  editAns.value=fav.favAns
  editFavId.value=fav.favId
}

const saveEdit = (fav) => {
  if(confirm("确定要保存吗?")){
    handleUpdate(fav)
  }
}

const cancelEdit = (fav) => {
  editAsk.value=""
  editAns.value=""
  editFavId.value=null
}

function handleUpdate(fav){
  axios({
    method: 'post',
    url: `/user/favs/update`,
    data: {
      favId: fav.favId,
      userId: fav.userId,
      favAsk: editAsk.value,
      favAns: editAns.value,
    }
  }).then(res=>{
    //favs.value=res.data.data
    //console.log(favs.value.length)
    alert(res.data.msg)
    loadFavs()
  }).catch(err=>{
    console.log(err.message)
  })
}

function handleDelete(fav){
  if(confirm("确认要删除吗?")) {
    axios({
      method: 'post',
      url: `/user/favs/delete`,
      data: fav,
    }).then(res => {
      alert(res.data.msg)
      loadFavs()
    })
  }
}

onMounted(()=>{
  loadFavs()
})
</script>

<template>
  <div>
    <h1>收藏</h1>
    <div v-if="!favs.length">还没有收藏哦</div>
    <form @submit.prevent>
      <input type="text" v-model="searchQuery" placeholder="请输入搜索内容">
      <button @click="loadFavs()">搜索</button>
    </form>
    <div v-for="fav in favs" :key="fav.favId">
      <span>{{ fav.favAsk.split('--')[0] }}<button @click="handleDetail(fav)">{{ showDetailId.includes(fav.favId)?"收起":"详细" }}</button></span>
      <div v-if="showDetailId.includes(fav.favId)">
        <div v-if="editFavId===fav.favId">
          <div>
            <table>提问:</table>
            <input type="text" v-model="editAsk"/>
          </div>
          <div>
            <table>回答:</table>
            <textarea v-model="editAns" rows="10"></textarea>
          </div>
          <button @click="saveEdit(fav)">保存</button>
          <button @click="cancelEdit(fav)">取消</button>
        </div>
        <div v-else>
          <h4>对象:{{ fav.favAsk.split('--')[1] }}</h4>
          <h4>提问:{{ fav.favAsk.split('--')[0] }}</h4>
          <p>{{ fav.favAns }}</p>
        </div>
      </div>
      <button @click="Edit(fav)">编辑</button>
      <button @click="handleDelete(fav)">删除</button>
    </div>
    <div>
      <span v-for="p in pageTotal" @click="changePage(p)">{{ p }}</span>【{{pageNum}}/{{pageTotal}}页,共{{pageSize}}条】
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">
      <button class="btn btn-neutral btn-ghost">首页</button>
    </RouterLink>
  </div>
</template>

<style scoped>

</style>