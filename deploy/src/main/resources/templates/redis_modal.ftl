<!-- Modal -->
<div class="modal fade" id="redisModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">redis配置</h4>
            </div>
            <div class="modal-body">

                <div class="box-body">

                    <form  id="redis-form">
                        <input type="hidden" name="redis_id">

                        <div class="form-group">
                            <label>类型</label>
                            <select name="redis_type" class="form-control">
                                <option value="standalone">standalone</option>
                                <option value="sentinel">sentinel</option>
                                <option value="cluster">cluster</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>配置类型</label>
                            <select name="config_type" class="form-control">
                                <option value="manual">手动</option>
                                <option value="rest">REST API</option>
                            </select>
                        </div>

                        <!-- standalone -->
                        <div class="form-group" for="standalone">
                            <label>host</label>
                            <input type="text"  name="standalone_host" class="form-control" >
                        </div>

                        <div class="form-group" for="standalone">
                            <label>port</label>
                            <input type="text" name="standalone_port"  class="form-control" >
                        </div>

                        <div class="form-group" for="standalone">
                            <label>password</label>
                            <input type="text" name="standalone_password"  class="form-control" placeholder="没有密码留空">
                        </div>

                        <!-- cluster -->
                        <div class="form-group hide" for="cluster"  >
                            <label>cluster nodes</label>
                            <input type="text"  name="cluster_nodes" class="form-control" >
                        </div>

                        <div class="form-group hide" for="cluster">
                            <label>password</label>
                            <input type="text" name="cluster_password"  class="form-control" placeholder="没有密码留空">
                        </div>


                        <!-- sentinel -->

                        <div class="form-group hide" for="sentinel">
                            <label>sentinel nodes</label>
                            <input type="text" name="sentinel_master"  class="form-control" >
                        </div>

                        <div class="form-group hide" for="sentinel">
                            <label>sentinel nodes</label>
                            <input type="text" name="sentinel_nodes"  class="form-control" placeholder="没有密码留空">
                        </div>

                        <!-- rest api -->
                        <div class="form-group hide" for="rest">
                            <label>rest url</label>
                            <input type="text"  name="rest_appid" class="form-control" >
                        </div>

                        <div class="form-group hide" for="rest">
                            <label>appid</label>
                            <input type="text" name="rest_url"  class="form-control" >
                        </div>

                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default test-redis"  >测试</button>
                <button type="button" class="btn btn-primary" id="redis-save-btn">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
