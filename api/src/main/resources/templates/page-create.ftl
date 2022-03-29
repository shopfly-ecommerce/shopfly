<input type="button" value="首页"  page_type="INDEX" class="page_create" />
<input type="button" value="商品页"  page_type="GOODS" class="page_create"/>
<input type="button" value="帮助页"   page_type="HELP" class="page_create"/>
<script>

    $(function () {
        $(".page_create").click(function () {
            var page_type = $(this).attr("page_type");
            $.ajax({
                url:"page-create/test?page_type="+page_type,
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