<input type="button" value="查看可退款列表" id="refund-list-btn" />
<div id="refund-bill-list">

</div>

<script>


    function refund(btn) {
        $.ajax({
            url:"refund/test?return_trade_no="+btn.attr("returnTradeNo")+"&refund_price="+btn.attr("tradePrice"),
            type:"get",
            success:function(result){
                alert(result)
            }
        })
    }

    $(function () {

        $("#refund-list-btn").click(function () {


            $.ajax({
                url:"refund/list",
                type:"get",
                success:function(result){
                    $("#refund-bill-list").html(result);
                    $("#refund-bill-list li a").click(function(){
                        refund($(this));
                    });

                }
            })



        });

    })



</script>