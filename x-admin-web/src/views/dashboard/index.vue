<template>
  <div class="indexbox">
    <div class="zbox">
      <div class="abc">
        <img class="myimg" :src="avatar" />
      </div>
      <div>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-card shadow="always"> 昵称：{{ name }} </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="always"> 账号：{{ username }} </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-card shadow="always"> 电话：{{ phone }} </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="always"> 邮箱：{{ email }} </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-card shadow="always">
              <!-- 身份：{{ status == 0 ? "普通用户" : "管理员" }} -->
              身份：{{ roles }}
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="always"> 创建时间：{{ create_time }} </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "Dashboard",
  computed: {
    ...mapGetters([
      "name",
      "token",
      "avatar",
      "username",
      "email",
      "phone",
      "status",
      "create_time",
      "roles",
    ]),
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.indexbox {
  width: 100%;
  height: 100%;
  background-image: url("../../assets/bg2.jpg");
  background-size: cover;
  position: absolute;
}
.zbox {
  width: 800px;
  margin: 50px auto;
}
.abc {
  text-align: center;
}
.myimg {
  width: 150px;
  height: 150px;
  margin-bottom: 30px;
}
.el-row {
  margin-bottom: 30px;
}
</style>
