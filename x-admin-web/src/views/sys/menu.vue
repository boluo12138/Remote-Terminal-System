<template>
  <div id="menu">
    <el-button class="add" @click="openDialog(null)" type="primary"
      >新增</el-button
    >
    <!-- 菜单管理新增对话框表单 -->
    <el-dialog
      ref="dialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form
        ref="form"
        :rules="rules"
        :inline="true"
        :model="form"
        label-width="100px"
        :label-position="labelPosition"
      >
        <!-- 父节点的id -->
        <el-form-item label="父节点Id" prop="parentId">
          <el-input v-model="form.parentId"></el-input>
        </el-form-item>
        <!-- 名称 -->
        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>

        <!-- 权限编码 -->
        <el-form-item label="权限编码" prop="redirect">
          <el-input v-model="form.redirect"></el-input>
        </el-form-item>

        <!-- 图标 -->
        <el-form-item label="图标" prop="icon">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>

        <!-- 类型 -->
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio label="0"><el-tag>目录</el-tag></el-radio>
            <el-radio label="1"><el-tag type="success">菜单</el-tag></el-radio>
            <el-radio label="2"><el-tag type="info">按钮</el-tag></el-radio>
          </el-radio-group> </el-form-item
        ><br />

        <!-- 菜单URL -->
        <el-form-item label="菜单URL" prop="path">
          <el-input v-model="form.path"></el-input>
        </el-form-item>

        <!-- 菜单组件 -->
        <el-form-item label="菜单组件" prop="component">
          <el-input v-model="form.component"></el-input>
        </el-form-item>

        <!-- 状态 -->
        <el-form-item label="状态" prop="hidden">
          <el-radio-group v-model="form.hidden">
            <el-radio label="0"><el-tag type="success">正常</el-tag></el-radio>
            <el-radio label="1"><el-tag type="danger">禁用</el-tag></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submit()" class="submit" type="primary"
          >保 存</el-button
        >
        <el-button @click="cancel('form')">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 表 -->

    <el-table
      :data="tableData"
      style="width: 100%; margin-top: 20px"
      :default-sort="{ prop: 'date', order: 'descending' }"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :border="true"
      height="550px"
      default-expand-all
      stripe
      lazy
      row-key="menuId"
    >
      <!-- prop="menuName" -->
      <el-table-column
        prop="title"
        label="名称"
        sortable
        width="180"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="redirect"
        label="权限编码"
        sortable
        width="150"
        align="center"
      >
        <!-- <el-table-column prop="menuId" label="id" width="180">
        </el-table-column> -->
      </el-table-column>
      <el-table-column prop="icon" label="图标" width="180" align="center">
      </el-table-column>
      <el-table-column prop="type" label="类型" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 0" type="primary">目录</el-tag>
          <el-tag v-if="scope.row.type == 1" type="success">菜单</el-tag>
          <el-tag v-if="scope.row.type == 2" type="info">按钮</el-tag>
        </template>
      </el-table-column>
      <!-- prop="path"
      prop="menuURL" -->
      <el-table-column prop="path" label="菜单URL" align="center">
      </el-table-column>
      <el-table-column prop="component" label="菜单组件" align="center">
      </el-table-column>
      <el-table-column prop="menuId" label="排序号" align="center">
      </el-table-column>

      <el-table-column prop="hidden" label="状态" width="180">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hidden == 0" type="success">正常</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="openDialog(scope.row)">编辑</el-button>
          <el-button type="text" @click="deleteMenu(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import menuApi from "@/api/menuManage";

export default {
  data() {
    return {
      tableData: [],
      selectedRowData: "",
      labelPosition: "right",
      dialogVisible: false,
      form: {
        parentId: "",
        title: "",
        redirect: "",
        icon: "",
        type: 0,
        path: "",
        component: "",
        hidden: "",
      },
      rules: {
        menuName: [{ required: true, message: "请输入名称", trigger: "blur" }],
        sort: [{ required: true, message: "请输入排序号", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "change" }],
        state: [{ required: true, message: "请选择状态", trigger: "change" }],
      },
      title:"",
      label: "",
      defaultProps: {
        children: "children",
        label: "label",
      },
    };
  },
  mounted() {
    this.getAllMenu();
  },
  methods: {
    async deleteMenu(row) {
      this.selectedRowData = row;
      console.log(this.selectedRowData);
      let res = await menuApi.deleteMenuById(this.selectedRowData);
      this.MenuInfo(res);
    },
    openDialog(row) {
      if (row == null) {
        this.title = "新增数据";
        this.dialogVisible = true;
        return;
      } else {
        this.title = "修改数据";
        this.selectedRowData = row;
        this.form = JSON.parse(JSON.stringify(this.selectedRowData));
        // this.form = this.selectedRowData;
      }
      this.dialogVisible = true;
    },
    filterTag(value, row) {
      return row.state === value;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    submit() {
      console.log(this.$refs.dialog.title);
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          //后续对表单处理
          console.log(this.form);
          let res = null;
          if (this.$refs.dialog.title === "新增数据") {
            //发送新增请求
            res = await menuApi.addMenu(this.form);
          } else {
            res = await menuApi.updateMenu(this.form);
          }
          this.MenuInfo(res);
        }
      });
    },
    cancel(formName) {
      this.dialogVisible = false;
      this.$refs[formName].resetFields();
    },
    handleNodeClick(data) {
      // 判断下拉选择的是否是1级目录
      this.label = data.label;
      this.searchForm.mineStatus = data.id;
      // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
      this.$refs.selectUpResId.blur();
    },
    // 选择器配置可以清空选项，用户点击清空按钮时触发
    handleClear() {
      // 将选择器的值置空
      this.label = "";
      this.searchForm.mineStatus = "";
    },
    //获取全部菜单列表
    getAllMenu() {
      menuApi.getAllMenu().then((response) => {
        this.tableData = response.data;
        // this.menuList = response.data;
        console.log(response.data);
      });
    },
    MenuInfo(res) {
      console.log(res);
      if (res.code === 20000) {
        this.$message({
          message: res.message,
          type: "success",
        });
        this.dialogVisible = false;
        this.getAllMenu();
      } else {
        this.$message({
          message: res.message,
          type: "warning",
        });
      }
    },
  },
};
</script>

<!-- <style lang="less" scoped>
  .add,.submit {
    color: white;
    background-color: #a5c3d4;
    border: #a5c3d4;
  }
  .add:hover,.submit:hover {
    color: white;
    background-color: #a5c3d4;
    border: #a5c3d4;
    opacity:0.5;
  }
  .add:focus,.submit:focus{
    color: white;
    background:#a5c3d4;
  }
</style> -->
