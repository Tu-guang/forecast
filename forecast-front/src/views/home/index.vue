<template>
  <a-layout class="index">
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo"/>
      <a-menu
          v-model="selectedKeys"
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1" @click="nextPage(1)">首页</a-menu-item>
        <a-menu-item key="2" @click="nextPage(2)">预测</a-menu-item>
        <a-menu-item class="menu-item" style="float: right">
          <div class="user-avatar">
            <a-avatar :src="avatarUrl" :size="36"/>
            <a-dropdown>
              <span class="user-name">{{ username }}</span>
              <a-icon type="down"/>
              <a-menu slot="overlay">
                <a-menu-item key="logout" @click="handleLogout">退出登录</a-menu-item>
              </a-menu>
            </a-dropdown>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
      <a-breadcrumb :style="{ margin: '16px 0' }">
        <a-breadcrumb-item>Home</a-breadcrumb-item>
        <a-breadcrumb-item>List</a-breadcrumb-item>
      </a-breadcrumb>
      <div v-if="isNextPage===1" :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">
        <welcome></welcome>
      </div>
      <div v-if="isNextPage===2" :style="{ background: '#fff', padding: '24px', minHeight: '800px' }">
        <UploadImg></UploadImg>
      </div>
    </a-layout-content>
    <a-layout-footer class="footer" :style="{ textAlign: 'center' }">
      Ant Design ©2018 Created by Ant UED
    </a-layout-footer>
  </a-layout>
</template>
<script>
import {defineComponent, ref} from 'vue';
import UploadImg from '../../components/UploadImg'
import Welcome from '../../components/Welcome'
import {Logout} from "@/api/api";

export default defineComponent({
  setup() {
    return {
      selectedKeys: ref(['1']),
      isNextPage: 1,
      avatarUrl: localStorage.getItem("useravatar"),
      username: localStorage.getItem("username"),
    };
  },
  components: {
    UploadImg,
    Welcome
  },
  methods: {
    nextPage(page) {
      console.log(page)
      this.isNextPage = page
    },
    handleLogout() {
      // 进行退出登录操作
      Logout().then((res) => {
        if (res.data.code === 0) {
          localStorage.clear()
          this.$message.success("退出登录成功")
          location.reload()
        }
      })
    },
  }
});
</script>
<style>
#components-layout-demo-fixed .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 24px 16px 0;
  float: left;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}

index {
  width: 100%;
  height: 100%;
  margin: 0;
}

.welcome-container {
  background: linear-gradient(to bottom, #3498db, #2980b9);
}

.welcome-content {
  text-align: center;
  margin: 3em 0;
  padding: 2em;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
}

.welcome-logo {
  margin-bottom: 1em;
}

.welcome-logo .title {
  font-size: 2.5em;
  font-weight: bold;
  margin-left: 10px;
  vertical-align: middle;
}

.welcome-text {
  font-size: 1.2em;
  line-height: 1.8em;
  margin-bottom: 2em;
}

.menu-item {
  background-color: transparent !important;
}

</style>