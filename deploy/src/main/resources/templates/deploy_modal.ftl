<div class="modal fade" id="deployModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增部署</h4>
            </div>
            <div class="modal-body">

                <div class="box-body">

                    <form  id="deploy-form">
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" name="deploy_name" class="form-control" placeholder="Enter ...">
                        </div>

                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" name="remark"  class="form-control" placeholder="Enter ...">
                        </div>



                        <div class="form-group">
                            <label>安装类型</label>
                            <select name="deploy_type" class="form-control">
                                <option value="带示例数据">standard</option>
                                <option value="无示例数据">basic</option>
                            </select>

                        </div>

                        <div class="form-group">
                            <label>管理员账号</label>
                            <input type="text" name="admin_name"  class="form-control" placeholder="Enter ...">
                        </div>


                        <div class="form-group">
                            <label>管理员密码</label>
                            <input type="text" name="admin_pwd"  class="form-control" placeholder="Enter ...">
                        </div>
                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save-btn">确定</button>
            </div>
        </div>
    </div>
</div>
