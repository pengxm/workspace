$(function(){

    // 单选按钮点击事件
    $('.order-center input').bind('click', function(e){
        e.stopPropagation();
        $('.order-center .checked-icon').removeClass('checked-icon-true');
        $(this).siblings('label').children('.checked-icon').addClass('checked-icon-true');
        console.log($(this).is(":checked"));
    });
    $('.order-center label').bind('click', function(e){
        e.stopPropagation();
        $('.order-center .checked-icon').removeClass('checked-icon-true');
        $(this).children('.checked-icon').addClass('checked-icon-true');
    });
    
    
    /*
    *手机验证
    */

    (function (){
        function Validate(el, msgEl, reg, rightMsg, errorMsg){
            this.el = el;
            this.msgEl = msgEl;
            this.reg = reg;
            this.rightMsg = rightMsg;
            this.errorMsg = errorMsg;
        }
        Validate.prototype.validate = function(){
            var _this = this;
            function throttle(fn,context,delay,text,mustApplyTime){
                clearTimeout(fn.timer);
                fn._cur=Date.now();  //记录当前时间

                if(!fn._start){      //若该函数是第一次调用，则直接设置_start,即开始时间，为_cur，即此刻的时间
                    fn._start=fn._cur;
                }
                if(fn._cur-fn._start>mustApplyTime){
                    //当前时间与上一次函数被执行的时间作差，与mustApplyTime比较，若大于，则必须执行一次函数，若小于，则重新设置计时器
                    fn.call(context,text);
                    fn._start=fn._cur;
                }else{
                    fn.timer=setTimeout(function(){
                        fn.call(context,text);
                    }, delay);
                }
            }
            function queryData(text){
                var $val = text,
                    reg = _this.reg;
                $(_this.msgEl).css('display', 'block');
                if (reg.test($val)) {
                    $(_this.msgEl).html(_this.rightMsg);
                    console.log('right');
                }else{
                    $(_this.msgEl).html(_this.errorMsg);
                    console.log('error');
                }
            }

            var input = $(_this.el).get(0);
            input.addEventListener("keyup", function(event){
                var event = event || window.event;
                event.stopPropagation();
                throttle(queryData, null, 500, this.value,1000);
            });
        };
        window.Validate = Validate;
    })();
    var telValidate = new Validate('.tel', '.tel-txt', /^0?1[3|4|5|8][0-9]\d{8}$/, '输入正确', '输入格式错误');
    telValidate.validate();

    //验证码验证
    var validateInput = $('.validate-input').get(0);
    validateInput.addEventListener("keyup", function(event){
        var event = event || window.event;
        event.stopPropagation();
        $('.validate-txt').css('display','block');
        if($('.validate-input').val() === $('.validate-val').text() && $('.validate-input').val() !== ''){
            $('.validate-txt').html('输入正确');
        }else {
            $('.validate-txt').html('验证码错误');
        }
    });

    // 短信验证码倒计时
    $('.msg-btn').click(function(){
        var btn = $('.msg-btn').get(0);
        var a = new MenuControl(1000);
        a.lockItem(btn, "60", "秒后重新获取");
    });
    
    // 密码验证
    var pwdVal = new Validate('.pwd-value', '.pwd-txt',/^(\w){5,17}$/, '输入正确', '格式错误');
    pwdVal.validate();


});