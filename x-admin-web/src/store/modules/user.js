import { login, logout, getInfo, getUserInfo } from "@/api/user";
import { getToken, setToken, removeToken } from "@/utils/auth";
import { resetRouter } from "@/router";

const getDefaultState = () => {
  return {
    token: getToken(),
    name: "",
    avatar: "",
    menuList: [],
    id: null,
    username: "",
    phone: "",
    status: "",
    email: "",
    create_time: "",
    roles:[],
    data: null,
  };
};

const state = getDefaultState();

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState());
  },
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_MENU_LIST: (state, menuList) => {
    state.menuList = menuList;
  },

  SET_ID: (state, id) => {
    state.id = id;
  },
  SET_USERNAME: (state, username) => {
    state.username = username;
  },

  SET_PHONE: (state, phone) => {
    state.phone = phone;
  },
  SET_TYPE: (state, status) => {
    state.status = status;
  },
  SET_EMAIL: (state, email) => {
    state.email = email;
  },
  SET_CREATE_TIME: (state, create_time) => {
    state.create_time = create_time;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
  SET_DATA: (state, data) => {
    state.data = data;
  },
};

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password })
        .then((response) => {
          const { data } = response;
          commit("SET_TOKEN", data.token);
          setToken(data.token);
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token)
        .then((response) => {
          const { data } = response;

          if (!data) {
            reject("[No DATA]: Verification failed, please Login again.");
          }
          const {
            name,
            avatar,
            menuList,
            id,
            username,
            phone,
            status,
            email,
            createTime,
            roles,
          } = data;

          commit("SET_NAME", name);
          commit("SET_AVATAR", avatar);
          commit("SET_MENU_LIST", menuList);
          commit("SET_ID", id);
          commit("SET_USERNAME", username);
          commit("SET_PHONE", phone);
          commit("SET_TYPE", status);
          commit("SET_EMAIL", email);
          commit("SET_ROLES", roles);
          commit("SET_CREATE_TIME", createTime);

          commit("SET_DATA", data);

          console.log(createTime);
          console.log("vuex data:", data, state);

          resolve(data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  // getUserInfo({ commit, state }) {
  //   return new Promise((resolve, reject) => {
  //     getUserInfo(state.token)
  //       .then((response) => {
  //         console.log("son get info", response);
  //         const { data } = response;

  //         if (!data) {
  //           reject("[No DATA]: Verification failed, please Login again.");
  //         }

  //         const {
  //           name,
  //           avatar,
  //           menuList,
  //           id,
  //           username,
  //           phone,
  //           type,
  //           email,
  //           createTime,
  //         } = data;

  //         commit("SET_NAME", name);
  //         commit("SET_AVATAR", avatar);
  //         commit("SET_MENU_LIST", menuList);
  //         commit('SET_ID', id)
  //         commit('SET_USERNAME', username)
  //         commit('SET_AVATAR', avatar)
  //         commit('SET_PHONE', phone)
  //         commit('SET_TYPE', type)
  //         commit('SET_EMAIL', email)
  //         commit('SET_CREATE_TIME', createTime)
  //         commit('SET_DATA', data)

  //         console.log(createTime);
  //         console.log("vuex data:", data, state);

  //         resolve(data);

  //       })
  //       .catch((error) => {
  //         reject(error);
  //       });
  //   });
  // },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token)
        .then(() => {
          removeToken(); // must remove  token  first
          resetRouter();
          commit("RESET_STATE");
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  // remove token
  resetToken({ commit }) {
    return new Promise((resolve) => {
      removeToken(); // must remove  token  first
      commit("RESET_STATE");
      resolve();
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
