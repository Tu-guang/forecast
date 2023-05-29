import axios from "axios";
import ElementUI from "element-ui";
import Vue from "vue";
import router from "@/router";
// import router from '../router'

Vue.config.productionTip = false;

const request = axios.create({
    timeout: 200000,
    headers: {
        "Content-Type": "application/json;charset=utf-8",
        "token": localStorage.getItem("token")
    },
});
// http response 拦截器
request.interceptors.response.use(
    (response) => {
        // response.data.err 是接口返回值，如果是401，说明token丢失，然后跳转至登录页面
        if (response.data.code === 401) {
            ElementUI.Message.error(response.data.message);
            router.push("/login");
            localStorage.clear();
        }
        return response;
    },
    (error) => {
        ElementUI.Message.error("服务器被端了，快联系管理员");
        return Promise.reject(error);
    }
);

export default request;