import axios from "axios";
import { ElMessage } from "element-plus";

// 创建 axios 实例
const request = axios.create({
    baseURL: 'http://localhost:9090',
    timeout: 30000  // 后台接口超时时间
})

// request 拦截器：请求发送前处理
request.interceptors.request.use(
    (config) => {
        config.headers['Content-Type'] = 'application/json';
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// response 拦截器：接口响应后统一处理
request.interceptors.response.use(
    (response) => {
        let res = response.data;
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }
        return res;
    },
    (error) => {
        // 核心修复：先判断 error.response 是否存在，再访问 status
        let errorMsg = '请求失败';

        if (error.response) {
            // 情况1：服务器有响应（状态码非2xx）
            const status = error.response.status;
            switch (status) {
                case 404:
                    errorMsg = '接口不存在';
                    break;
                case 500:
                    errorMsg = '服务器错误';
                    break;
                default:
                    errorMsg = `请求失败：${status} - ${error.response.statusText}`;
                    console.log('请求状态码：', status);
            }
        } else if (error.request) {
            // 情况2：请求已发送，但无响应（网络/超时）
            errorMsg = '网络异常，请检查网络连接或接口地址';
            console.log('网络错误：', error.message);
        } else {
            // 情况3：请求配置错误
            errorMsg = `请求配置错误：${error.message}`;
            console.log('配置错误：', error.message);
        }

        // 统一提示错误信息
        ElMessage.error(errorMsg);
        return Promise.reject(error);
    }
);

// 导出
export default request;