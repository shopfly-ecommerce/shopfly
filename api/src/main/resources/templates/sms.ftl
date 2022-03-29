输入手机号：<input type="text" id="mobile" name="mobile">
<input type="button" value="发送短信" id="sms-test-btn" />
<script>

    $(function () {
        $("#sms-test-btn").click(function () {
            $.ajax({
                url:"sms/test?mobile="+$("#mobile").val(),
                type:"get",
                success:function(result){
                    if("ok"==result){
                        alert("短信已经发送")
                    }
                }
            })
        });

    })



</script>