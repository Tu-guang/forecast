<template>
  <div class="welcome-container">
    <a-row type="flex" justify="center" align="top">
      <a-col :span="24" :md="18" :xl="12">
        <div class="welcome-content">
          <div class="welcome-logo">
            <a-icon type="rocket" />
            <span class="title">Welcome to My Site</span>
          </div>
          <div class="welcome-text">
            <p>欢迎来到我的网站!</p>
            <p>我会在这里分享一些有趣的事情，希望你会喜欢。</p>
          </div>
          <a-button type="primary" size="large">开始探索</a-button>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({

  methods: {

  },
});
</script>

<style scoped>
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
</style>
