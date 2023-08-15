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
      <el-form-item prop="server">
        <el-input
          v-model="selectForm.server"
          placeholder="部署服务器"
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
        <el-form-item label="主机" prop="server">
          <el-input v-model="form.server"></el-input>
        </el-form-item>

        <!-- ip -->
        <el-form-item label="ip" prop="ip">
          <el-input v-model="form.ip"></el-input>
        </el-form-item>

        <!-- 端口-->
        <el-form-item label="端口" prop="port">
          <el-input v-model="form.port"></el-input>
        </el-form-item>

        <!-- 命令 -->
        <el-form-item label="命令" prop="command">
          <el-input v-model="form.command"></el-input>
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
        <el-button @click="handleClose">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 表格 -->
    <el-table
      :data="tableData"
      style="width: 100%"
      :border="true"
      height="500px"
    >
      <el-table-column prop="name" label="名称" width="180" align="center">
      </el-table-column>
      <el-table-column
        prop="server"
        label="部署服务器"
        width="200"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="ip" label="服务器ip" width="200" align="center">
      </el-table-column>
      <el-table-column prop="port" label="部署端口" width="200" align="center">
      </el-table-column>
      <el-table-column prop="command" label="命令" width="260" align="center">
      </el-table-column>
      <el-table-column label="操作" width="260" align="center">
        <template slot-scope="scope">
          <el-button type="text" style="color: rgb(0, 187, 0)" @click="openTerminal(scope.row)">查看</el-button>
          <el-button
            type="text"
            style="color: rgb(0, 158, 190)"
            @click="openDialog1(scope.row)"
            >编辑</el-button
          >
          <span>&nbsp; <i class="sep">|</i> &nbsp;</span>
          <el-button
            type="text"
            style="color: rgb(255, 0, 0)"
            @click="deleteData(scope.row)"
            >删除</el-button
          >
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
import logApi from "@/api/logManage";
export default {
  data() {
    return {
      //分页参数
      total: 0,
      //对话框标题
      title: "",
      dialogVisible: false,
      //查询 重置 添加栏参数
      selectForm: {
        name: "",
        server: "",
        pageNum: 1,
        pageSize: 10,
      },
      copyForm:{},
      //添加编辑表单参数
      form: {
        name: "",
        server: "",
        ip: "",
        port: "",
        command: "",
        username: "",
        password: "",
      },
      tableData: [],
    };
  },
  mounted() {
    this.copyForm = JSON.parse(JSON.stringify(this.form));
    this.getLogList();
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
          const res = await logApi.deleteLogById(row.id);
          this.MenuInfo(res);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //取消清空表单
    handleClose() {
      this.form = JSON.parse(JSON.stringify(this.copyForm));
      this.dialogVisible = false;
    },
    //查询
    onSubmit() {
      console.log(JSON.stringify(this.selectForm));
      this.getLogList();
    },
    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.getLogList();
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
            res = await logApi.addLog(this.form);
            // console.log(res)
          } else {
            res = await logApi.updateLog(this.form);
          }
          this.MenuInfo(res);
        }
      });
    },
    //分页
    handleSizeChange(pageSize) {
      this.selectForm.pageSize = pageSize;
      this.getLogList();
      console.log(`每页 ${this.pageSize} 条`);
    },
    handleNumChange(pageNum) {
      this.selectForm.pageNum = pageNum;
      this.getLogList();
    },
    async getLogList() {
      // console.log(this.selectForm);
      const res = await logApi.getLogList(this.selectForm);
      // console.log(res);
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
    },
    MenuInfo(res) {
      console.log(res);
      if (res.code === 20000) {
        this.$message({
          message: res.message,
          type: "success",
        });
        this.getLogList();
        // //关闭弹窗并重置表单
        this.handleClose();
      } else {
        this.$message({
          message: res.message,
          type: "warning",
        });
      }
    },
    openTerminal(row) {
            let terminalInfo = {
                name: row.name,
                ip: row.ip,
                port: row.port,
                username: row.username,
                password: row.password,
            };
            let command = row.command;
            this.$router.push({
                name: "terminal",
                query: {
                    method: "add",
                    terminalInfo: terminalInfo,
                    command: command,
                },
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
