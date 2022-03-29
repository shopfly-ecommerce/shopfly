<#include 'common/header.ftl' />

<div class="content-wrapper" style="min-height: 946px;">

    <section class="content-header">
        <h1>
            新建部署
            <small>new install</small>
        </h1>
    </section>


    <section class="content">

        <div class="row">

            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">基本信息 </h3>
                    </div>
                    <!-- /.box-header -->

                    <div class="box-body">

                        <form  id="base-form">
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" name="dep_name" class="form-control" placeholder="Enter ...">
                        </div>

                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" class="form-control" placeholder="Enter ...">
                        </div>

                        <div class="form-group">
                            <label>安装类型</label>
                            <input type="text" class="form-control" placeholder="Enter ...">
                        </div>

                        <div class="form-group">
                            <label>管理员账号</label>
                            <input type="text" class="form-control" placeholder="Enter ...">
                        </div>


                        <div class="form-group">
                            <label>管理员密码</label>
                            <input type="text" class="form-control" placeholder="Enter ...">
                        </div>
                        </form>
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer">
                        <button type="submit" id="save_btn" class="btn btn-info pull-right">保存</button>
                    </div>
                    <!-- /.box-footer -->

                </div>
            </div>




        </div>
    </section>

    <!-- /.context -->
</div>


<script>
$(function () {
    $("#save_btn").click(function () {
        $("#base-form").ajaxSubmit({
            url:"/data/deploy/base",
            type:"post",
            success:function () {
                alert("ok")
            },
            error:function () {
                alert("error")
            }
        })
    });
})
</script>

<#include 'common/footer.ftl' />
