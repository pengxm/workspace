$(function(){
    function getvl(name) {
        var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
        if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    };
    console.log(getvl('flag'));//调用传入需要获取参数
    var flagNum = getvl('flag');
    var timer = null;
    var flag = true;



    if(flagNum == 1) {
        $('.flagLeft').attr('src', 'images/rs_07.png');
        $('.left h2').html('瑞士');
        $('.flagRight').attr('src', 'images/bl_07.png');
        $('.right h2').html('波兰');
    }

    if(flagNum == 2) {
        $('.flagLeft').attr('src', 'images/wes_12.png');
        $('.left h2').html('威尔士');
        $('.flagRight').attr('src', 'images/beel_12.png');
        $('.right h2').html('北爱尔兰');
    }

    if(flagNum == 3) {
        $('.flagLeft').attr('src', 'images/kldy_12.png');
        $('.left h2').html('克罗地亚');
        $('.flagRight').attr('src', 'images/pty_12.png');
        $('.right h2').html('葡萄牙');
    }

    if(flagNum == 4) {
        $('.flagLeft').attr('src', 'images/fg_12.png');
        $('.left h2').html('法国');
        $('.flagRight').attr('src', 'images/eel_12.png');
        $('.right h2').html('爱尔兰');
    }

    if(flagNum == 5) {
        $('.flagLeft').attr('src', 'images/dg_16.png');
        $('.left h2').html('德国');
        $('.flagRight').attr('src', 'images/slfk_16.png');
        $('.right h2').html('斯洛伐克');
    }


    var prizeName = '10元3G'; // 请求奖品

    $('.btn').click(function(){
        if(flag == true) {
            if(prizeName == '小米手环') {           
                $('.footBall').addClass('run1');
                $('.alert').removeClass('act');
                $('.alert2').addClass('act');
                $('.button2').click(function(){
                    $('.alert2 h3, h4').css('display','none');
                    $('.alert2 .user').css('display','block');
                    $('.alert2 .button2').addClass('button');
                    $('.button').click(function(){
                        $('.popup').slideUp(400);
                    });
                });
            } else if(prizeName == '5元1G') {
                $('.footBall').addClass('run2');
                $('.alert').removeClass('act');
                $('.alert1').addClass('act');
            } else if(prizeName == 'Skullcandy耳机') {
                $('.footBall').addClass('run3');
                $('.alert').removeClass('act');
                $('.alert2').addClass('act');
                $('.button2').click(function(){
                    $('.alert2 h3, h4').css('display','none');
                    $('.alert2 .user').css('display','block');
                    $('.alert2 .button2').addClass('button');
                    $('.button').click(function(){
                        $('.popup').slideUp(400);
                    });
                });
            } else if(prizeName == '10元3G') {
                $('.footBall').addClass('run4');
                $('.alert').removeClass('act');
                $('.alert1').addClass('act');
            }   
            $('h1').html('您还有0次抽奖机会');
            $('b').html(prizeName);   
            $('.ballBg').css('display', 'none'); 
            timer = setTimeout(function(){
                $('.popup').slideDown(300);
            }, 1200);
        } else {
            alert('您没有抽奖机会了');
            return false;
        }
        flag = false;
    });
    $('.button').click(function(){
        $('.popup').slideUp(300);
    });




})
