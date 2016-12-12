/**
 * 前端验证组件
 **/
(function(){

	function Validate(msgMethod){
		this.autoMsg=true;
		this.showMethod=msgMethod;
	}
	/**
	 * 验证制定dom对象是否符合规范
	 * 支持三种参数放入方式 dom对象、 字符串选择器、 dom对象数组
	 * @return : boolean
	 */
	Validate.prototype.validate=function(node){
		//dom对象
		if(node instanceof HTMLElement){
			return this.check(node);
		}
		//选择器
		if(typeof node === "string"){
			let flag=false,list=document.querySelectorAll(node);
			list.length&&(flag=Array.prototype.every.call(list,this.check,this));
			return flag;
		}
		//dom 对象数组
		if(node instanceof Array){
			console.log("待验证dom对象数量"+node.length);
			flag=!!node.length&&node.every(this.check,this);
			return flag;
		}

		return false;
	};

	/**
	 * 设置是否自动提示错误信息
	 * @param {[type]} flag [description]
	 */
	Validate.prototype.setAutoMsg = function(flag) {
		this.autoMsg=!!flag;
	};

	/**
	 * 验证函数
	 * @param  {[type]} target [dom对象]
	 * @return {[type]}        [boolean]
	 */
	Validate.prototype.check = function(target){
		let value=target.value,
			pattern=target.getAttribute("pattern"),
			title=target.getAttribute("title"),
			classList=target.classList;
		
		if(typeof value === "undefined") {
			console.error("can not get target value");
			return false;
		}

		if(classList&&classList.contains("norequired")&&value.length==0){
			return true;
		}
		try{
			var reg=new RegExp(pattern);
			this.autoMsg&&!reg.test(value)&&this.showMethod.call(window,title);
			
			return reg.test(value);
		}catch(error){
			console.log(error);
		}
	};

	window.Validate=Validate;
})();