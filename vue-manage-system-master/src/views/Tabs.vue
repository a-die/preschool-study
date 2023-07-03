<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 用户反馈信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`未读消息(${state.unread.length})`" name="first">
                    <el-table :data="state.unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template #default="scope">
                                <span class="message-title">{{scope.row.feedbackFromContent}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" width="180">
                        	<template #default="scope">
                        		{{ formatDate(scope.row.createTime) }}
                        	</template>
                        </el-table-column>
                        <el-table-column width="120">
                            <template #default="scope">
                                <el-button size="small" @click="handleRead(scope.$index,1)">处理</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary">全部处理</el-button>
                    </div>
                </el-tab-pane>
				
                <el-tab-pane :label="`已处理消息(${state.deal.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table :data="state.deal" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template #default="scope">
                                    <span class="message-title">{{scope.row.feedbackFromContent}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createTime" width="180">
                            	<template #default="scope">
                            		{{ formatDate(scope.row.createTime) }}
                            	</template>
                            </el-table-column>
                            <el-table-column width="120">
                                <template #default="scope">
                                    <el-button size="small" @click="handleRead1(scope.$index,2)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">删除全部</el-button>
                        </div>
                    </template>
                </el-tab-pane>
				
                <el-tab-pane :label="`回收站(${state.recycle.length})`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="state.recycle" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template #default="scope">
                                    <span class="message-title">{{scope.row.feedbackFromContent}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createTime" width="180">
                            	<template #default="scope">
                            		{{ formatDate(scope.row.createTime) }}
                            	</template>
                            </el-table-column>
                            <el-table-column width="120">
                                <template #default="scope">
                                    <el-button @click="handleRestore(scope.$index)">还原</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">清空回收站</el-button>
                        </div>
                    </template>
                </el-tab-pane>
            </el-tabs>
        </div>
		
		<!-- 弹窗 -->
		<el-dialog title="审核" v-model="editVisible" width="30%">
			
		    <el-form label-width="70px">

		    </el-form>
			
		    <template #footer>
		        <span class="dialog-footer">
		            <el-button @click="editVisible = false">取 消</el-button>
		            <el-button type="primary" @click="saveEdit">确定</el-button>
		        </span>
				
		    </template>
			
		</el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";
import qs from "qs";
export default {
    name: "tabs",
    setup() {
        const message = ref("first");
		const identify = ref(0);
        const state = reactive({
            unread: [
                
            ],
            deal: [
               
            ],
            recycle: [
                
            ],
        });
		
		// 获取表格数据
		const getData = () => {
			console.log("准备获取数据啦");

			
			try {
				//通过状态查找反馈信息，未读
				axios.post('http://localhost:8081/feedback/getFeedBackByState',
					qs.stringify({
						state: 0
					})
				)
				.then(res=> {
					console.log(res.data)
					state.unread = res.data;
				})
				.catch(error=> {
					console.log(error)
				});
				
				//通过状态查找反馈信息，已处理
				axios.post('http://localhost:8081/feedback/getFeedBackByState',
					qs.stringify({
						state: 1
					})
				)
				.then(res=> {
					console.log(res.data)
					state.deal = res.data;
				})
				.catch(error=> {
					console.log(error)
				});
				
				//通过状态查找反馈信息，删除
				axios.post('http://localhost:8081/feedback/getFeedBackByState',
					qs.stringify({
						state: 2
					})
				)
				.then(res=> {
					console.log(res.data)
					state.recycle = res.data;
				})
				.catch(error=> {
					console.log(error)
				});
				
			  
			} catch (e) {
			  console.log(e);
			}
			
			
		};
		getData();
		
		
		const formatDate = (data) => {
		                // 获取单元格数据
			console.log(data)
		    if(data == null) {
		        return null
		    }
		    let dt = new Date(data)
		    return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
		};
		
		
		// 表格编辑时弹窗和保存
		const editVisible = ref(false);
		let form = reactive({
			title: "",
		    content: "",
			id: ""
		});
		
		

		let idx = -1;
        const handleRead = (index,op) => {
			idx = index;
			
			identify.value = op;
			console.log(identify.value);
			console.log("开始处理")
			//在这个位置弹窗出来，填写反馈信息
			editVisible.value = true;
        };
		
		const saveEdit = () => {
		    editVisible.value = false;
			const item = state.unread.splice(idx, 1);
			console.log(item);
			state.deal = item.concat(state.deal);
		    ElMessage.success(`修改第 ${idx + 1} 行成功`);
			
			axios.post('http://localhost:8081/feedback/updateFeedback',
				qs.stringify({
					id : Number(form.id),
					state: Number(identify.value)
				})
			)
			.then(res=> {
				console.log(res.data)
				state.unread = res.data;
			})
			.catch(error=> {
				console.log(error)
			});
			
				
		    Object.keys(form).forEach((item) => {
		        tableData.value[idx][item] = form[item];
				console.log(form[item][1]);
				
			
		    });
			
			getData();
		};
		
        const handleDel = (index) => {
            const item = state.deal.splice(index, 1);
            state.recycle = item.concat(state.recycle);
        };
        const handleRestore = (index) => {
            const item = state.recycle.splice(index, 1);
            state.read = item.concat(state.read);
        };

        return {
            message,
            state,
            handleRead,
            handleDel,
            handleRestore,
			getData,
			saveEdit,
			formatDate,
			editVisible,
			identify,
        };
    },
};
</script>

<style>
.message-title {
    cursor: pointer;
}
.handle-row {
    margin-top: 30px;
}
</style>

