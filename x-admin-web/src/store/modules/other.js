const state = {
  terminalInfo: [],
};
const mutations = {
  ADD_TERMINAL(state, value) {
    // let i = -1;
    // state.terminalInfo.forEach((item, index) => {
    //   if (item.content.host === value.content.host && item.content.port === value.content.port) {
    //     i = index;
    //   }
    // });
    // if (i !== -1) {
    //   console.log("重复添加", value);
    // } else {
    state.terminalInfo.push(value);
    // }
  },
  REMOVE_TERMINAL(state, value) {
    let i = -1;
    state.terminalInfo.forEach((item, index) => {
      if (item.name === value) {
        i = index;
      }
    });
    if (i !== -1) {
      state.terminalInfo.splice(i, 1);
    } else {
      console.log("未找到", value);
    }
  },
};

export default {
  state,
  mutations,
};
