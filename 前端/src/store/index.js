import { createStore } from 'vuex'

export default createStore({
  state () {
    return {
      headFlag: true,
      minePage: 1,
      mineArticle: 4,
      mineStudy: 1,
      mineSetting: 1,
      loginFlag: 0,
      username: '',
      portrait: '@/assets/back5.jpg',
      type: 0,
      searchFlag: true,
      subjectIndex: 0,
      headImg: ''
    }
  },
  mutations: {
    setHeadTrue (state){
      state.headFlag = true;
    },
    setHeadFalse (state){
      state.headFlag = false;
    },
    setMinePage (state,n){
      state.minePage = n;
    },
    setSubject (state,n){
      state.subjectIndex = n;
    },
    changeMinePage(state,n){
      state.minePage=n;
    },
    changeMineArticle(state,n){
      state.mineArticle=n;
    },
    changeMineStudy(state,n){
      state.mineStudy=n;
    },
    changeMineSetting(state,n){
      state.mineSetting=n;
    },
    changeLoginFlag(state,n){
      state.loginFlag=n;
    },
    changeUserName(state,n){
      state.username=n;
    },
    changePortrait(state,n){
      state.portrait=n;
    }
  },
  actions: {
    changeMinePage({commit},page){
      commit('changeMinePage',page);
    },
    changeMineArticle({commit},mineArticle){
      commit('changeMineArticle',mineArticle);
    },
    changeMineStudy({commit},mineStudy){
      commit('changeMineStudy',mineStudy);
    },
    changeMineSetting({commit},mineSetting){
      commit('changeMineSetting',mineSetting);
    },
    changeLoginFlag({commit},login){
      commit('changeLoginFlag',login);
    },
    changeUserName({commit},username){
      commit('changeUserName',username);
    },
    changePortrait({commit},portrait){
      commit('changePortrait',portrait);
    }
  }
})