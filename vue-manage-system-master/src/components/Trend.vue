<template>
	<div class="com-container">
		<div class="title">
			<span :style="{fontSize: fontsize+'px'}">{{'▍ '+title}}</span>
			<span class="iconfont title-icon" :style="{fontSize: fontsize+'px'}" @click="showChoices=!showChoices">&#xe6eb;</span>
			<div class="select-con" v-show="showChoices">
				<div class="select-item" :style="{fontSize: fontsize+'px',marginLeft:fontsize+'px'}" v-for="item in selectData" :key="item.key" @click="handleSelect(item.key)">
					{{item.title}}
				</div>
			</div>
		</div>
		<div class="com-chart5"></div>
	</div>
</template>

<script>
	export default {
		data(){
			return {
				chartInstance:null,
				allData:[],
				xData:null,
				yData:[],
				selectData:[],
				showChoices:false,//是否显示可选项
				dataChoice:'data1',//当前选择的数据对象
				title:'',
				fontsize:0,
			}
		},
		mounted(){
			this.$socket.registerCallBack("Trend",this.getData)
			this.initChart()
			this.getData()
			window.addEventListener('resize',this.screenAdapter)
		},
		unmounted(){
			window.removeEventListener('resize',this.screenAdapter)
		},
		destroyed(){
			this.$socket.unRegisterCallBack("Trend")
		},
		methods:{
			initChart(){
				this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart5")[0],'white')
				var initOption={
					xAxis:{
						type:'category',
						boundaryGap:false,
						axisLabel:{
							color:'#bbbbbb'
						},
					},
					yAxis:{
						type:'value',
						axisLine:{
							show:true,
						},
						splitLine:false,
						axisLabel:{
							color:'#bbbbbb'
						}
					},
					grid:{
						show:false,
						top:'30%',
						left:'15%',
						right:'10%',
						bottom:'12%',
					},
					series:[
						{
							type:'line',
						    smooth:true,
						    areaStyle:{
								color:{
									type: 'linear', // 线性渐变
									x: 0,
									y: 0,
									x2: 0,
									y2: 1,
									colorStops:[
									{
									  offset: 0, color: '#5052EE' // 0%处的颜色为红色
									},
									{
									offset: 1, color: '#AB6EE5' // 100%处的颜色为蓝
									}
									]
								}
							}
					    },
				    ]
				}
				this.chartInstance.setOption(initOption)
			},
			
			
			getData(){
				var that=this
				this.$axios.get('http://localhost:8081/getTrendData')
				.then(function(res){
					that.allData=res.data;
					that.updateChart()
					that.screenAdapter()
				})
				.catch(function(err){
					//请求失败，回调
					console.log(err)
				})
			},
			
			
			updateChart(){
				this.selectData=[]
				if(this.dataChoice!='data1'){
					this.selectData.push(this.allData.data1)
				}
				if(this.dataChoice!='data2'){
					this.selectData.push(this.allData.data2)
				}
				if(this.dataChoice!='data3'){
					this.selectData.push(this.allData.data3)
				}
				//折线图标题
				this.title=this.allData[this.dataChoice].title
				
				this.xData=this.allData.category
				this.yData=this.allData[this.dataChoice].data
				var dataOption={
					xAxis:{
						data:this.xData
					},
					series:[
						{
							data:this.yData
						}
					]
				}
				this.chartInstance.setOption(dataOption)
			},
			screenAdapter(){
				this.fontsize=document.getElementsByClassName("com-chart5")[0].offsetWidth/100*3.6
				var adapterOption={
					
				}
				this.chartInstance.setOption(adapterOption)
				this.chartInstance.resize()
			},
			handleSelect(currentType){
				this.dataChoice=currentType
				this.updateChart()
				this.showChoices=false
				
			}
		}
	}
</script>

<style>
	.title {
		position: absolute;
		left:20px;
		top:10px;
		z-index:10;
		color:white;
		
	}
	.title-icon {
		margin-left: 10px;
		cursor:pointer;
	}
	
</style>
