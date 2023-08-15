<template>
  <div class="terminal-view">
    <el-dropdown>
      <el-button type="primary">
        快速启动<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <div v-for="(item, index) in convList" :key="index">
          <el-dropdown-item @click.native="toConv(index)">{{
            item.name
          }}</el-dropdown-item>
        </div>
      </el-dropdown-menu>
    </el-dropdown>
    <div v-loading="isloading" :element-loading-text="waitingtext">
      <el-tabs
        v-model="editableTabsValue"
        type="card"
        @tab-click="handleClick"
        closable
        @tab-remove="removeTab"
        v-if="isShow"
      >
        <el-tab-pane
          v-for="(item, index) in editableTabs"
          :key="index"
          :label="item.titlename"
          :name="item.name"
        >
        </el-tab-pane>
      </el-tabs>
      <div
        v-for="(item, index) in editableTabs"
        :key="index"
        :label="item.title"
        :name="item.name"
        v-show="isShow"
      >
        <terminal ref="terminal" :terminalInfo="item.content"></terminal>
      </div>
    </div>
  </div>
</template>

<script>
import convApi from "@/api/convManage";
import terminal from "@/components/terminal/terminal.vue";
export default {
  components: { terminal },
  data() {
    return {
      editableTabsValue: "0",
      editableTabs: [],
      tabIndex: 2,
      onTab: 1,
      isTerminal: false,
      isShow: false,
      isloading: true,
      timer: null,
      command: "",
      waitingtext: "",
      convList: [],
    };
  },
  created() {
    this.init();
    this.getConvList();
    window.addEventListener("beforeunload", this.$router.push("terminal"));
  },
  computed: {
    colorIndication() {
      // console.log("colorIndication")
      // let indication = []
      // console.log('执行computed')
      // console.log("$$$", this.$refs.terminal)
      // this.$refs.terminal.forEach(item => {
      //   console.log('执行refs')
      //   console.log(item.indication)
      //   switch(item.indication) {
      //     case 'connecting':
      //       indication.push('🟡')
      //       break
      //     case 'success':
      //       indication.push('🟢')
      //       break
      //     case 'error':
      //       indication.push('❌')
      //       break
      //     case 'close':
      //       indication.push('🔴')
      //       break
      //     default:
      //       indication.push('⚪')
      //   }
      // });
      return this.colorIndication_1();
    },
  },
  methods: {
    addLine(value) {
      if (value !== undefined) {
        let data = value + "\n";
        this.$refs.terminal[this.onTab].getInfo(data);
      }
    },
    getConvList() {
      convApi.getConvAllList().then((response) => {
        this.convList = response.data;
      });
    },
    toConv(value) {
      let terminalInfo = {
        name: this.convList[value].name,
        ip: this.convList[value].ip,
        port: this.convList[value].port,
        username: this.convList[value].username,
        password: this.convList[value].password,
      };
      this.addTab(terminalInfo);
      this.isShow = false;
      this.isloading = true;
      this.init();
    },
    init() {
      if (this.$route.query.method === "add") {
        this.addTab(this.$route.query.terminalInfo);
        this.command = this.$route.query.command;
      }
      // console.log(this.$store.state.other.terminalInfo)
      setTimeout(() => {
        this.editableTabs = this.$store.state.other.terminalInfo;
        this.editableTabsValue =
          this.$store.state.other.terminalInfo.length + "";
        // console.log(this.editableTabsValue)
        if (this.editableTabsValue > 0) {
          this.getIndication();
          this.waitingtext = "等待终端启动";
          this.onTab = this.editableTabsValue * 1 - 1;
          // console.log(this.onTab)
          setTimeout(() => {
            this.isShow = true;
            this.addLine(this.command);
            this.isloading = false;
          }, 1000);
        } else {
          this.waitingtext = "未选择要开启的终端";
        }
      }, 100);
      // console.log(this.editableTabs,this.editableTabsValue)
      // console.log(this.$route.query)
    },
    getIndication() {
      console.log("getIndication");
      let that = this;
      this.timer = setInterval(() => {
        let flag = false;
        this.editableTabs.forEach((item, index) => {
          if (!flag) {
            console.log(this.timer, this.editableTabs[index].titlename);
            flag = true;
          }
          // this.$refs.terminal.forEach(item => {
          //   console.log(item.indication)
          // });
          console.log("##############");
          console.log(that.$refs.terminal);
          console.log(that.colorIndication_1());
          console.log(that.colorIndication_1()[index]);
          item.titlename =
            (this.colorIndication_1()[index] === undefined
              ? "⚪"
              : this.colorIndication_1()[index]) + item.title;
        });
      }, 1000);
    },
    addTab(tabInfo) {
      this.isTerminal = true;
      let obj = {
        title: tabInfo.name,
        name: this.$store.state.other.terminalInfo.length + 1 + "",
        content: {
          host: tabInfo.ip,
          port: tabInfo.port,
          username: tabInfo.username,
          password: tabInfo.password,
        },
      };
      // console.log(obj)
      this.$store.commit("ADD_TERMINAL", obj);
      // this.editableTabsValue = newTabName;
    },
    removeTab(targetName) {
      // console.log(targetName)
      this.$store.commit("REMOVE_TERMINAL", targetName);
      // let tabs = this.editableTabs;
      // let activeName = this.editableTabsValue;
      // if (activeName === targetName) {
      //   tabs.forEach((tab, index) => {
      //     if (tab.name === targetName) {
      //       let nextTab = tabs[index + 1] || tabs[index - 1];
      //       if (nextTab) {
      //         activeName = nextTab.name;
      //       }
      //     }
      //   });
      // }
      // this.editableTabsValue = activeName;
      // this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    },
    handleClick(tab, event) {
      this.onTab = tab.index;
      this.$refs.terminal[tab.index].setOpenId(tab.index);
    },
    colorIndication_1() {
      console.log("colorIndication");
      let indication = [];
      console.log("执行computed");
      console.log("$$$", this.$refs.terminal);
      this.$refs.terminal.forEach((item) => {
        console.log("执行refs");
        console.log(item.indication);
        switch (item.indication) {
          case "connecting":
            indication.push("🟡");
            break;
          case "success":
            indication.push("🟢");
            break;
          case "error":
            indication.push("❌");
            break;
          case "close":
            indication.push("🔴");
            break;
          default:
            indication.push("⚪");
        }
      });
      return indication;
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },
};
</script>

<style>
.terminal-view {
  height: 800px;
}
</style>
