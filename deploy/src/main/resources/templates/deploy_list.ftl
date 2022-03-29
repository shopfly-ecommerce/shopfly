<#include 'common/header.ftl' />
<script src="/dist/js/deploy.js?version=4"></script>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            部署列表
        </h1>

    </section>

    <!-- Main context -->
    <section class="content">

        <div class="box">
            <div class="box-header">
                <button type="button" style="width: 150px" class="btn btn-block btn-success" onclick="addDeploy()">新建部署</button>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="deploy_grid" class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>备注</th>
                        <th>类型</th>
                        <th>添加时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->
        </div>

    </section>
    <!-- /.context -->
</div>



<!-- 新部署Modal -->
<#include 'deploy_modal.ftl' />
<!-- Modal -->


<!-- redis 管理 Modal -->
<#include 'redis_modal.ftl' />
<!-- Modal -->

<!-- rabbitmq 管理 Modal -->
<#include 'rabbitmq_modal.ftl' />
<!-- Modal -->

<!-- elasticsearch 管理 Modal -->
<#include 'es_modal.ftl' />
<!-- Modal -->


<#include 'common/footer.ftl' />
