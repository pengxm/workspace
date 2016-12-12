$(function(){
  $('.btn').click(function(){
    // 请求数据， 查看之前是否有签到， 如果有则继续累加， 当签到积累到七天后， 再次签到则从第一天开始
    // $.ajax({
    //   url: '',
    //   type : '',
    //   dataType : '',
    //   success : function(){
    //
    //   }
    // });

    // 假设请求数据， 用户没有签到则
    $('.sign').eq(0).css('display', 'block');
    // 假设之前用户有点击记录， 则在这之上累加

    // var len = 3;
    // for(var i = 0; i < len + 1; i++){
    //   $('.sign').eq(i).css('display', 'block');
    // }




  });
});
