(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-51294eb8"],{"2c90":function(e,t,n){"use strict";n.r(t);n("b0c0");var c=n("7a23"),r=function(e){return Object(c["pushScopeId"])("data-v-83ce3304"),e=e(),Object(c["popScopeId"])(),e},i={id:"manageColumn"},o={class:"left"},a={class:"columns"},l=["onClick"],s={class:"right"},u={class:"head"},d=r((function(){return Object(c["createElementVNode"])("span",null,"> 管理文章",-1)})),m={class:"doChoice"},p={class:"article"},h={key:0,style:{padding:".5rem"}},b=Object(c["createTextVNode"])("全选  "),g={key:0},f=["value"],k={key:1},O={key:0},j=["onClick"],v=r((function(){return Object(c["createElementVNode"])("span",null,"|",-1)})),x=["onClick"];function w(e,t,n,r,w,C){var y=Object(c["resolveComponent"])("el-option"),A=Object(c["resolveComponent"])("el-select");return Object(c["openBlock"])(),Object(c["createElementBlock"])("div",i,[Object(c["createElementVNode"])("div",o,[Object(c["createElementVNode"])("button",{onClick:t[0]||(t[0]=function(e){return C.goWhite()})},"发布文章"),Object(c["createElementVNode"])("div",a,[Object(c["createElementVNode"])("ul",null,[(Object(c["openBlock"])(!0),Object(c["createElementBlock"])(c["Fragment"],null,Object(c["renderList"])(w.Mycolumns,(function(e,t){return Object(c["openBlock"])(),Object(c["createElementBlock"])("li",{key:e.id,class:Object(c["normalizeClass"])(w.columnIndex==e.id?"active":""),onClick:function(n){return C.changeID(e.id,t)}},Object(c["toDisplayString"])(e.name),11,l)})),128))])])]),Object(c["createElementVNode"])("div",s,[Object(c["createElementVNode"])("div",u,[Object(c["createTextVNode"])(Object(c["toDisplayString"])(w.Mycolumns[w.cIndex].name)+" ",1),d]),Object(c["createElementVNode"])("div",m,[Object(c["createVNode"])(A,{modelValue:w.doOne,"onUpdate:modelValue":t[1]||(t[1]=function(e){return w.doOne=e}),placeholder:"请选择博文类型"},{default:Object(c["withCtx"])((function(){return[(Object(c["openBlock"])(!0),Object(c["createElementBlock"])(c["Fragment"],null,Object(c["renderList"])(w.options,(function(e){return Object(c["openBlock"])(),Object(c["createBlock"])(y,{key:e.id,label:e.name,value:e.id},null,8,["label","value"])})),128))]})),_:1},8,["modelValue"]),1!=w.doOne?(Object(c["openBlock"])(),Object(c["createElementBlock"])("button",{key:0,onClick:t[2]||(t[2]=function(e){return C.apply()})},"应用")):Object(c["createCommentVNode"])("",!0)]),Object(c["createElementVNode"])("div",p,[1!==w.doOne?(Object(c["openBlock"])(),Object(c["createElementBlock"])("p",h,[b,Object(c["withDirectives"])(Object(c["createElementVNode"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(e){return w.allCheck=e}),type:"checkbox",onChange:t[4]||(t[4]=Object(c["withModifiers"])((function(e){return C.checkAllChecks()}),["stop"]))},null,544),[[c["vModelCheckbox"],w.allCheck]])])):Object(c["createCommentVNode"])("",!0),Object(c["createElementVNode"])("ul",null,[(Object(c["openBlock"])(!0),Object(c["createElementBlock"])(c["Fragment"],null,Object(c["renderList"])(w.articles,(function(n,r){return Object(c["openBlock"])(),Object(c["createElementBlock"])("li",{class:"EveryArticle",key:n.id},[Object(c["createElementVNode"])("div",null,[1!==w.doOne?(Object(c["openBlock"])(),Object(c["createElementBlock"])("span",g,[Object(c["withDirectives"])(Object(c["createElementVNode"])("input",{type:"checkbox",value:n.id,"onUpdate:modelValue":t[5]||(t[5]=function(e){return w.checkArticle=e})},null,8,f),[[c["vModelCheckbox"],w.checkArticle]])])):(Object(c["openBlock"])(),Object(c["createElementBlock"])("span",k,"::")),Object(c["createTextVNode"])(Object(c["toDisplayString"])(n.title),1)]),1===w.doOne?(Object(c["openBlock"])(),Object(c["createElementBlock"])("div",O,[Object(c["createElementVNode"])("span",{style:{color:"var(--blue)","font-size":".8rem"},onClick:function(t){return e.$router.push({name:"blog",params:{id:n.id}})}},"查看",8,j),v,Object(c["createElementVNode"])("span",{style:{color:"var(--red)","font-size":".8rem"},onClick:function(e){return C.deleteArFromColumn(r)}},"删除",8,x)])):Object(c["createCommentVNode"])("",!0)])})),128))])])])])}var C=n("1da1"),y=(n("a9e3"),n("4de4"),n("d3b7"),n("e9c4"),n("d81d"),n("96cf"),n("c9a1")),A={data:function(){return{Mycolumns:[{id:5,name:"wawa",user_id:985669637}],columnIndex:5,doOne:1,options:[{id:1,name:"批量操作"},{id:2,name:"添加文章"}],articles:[{btype:0,column_id:0,comment_count:2,content:"# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + width + margin-right\n盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n（因为用不了ie所以没有测试了！）",create_time:"2021-12-11T02:02:53.000+00:00",id:4,identify:0,status:0,subject:1,title:"盒子模型",type:0,up_count:0,update_time:"2021-12-11T02:02:53.000+00:00",url:null,user_id:985669637}],cIndex:0,allCheck:!1,checkArticle:[]}},methods:{changeID:function(e,t){this.columnIndex=e,this.cIndex=t,this.$router.push({name:"columnManage",params:{column:e}})},deleteArFromColumn:function(e){var t=this;return Object(C["a"])(regeneratorRuntime.mark((function n(){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:y["a"].confirm("确认删除？","确认消息").then((function(){t.deleteAr(e)}))["catch"]((function(){}));case 1:case"end":return n.stop()}}),n)})))()},deleteAr:function(e){var t=this;return Object(C["a"])(regeneratorRuntime.mark((function n(){var c,r,i;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return c={btype:t.articles[e].btype,column_id:-1,comment_count:t.articles[e].comment_count,content:t.articles[e].content,id:t.articles[e].id,identify:t.articles[e].identify,status:t.articles[e].status,subject:t.articles[e].subject,title:t.articles[e].title,type:t.articles[e].type,up_count:t.articles[e].up_count,url:t.articles[e].url,user_id:t.articles[e].user_id},n.next=3,t.$http.post("/discussPost/updateOnePost",t.qs.stringify(c));case 3:r=n.sent,i=r.data,0===i.code&&(t.getColumnArticle(),y["a"].alert("删除成功","提示消息")["catch"]((function(){})));case 6:case"end":return n.stop()}}),n)})))()},getAllColumns:function(){var e=this;return Object(C["a"])(regeneratorRuntime.mark((function t(){var n,c,r,i;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n={id:Number(localStorage.getItem("id"))},t.next=3,e.$http.post("/column-self/showAll",e.qs.stringify(n));case 3:c=t.sent,r=c.data,i=0;case 6:if(!(i<r.length)){t.next=13;break}if(r[i].id!==e.columnIndex){t.next=10;break}return e.cIndex=i,t.abrupt("break",13);case 10:i++,t.next=6;break;case 13:e.Mycolumns=r;case 14:case"end":return t.stop()}}),t)})))()},getColumnArticle:function(){var e=this;return Object(C["a"])(regeneratorRuntime.mark((function t(){var n,c,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n={column_id:e.columnIndex},t.next=3,e.$http.post("/column-self/showPosts",e.qs.stringify(n));case 3:c=t.sent,r=c.data,e.articles=r;case 6:case"end":return t.stop()}}),t)})))()},getNoColumnArticles:function(){var e=this;return Object(C["a"])(regeneratorRuntime.mark((function t(){var n,c,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n={id:Number(localStorage.getItem("id")),identify:0},t.next=3,e.$http.post("/discussPost/showAllPosts",e.qs.stringify(n));case 3:c=t.sent,r=c.data,e.articles=r.filter((function(e){return-1===e.column_id}));case 6:case"end":return t.stop()}}),t)})))()},apply:function(){var e=this;return Object(C["a"])(regeneratorRuntime.mark((function t(){var n,c,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(0!==e.articles.length){t.next=4;break}y["a"].alert("没有空闲文章！","提示消息")["catch"]((function(){})),t.next=16;break;case 4:if(0!==e.checkArticle.length){t.next=8;break}y["a"].alert("请选择文章","提示消息")["catch"]((function(){})),t.next=16;break;case 8:return n={articles:JSON.stringify(e.checkArticle),columnId:e.columnIndex},t.next=11,e.$http.post("/column-self/addArticlesForColumn",e.qs.stringify(n));case 11:c=t.sent,r=c.data,0===r.code&&(e.getNoColumnArticles(),y["a"].alert("添加成功","提示消息")["catch"]((function(){}))),e.allCheck=!1,e.checkArticle=[];case 16:case"end":return t.stop()}}),t)})))()},checkAllChecks:function(){this.allCheck?this.checkArticle=this.articles.map((function(e){return e.id})):this.checkArticle=[]},goWhite:function(){this.$router.push({name:"write"})}},mounted:function(){this.columnIndex=Number(this.$route.params.column),this.getAllColumns(),this.getColumnArticle()},watch:{doOne:function(e){1===e?this.getColumnArticle():2===e?this.getNoColumnArticles():this.getColumnArticle()},columnIndex:function(){this.doOne=1,this.getColumnArticle()},checkArticle:function(){this.checkArticle.length!==this.articles.length?this.allCheck=!1:this.allCheck=!0}}},N=(n("685b"),n("6b0d")),E=n.n(N);const V=E()(A,[["render",w],["__scopeId","data-v-83ce3304"]]);t["default"]=V},"685b":function(e,t,n){"use strict";n("eaf8")},d81d:function(e,t,n){"use strict";var c=n("23e7"),r=n("b727").map,i=n("1dde"),o=i("map");c({target:"Array",proto:!0,forced:!o},{map:function(e){return r(this,e,arguments.length>1?arguments[1]:void 0)}})},eaf8:function(e,t,n){}}]);
//# sourceMappingURL=chunk-51294eb8.c34cd61e.js.map