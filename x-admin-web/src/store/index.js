import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import app from "./modules/app";
import settings from "./modules/settings";
import user from "./modules/user";
import tagsView from "./modules/tagsView";
import other from "./modules/other";
Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    tagsView,
    other,
  },
  getters,
});

export default store;
