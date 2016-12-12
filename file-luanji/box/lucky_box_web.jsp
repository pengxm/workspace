<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<title>${luckyDraw.title }</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="../../css/shop/luckyBox_base.css" rel="stylesheet" type="text/css" />
	<link href="../../css/shop/Chests-pc.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="../../css/activity/voice/css/message.css" />
	<script type="text/javascript" src="../../js/activity/voice/dialog.js"></script>
	<script type="text/javascript" src="../../js/shop/jquery.js"></script>
	<script type="text/javascript" src="../../js/shop/base.js"></script>
	<!-- 动态js-->
	<script defer type="text/javascript" src="../../js/shop/${luckyDraw.modality}.js"></script>
	
	<link href="../../css/shop/base.css" rel="stylesheet" type="text/css" />
</head>
<jsp:include page="../include/header.jsp">
<jsp:param value="index" name="header_type"/>
</jsp:include>
<body>
	<hgroup>
		<section style="background:${luckyDraw.backgroundColor} url('${luckyDraw.bannerImage}') no-repeat top center/auto auto;">
			<!-- 页面-->
			<section class="center page" style="background: url('${luckyDraw.bannerImageWeb}') no-repeat top center /100% auto;">
				<!-- 此模块为主要内容，包括 抽奖模块，和规则模块，模块固定宽度640px-->
				<hgroup class="center details">
					<c:if test="${luckyDraw.modality == 'Chests' }">
						<!-- 宝箱抽奖模块-->
						<section class="center box-prize" data-script="box-prize">
							<img src="../../images/shop/images_luckyBox/open_03.png" alt="开启神秘宝箱">
							<span class="hint">任选一个宝箱，打开有惊喜</span>
							<ul id="boxList">
								<li value="0">
									<img  class="animation" src="../../images/shop/images_luckyBox/box_close_03.png" />
									<input class="open" type="button" value="开宝箱"/>
								</li>
								<li value="1">
									<img class="animation" src="../../images/shop/images_luckyBox/box_close_03.png" />
									<input class="open" type="button" value="开宝箱"/>
								</li>
								<li value="2">
									<img class="animation" src="../../images/shop/images_luckyBox/box_close_03.png" />
									<input class="open" type="button" value="开宝箱"/>
								</li>
							</ul>
							<!--中奖提示-->
							<section class="message _center">
							
							</section>
						</section>
					</c:if>
					<div class="range">
						<div class="title">
							<span>兑换范围</span>
						</div>
						<div class="text">
							<span>兑换范围：全国</span>
						</div>
					</div>
					<!-- 活动奖品 -->
					<div class="block prize" style="margin-top:10px;">
						<div class="title">
							<span>活动奖品</span>
						</div>
						<a href="javascript:void(0)" class="aleft agrayleft" >左一个</a> 
		                <a href="javascript:void(0)" class="aright" >右一个</a> 
		                <div class="show_list">
		                	<ul class="clr" style="left: 3px;width:${fn:length(prizeDtos)*220}px;">
		                       <c:forEach items="${prizeDtos }" var="obj" varStatus="o">
			                       <li>
			                           <a href="javascript:;"><div class="img"><img src="${obj.imageUrl }"/></div></a>
										<div class="jcontent">
											<%-- <div class="jx" style="overflow:hidden"><span style="font-weight:16px;">${obj.prizeName }</span></div> --%>
											<div class="name">${obj.pName }</div>
										</div>
			                       </li>
		                       </c:forEach>
		                    </ul>
		                </div> 
					</div>
					<!-- 活动规则-->
					<section class="rules">
						<div class="title">
							<span>活动说明</span>
						</div>
						${luckyDraw.rule }
					</section>
					
					<!-- 动态直播-->
					<section class="dynamic">
						<div class="container">
							<div>
								<p>您的邮币:</p>
								<p>
									<span style="color:red;" id="integral">0</span>个
								</p>
							</div>
							<div>
								<p>
									<font>兑</font>
									<span class="gn"><a href="http://club.mail.wo.cn/shop/shop.shtml?act=list&goodsTypeStr=UnicomProducts&type=liuliang" target="_blank">流量包</a></span>
								</p>
								<p>
									<font>兑</font>
									<span class="yl"><a href="http://club.mail.wo.cn/shop/shop.shtml?act=list&goodsTypeStr=UnicomProducts&type=huafei" target="_blank">充值卡</a></span>
								</p>
							</div>
						</div>
						<!--动态列表-->
						<h1>动态直播</h1>
						<article id="list">
							<ul>
								<c:forEach items="${luckyLogList.result }" var="obj" >
									<li>
										<span class="phone">${obj.userName}</span>
										<span class="des">
											参与${obj.luckyTitle}－获得<mark>${obj.prizeName}</mark>
										</span>
									</li>
								</c:forEach>
							</ul>
						</article>
					</section>
				</hgroup>
			<div class="block" style="margin-top:10px;height:auto;border:none;position:absolute;bottom:0px;right:140px;">
					<div class="jiathis_style_24x24">
						<span style="float: left;line-height: 30px;">分享到：</span>
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<!-- <a class="jiathis_button_weixin"></a> -->
						<a class="jiathis_button_renren"></a>
						<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
					</div>
					<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
			</div>
			</section>
			<c:if test="${luckyDraw.luckyDrawStatus=='Over' }">
				<img class="actionover" style="position:absolute;left:0;right:0;width:auto;top:15vw;margin:auto;" src="../../images/shop/actionover.png" />
				<div style="position:absolute;width:100%;height:100%;left:0;top:0;background:rgba(0,0,0,0.25)"></div>
			</c:if>
		</section>
	</hgroup>
	<jsp:include page="../include/fooder.jsp"></jsp:include>
</body>
<!-- 打开宝箱示例-->
<script type="text/javascript">
		$(function(){
			ChestsLucky();
			getIntegral();
			$(".phone").each(function() {
				var number = $(this).text();
				$(this).html(number.substr(0, 3) + "****" + number.substr(7, 4))
			});
			var $ul = $('.clr');
			var oldleft = 3;
			var offset = oldleft;
			var licount = $('.clr li').length;
			$('.prize .aright').click(function(){
				if(licount>3){
					if(3-offset<(licount-3)*220){
						$ul.animate({
							left:offset-220
						},500,function(){
							offset = offset-220;
						});
					}
				}
			});
			$('.prize .aleft').click(function(){
				if(-offset>oldleft){
					$ul.animate({
						left:offset+220
					},500,function(){
						offset = offset+220;
					});
				}
			});
		});
		
		var getLucky=function(e){
			var i=$(this).val();
			$.ajax({
		  		 url: "../../wap/shop/recommend.shtml?act=prize&id=${luckyDraw.id}",
		           dataType: "json",
		           cache: false,
		           async:false,
		           success: function(data) {
		        	 if (data.code == -1) {
			              box.showMessage("<p>活动未开始或者已过期!</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
					        	 this.hide();
					      });
			         }
		           	 if(data.code==-2){
		  	                location.href="../../apiCloud/oauth.shtml?act=webcheck&backUrl="+encodeURIComponent(window.location.href);
		  	         }
		           	 if (data.code == -8) {
		           		 box.showMessage("<p>号码不在活动范围内!</p><a  target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
				        	 this.hide();
				         });
		             }
			         if(data.code==-3){
			        	 box.showMessage("<p>抽奖机会用完了!</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
				        	 this.hide();
				         });
			          }
		             if(data.code==-4){
		            	 box.showMessage("<p>邮币不足!</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
				        	 this.hide();
				         });
		             }
		          	 if(data.code==-7){
		          		 box.showMessage("<p>您的邮币被冻结!</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
				        	 this.hide();
				         });
		             }
		             if (data.code==1) {
		            		prize = data.data.index;
		                    prizeType=data.data.prizeName;
		                    goodsName=data.data.goodsName;
		                    goodsImg=data.data.goodsImg;
		                    if (prize != null) {
				                  if(prize>0){
				                	  box.openBox(i);
				                	  box.cancelAnimation(i);
				                	  box.showMessage("<p>恭喜您获得:"+prizeType+"!</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
								        	 this.hide();
								      });
				                  }else{
				                	  box.showMessage("<p>很遗憾哦！没能抽到奖品</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
								        	 this.hide();
								      });
				                  }
				            } else {
				            	 box.showMessage("<p>很遗憾哦！没能抽到奖品</p><a target='_blank' href='http://club.mail.wo.cn/shop/recommend.shtml?act=task&header_type=recommend'>更多活动（点击进入活动首页）</a><input type='button' value='确定'>",function(){
						        	 this.hide();
						     	 });
				            }
		               }
		           }
		     });
		}
		
		function ChestsLucky(){
			window.dia=Dialog();
			window.box=new Box("../../images/shop/images_luckyBox/box_close_03.png","../../images/shop/images_luckyBox/box_open_03.png");
			//if(window.attachEvent){
			//	var list=document.getElementById("boxList").getElementsByTagName("li");
			//	for(var i in list){
			//		 list[i].onclick=function(e){
			//			getLucky(e);
			//		}; 
			//	}
			//}else{
			//	$(".box-prize ul li").bind("click",getLucky);
			//}
			$(".box-prize ul li").bind("click",getLucky);
		};
		function getIntegral(){
			if(${userName!=null}){
				$.ajax({
					url : "../../wap/mine/me.shtml?act=getIntegral",
					type : "get",
					dataType : "json",
					async : false,
					success : function(data) {
						$("#integral").html(data.integral);
					}
				});
			}
		}
</script>
</html>