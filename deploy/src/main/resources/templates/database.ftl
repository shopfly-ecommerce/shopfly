<#include 'common/header.ftl' />

<div class="content-wrapper" style="min-height: 946px;">

    <section class="content-header">
        <h1>
            新建安装
            <small>new install</small>
        </h1>
    </section>


    <section class="content">

        <div class="row">
        <#list dbList as database>
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">${database.serviceTypeName!''}</h3>
                    </div>
                    <!-- /.box-header -->

                    <div class="box-body">
                        <form id="form${database.dbId}">
                            <div class="form-group">
                                <label>数据库类型</label>
                                <input type="hidden" name="db_type"  value="${database.dbType!''}">
                                <input type="text" disabled="disabled" class="form-control" placeholder="Enter ..."
                                       value="${database.dbType!''}" >
                            </div>

                            <div class="form-group">
                                <label>数据库ip</label>
                                <input type="text" class="form-control" placeholder="Enter ..." name="db_ip"
                                       value="${database.dbIp!''}">
                            </div>

                            <div class="form-group">
                                <label>端口号</label>
                                <input type="text" class="form-control" placeholder="Enter ..."
                                       value="${database.dbPort!''}" name="db_port">
                            </div>

                            <div class="form-group">
                                <label>数据库</label>
                                <input type="text" class="form-control" placeholder="Enter ..."
                                       value="${database.dbName!''}" name="db_name">
                            </div>


                            <div class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control" placeholder="Enter ..."
                                       value="${database.dbUsername!''}" name="db_username">
                            </div>
                            <div class="form-group">
                                <label>密码</label>
                                <input type="text" class="form-control" placeholder="Enter ..."
                                       value="${database.dbPassword!''}" name="db_password">
                            </div>
                        </form>
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer">
                        <button type="button" class="btn btn-default test" dbid="${database.dbId}">测试</button>
                        <button type="button" class="btn btn-info pull-right save" dbid="${database.dbId}">保存</button>
                    </div>
                    <!-- /.box-footer -->

                </div>
            </div>
        </#list>

        </div>
    </section>

    <!-- /.context -->
</div>


<script>
    $(function () {
        $("button.save").click(function () {
            var dbid = $(this).attr("dbid");
            save(dbid);
        });

        $("button.test").click(function () {
            var dbid = $(this).attr("dbid");
            test(dbid);
        });

    });

    function save(dbid) {
        $("#form"+dbid).ajaxSubmit({
            url: "/data/databases/" + dbid,
            type: "PUT",
            success: function () {
                alert("ok")
            },
            error: function () {
                alert("error")
            }
        })
    }

    function test(dbid) {
        $("#form"+dbid).ajaxSubmit({
            url: "/data/databases/connection",
            type: "GET",
            success: function (result) {
                alert(result)
            },
            error: function () {
                alert("error")
            }
        })
    }

</script>

<#include 'common/footer.ftl' />
