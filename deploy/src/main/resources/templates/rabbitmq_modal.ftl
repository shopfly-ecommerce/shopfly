<!-- Modal -->
<div class="modal fade" id="mqModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">RabbitMq配置</h4>
            </div>
            <div class="modal-body">

                <div class="box-body">

                    <form  id="mq-form">
                        <input type="hidden" name="id">
                        <!-- rabbitmq -->
                        <div class="form-group">
                            <label>host</label>
                            <input type="text"  name="host" class="form-control" >
                        </div>

                        <div class="form-group" >
                            <label>port</label>
                            <input type="text" name="port"  class="form-control" >
                        </div>

                        <div class="form-group" >
                            <label>username</label>
                            <input type="text" name="username"  class="form-control" >
                        </div>

                        <div class="form-group" >
                            <label>password</label>
                            <input type="text" name="password"  class="form-control" >
                        </div>
                        <div class="form-group" >
                            <label>virtual_host</label>
                            <input type="text" name="virtual_host"  class="form-control" >
                        </div>

                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default test-mq">测试</button>
                <button type="button" class="btn btn-primary" id="mq-save-btn">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
