require(["dojo/dom","dojo/on","dojo/dom-style","dojo/query","dojo/dom-class","dojo/fx","dojo/dom-attr","dojo/_base/fx","dojo/request/script","dojo/json","dojo/ready"],    function(dom, on, domStyle, query, domClass, coreFx, domAttr, fx, script, JSON, ready){	var click = false;	var ltrID = 0;	var telNumber = '';	var prizeName = '';	var prizeValue = 0;	ready(function(){		prizeName = document.getElementById('hdnPrize').value;    	var prizeInnerHTML = document.getElementById('przName').innerHTML;    	    	// 获取活动日期        var startTime = document.getElementById('hdndivBgnDtTm').value;         var endTime = document.getElementById('hdndivEndDtTm').value;        document.getElementById('startTime').innerHTML = startTime.slice(4,6)+'月'+startTime.slice(6,8)+'日';        document.getElementById('endTime').innerHTML = endTime.slice(4,6)+'月'+startTime.slice(6,8)+'日';		 // 奖品列表		prizeList = eval('('+prizeInnerHTML+')');		console.log(typeof prizeList);		dom.byId('ruleTtl').innerHTML = dom.byId("hdnTtl").value;		dom.byId('ruleCon').innerHTML = dom.byId("hdnCon").value;    	myTab();		for(var i =0, length = prizeList.length; i< length; i++){			if(prizeList[i] == prizeName){				prizeValue = i;			}		}		// 判断是否登录    	on(dom.byId('pointer'), 'click', readyOne);        if(document.getElementById('hdnReqTyp').value == 1){        	on(dom.byId('pointer'), 'click', readyOne);        } else if(document.getElementById('hdnReqTyp').value == 2){        	readyTwo();        	on(dom.byId('pointer'), 'click', function(){        		return false;        	});        }        function readyOne(){         	document.getElementById('hdnReqTyp').value = "2";        	frm_act05.submit();        	        };//        window.onload=function(){            ////            readyTwo();//        	lottery.init('lottery');//            on(dom.byId('start'), 'click', function(){//                if (click) {//                    return false;//                }else{//                    lottery.speed = 100;//                    roll();//                    click = true;//                    document.getElementById('sureBtn').onclick = function(){//                        Slider.slideUp(popup, 300);//                    };//                    return false;//                }//            });//        };                            });	var myTab = function(){        var n = 10;        var timer = null;        on(dom.byId('ruleLi'), 'click', function(){            clearInterval(timer);            domClass.remove(dom.byId('record'), 'act');            domClass.add(dom.byId('rule'), 'act');        });        on(dom.byId('recordLi'), 'click', function(){            domClass.remove(dom.byId('rule'), 'act');            domClass.add(dom.byId('record'), 'act');            clearInterval(timer);            var h = dom.byId('scrollUl').clientHeight;            if(h>170){                timer = setInterval(function(){                    n+=1;                    if(n>(h-120)){n=10;}                    domStyle.set('scrollUl', 'top', -n+'px');                },50);            };        });    };	var lottery = {            index:-1,	//当前转动到哪个位置，起点位置            count:0,	//总共有多少个位置            timer:0,	//setTimeout的ID，用clearTimeout清除            speed:20,	//初始转动速度            times:0,	//转动次数            cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节            prize:-1,	//中奖位置            init:function(){                if (query(".lottery-unit").length>0) {                    $lottery = dom.byId("#lottery");                    $units = query(".lottery-unit");                    this.obj = $lottery;                    this.count = $units.length;                    query(".lottery-unit").forEach(function(node) {                        domClass.remove(node, "active");                    });                }            },            roll:function(){                var index = this.index;                var count = this.count;                var lottery = this.obj;                query("#lottery-unit-"+index).forEach(function(node) {                    domClass.remove(node, "active");                });                index += 1;                if (index>count-1) {                    index = 0;                }                query("#lottery-unit-"+index).forEach(function(node) {                    domClass.add(node, "active");                });                this.index = index;                return false;            },            stop:function(index){                this.prize=index;                return false;            }        };        function roll(){            lottery.times += 1;            lottery.roll();            if (lottery.times > lottery.cycle+10 && lottery.prize == lottery.index) {                clearTimeout(lottery.timer);                if (popup.offsetHeight === 0) {                    // 不可见，调用Slider.slideDown函数：在300毫秒内下拉                    Slider.slideDown(popup, 300);                }                lottery.prize=-1;                lottery.times=0;                click=false;       //  如果已经点击则点击失效，运动停止后可以再次点击            }else{                if (lottery.times<lottery.cycle) {                    lottery.speed -= 10;                }else if(lottery.times==lottery.cycle) {//                    var index = Math.random()*(lottery.count)|0;                    lottery.prize = prizeValue;   // 设置奖品中奖位置                }else{                    if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {                        lottery.speed += 110;                    }else{                        lottery.speed += 20;                    }                }                if (lottery.speed < 40) {                    lottery.speed=40;                };                lottery.timer = setTimeout(roll,lottery.speed);            }            return false;        }        function readyTwo(){    		telNumber = dom.byId('hdntelNumber').value; 		        	ltrID = dom.byId('hdnLtrID').value;            lottery.init('lottery');            on(dom.byId('start'), 'click', function(){                if (click) {                    return false;                }else{                    lottery.speed = 100;                    roll();                    click = true;                    document.getElementById('sureBtn').onclick = function(){                        Slider.slideUp(popup, 300);                    };                    return false;                }            });            var Index = '';        	var addTimeMonth = '';        	var addTimeDay = '';    	            var d = new Date(),            dateNow = d.toString();            script.get('/online/services/ol037?channel=f64b10f722&&param={"ltrId":"'+ltrID+'","mblNum":"'+telNumber+'"}',{                jsonp: "callback",                query: {                    clienttime: dateNow                }            }).then(function(data){              	                json = eval('('+data.json+')');                msg = eval('('+data.msg+')');                 var przNm = '';                                                     if (json.msg.promptMsg[0].msgID == 'MOL03701I'){                	przNm = json.luckyNumbers[0].przNm;                    telNumber = telNumber.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');                    var html = '';                    var actTimeMonth = '';                    var actTimeDay = '';                    for(var i =0, length = json.luckyNumbers.length; i<length; i++){                        actTimeMonth = json.luckyNumbers[i].actDtTm.slice(4,6);                        actTimeDay = json.luckyNumbers[i].actDtTm.slice(6,8);                        html += '<li class="myLi">'+actTimeMonth+'月'+actTimeDay+'日  '+telNumber+' 谢谢参与'+'</li>';                        if(json.luckyNumbers[i].actDtTm == json.luckyNumbers[i].addDtTm){                        	Index = i;                            addTimeMonth = actTimeMonth;                            addTimeDay = actTimeDay;                        }                    };                    document.getElementById('scrollUl').innerHTML = html;                    query('#scrollUl .myLi')[Index].innerHTML = addTimeMonth+'月'+addTimeDay+'日  '+telNumber+' '+przNm;                }                        },function(error){                alert(error);            });                                                                                                        };});