<template>
	<div class="com-container">
		<div class="com-chart">
		</div>
	</div>
</template>

<script>
	export default {
		data(){
			return {
				chartInstance:null,
				allData:[],
			}
		},
        mounted(){
			    this.$socket.registerCallBack("Map",this.getData)
				this.getData()
				window.addEventListener('resize',this.screenAdapter)
		},
		unmounted(){
				window.removeEventListener('resize',this.screenAdapter)
		},
		destroyed(){
			this.$socket.unRegisterCallBack("Map")
		},
		methods:{
			// initChart(){
			// 	this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart")[0])
			// },
			getData(){
				var that=this
				this.$axios.get('http://localhost:8081/getMapData')
				.then(function(res){
					//请求成功，回调
					that.allData=res.data;
					console.log(that.allData)
					that.updateChart()
					that.screenAdapter()
				})
				.catch(function(err){
					//请求失败，回调
					console.log(err)
				})
			},
			updateChart(){
				var that=this
				this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart")[0])
				this.$axios.get('http://localhost:3000/static/json/map/china.json')
				.then(function(res){
					that.$echarts.registerMap('chinaMap',res.data)
					var dataOption={
						    geo:{
								type:'map',
								map:'chinaMap',
								label: {
								            show: true // 展示标签
								       },
							},
							series: [
							          {
										data:that.allData,
							            geoIndex: 0,
							            type: 'map',
							          }
							        ],
							 visualMap: {
							          min: 0,
							          max: 300,
							          inRange: {
							            color: ['#85c3e0', '#3071bd'] // 控制颜色渐变的范围
							          },
									  text:['用户数'],
									  textStyle:{
										  color:'white',
									  }
							 }
						}
						that.chartInstance.setOption(dataOption)
					})
					.catch(function(err){
						//请求失败，回调
						console.log(err)
					})
			},
			screenAdapter(){
				this.updateChart();
				this.chartInstance.resize()
			}
		}
	}
</script>

<style>
</style>