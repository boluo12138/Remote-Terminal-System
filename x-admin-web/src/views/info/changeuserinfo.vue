<template>
  <div class="ybox">
    <div class="box">
      <el-form
        :model="userForm"
        :rules="rules2"
        ref="userForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item class="myavatar">
          <!-- <el-avatar shape="square" :size="50" :fit="fit" :src="url"></el-avatar>  -->
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <img v-else :src="userForm.url" class="avatar" />
          </el-upload>
        </el-form-item>

        <el-form-item label="昵称：" prop="name">
          <el-input v-model="userForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话：" prop="phone">
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱：" prop="email">
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item class="bottom">
          <el-button type="primary" @click="submitForm('userForm')"
            >提交</el-button
          >
          <el-button @click="resetForm('userForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import store from "../../store";
import userApi from "@/api/userManage";
import OSS from "ali-oss";
export default {
  data() {
    var validateemail = (rule, value, callback) => {
      var regex = new RegExp(
        "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)*.[a-zA-Z0-9]{2,6}$"
      );
      if (value === "") {
        callback(new Error("请输入邮箱"));
      } else if (!regex.test(value)) {
        callback(new Error("邮箱格式不正确"));
      } else {
        callback();
      }
    };
    var validatephone = (rule, value, callback) => {
      var regex = new RegExp(
        "^((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[56])|(17[0-8])|(18[0-9])|(19[1589]))\\d{8}$"
      );
      if (value === "") {
        callback(new Error("请输入手机号"));
      } else if (!regex.test(value)) {
        callback(new Error("电话格式不正确"));
      } else {
        callback();
      }
    };
    return {
      imageUrl: null,
      userForm: {
        id: store.state.user.id,
        // username: store.state.user.username,
        // phone: store.state.user.phone,
        // email: store.state.user.email,
      },
      rules2: {
        changeemail: [{ validator: validateemail, trigger: "blur" }],
        changephone: [{ validator: validatephone, trigger: "blur" }],
      },
    };
  },
  computed: {
    ...mapGetters(["avatar", "username", "phone", "email", "data"]),
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }

      const client = new OSS({
        accessKeyId: "",  #阿里云accessKeyId
        accessKeySecret: "", #阿里云accessKeySecret
        bucket: "boluo12138",
        region: "oss-cn-beijing",
        secure: true,
        // 更多配置项可以根据需要添加
      });
      // http://boluo12138.oss-cn-beijing.aliyuncs.com/1691570713867_3.jpg
      // const fileName = Date.now() + "_" + file.name;
      // 调用OSS SDK的put方法上传文件
      client
        .put(file.name, file)
        .then((response) => {
          console.log("图片上传成功:", response);
          // 在这里可以处理上传成功的逻辑，比如保存返回的文件URL等
          if (response.res.status === 200) {
            // console.log(response.url)
            this.imageUrl = response.url;
            this.userForm.avatar = this.imageUrl;
            // store.commit("user/SET_AVATAR", this.imageUrl);
            console.log(
              "store avatar: ",
              store.state.user.avatar,
              this.userForm.avatar
            );
          }
        })
        .catch((error) => {
          console.error("图片上传失败:", error);
        });
      // this.imageUrl =
      //     "https://project-static-file.oss-cn-hangzhou.aliyuncs.com/avatar/" +
      //     file.name;
      // this.imageUrl =
      //   "https://boluo12138.oss-cn-beijing.aliyuncs.com/" + fileName;
      // this.userForm.avatar = this.imageUrl;
      // // store.commit('user/SET_AVATAR', this.imageUrl);
      // console.log(
      //   "store avatar: ",
      //   store.state.user.avatar,
      //   this.userForm.avatar
      // );

      // return isJPG && isLt2M;
      return false;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm("是否修改个人信息?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              console.log(this.userForm);
              userApi.updateUser(this.userForm).then((response) => {
                console.log(response);
                if (response.code === 20000) {
                  store.dispatch("user/getInfo");
                  // 成功提示
                  this.$message({
                    message: response.message,
                    type: "success",
                  });
                }
                // store.dispatch("user/getInfo");
                console.log("update user: ", this.userForm, store.state.user);
                // 关闭对话框
                this.dialogFormVisible = false;

                this.$router.push({ path: this.redirect || "/" });
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消修改",
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    // 根据id查询用户数据
    userApi.getUserById(this.userForm.id).then((response) => {
      this.userForm = response.data;
      console.log("load user info: ", this.userForm);
      // store.commit('user/SET_AVATAR', this.userForm.url);
    });
  },
};
</script>

<style>
.ybox {
  width: 100%;
  height: 100%;
  /* background-image: url("../../assets/bg2.jpg"); */
  background-size: cover;
  position: absolute;
}

.box {
  width: 400px;
  height: 100%;
  margin: 0 auto;
  padding-top: 50px;
}

.bottom {
  margin-top: 30px;
  margin-left: 50px;
}
.myavatar {
  margin-left: 50px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 20px;
  color: #8c939d;
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}
.avatar {
  width: 150px;
  height: 150px;
  display: block;
}
</style>
