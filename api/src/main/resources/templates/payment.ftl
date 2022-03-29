<input type="button" value="PC跳转支付测试" id="alipay-pc-nomarl-test-btn" /> <input type="button" value="PC二维码支付测试" id="alipay-pc-qr-test-btn" />
<input type="button" value="wap支付测试" id="alipay-wap-test-btn" /> <input type="button" value="weixin支付测试" id="weixin-pay-btn" />

<div id="alipay-pc-box">


</div>

<script>

    $(function () {
        $("#alipay-pc-nomarl-test-btn").click(function () {
            $("#alipay-pc-box").load("payment/alipay/pc/normal")
        });

        $("#alipay-pc-qr-test-btn").click(function () {
            $("#alipay-pc-box").load("payment/alipay/pc/qr/iframe")
        });

        $("#alipay-wap-test-btn").click(function () {
            $("#alipay-pc-box").load("payment/alipay/wap")
        });


        $("#weixin-pay-btn").click(function () {
            $("#alipay-pc-box").load("payment/weixin/pc/qr/iframe")
        });



    })



</script>