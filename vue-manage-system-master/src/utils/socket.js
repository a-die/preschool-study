export default class SocketService{
	
	static instance=null
	static get Instance(){
		if(!this.instance){
			this.instance=new SocketService()
		}
		return this.instance
	}
	
	socket = null
	//存储回调函数
	callBackMapping={}
	
	connect(){
		if (!window.WebSocket) {
		        console.log("您的浏览器不支持WebSocket");
		      } else {
		        console.log("您的浏览器支持WebSocket");
			}
		// 开启一个websocket服务
		this.socket = new WebSocket("ws://localhost:8081/imserver/admin");
		//打开事件
		   this.socket.onopen = function () {
		          console.log("websocket已打开");
		        };
		//  浏览器端收消息，获得从服务端发送过来的文本消息
		    var that=this;
		    this.socket.onmessage = function (msg) {
				 console.log("浏览器发来消息"+msg.data)
				  //得到回调函数标识
				  var socketType=msg.data
				  if(that.callBackMapping[socketType]){
					  that.callBackMapping[socketType].call(this)
				  }
		        };
		//关闭事件
		    this.socket.onclose = function () {
		          console.log("websocket已关闭");
		        };
		//发生了错误事件
		   this.socket.onerror = function () {
		          console.log("websocket发生了错误");
		        }
	}
	registerCallBack(type,callBack){
		this.callBackMapping[type]=callBack
	}
	unRegisterCallBack(type){
		this.callBackMapping[type]=null
	}
}