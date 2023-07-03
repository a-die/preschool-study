<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.reportReason" placeholder="举报原因" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button @click="getDataByProcessStatus(0)">未处理</el-button>
				<el-button @click="getDataByProcessStatus(1)">已处理</el-button>
            </div>
			
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
				<el-table-column prop="reportIdTo" label="该博文ID" width="100"></el-table-column>
				<el-table-column prop="reportIdFrom" label="举报人id" v-show="false"></el-table-column>
				<el-table-column prop="username" label="举报人" width="150"></el-table-column>
                <el-table-column prop="reportReason" label="举报原因" width="150"></el-table-column>
				<el-table-column prop="reportInformation" label="附加信息"></el-table-column>
                <!-- <el-table-column prop="url" label="该博文链接"></el-table-column> -->
				<el-table-column label="是否处理">
					<template #default="scope">
					    <el-tag :type="
					            scope.row.processStatus === 1? 'success': 'primary'
					        ">{{ scope.row.processStatus ==1?'已处理':'未处理' }}</el-tag>
					</template>
				</el-table-column>
                <el-table-column prop="createTime" label="发表时间">
					<template #default="scope">
						{{ formatDate(scope.row.createTime) }}
					</template>
				</el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">审核
						</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="审核" v-model="editVisible" width="30%">
            <el-form label-width="70px">
				<el-form-item label="ID" v-show="false">
				    <el-input v-model="form.id"></el-input>
				</el-form-item>
                <el-form-item label="举报人ID" v-show="false">
                    <el-input v-model="form.reportIdFrom"></el-input>
                </el-form-item>
                <el-form-item label="举报原因">
                    <el-input v-model="form.reportReason" :disabled="true"></el-input>
                </el-form-item>
				
				<el-form-item label="标题">
					<el-input v-model="form.title" disabled></el-input>
				</el-form-item>
				
				<el-form-item label="内容">
					<v-md-preview :text="form.content"></v-md-preview>
				</el-form-item>
				
				<el-form-item label="回馈内容">
				    <el-input v-model="form.feedbackToContent"></el-input>
				</el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchData } from "../api/index";
import axios from "axios";
import qs from "qs";
export default {
    name: "basetable",
    setup() {
        const query = reactive({
            reportReason: "",
            pageIndex: 1,
            pageSize: 10,
        });
		
        const tableData = ref([]);
        const pageTotal = ref(0);
		
        // 获取表格数据
        const getData = () => {
			console.log("准备获取数据啦");
			//设置每一页的大小
			query.pageSize=10;
			try {
				//查找所有的举报信息数量
				axios.get('http://localhost:8081/reporting/getReportingNum'
				)
				.then(res=> {
					console.log(res.data)
					pageTotal.value = res.data|| 50;
					
				})
				.catch(error=> {
					console.log(error)
				});
				
				
				//查找当前页面的举报信息
				axios.post('http://localhost:8081/reporting/getAllReportingInformation',
					qs.stringify({
					  page : query.pageIndex,
					  size : query.pageSize
					})
				)
				  .then(res=> {
				    console.log(res.data)
					tableData.value = res.data;
					//pageTotal.value = res.data.length || 50;
				  })
				  .catch(error=> {
				    console.log(error)
				  });
			  
			} catch (e) {
			  console.log(e);
			}
			
        };
		
		getData();
		
		const getDataByProcessStatus = (op) => {
			console.log(op);
			console.log("准备获取数据啦");
			//设置每一页的大小
			query.pageSize=10;
			try {
				//查找所有的举报信息数量
				axios.post('http://localhost:8081/reporting/getReportingNumByProcessStatus',
					qs.stringify({
					  processStatus: op
					})
				)
				.then(res=> {
					console.log(res.data)
					pageTotal.value = res.data|| 50;
					
				})
				.catch(error=> {
					console.log(error)
				});
				
				
				//查找当前页面的举报信息
				axios.post('http://localhost:8081/reporting/getAllReportingInformationByProcessStatus',
					qs.stringify({
					  page : query.pageIndex,
					  size : query.pageSize,
					  processStatus: op
					})
				)
				  .then(res=> {
				    console.log(res.data)
					tableData.value = res.data;
					//pageTotal.value = res.data.length || 50;
				  })
				  .catch(error=> {
				    console.log(error)
				  });
			  
			} catch (e) {
			  console.log(e);
			}
		}
		
	
		const getDataByreportReason = () =>  {
			console.log("根据举报原因直接查询");
			try {
				console.log(query.reportReason)
				if(query.reportReason=="")getData();
				else{
					axios.post('http://localhost:8081/reporting/getReportingInformationByReportReason',
						qs.stringify({
							reportReason : query.reportReason
						})
					)
					.then(res=> {
						console.log(res.data)
						tableData.value = res.data;
						pageTotal.value = res.data.length || 50;
					
					})
					.catch(error=> {
						console.log(error)
					});
				
				}
				
			} catch (e) {
				console.log(e);
			}
			
			
		};
		
        

        // 查询操作
        const handleSearch = () => {
            query.pageIndex = 1;
            getDataByreportReason();
        };
        // 分页导航
        const handlePageChange = (val) => {
            query.pageIndex = val;
            getData();
        };
		

        // 删除操作
        const handleDelete = (index) => {
            // 二次确认删除
            ElMessageBox.confirm("确定要删除吗？", "提示", {
                type: "warning",
            })
                .then(() => {
                    ElMessage.success("删除成功");
                    tableData.value.splice(index, 1);
                })
                .catch(() => {});
        };
		
		const upadteStatus = (row) =>{
			console.log(row)
			var that = this;
			let op
			if(row.status==0)op="确定要将该用户冻结吗?"; //该用户是解封状态，点击就是冻结
			else op="确定要将该用户解封吗?"
			ElMessageBox.confirm(op, "提示", {
			    type: "warning",
			})
			.then(() => {
				axios.post('http://localhost:8081/user/updateStatus',
						qs.stringify({
							id: row.id,
							status: row.status ^ 1
						})
					) .then(response=> { 
					    console.log(response) 
						getData();
						if(row.status == 0)ElMessage.success("冻结成功");
						else ElMessage.success("解封成功");
						
					}) .catch(error=> { 
					    console.log(error)
						ElMessage.error("再接再厉");
					});
			    
				
			    
			})
			.catch(() => {});
		}

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
			id: "", //该举报信息id
			reportIdFrom: "",//举报人id
			feedbackToContent: "",//填写的回馈内容
			reportReason: "",//举报原因，作为反馈内容
			title: "",//文章标题
			content: "",//文章内容
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
            });
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
            ElMessage.success(`回馈第 ${idx + 1} 行成功`);
			console.log(form.reportIdFrom);
			// 添加信息到反馈表，反馈表状态设置为1，是因为该种情况算已经后台已经成功反馈了
			axios.post('http://localhost:8081/feedback/addFeedback', 
					qs.stringify({
						userId: form.reportIdFrom,
						feedbackToContent: form.feedbackToContent,
						feedbackFromContent: form.reportReason,
						state :1,
					})
				) .then(response=> { 
				    console.log(response) 
				}) .catch(error=> { 
				    console.log(error)
				});
			//更新该举报信息的状态,更新状态之后，在表格中的内容的状态也要改变，考虑是否像博文表那样分两个表格来进行处理
			axios.post('http://localhost:8081/reporting/updateProcessStatus',
					qs.stringify({
						id: form.id,
						processStatus: 1,
					})
				) .then(response=> { 
				    console.log(response) 
				}) .catch(error=> { 
				    console.log(error)
				});
			
			
			
				
			
			
        };
		
		getData();

        return {
            query,
            tableData,
            pageTotal,
            editVisible,
            form,
            handleSearch,
            handlePageChange,
            handleDelete,
            handleEdit,
            saveEdit,
			upadteStatus,
			getDataByProcessStatus,
        };
		
		
    },
	methods: {
		formatDate(data){
		                // 获取单元格数据
		    //let data = row[column.property]
			console.log(data)
		    if(data == null) {
		        return null
		    }
		    let dt = new Date(data)
		    return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
		},

	}
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
