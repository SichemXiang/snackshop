import { createStore } from "vuex";
import router from "@/router";

export default createStore({
  state: {
    token:sessionStorage.getItem('token') ,
    phone:sessionStorage.getItem('phone') ,
    avatar:sessionStorage.getItem('avatar')
  },
  getters: {},
  mutations: {
    setToken(state,data){
      state.token = data;
      sessionStorage.setItem('token',data);

    },
    //向vuex中存入电话信息
    setPhone(state,data){
      state.token = data;
      sessionStorage.setItem('phone',data);

    },
    setAvatar(state,data){
      state.token = data;
      sessionStorage.setItem('avatar',data);

    },

  },
  actions: {
    //清空本地信息
    frontLogout(){
      window.sessionStorage.clear();
      window.localStorage.clear();
      router.replace("/login");
    }
  },
  modules: {},
});
