(function(){
		function MenuControl(interval){
			//时间间隔  1000毫秒
			this.interval=interval||1000;
		}
		/*锁定指定元素 指定时间*/
		MenuControl.prototype.lockItem=function(item,time,txt){
			if(item&&item.tagName.toLowerCase()=='input'){
				//获取元素原value
				var value=item.getAttribute("value");
				console.log(value);
				//禁用元素
				item.setAttribute("disabled","disabled");
				//创建定时器
				var timer=window.setInterval(function(){
					console.log(time);
					if(time>0){
						item.setAttribute("value",txt?--time+txt:--time);
					}else{
						window.clearInterval(timer);
						item.removeAttribute("disabled");
						item.setAttribute("value",value);
					}
				},this.interval);
			}else{
				console.log("不合法元素类型");
			}
		};
		window.MenuControl=MenuControl;
}());