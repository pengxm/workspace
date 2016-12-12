require(["dojo/dom","dojo/on","dojo/dom-style","dojo/query","dojo/dom-class","dojo/fx","dojo/dom-attr","dojo/_base/fx","dojo/ready"],
    function(dom, on, domStyle, query, domClass, coreFx, domAttr, fx, ready){
    window.onload = function(){
        //设置遮罩层的高度
        var popupH = document.documentElement.clientHeight;
        console.log(popupH);
        document.getElementById('popup').style.height = popupH+'px';
        drawRouletteWheel();       
    
    };
     
    ready(function(){
        drawRouletteWheel();
        // 获取活动日期
        var startTime = dom.byId('hdndivBgnDtTm').value;
        var endTime = dom.byId('hdndivEndDtTm').value;
        dom.byId('startTime').innerHTML = startTime.slice(4,6)+'月'+startTime.slice(6,8)+'日';
        dom.byId('endTime').innerHTML = endTime.slice(4,6)+'月'+startTime.slice(6,8)+'日';
        
        var c = 1;
        
//        if(document.getElementById('hdnReqTyp').value == 1){
//        	on(dom.byId('pointer'), 'click', readyOne);
//        } else if(document.getElementById('hdnReqTyp').value == 2){
//        	readyTwo();
//        }
        on(dom.byId('pointer'), 'click', readyTwo);
        function readyOne(){
        	document.getElementById('hdnReqTyp').value = "2";
        	frm_act05.submit();
        }
        
        function readyTwo(){        	
        	console.log(dom.byId('hdnPrize').value);
            if(c>1){ alert('您今天已抽过奖'); return false; };
            c++;
            var data = 2;
//            var data = dom.byId('hdnPrize').value;
            function callBack(){
                setTimeout(function(){
                    if(data==1){
                        query('.smile').attr('src', 'images/cry.png');
                    }                    
                    domClass.add(dom.byId('popup'), 'act');
                    var txt = turnplate.restaraunts[data-1];
                    dom.byId('prizeT').innerHTML = txt;
                    // 创建节点   插入到 scrollUl 中;
                    var newItem=document.createElement("li");
                    var textnode=document.createTextNode('6月1日中奖情况: '+ txt);
                    newItem.appendChild(textnode);
                    var list=document.getElementById("scrollUl");
                    list.insertBefore(newItem,list.childNodes[0]);

                    query('.btn').on('click', function(){
                        coreFx.wipeOut({
                            node: "popup",
                            duration: 300
                        }).play();
                    });
                },5200);
            }

            switch (data) {
                case 1:
                    domClass.add(dom.byId('wheelcanvas'), 'run1');
                    callBack();
                    break;
                case 2:
                    domClass.add(dom.byId('wheelcanvas'), 'run2');
                    domClass.add(dom.byId('wrap'), 'run2');
                    callBack();
                    break;
                case 3:
                    domClass.add(dom.byId('wheelcanvas'), 'run3');
                    callBack();
                    break;
                case 4:
                    domClass.add(dom.byId('wheelcanvas'), 'run4');
                    callBack();
                    break;
                case 5:
                    domClass.add(dom.byId('wheelcanvas'), 'run5');
                    callBack();
                    break;
                case 6:
                    domClass.add(dom.byId('wheelcanvas'), 'run6');
                    callBack();
                    break;
            }
        }

        query('.rule').on('click', function(){
            domClass.remove(dom.byId('record'), 'act');
            domClass.add(dom.byId('rule'), 'act');
        });
        var n = 0;
        var timer = null;
        query('.record').on('click', function(){
            domClass.remove(dom.byId('rule'), 'act');
            domClass.add(dom.byId('record'), 'act');
            clearInterval(timer);
            var h = dom.byId('scrollUl').clientHeight;
            timer = setInterval(function(){
                n+=2;
                if(n>(h-90)){n=0;}
                domStyle.set('scrollUl', 'top', -n+'px');
            },100);
        });
    });
function drawRouletteWheel() {
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        //根据奖品个数计算圆周角度
        var arc = Math.PI / (turnplate.restaraunts.length / 2);
        var ctx = canvas.getContext("2d");
        //在给定矩形内清空一个矩形
        ctx.clearRect(0, 0, 422, 422);
        //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
        ctx.strokeStyle = "rgba(0,0,0,0)";
        //font 属性设置或返回画布上文本内容的当前字体属性
        ctx.font = '16px Microsoft YaHei';
        for (var i = 0; i < turnplate.restaraunts.length; i++) {
            var angle = turnplate.startAngle + i * arc;
            ctx.fillStyle = turnplate.colors[i];
            ctx.beginPath();
            //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
            ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
            ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
            ctx.stroke();
            ctx.fill();
            //锁画布(为了保存之前的画布状态)
            ctx.save();

            //----绘制奖品开始----
            ctx.fillStyle = "#fff"; //设置奖品字体颜色
            var text = turnplate.restaraunts[i];
            var line_height = 17;
            //translate方法重新映射画布上的 (0,0) 位置
            ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

            //rotate方法旋转当前的绘图
            ctx.rotate(angle + arc / 2 + Math.PI / 2);

            /* 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) */
            if (text.indexOf("M") > 0) {//流量包
                var texts = text.split("M");
                for (var j = 0; j < texts.length; j++) {   // 设置奖品两行字体大小
                    ctx.font = j == 0 ? 'bold 20px Microsoft YaHei' : '16px Microsoft YaHei';
                    if (j == 0) {
                        ctx.fillText(texts[j] + "M", -ctx.measureText(texts[j] + "M").width / 2, j * line_height);
                    } else {
                        ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                    }
                }
            } else if (text.indexOf("M") == -1 && text.length > 6) {//奖品名称长度超过一定范围
                text = text.substring(0, 6) + "||" + text.substring(6);
                var texts = text.split("||");
                for (var j = 0; j < texts.length; j++) {
                    ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                }
            } else {
                //在画布上绘制填色的文本。文本的默认颜色是黑色
                //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
                ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
            }

            //添加对应图标
            if (text.indexOf("邮币") > 0) {
                var img = document.getElementById("shan-img");
                img.onload = function () {
                    ctx.drawImage(img, -15, 10);
                };
                ctx.drawImage(img, -15, 10);
            } else if (text.indexOf("谢谢参与") >= 0) {
                var img = document.getElementById("sorry-img");
                img.onload = function () {
                    ctx.drawImage(img, -15, 10);
                };
                ctx.drawImage(img, -15, 10);
            }
            //把当前画布返回（调整）到上一个save()状态之前
            ctx.restore();
            //----绘制奖品结束----
        }
    }
}
		
		var prizeA = document.getElementById('przName').innerHTML;
		prizeA = eval('('+prizeA+')');
        var turnplate = {         //大转盘奖品名称
            restaraunts: [prizeA[0], prizeA[1], prizeA[2], prizeA[3], prizeA[4], prizeA[5]],
            colors: ["rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)", "rgba(0,0,0,0)"],	 //颜色				//大转盘奖品区块对应背景颜色
            outsideRadius: 152,			//大转盘外圆的半径
            textRadius: 135,				//大转盘奖品位置距离圆心的距离
            insideRadius: 10,			//大转盘内圆的半径
            startAngle: 0,				//开始角度
            bRotate: false				//false:停止;ture:旋转
        };
        
});

