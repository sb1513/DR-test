<script setup>
import {ref} from "vue";

const searchQuery=ref('')
const ans=ref('')
const api_key='sk-ws-H.PMMRMPX.PuCC.MEQCIDzt30LX8eQ7rtRq_d-_fd6bYhDT9_IZKSHktCZawLxmAiBEyT8fUXCapD40pAThgBM3ps0NvWG8J23r4j2jE1GEtg'

const send = () =>  {
// 1. 准备要发送的 JSON 数据对象
  const jsonData = {
    "model": "www.modelscope.cn/empero-ai/Qwen3.8-9B-Distill-GGUF",       // 必填，模型名称
    "messages": [                // 必填，消息列表
      {"role": "system", "content": "你是一只狗，你聪明伶俐，听主人的话"},
      {"role": "user", "content": searchQuery.value},
    ],
    "think": false,
    "stream": false,             // 可选，是否开启流式输出
    "options": {                 // 可选，模型参数
      "temperature": 0.7,
      "max_tokens": 50000,
    }
  };

// 2. 发起 fetch POST 请求
  fetch('http://localhost:11434/api/chat', {
    method: 'POST', // 指定请求方法为 POST
    headers: {
      'Content-Type': 'application/json', // 告诉服务器发送的是 JSON 格式
    },
    body: JSON.stringify(jsonData) // 将 JS 对象转换为 JSON 字符串
  })
      .then(response => {
        // 检查请求是否成功（状态码在 200-299 之间）
        if (!response.ok) {
          throw new Error(`HTTP 错误! 状态码: ${response.status}`);
        }
        // 将响应体解析为 JSON 对象
        return response.json();
      })
      .then(data => {
        // 成功获取并解析数据后的处理逻辑
        console.log('请求成功，返回数据:', data);
        //document.getElementById("answer").innerHTML=data.message.content
        ans.value=data.message.content
        //console.log(ans.value)
      })
      .catch(error => {
        // 捕获网络错误或解析过程中的异常
        console.error('请求失败:', error);
      });
}
</script>

<template>
  <div class="text-2xl">Dog Chat</div>
  <form @submit.prevent="send()">
    <input type="text" v-model="searchQuery" placeholder="输入你想问的内容"/>
    <button class="btn btn-neutral cursor-pointer btn-ghost">send</button>
  </form>
  <RouterLink :to="{name: 'HomePage-index'}">
    <button class="btn btn-neutral cursor-pointer btn-ghost">Home</button>
  </RouterLink>
  <div>{{ans}}</div>

</template>

<style scoped>

</style>