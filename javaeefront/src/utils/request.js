import axios from "axios";
import { ElMessage } from "element-plus";

// 创建 axios 实例
const request = axios.create({
    baseURL: 'http://localhost:9090',
    timeout: 30000  // 后台接口超时时间
})

// request 拦截器
// 可以在请求发送前对请求做一些处理
request.interceptors.request.use(
    (config) => {
        config.headers['Content-Type'] = 'application/json';
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    (response) => {
        let res = response.data;
        // 兼容服务端返回的字符串数据
        if (typeof res ==='string') {
            res = res? JSON.parse(res) : res;
        }
        return res;
    },
    (error) => {

    if(error.response.status === 404) {
        ElMessage.error('接口不存在')
    }else if(error.response.status===500){
        ElMessage.error('服务器错误')
    }else {
        console.log(error.response.status);
    }

        return Promise.reject(error);
    }
);
// 导出
export default request;