<input type="button" value="QQ PC登录" id="qq-pc-login-btn" />
<input type="button" value="QQ WAP登录" id="qq-wap-login-btn" />
<input type="button" value="微信PC登录" id="weixin-pc-login-btn" />

<input type="button" value="微博 PC登录" id="weibo-pc-login-btn" />
<input type="button" value="微博 WAP登录" id="weibo-wap-login-btn" />

<input type="button" value="支付宝 PC登录" id="alipay-pc-login-btn" />
<input type="button" value="支付宝 WAP登录" id="alipay-wap-login-btn" />


<div id="login-box">
</div>

<script>

    $(function () {

        $("#weixin-pc-login-btn").click(function () {
           window.open("../passport/connect/pc/WECHAT")
        });

        $("#qq-pc-login-btn").click(function () {
            window.open("../passport/connect/pc/QQ")
        });

        $("#qq-wap-login-btn").click(function () {
            window.open("../passport/connect/wap/QQ")
        });

        $("#weibo-wap-login-btn").click(function () {
            window.open("../passport/connect/wap/WEIBO")
        });

        $("#weibo-pc-login-btn").click(function () {
            window.open("../passport/connect/pc/WEIBO")
        });

        $("#alipay-pc-login-btn").click(function () {
            window.open("../passport/connect/pc/ALIPAY")
        });

        $("#alipay-wap-login-btn").click(function () {
            window.open("../passport/connect/wap/ALIPAY")
        });

    })



</script>