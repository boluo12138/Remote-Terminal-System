<template>
  <div class="terminal-view">
      <div id="terminal" ref="terminal"></div>
  </div>
</template>

<script>
export default {
  props: {
      terminalInfo: Object,
  },
  data() {
      return {
          terminal: {
              operate: "connect",
              host: this.terminalInfo.host,
              port: this.terminalInfo.port,
              username: this.terminalInfo.username,
              password: this.terminalInfo.password,
          },
          indication: "none",
          openId: 0,
          term: "",
          client: "",
      };
  },
  mounted() {
      this.openTerminal(this.terminal);
  },
  methods: {
      openTerminal(options) {
          let that = this;
          let is_connect = false;
          // console.log(options)
          this.client = new WSSHClient();
          this.term = new Terminal({
              cols: 150,
              rows: 40,
              cursorBlink: true, // 光标闪烁
              cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
              scrollback: 800, //回滚
              tabStopWidth: 8, //制表宽度
              screenKeys: true,
          });

          this.setOpenId(this.$store.state.other.terminalInfo.length - 1);

          this.term.on("data", function (data) {
              //键盘输入时的回调函数
              // console.log(typeof(data),data)
              that.client.sendClientData(data);
          });
          this.term.open(document.getElementById("terminal"));
          //在页面上显示连接中...
          this.term.write("Connecting...\n");
          this.indication = "connecting";
          //执行连接操作
          that.client.connect({
              onError: function (error) {
                  //连接失败回调
                  that.indication = "error";
                  // setTimeout(() => {
                  //   that.$message.error('登录失败，请检查账号密码IP等')
                  //   that.term.write('Error: ' + error + '\r\n');
                  // }, 2800);
              },
              onConnect: function () {
                  //连接成功回调
                  that.client.sendInitData(options);
                  that.indication = "connecting";
              },
              onClose: function () {
                  //连接关闭回调
                  that.term.write("\rconnection closed");
                  that.indication = "close";
              },
              onData: function (data) {
                  //收到数据时回调
                  // console.log('back\n', data)
                  if (data.indexOf("logout") !== -1) {
                      that.indication = "close";
                      // console.log("##close indication")
                  } else if (!is_connect) {
                      that.indication = "success";
                      is_connect = true;
                  }
                  that.term.write(data);
              },
          });
      },
      setOpenId(id) {
          setTimeout(() => {
              document
                  .getElementById("terminal")
                  .getElementsByClassName("terminal")
                  .forEach((item, index) => {
                      item.style.height = 736 + "px";
                      item.style.display = "block";
                      if (index !== id * 1) {
                          item.style.display = "none";
                      }
                  });
              // console.log(document.getElementById('terminal').getElementsByClassName('terminal'))
          }, 100);
      },
      getInfo(value) {
          // console.log(typeof(value), value)
          // this.term.write(value)
          this.client.sendClientData(value);
      },
  },
};
</script>

<style scoped>
</style>
