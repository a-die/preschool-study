(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3c050323"],{2438:function(e,t,n){},3957:function(e,t,n){"use strict";n("2438")},"69ce":function(e,t,n){"use strict";n.r(t);var c=n("7a23"),i=function(e){return Object(c["pushScopeId"])("data-v-0e5f294b"),e=e(),Object(c["popScopeId"])(),e},o={id:"mine-setting"},a={class:"se-left"},r=i((function(){return Object(c["createElementVNode"])("a",null,"账号安全",-1)})),s=[r],u=i((function(){return Object(c["createElementVNode"])("a",null,"修改资料",-1)})),l=[u],m=i((function(){return Object(c["createElementVNode"])("a",null,"编辑主页",-1)})),b=[m],d={class:"se-right"};function g(e,t,n,i,r,u){var m=Object(c["resolveComponent"])("router-view");return Object(c["openBlock"])(),Object(c["createElementBlock"])("div",o,[Object(c["createElementVNode"])("div",a,[Object(c["createElementVNode"])("ul",null,[Object(c["createElementVNode"])("li",{onClick:t[0]||(t[0]=function(e){return u.changeMine(1)}),class:Object(c["normalizeClass"])({active:1==e.mineSetting})},s,2),Object(c["createElementVNode"])("li",{onClick:t[1]||(t[1]=function(e){return u.changeMine(2)}),class:Object(c["normalizeClass"])({active:2==e.mineSetting})},l,2),Object(c["createElementVNode"])("li",{onClick:t[2]||(t[2]=function(e){return u.changeMine(3)}),class:Object(c["normalizeClass"])({active:3==e.mineSetting})},b,2)])]),Object(c["createElementVNode"])("div",d,[Object(c["createVNode"])(m)])])}var j=n("5530"),O=n("5502"),f={data:function(){return{}},computed:Object(j["a"])({},Object(O["d"])(["mineSetting"])),methods:Object(j["a"])(Object(j["a"])({},Object(O["b"])(["changeMineSetting"])),{},{changeMine:function(e){this.changeMineSetting(e),1===e?this.$router.push("/mine/mysetting/settingSafe"):2===e?this.$router.push("/mine/mysetting/settingInfo"):this.$router.push("/mine/mysetting/settingMyPage")}}),unmounted:function(){this.$store.commit("changeMineSetting",1)}},h=(n("3957"),n("6b0d")),p=n.n(h);const v=p()(f,[["render",g],["__scopeId","data-v-0e5f294b"]]);t["default"]=v}}]);
//# sourceMappingURL=chunk-3c050323.2d35d5d0.js.map