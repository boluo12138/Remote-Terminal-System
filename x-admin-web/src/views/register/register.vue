<template>
  <div class="wrapper">
    <div
      style="
        margin: 90px auto;
        background-color: #fff;
        width: 420px;
        height: 450px;
        padding: 20px;
        border-radius: 10px;
      "
    >
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>注 册</b>
      </div>
      <el-form :model="registerForm" :rules="rules" ref="registerForm">
        <el-form-item prop="username">
          <el-input
            placeholder="请输入账号"
            size="medium"
            prefix-icon="el-icon-user"
            v-model="registerForm.username"
            >123qwe</el-input
          >
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            size="medium"
            prefix-icon="el-icon-lock"
            show-password
            v-model="registerForm.password"
            >@12345Qwe</el-input
          >
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            placeholder="请确认密码"
            size="medium"
            prefix-icon="el-icon-lock"
            show-password
            v-model="registerForm.confirmPassword"
            >@12345Qwe</el-input
          >
        </el-form-item>
        <el-form-item prop="email">
          <el-input
            placeholder="请输入邮箱"
            size="medium"
            prefix-icon="el-icon-message"
            v-model="registerForm.email"
            >123@qq.com</el-input
          >
        </el-form-item>

        <el-form-item prop="code">
          <el-input
            style="width: 200px"
            placeholder="请确认验证码"
            size="medium"
            prefix-icon="el-icon-position"
            v-model="registerForm.code"
          ></el-input>
          <el-button
            type="primary"
            size="medium"
            :disabled="disable"
            @click="sendVerificationCode"
            style="float: right"
          >
            {{ buttonName }}
          </el-button>
        </el-form-item>

        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button
            type="warning"
            size="small"
            autocomplete="off"
            @click="$router.push('/login')"
            >返回登录</el-button
          >
          <el-button
            type="primary"
            size="small"
            autocomplete="off"
            @click="RegisterHandle"
            >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { sendEmail, register } from "@/api/user";
export default {
  name: "Login",
  data() {
    return {
      registerForm: {},
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在 3 到 10 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 6, message: "至少六位", trigger: "blur" },
          {
            pattern: /^(?=.*\d)(?=.*[a-zA-Z]).{6,}$/,
            message: "必须包含数字，拼英，至少六位",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 6, message: "特殊字符至少六位", trigger: "blur" },
          {
            pattern: /^(?=.*\d)(?=.*[a-zA-Z]).{6,}$/,
            message: "必须包含数字，拼英，至少八六位",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "邮箱不能为空", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
          {
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
            message: "无效邮箱",
            trigger: "blur",
          },
        ],
      },
      buttonName: "获取验证码",
      count: 60,
      disable: false,
    };
  },
  methods: {
    RegisterHandle() {
      console.log(this.registerForm);
      this.$refs.registerForm.validate(async (valid) => {
        if (valid) {
          if (
            this.registerForm.password === this.registerForm.confirmPassword
          ) {
            var res = await register(this.registerForm);
            console.log(res);
            if (res.code === 20000) {
              this.$bus.$emit("alertInfo", "成功", "注册成功", "success");
              this.$router.push({ path: "/login" });
            } else {
              this.$bus.$emit("alertInfo", "失败", res.message);
            }
          } else {
            this.$bus.$emit("alertInfo", "失败", "两次输入的密码不一致");
          }
        }
      });
    },
    // alertInfo(title,message,type){
    //   this.$notify({
    //       title,
    //       message,
    //       type
    //     });
    // },
    async sendVerificationCode() {
      var res = await sendEmail({
        email: this.registerForm.email,
      });
      console.log(res);
      if (res.code === 20000) {
        this.$bus.$emit("alertInfo", "成功", "发送成功", "success");
      } else {
        this.$bus.$emit("alertInfo", "失败", "发送失败", "");
      }
      var timer = setInterval(() => {
        if (this.count < 1) {
          this.disable = false;
          this.buttonName = "获取验证码";
          this.count = 60;
          clearInterval(timer);
        } else {
          this.disable = true;
          this.buttonName = this.count-- + "s后重发";
        }
      }, 1000);
      // let passwordreg = /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,12}/;
      // let emailreg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      // if (emailreg.test(this.user.email) && (passwordreg.test(this.user.password)) && (this.user.password === this.user.confirmPassword) && (this.user.username !== '')) {
      //   this.request.post("/user/registerCode", this.user).then(res => {
      //     if (res.code === '200') {
      //       this.$message.success("发送成功")
      //     } else {
      //       this.$message.error(res.msg)
      //     }
      //   })
      //   var timer = setInterval(() => {
      //     if (this.count < 1) {
      //       this.disable = false;
      //       this.buttonName = "获取验证码";
      //       this.count = 60;
      //       clearInterval(timer);
      //     } else {
      //       this.disable = true;
      //       this.buttonName = this.count-- + "s后重发"
      //     }
      //   }, 1000)
      // } else {
      //   this.$message.error("请先填写用户名，密码！")
      // }
    },
  },
};
</script>

<style>
.wrapper {
  height: 100vh;
  background: url("../../assets/bg1.jpg");
  background-size: cover;
  overflow: hidden;
}
</style>
