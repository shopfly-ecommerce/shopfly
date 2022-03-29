输入邮件：<input type="text" id="email" name="email">
<input type="button" value="发送邮件" id="email-test-btn" />
<script>

    $(function () {
        $("#email-test-btn").click(function () {
            $.ajax({
                url:"email/test?email="+$("#email").val(),
                type:"get",
                success:function(result){
                    if("ok"==result){
                        alert("邮件已经发送")
                    }
                }
            })
        });

    })
</script>