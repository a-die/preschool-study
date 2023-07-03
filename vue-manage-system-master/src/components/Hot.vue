<template>
	<div class="com-container">
		<div class="title">
			<span :style="{fontSize:fontsize+'px'}">{{'▍ '+title}}</span>
		</div>
		<div class="com-chart1"></div>
		<sapn class="arr-left" @click='toleft()' :style="{fontSize:fontsize+'px'}">&lt;</sapn>
		<span class="arr-right" @click="toright()" :style="{fontSize:fontsize+'px'}">></span>
	</div>
</template>

<script>
	//import chalk from '../../public/static/theme/westeros.js'
	export default {
		data(){
			return {
				chartInstance:null,
				allData:[],
				seriesData:[],
				legendData:[],
				currentIndex:0,//当前展示的一级分类数据
				title:'',
				fontsize:0,
			}
		},
		mounted(){
			this.$socket.registerCallBack("Hot",this.getData)
			this.initChart()
			this.getData()
			window.addEventListener('resize',this.screenAdapter)
		},
		unmounted(){
			window.removeEventListener('resize',this.screenAdapter)
		},
		destroyed(){
			this.$socket.unRegisterCallBack("Hot")
		},
		methods:{
			initChart(){
				this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart1")[0],'white')
				var initOption={
					legend:{
						top:'13%',
						textStyle:{
							color:'white',
						}
					},
					series:[{
						type:'pie',
					}]
				}
				this.chartInstance.setOption(initOption)
			},
			getData(){
				var that=this
				this.$axios.get('http://localhost:8081/getHotData')
				.then(function(res){
					//请求成功，回调
					that.allData=res.data;
					that.updateChart()
					that.screenAdapter()
				})
				.catch(function(err){
					//请求失败，回调
					console.log(err)
				})
			},
			//会变化的部分，全放在这个方法里面
			updateChart(){
				//设置标题
				this.title=this.allData[this.currentIndex].name+'类博文数占比统计'
				//设置给饼图的数据, 数组,数组中包含一个又一个的对象
				this.seriesData=this.allData[this.currentIndex].children.map(item=>{
					return {
						name:item.name,
						value:item.value
					}
				})
				this.legendData=this.allData[this.currentIndex].children.map(item=>{
					return {
						name:item.name,
					}
				})
				var dataOption={
					legend:{
						data:this.legendData
					},
					series:[
						{
							data:this.seriesData,
							label:{
								show:true,
								color:'#bbbbbb',
								formatter:function(arg){
									return "数量:"+arg.value
								}
							}
						}
					]
				}
				this.chartInstance.setOption(dataOption)
			},
			screenAdapter(){
				this.fontsize=document.getElementsByClassName("com-chart1")[0].offsetWidth/100*3.6
				var adapterOption={
					legend:{
						itemWidth:this.fontsize,
						itemHeight:this.fontsize,
						itemGap:this.fontsize,
						textStyle:{
							fontSize:this.fontsize*0.9,
						}
					},
					series:[
						{
							//饼图大小
							radius:this.fontsize*5,
							//饼图位置,center确定饼图的圆心的位置
							center:['50%','67%'],
						}
					]
				}
				this.chartInstance.setOption(adapterOption)
				this.chartInstance.resize()
			},
			toleft(){
				this.currentIndex--
				if(this.currentIndex<0){
					this.currentIndex=this.allData.length-1
				}
				this.updateChart()
			},
			toright(){
				this.currentIndex++
				if(this.currentIndex==this.allData.length){
					this.currentIndex=0
				}
				this.updateChart()
			}
		}
	}
</script>

<style>
	.title {
			position: absolute;
			left:20px;
			top:20px;
			z-index:10;
			color:white;
	}
	.arr-left {
		position:absolute;
		left:6%;
		top:50%;
		cursor:pointer;
		color:white;
	}
	 .arr-right {
		 position:absolute;
		 right:6%;
		 top:50%;
		 cursor:pointer;
		 color:white;
	 }
</style>