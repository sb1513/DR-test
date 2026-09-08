import axios from 'axios';

const service = axios.create({
    timeout: 50000,
    baseURL: "http://localhost:8080",
})

service.interceptors.request.use(
  (config) => {
    // 从本地存储动态获取 token
    const token = sessionStorage.getItem('token');
    if (token) {
      // 为每个请求动态添加 Authorization 头
      config.headers['Authorization'] = `${token}`;
    }
    return config; // 必须返回 config，否则请求会被阻塞
  })

  //回来的时候拦截(自动处理所有异常)
service.interceptors.response.use(
  // 1. 处理 HTTP 2xx 响应（拦截业务异常）
  (response) => {
    const res = response;
    if (res.data.code === 2000)
        return res; // 业务成功直接返回数据
    alert(res.data.msg ); // 业务异常提示
    return Promise.reject(new Error(res.data.message));
  },
  // 2. 处理 HTTP 4xx / 5xx 及网络异常
  (error) => {
    const msg = error.response?.data?.message || '系统异常，请稍后重试';
    alert(msg);
    return Promise.reject(error);
  }
);

export default service;