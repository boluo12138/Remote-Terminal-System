<template>
  <div>
    <!-- 查询 重置 添加栏 -->
    <el-form ref="selectForm" :inline="true" :model="selectForm">
      <el-form-item prop="name">
        <el-input
          v-model="selectForm.name"
          placeholder="名称"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item prop="ip">
        <el-input
          v-model="selectForm.ip"
          placeholder="主机"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button @click="resetForm('selectForm')">重置</el-button>
        <el-button type="primary" plain @click="openDialog1(null)"
          >添加</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 添加、编辑表单对话框 -->
    <el-dialog
      ref="dialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form ref="form" :inline="true" :model="form" label-width="100px">
        <!-- 名称 -->
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>

        <!-- 主机 -->
        <el-form-item label="主机" prop="ip">
          <el-input v-model="form.ip"></el-input>
        </el-form-item>

        <!-- 端口-->
        <el-form-item label="端口" prop="port">
          <el-input v-model="form.port"></el-input>
        </el-form-item>

        <!-- 用户名 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-if="title == '添加数据'" type="primary" @click="submit"
          >提 交</el-button
        >
        <el-button v-else type="primary" @click="submit">保 存</el-button>
        <el-button @click="cancel_one('form')">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 表格 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      :border="true"
      height="500px"
    >
      <el-table-column prop="name" label="名称" width="255" align="center">
      </el-table-column>
      <el-table-column prop="ip" label="主机" width="260" align="center">
      </el-table-column>
      <el-table-column prop="port" label="端口" width="260" align="center">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="260"
        align="center"
      >
      </el-table-column>
      <el-table-column label="操作" width="260" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="openTerminal(scope.row)">连接</el-button>
          <el-button type="text" @click="openDialog1(scope.row)"
            >编辑</el-button
          >
          <span>&nbsp; <i class="sep">|</i> &nbsp;</span>
          <el-button type="text" @click="deleteData(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block" style="margin-top: 20px; float: right">
      <span class="demonstration"></span>
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleNumChange"
        :current-page="selectForm.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="selectForm.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import convApi from "@/api/convManage.js";

export default {
  data() {
    return {
      //分页参数
      total: 0,
      // currentPage: 1,
      // pageSize: 10,
      //对话框标题
      title: "",
      dialogVisible: false,
      //查询 重置 添加栏参数
      selectForm: {
        name: "",
        ip: "",
        pageNum: 1,
        pageSize: 5,
      },
      //添加编辑表单参数
      form: {
        name: "",
        ip: "",
        port: "",
        username: "",
        password: "",
      },
      // tableData: [{
      //   id:1,
      //   name: 'xw服务器',
      //   host: '10.18.60.12',
      //   port: '22',
      //   userName: 'root'
      // },
      //   {
      //   id:2,
      //   name: '111',
      //   host: '111',
      //   port: '22',
      //   userName: '111'
      // },
      //   {
      //   id:3,
      //   name: '222',
      //   host: '222',
      //   port: '22',
      //   userName: '222'
      // },
      //   {
      //   id:4,
      //   name: '222',
      //   host: '222',
      //   port: '22',
      //   userName: '222'
      // }]
      tableData: [],
    };
  },
  mounted() {
    this.getConvList();
  },
  methods: {
    //删除
    deleteData(row) {
      this.$confirm(`您确认删除会话 ${row.name} ?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          console.log(row);
          const res = await convApi.deleteConvById(row.id);
          this.MenuInfo(res);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //编辑对话框 保存按钮
    save(formName) {
      console.log(this.$refs.dialog.title);
      this.$refs.form.validate((valid) => {
        if (valid) {
          //后续对表单处理
          console.log(this.form);
          // console.log(this.form.id);
          let index = this.form.id - 1;
          console.log(this.tableData[index]);
          this.tableData[index].name = this.form.name;
          this.tableData[index].host = this.form.host;
          this.tableData[index].port = this.form.port;
          this.tableData[index].userName = this.form.userName;
          // console.log(this.form);
          // dataApi.addData(this.form).then((res) => {
          //     console.log(res);
          //   })
          //   .catch((err) => {
          //     console.log(err);
          //   });
          // //关闭弹窗并重置表单
          this.cancel_one(formName);
        }
      });
    },
    //查询
    onSubmit() {
      console.log(JSON.stringify(this.selectForm));
      this.getConvList();
    },
    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.getConvList();
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    //添加编辑对话框
    openDialog1(row) {
      if (row == null) {
        this.title = "添加数据";
        this.dialogVisible = true;
        return;
      } else {
        this.title = "修改数据";
        this.selectedRowData = row;
        this.form = JSON.parse(JSON.stringify(this.selectedRowData));
      }
      this.dialogVisible = true;
    },
    //添加对话框 提交按钮
    submit() {
      console.log(this.$refs.dialog.title);
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          //后续对表单处理
          console.log(this.form);
          let res = null;
          if (this.$refs.dialog.title === "添加数据") {
            res = await convApi.addConv(this.form);
            // console.log(res)
          } else {
            res = await convApi.updateConv(this.form);
          }
          this.MenuInfo(res);
          // dataApi.addData(this.form).then((res) => {
          //     console.log(res);
          //   })
          //   .catch((err) => {
          //     console.log(err);
          //   });
          // //关闭弹窗并重置表单
          // this.cancel_one(formName);
        }
      });
    },
    //添加编辑对话框 取消按钮
    cancel_one(formName) {
      this.dialogVisible = false;
      this.$refs[formName].resetFields();
    },
    //分页
    handleSizeChange(pageSize) {
      this.selectForm.pageSize = pageSize;
      this.getConvList();
      console.log(`每页 ${this.pageSize} 条`);
    },
    handleNumChange(pageNum) {
      this.selectForm.pageNum = pageNum;
      this.getConvList();
      console.log(`当前页: ${this.currentPage}`);
    },
    async getConvList() {
      // console.log(this.selectForm);
      const res = await convApi.getConvList(this.selectForm);
      if (res.code === 20000) {
        this.tableData = JSON.parse(JSON.stringify(res.data.records));
        this.total = res.data.total;
        this.$message({
          message: res.message,
          type: "success",
        });
      } else {
        this.$message({
          message: res.message,
          type: "warn",
        });
      }
      // console.log(res)
    },
    MenuInfo(res) {
      console.log(res);
      if (res.code === 20000) {
        this.$message({
          message: res.message,
          type: "success",
        });
        this.getConvList();
        // //关闭弹窗并重置表单
        this.cancel_one(this.form);
      } else {
        this.$message({
          message: res.message,
          type: "warning",
        });
      }
    },
    //连接终端
    openTerminal(row) {
      let terminalInfo = {
        name: row.name,
        ip: row.ip,
        port: row.port,
        username: row.username,
        password: row.password,
      };
      console.log(terminalInfo)
      this.$router.push({
        path: "/terminal",
        query: { method: "add", terminalInfo: terminalInfo },
      });
    },
  },
};
</script>

<style>
.sep {
  color: lightgray;
}
</style>
