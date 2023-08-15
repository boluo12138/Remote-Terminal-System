<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  mounted() {
    this.$bus.$on("alertInfo", this.alertInfo);
  },
  beforeDestroy() {
    this.$bus.$off("alertInfo");
  },
  methods: {
    alertInfo(title, message, type) {
      this.$notify({
        title,
        message,
        type,
      });
    },
  },
};
</script>

<style>
.app-main {
  padding: 10px;
}
.el-card {
  margin-bottom: 10px;
}
</style>
