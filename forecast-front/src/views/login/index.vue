<template>
  <div class="login-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="login-title">🔐预测系统</span>
      </div>
      <div class="login-form">
        <el-form :model="form" :rules="loginRules" ref="loginForm">
          <el-form-item prop="userName">
            <el-input type="text" v-model="form.useraccount" auto-complete="off" placeholder="请输入用户名">
              <template slot="prepend"><i style="font-size:20px" class="el-icon-user"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item prop="passWord">
            <el-input type="text" v-model="form.password" auto-complete="off" placeholder="请输入密码">
              <template slot="prepend"><i style="font-size:20px" class="el-icon-key"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width:100%;" type="primary" @click="handleLogin" :loading="loading">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import {Login} from "@/api/api";
import ElementUI from "element-ui";
export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      form: {
        useraccount: '',
        password: ''
      },
      loginRules: {
        useraccount: [
          {required: true, message: '请输入账户', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate().then(() => {
        this.loading = true;
        //模拟异步请求后台接口 登录操作
        Login(this.form).then((res) => {
          console.log(res)
          if (res.data.code === 0) {
            setTimeout(() => {
              ElementUI.Message({
                message: '登录成功！',
                type: 'success'
              });
              localStorage.setItem("token", res.data.data.token)
              localStorage.setItem("username", res.data.data.username)
              localStorage.setItem("useravatar", res.data.data.useravatar)
              localStorage.setItem("gender", res.data.data.gender)
              this.$router.push('/');
              this.loading = false;
            }, 1000)
          } else {
            setTimeout(() => {
              ElementUI.Message({
                message: '登录失败！',
                type: 'error'
              });
              // this.$router.push('/login');
              this.loading = false;

            }, 1000)
          }
        })
      }).catch((() => {
        ElementUI.Message({
          message: '输入错误！',
          type: 'warning'
        });
      }))

    }
  }
}
</script>

<style scoped>
.login-page {
  background-image: linear-gradient(180deg, #e1f5e1 0%, #c0d4e0 100%);
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-title {
  font-size: 20px;
}

.box-card {
  width: 375px;
}

</style>

