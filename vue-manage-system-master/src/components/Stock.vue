<template>
	<div class="com-container">
		<div class="com-chart4"></div>
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
				this.initChart()
				this.getData()
				window.addEventListener('resize',this.screenAdapter)
		},
		unmounted(){
				window.removeEventListener('resize',this.screenAdapter)
		},
		methods:{
			initChart(){
				this.chartInstance=this.$echarts.init(document.getElementsByClassName("com-chart4")[0],'dark')
				var initOption={
					
				}
				this.chartInstance.setOption(initOption)
			},
			getData(){
				this.allData=
				[{name:'',count:''},{},{},{}]
				// this.$axios.get('http://localhost:8083/findAll')
				// .then(function(res){
				// 	that.allData=res.data;
				this.updateChart()
				this.screenAdapter()
				// })
				// .catch(function(err){
				// 	//请求失败，回调
				// 	console.log(err)
				// })
			},
			updateChart(){
				var dataOption={
					
				}
				this.chartInstance.setOption(dataOption)
			},
			screenAdapter(){
				var size=document.getElementsByClassName("com-chart4")[0].offsetWidth/100*3.6
				var adapterOption={
					
				}
				this.chartInstance.setOption(adapterOption)
				this.chartInstance.resize()
			}
		}
	}
</script>

<style>
</style>
