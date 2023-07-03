<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 未审核博文
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.title" placeholder="标题" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="username" label="用户名" width="150"></el-table-column>
				
				<el-table-column prop="title" label="标题" width="150"></el-table-column>
				
                <el-table-column prop="comment_count" label="评论数量" width="100"></el-table-column>
				
				<el-table-column prop="up_count" label="点赞数量" width="100"></el-table-column>
				
				<el-table-column label="博文类型" width="200">
					<template #default="scope">
						<el-tag :type=" 
								scope.row.btype === 0? 'primary' : 
								scope.row.btype === 1? 'success' : 'warning' ">
								{{ scope.row.btype === 0?'笔记' : 
								scope.row.btype === 1? '踩坑记录' : '提问' }}
						</el-tag>

					</template>
				</el-table-column>
                
				<el-table-column prop="name" label="学科类型" width="100"></el-table-column>

                <el-table-column prop="create_time" label="发表时间">
					<template #default="scope">
						{{ formatDate(scope.row.create_time) }}
					</template>
				</el-table-column>
				
				<el-table-column label="是否置顶">
					<template #default="scope">
						<el-radio-group v-model="scope.row.type" @change="changeType(scope.$index,scope.row)">
							<el-radio :label="1">是</el-radio>
							<el-radio :label="0">否</el-radio>
						</el-radio-group>
					</template>
				</el-table-column>
				
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">查看
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>



		<!-- 审核文章 -->
		<el-dialog title="审核" v-model="editVisible" width="70%">
			
		    <el-form label-width="70px">
				<el-form-item label="id" v-show="false">
				    <el-input v-model="form.id"></el-input>
				</el-form-item>
				
				
				<el-form-item label="标题">
					<el-input v-model="form.title" disabled></el-input>
				</el-form-item>
				
				<el-form-item label="内容">
					<v-md-preview :text="form.content"></v-md-preview>
				</el-form-item>
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
import { fetchData } from "../../api/index";
import axios from "axios";
import qs from "qs";
export default {
    name: "NotAuditBlogTable",
    setup() {
        const query = reactive({
            title: "",
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
				
				//查找所有的博文数量
				axios.post('http://localhost:8081/discussPost/getBlogNumByIdentify',
					qs.stringify({
						identify: 0
					})
				)
				.then(res=> {
					console.log(res.data)
					pageTotal.value = res.data;
					
				})
				.catch(error=> {
					console.log(error)
				});
				
				//查找当前页面的博文
				axios.post('http://localhost:8081/discussPost/getBlogByIdentify',
				
					qs.stringify({
					  page : query.pageIndex,
					  size : query.pageSize,
					  identify : 0
					})
				)
				  .then(res=> {
				    console.log(res.data)
					tableData.value = res.data;
					//pageTotal.value = 4;
				  })
				  .catch(error=> {
				    console.log(error)
				  });
			  
			} catch (e) {
			  console.log(e);
			}
			
			
        };
        getData();
		
		const getDataBytitle = () =>  {
			console.log("根据标题模糊查询");
			try {
				console.log(query.title)
				if(query.title=="")getData();
				else{
					axios.post('http://localhost:8081/discussPost/getDiscussPostListByTitle',
						qs.stringify({
							title : query.title,
							identify : 0
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
            getDataBytitle();
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

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
			title: "",
            content: "",
			id: ""
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
            // ElMessage.success(`修改第 ${idx + 1} 行成功`);
			// axios.post('http://localhost:8081/discussPost/setIdentify', 
			// 	    qs.stringify({
			// 			id : form.id,
			// 			identify: 0
			// 	    })
					
			// 	) .then(response=> { 
			// 	    console.log(response) 
			// 	}) .catch(error=> { 
			// 	    console.log(error)
			// 	});
				
            Object.keys(form).forEach((item) => {
                tableData.value[idx][item] = form[item];
				console.log(form[item][1]);
				
			
            });
			
			getData();
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
        };
    },
	methods: {
		formatDate(data){
		                // 获取单元格数据
			console.log(data)
		    if(data == null) {
		        return null
		    }
		    let dt = new Date(data)
		    return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
		},
		changeType(index,row){
			console.log(row)
			console.log(row.type)
			//改变置顶状态
			axios.post('http://localhost:8081/discussPost/updateBlogType',
				    qs.stringify({
						id: row.id,
						type: row.type
				    })
					
				) .then(response=> { 
				    console.log(response) 
				}) .catch(error=> { 
				    console.log(error)
				});
		}
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
