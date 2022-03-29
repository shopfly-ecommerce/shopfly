<input type="button" value="生成索引"  class="index_create" />
<script>

    $(function () {
        $(".index_create").click(function () {
            $.ajax({
                url:"index-create/test",
                type:"get",
                success:function(result){
                    if("ok"==result){
                        alert("生成消息已经发送")
                    }
                }
            })
        });
    })
</script>