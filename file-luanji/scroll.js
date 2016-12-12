(function(){
	function Scroller(container, scroller, speed){
		this.container=container;
		this.scroller=scroller;
		this.speed=speed;
	}
	Scroller.prototype.initSize=function(){
		this.container.height=this.container.clientHeight;
		this.scroller.height=this.scroller.offsetHeight;
		this.maxScroll=this.scroller.height-this.container.height>0?this.scroller.height-this.container.height:0;
		this.animationTime=this.maxScroll/this.speed*1000;
		this.currentScrollTop=0;
	};
	Scroller.prototype.start = function() {
		var _this=this;
		_this.initSize();
		if(_this.maxScroll){
			window.setInterval(function(){
				if(_this.currentScrollTop>=_this.maxScroll){
					_this.currentScrollTop=0;
				}
				_this.scroller.style.transform="translate(0px,-"+_this.currentScrollTop+"px)";
				_this.currentScrollTop+=_this.speed;
			},30);
		}
	};
	window.Scroller=Scroller;
//	document.addEventListener("DOMContentLoaded",function(){
//		var scroller=new Scroller($("#scroller").parent().get(0),$("#scroller").get(0),1);
//		scroller.start();			
//	});
})()