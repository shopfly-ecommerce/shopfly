开始时间：<input type="text" id="startTime" name="startTime" value="2020-09-24 00:00:00">时间格式:2020-09-24 00:00:00<br>
结束时间：<input type="text" id="endTime" name="endTime" value="2020-09-24 23:59:59">时间格式:2020-09-24 23:59:59<br>
<div id="seckill-times">
    <div class="times">
        抢购时刻：
        <select class="select-times" name="times"></select>
        GOODSID： <input type="text" class="skuIds" name="skuIds">goodsID1,goodsID2,goodsID3,goodsID4<br>
    </div>
    <div class="times">
        抢购时刻：
        <select class="select-times" name="times"></select>
        GOODSID： <input type="text" class="skuIds" name="skuIds">goodsID1,goodsID2,goodsID3,goodsID4<br>
    </div>
    <div class="times">
        抢购时刻：
        <select class="select-times" name="times"></select>
        GOODSID： <input type="text" class="skuIds" name="skuIds">goodsID1,goodsID2,goodsID3,goodsID4<br>
    </div>
</div>
<input type="button" value="添加时刻和商品" id="seckill-times-btn" />
<input type="button" value="删除时刻和商品" id="seckill-times-del-btn" />
<input type="button" value="添加抢购活动" id="seckill-test-btn" />
<script>

    $(function () {
        var options = "";
        for (let i = 0; i < 24; i++) {
            options += "<option value=\"" + i + "\">" + i + "</option>";
        }
        $(".select-times").append(options);


        $("#seckill-times-btn").click(function () {
            var len = $(".times").length
            if (len > 10) {
                alert("最多添加10个");
                return false;
            }
            $("#seckill-times").append("    <div class=\"times\">\n" +
                "        抢购时刻：\n" +
                "        <select class=\"select-times\" name=\"times\"></select>\n" +
                "        GOODSID： <input type=\"text\" class=\"skuIds\" name=\"skuIds\">goodsID1,goodsID2,goodsID3,goodsID4<br>\n" +
                "    </div>");
            $(".select-times").append(options);
        });
        $("#seckill-times-del-btn").click(function () {
            $(".times").last().remove();
        });

        $("#seckill-test-btn").click(function () {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            var times = "";
            var skuIds  = "";
            var mulTimes = false;
            var mulSkuids = false;
            $(".times").each(function () {
                var selected = $(this).find("option:selected").val() + ",";
                if (times.indexOf(selected) < 0) {
                    times += selected
                } else {
                    mulTimes = true;
                }
            })

            $(".skuIds").each(function () {
                var select = $(this).val() + ";";
                if (skuIds.indexOf(select) < 0) {
                    skuIds += select;
                } else {
                    mulSkuids = true
                }

            })

            if (mulSkuids || mulTimes) {
                alert("时刻或者商品ID存在重复，请重新填写");
                return false;
            }
            console.log('seckill:::times',times);
            console.log('seckill:::skuIds',skuIds);
            $.ajax({
                url:"seckill",
                type:"get",
                dataType : 'json',
                data:{start_time:startTime,end_time:endTime,times:times,sku_ids:skuIds},
                success:function(result){
                    if("RELEASE"==result.seckill_status){
                        alert("限时抢购活动添加成功")
                        location.reload();
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    /*错误信息处理*/
                    console.log("进入error---");
                    console.log("状态码："+xhr.status);
                    console.log("状态:"+xhr.readyState);//当前状态,0-未初始化，1-正在载入，2-已经载入，3-数据进行交互，4-完成。
                    console.log("错误信息:"+xhr.statusText );
                    console.log("返回响应信息："+xhr.responseText );//这里是详细的信息
                    console.log("请求状态："+textStatus);
                    console.log(errorThrown);
                    console.log("请求失败");
                    alert("返回响应信息："+xhr.responseText);
                }
            })
        });

    })



</script>