<!--商家销售统计的横向柱状图-->
<template>
	<div class="com-container">
		<div class="com-chart2"></div>
	</div>
</template>

<script>
	export default{
		data(){
			return {
				chartInstance:null,
				allData:[],
				xdataArr:[],
				ydataArr:[],
				currentPage:1,
				totalPage:0,
				timeId:null
			}
		},
		mounted(){
			this.$socket.registerCallBack("Horizon",this.getData)
			this.initChart()
			this.getData()
			window.addEventListener('resize',this.screenAdapter)
		},
		unmounted(){
			clearInterval(this.timeId)
			window.removeEventListener('resize',this.screenAdapter)
		},
		destroyed(){
			this.$socket.unRegisterCallBack("Horizon")
		},
		methods:{
			//初始化echarts实例对象
			initChart(){
				this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart2")[0],'white')
				
				var initOption={
					title:{
						text:'▍ 用户发表博文数统计',
						textStyle:{
							color:'white',
							fontWeight:'normal',
						},
						left:19+'px',
						top:19,
						bottom:40,
					},
					grid:{
						top:'22%',
						left:'18%',
						right:'20%',
						bottom:'10%',
					},
					xAxis:{
						type:'value',
						name:'用户数',
						nameLocation:'end',
						nameGap:30,
						nameTextStyle:{
							color:'white',
							verticalAlign:'top'
						},
						axisLabel:{
							color:'#bbbbbb'
						}
					},
					yAxis:{
						type:'category',
						name:'博文篇数',
						nameLocation:'middle',
						nameGap:43,
						nameRotate:90,
						nameTextStyle:{
							color:'white'
						},
						axisLine: {
						      lineStyle: {
								  color:'#bbbbbb'
							  }
						},
						axisLabel:{
							color:'#bbbbbb'
						}
					},
					//鼠标滑过有柱子显示
					tooltip:{
						trigger:'axis',
						axisPointer:{
							type:'line',
							//置于底层
							z:0,
							lineStyle:{
								type:'solid',
								color:'#2D3443'
							}
						}
					},
					series:[
						{
							type:'bar',
							barWidth:66,
							label:{
								show:true,
								position:'right',
								textStyle:{
									color:'white'
								}
							},
							//每个柱子的样式
							itemStyle:{
								color:'#0098d9',
								//设置圆角柱子
								//顺序为：左上角，右上角，右下角，左下角
								barBorderRadius:[0,33,33,0],
							}
						}
					]
				}
				this.chartInstance.setOption(initOption)
				
				this.chartInstance.on('mouseover',()=>{
					clearInterval(this.timeId)
				})
				this.chartInstance.on('mouseout',()=>{
					this.startInterval()
				})
			},
			//获取服务器数据
			getData(){
				var that=this;
				this.$axios.get('http://localhost:8081/getHorizonData')
				.then(function(res){
					that.allData=res.data;
					that.updateChart()
					that.startInterval();
					that.screenAdapter()
				})
				.catch(function(err){
					//请求失败，回调
					console.log(err)
				})
				
			},
			//更新图表
			updateChart(){
				//分页
				this.totalPage=this.allData.length%4==0?this.allData.length/4:this.allData.length/4+1;
				var start=(this.currentPage-1)*4;
				var end=this.currentPage*4;
				
				var showData=this.allData.slice(start,end)
				
				//每次更新展示页数据前，需清空数组
				this.xdataArr=[];
				this.ydataArr=[];
				//请求成功，回调
				for(var i=0;i<showData.length;i++){
						var category=showData[i].category;
						var count=showData[i].count;
						//放入数组中
						this.xdataArr.push(count);
						this.ydataArr.push(category);
				}
				var dataOption={
		
					yAxis:{
						data:this.ydataArr
					},
					series:[
						{
							data:this.xdataArr,
						}
					]
				}
				this.chartInstance.setOption(dataOption)
				
			},
			startInterval(){
				if(this.timeId){
					clearInterval(this.timeId);
				}
				this.timeId=setInterval(()=>{
					this.currentPage++;
					if(this.currentPage>this.totalPage){
						this.currentPage=1;
					}
					this.updateChart();
				},3000)
			},
			screenAdapter(){
				var size=document.getElementsByClassName("com-chart2")[0].offsetWidth/100*3.6
				var adapterOption={
					title:{
						textStyle:{
							fontSize:size,
						},
					},
					xAxis:{
						nameTextStyle: {
						    fontSize:size*0.9,
						},
					},
					yAxis:{
						nameTextStyle: {
						    fontSize:size*0.9,
						},
					},
					//鼠标滑过有柱子显示
					tooltip:{
						axisPointer:{
							lineStyle:{
								width:size,
							}
						}
					},
					series:[
						{
							barWidth:size,
							//每个柱子的样式
							itemStyle:{
								//设置圆角柱子
								//顺序为：左上角，右上角，右下角，左下角
								barBorderRadius:[0,size/2,size/2,0],
								
							}
						}
					]
				}
				this.chartInstance.setOption(adapterOption)
				//!!!!!一定要调用echarts实例对象的resize方法
				this.chartInstance.resize()
			}
		}
	}
</script>

<style>
</style>
