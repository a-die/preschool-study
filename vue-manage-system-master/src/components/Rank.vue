<!-- 地区销售排行 -->
<template>
	<div class="com-container">
		<div class="com-chart3"></div>
	</div>
</template>

<script>
export default {
  data () {
    return {
      chartInstance: null,
      allData:[],
	  xdataArr:[],
	  ydataArr:[],
	  startValue:0,
	  endValue:4,
      timerId: null // 定时器的标识
    }
  },
  mounted () {
	this.$socket.registerCallBack("Rank",this.getData)
    this.initChart()
    this.getData()
    window.addEventListener('resize', this.screenAdapter)
  },
  unmounted () {
    window.removeEventListener('resize', this.screenAdapter)
    clearInterval(this.timerId)
  },
  destroyed(){
  	this.$socket.unRegisterCallBack("Rank")
  },
  methods: {
    initChart () {
      this.chartInstance = this.$echarts.init(document.getElementsByClassName("com-chart3")[0],'white')
      var initOption = {
        title: {
          text: '▍ 各学科博文数量统计',
          left: 20,
          top: 20,
		  textStyle:{
		  	color:'white',
		  	fontWeight:'normal',
		  },
        },
		grid:{
			top:'24%',
			left:'10%',
			right:'10%',
			bottom:'25%',
		},
        xAxis: {
          type: 'category',
		  axisLine: {
		        lineStyle: {
		  		  color:'#bbbbbb'
		  	  }
		  },
		  axisLabel:{
		  	color:'#bbbbbb',
			interval:0,
			rotate:40,
		  },
		  axisTick:{
			  show:false,
		  }
        },
        yAxis: {
          type: 'value',
		  axisLine: {
		        lineStyle: {
		  		  color:'#bbbbbb'
		  	  }
		  },
		  axisLabel:{
		  	color:'#bbbbbb'
		  }
        },
		tooltip:{
			show:true,
			formatter:'{b}类博文数量:{c}'
		},
        series: [
          {
            type: 'bar',
			itemStyle:{
				//颜色渐变
				color: {
				type: 'linear', // 线性渐变
				x: 0,
				y: 0,
				x2: 0,
				y2: 1,
				colorStops:[
				{
					offset: 0, color: '#3fb1e3' // 0%处的颜色为红色
				},
				{
					offset: 1, color: '#6be6c1' // 100%处的颜色为蓝
				}
				]
			}
			}
          }
        ]
      }
      this.chartInstance.setOption(initOption)
      this.chartInstance.on('mouseover', () => {
        clearInterval(this.timerId)
      })
      this.chartInstance.on('mouseout', () => {
        this.startInterval()
      })
    },
    getData () {
      var that=this;
      this.$axios.get('http://localhost:8081/getRankData')
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
    updateChart () {
      this.xdataArr=[];
      this.ydataArr=[];
      //请求成功，回调
      for(var i=0;i<this.allData.length;i++){
      		var subject=this.allData[i].subject;
      		var count=this.allData[i].count;
      		//放入数组中
      		this.xdataArr.push(subject);
      		this.ydataArr.push(count);
      }
      var dataOption = {
        xAxis: {
          data: this.xdataArr
        },
		//利用区域缩放，来做区域滚动显示
		dataZoom:{
			show:false,
			startValue:this.startValue,
			endValue:this.endValue,
		},
        series: [
          {
            data: this.ydataArr
          }
        ]
      }
      this.chartInstance.setOption(dataOption)
    },
    screenAdapter () {
      var size=document.getElementsByClassName("com-chart3")[0].offsetWidth/100*3.6
      var adapterOption = {
       title:{
       	textStyle:{
       		fontSize:size,
       	},
       },
       series:[
       	{
       		barWidth:size*1.3,
       	}
       ]
      }
      this.chartInstance.setOption(adapterOption)
      this.chartInstance.resize()
    },
	startInterval(){
		if(this.timerId){
			clearInterval(this.timerId);
		}
		this.timerId=setInterval(()=>{
			this.startValue++
			this.endValue++
			if(this.endValue>this.allData.length-1){
				this.startValue=0
				this.endValue=4
			}
			this.updateChart()
		},2000)
	}
  }
}
</script>

<style>
</style>
