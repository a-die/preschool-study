<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 博文下评论
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="username" label="评论者" width="150"></el-table-column>
				<!-- <el-table-column prop="discussPostUrl" label="评论博文链接"></el-table-column> -->
                <el-table-column prop="content" label="评论内容"></el-table-column>
                <el-table-column prop="create_time" label="发表时间">
					<template #default="scope">
						{{ formatDate(scope.row.create_time) }}
					</template>
				</el-table-column>
                <el-table-column label="操作" align="center">
                    <template #default="scope">
						<el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">查看博文信息
						</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除并通知其违规</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="查看" v-model="editVisible" width="70%">
            <el-form label-width="70px">
            	<el-form-item label="id" v-show="false">
            	    <el-input v-model="form.id"></el-input>
            	</el-form-item>
            	
            	
            	<el-form-item label="标题">
            		<el-input v-model="form.title" disabled></el-input>
            	</el-form-item>
            	
            	<el-form-item label="内容">
            		<v-md-preview :text="form.content1"></v-md-preview>
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
            address: "",
            name: "",
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
				//查找所有的评论数量
				axios.get('http://localhost:8081/comment/getCommentNum'
				)
				.then(res=> {
					console.log(res.data)
					pageTotal.value = res.data|| 50;
					
				})
				.catch(error=> {
					console.log(error)
				});
				
				
				//查找当前页面的用户
				axios.post('http://localhost:8081/comment/ShowAllCommentsByPage',
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
		
	
		const getDataByname = () =>  {
			console.log("根据性名模糊查询");
			try {
				console.log(query.name)
				if(query.name=="")getData();
				else{
					axios.post('http://localhost:8081/comment/showCommentsByUsername',
						qs.stringify({
							username : query.name
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
            getDataByname();
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
			title: "",
			content1: "",
			id: ""
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            Object.keys(form).forEach((item) => {
                form[item] = row[item];
				console.log(form[item]);
            });
			// console.log(form[item]);
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
   //          ElMessage.success(`修改第 ${idx + 1} 行成功`);
			// console.log(form);
			// axios.post('http://localhost:8081/user/updateSomeById', 
			// 		qs.stringify({
			// 			id: form.id,
			// 			username: form.username,
			// 			phonenumber: form.phonenumber,
			// 			email: form.email
			// 		})
			// 	) .then(response=> { 
			// 	    console.log(response) 
			// 	}) .catch(error=> { 
			// 	    console.log(error)
			// 	});
				
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
				console.log(form[item][1]);
				
			
            });
			
			
        };

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
