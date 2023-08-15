<template>
    <div class="xbox">
        <div class="box">
            <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
                <el-form-item label="旧密码：" prop="oldpass">
                    <el-input type="password" v-model="ruleForm2.oldpass" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="新密码：" prop="pass">
                    <el-input type="password" v-model="ruleForm2.pass" autocomplete="off"></el-input>

                </el-form-item>

                <el-form-item label="确认密码：" prop="checkPass">
                    <el-input type="password" v-model="ruleForm2.checkPass" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item class="bottom">
                    <el-button type="primary" @click="submitForm('ruleForm2')">提交</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>

</template>

<script>
import { mapGetters } from "vuex";
import store from "../../store";
import userApi from "@/api/userManage";
import router, { constantRoutes } from "@/router";

export default {
    data() {
        var validatePass0 = (rule, value, callback) => {
            var regex = new RegExp(
                "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}"
            );
            if (value === "") {
                callback(new Error("请输入旧密码"));
            } else if (value.length < 8 || value.length > 16) {
                callback(new Error("请输⼊8~16位密码"));
            } else if (!regex.test(value)) {
                callback(
                    new Error(
                        "密码必须同时包含字母、数字和特殊字符其中三项且⾄少8位"
                    )
                );
            } else {
                callback();
            }
        };
        var validatePass1 = (rule, value, callback) => {
            var regex = new RegExp(
                "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}"
            );
            if (value === "") {
                callback(new Error("请输入新密码"));
            } else if (value.length < 8 || value.length > 16) {
                callback(new Error("请输⼊8~16位密码"));
            } else if (!regex.test(value)) {
                callback(
                    new Error(
                        "密码必须同时包含字母、数字和特殊字符其中三项且⾄少8位"
                    )
                );
            } else {
                if (this.ruleForm2.checkPass !== "") {
                    this.$refs.ruleForm2.validateField("checkPass");
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            var regex = new RegExp(
                "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}"
            );
            if (value === "") {
                callback(new Error("请再次输入密码"));
            } else if (value.length < 8 || value.length > 16) {
                callback(new Error("请输⼊8~16位密码"));
            } else if (!regex.test(value)) {
                callback(
                    new Error(
                        "密码必须同时包含字母、数字和特殊字符其中三项且⾄少8位"
                    )
                );
            } else if (value !== this.ruleForm2.pass) {
                callback(new Error("两次输入密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            ruleForm2: {
                oldpass: "",
                pass: "",
                checkPass: "",
            },
            rules2: {
                // oldpass: [{ validator: validatePass0, trigger: "blur" }],
                // pass: [{ validator: validatePass1, trigger: "blur" }],
                // checkPass: [{ validator: validatePass2, trigger: "blur" }],
            },
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$confirm("是否确认修改密码?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                        .then(() => {
                            console.log(
                                store.state.user.token,
                                store.state.user.username,
                                this.ruleForm2.oldpass,
                                this.ruleForm2.pass
                            );
                            userApi
                                .updatePassword(
                                    store.state.user.token,
                                    store.state.user.username,
                                    this.ruleForm2.oldpass,
                                    this.ruleForm2.pass
                                )
                                .then((response) => {
                                    console.log(
                                        "update password success: ",
                                        response.data
                                    );
                                    this.$router.go(0); // 刷新页面
                                });
                        })
                        .catch(() => {
                            this.$message({
                                type: "info",
                                message: "已取消修改",
                            });
                        });
                }
            });
            //   async logout = () => {
            //   this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            // }
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
};
</script>

<style>
.xbox {
    width: 100%;
    height: 100%;
    background-image: url("../../assets/bg1.jpg");
    background-size: cover;
    position: absolute;
}

.box {
    width: 400px;
    height: 100%;
    margin: 0 auto;
    padding-top: 50px;
}
</style>
