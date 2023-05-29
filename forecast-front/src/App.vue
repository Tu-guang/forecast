<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted() {},
}
</script>
<style>
html,body, #app{
  width: 100%;
  height: 100%;
  margin: 0;
}
#app {
  display: flex;
  flex-direction: column;
}

</style>