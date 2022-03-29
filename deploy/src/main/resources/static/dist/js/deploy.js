var table;

$(function () {
    saveDeploy();
    initGrid();
    bindRedisEvent();
    bindMqEvent();
    bindEsEvent();
});



function saveDeploy() {

    /**
     * 发出添加请求
     */
    $("#save-btn").click(function () {
        // if( $("#deploy-form").valid() ){
        $("#deploy-form").ajaxSubmit({
            url:"../../data/deploys",
            type:"POST",
            success:function () {
                alert("保存成功")
                $('#deployModal').modal("hide");
                table.ajax.url("../../data/deploys").load();
            }
        }) ;
        // }

    });
}

/**
 * ----------------------------------------------------
 * 打开对话框
 * ----------------------------------------------------
 */

/**
 * 打开新增部署对话框
 */
function addDeploy() {
    $('#deployModal').modal();
}

/**
 * 打开redis配置对话框
 * @param deployid
 */
function  openRedisModel(deployid) {
    $('#redisModal').modal();
    initReidsForm(deployid);

}

/**
 * 打开rabbimq配置对话框
 * @param deployid
 */
function  openMqModel(deployid) {
    $('#mqModal').modal();
    initRabbitmqForm(deployid);

}


/**
 * 打开rabbimq配置对话框
 * @param deployid
 */
function  openEsModel(deployid) {
    $('#esModal').modal();
    initEsForm(deployid);

}

/**
 * ----------------------------------------------------
 * 绑定事件
 * ----------------------------------------------------
 */

/**
 * 绑定redis对话框中的相应事件
 */
function bindRedisEvent() {

    $("[name=redis_type]").change(function () {
        changeRedisForm();
    });

    $("[name=config_type]").change(function () {
        // var ss = $(this).children('option:selected').val();
        changeRedisForm();
    });

    //测试reids
    $(".test-redis").click(function () {
        $("#redis-form").ajaxSubmit({
            url: "/data/redis/connection",
            type: "GET",
            success: function (result) {
                if (result) {
                    alert("连接成功");
                }else {
                    alert("连接失败");
                }            },
            error: function () {
                alert("error")
            }
        })
    });


    $("#redis-save-btn").click(function () {

        var redisid  = $("[name=redis_id]").val();

        $("#redis-form").ajaxSubmit({
            url: "/data/redis/"+ redisid ,
            type: "PUT",
            success: function () {
                alert("保存成功")
            },
            error: function () {
                alert("error")
            }
        })
    });


}


/**
 * 绑定rabbitmq对话框中的相应事件
 */
function bindMqEvent() {
    $(".test-mq").click(function () {
        $("#mq-form").ajaxSubmit({
            url: "/data/rabbitmq/connection",
            type: "GET",
            success: function (result) {
                if (result) {
                    alert("连接成功");
                }else {
                    alert("连接失败");
                }            },
            error: function () {
                alert("error")
            }
        })
    });

    $("#mq-save-btn").click(function () {

        var rabbitmq_id  = $("#mq-form [name=id]").val();

        $("#mq-form").ajaxSubmit({
            url: "/data/rabbitmq/"+ rabbitmq_id ,
            type: "PUT",
            success: function () {
                alert("保存成功")
            },
            error: function () {
                alert("error");
            }
        })
    });

}


/**
 * 绑定 elasticsearch 对话框中的相应事件
 */
function bindEsEvent() {

    $(".test-es").click(function () {
        $("#es-form").ajaxSubmit({
            url: "/data/elasticsearchs/connection",
            type: "GET",
            success: function (result) {
                if (result) {
                    alert("连接成功");
                }else {
                    alert("连接失败");
                }

            },
            error: function () {
                alert("error")
            }
        })
    });

    $("#es-save-btn").click(function () {

        var es_id  = $("#es-form [name=id]").val();
        var method ="POST";
        if (es_id) {
            method = "PUT";
        }

        $("#es-form").ajaxSubmit({
            url: "/data/elasticsearchs/"+ es_id ,
            type: method,
            success: function () {
                alert("保存成功")
            },
            error: function () {
                alert("error");
            }
        })
    });

}



/**
 * redis表单控件切换公用方法
 */
function changeRedisForm() {
    var redis_type = $("[name=redis_type]").val();
    var config_type = $("[name=config_type]").val();
    $('[for]').hide();
    if( config_type == 'manual' ){
        $("[for="+ redis_type +"]").removeClass("hide").show();
    }else {
        $("[for="+ config_type +"]").removeClass("hide").show();
    }

}

/**
 * 填充redis form
 * @param deployid
 */
function initReidsForm(deployid) {
    $.ajax({
        url: "/data/deploys/"+deployid+"/redis",
        type: "GET",
        success: function (redisData) {
           fillForm("redis-form",redisData);
            changeRedisForm();
        },
        error: function () {
            alert("error")
        }
    })
}

/**
 * 填充rabbitmq form
 * @param deployid
 */
function initRabbitmqForm(deployid) {
    $.ajax({
        url: "/data/deploys/"+deployid+"/rabbitmq",
        type: "GET",
        success: function (redisData) {
            fillForm("mq-form",redisData);
        },
        error: function () {
            alert("error")
        }
    })
}


/**
 * 填充 es form
 * @param deployid
 */
function initEsForm(deployid) {
    $.ajax({
        url: "/data/deploys/"+deployid+"/elasticsearch",
        type: "GET",
        success: function (esData) {
            fillForm("es-form",esData);
        },
        error: function () {
            alert("error")
        }
    })
}



/**
 * 公用的填充form数据方法
 * @param formid
 * @param data
 */
function fillForm(formid,data) {

    if(!data){return;}

    for( value in data){
        $("#"+formid +" [name='"+value +"']").val(data[value]);
    }

}

function loadding() {
    $(".deploy_btn_box button").addClass("disabled");

    var overflow ="";
    overflow +='<div class="overlay">';
    overflow +='<i class="fa fa-sync fa-spin"></i>'
    overflow +='</div>'
    var oEl  = $(overflow);
    $(".deploy_btn_box").append(oEl)

    return oEl;
}

/**
 * 执行一次部署
 * @param deployid
 */
function executeDeploy(type,deployid) {

    if ( !confirm("本操作会覆盖所有的数据，确认要执行部署吗？") ){
        return false;
    }

    var oEl  = loadding();

    $.ajax({
        url: "/data/deploys/"+deployid+"/"+type+"/executor",
        type: "PUT",
        success: function (data) {
            if (data == "ok") {

                $(".overlay").remove();

                $(".deploy_btn_box button").removeClass("disabled");
                alert("部署成功")
            }else {
                $(".overlay").remove();
                $(".deploy_btn_box button").removeClass("disabled");

                alert("部署失败")
            }

        },
        error: function () {
            $(".overlay").remove();
            $(".deploy_btn_box button").removeClass("disabled");

            alert("error")
        }
    })
}


/**
 * 初始化grid列表
 */
function initGrid() {

    table= $("#deploy_grid").DataTable({
        "paging": false,
        "searching": false,
        ajax: {
            url: '../../data/deploys',
        },

        columns: [ //定义列

            {
                data: "deploy_name"
            },
            {
                data: "remark"
            },
            {
                data: "deploy_type"
            }
            ,
            {
                data: "create_time_text"
            }  ,
            {
                data: null,
                "render": function (data, type, row) {
                    var btnHtml = "";
                    btnHtml+='<div class="btn-group deploy_btn_box" >'
                    btnHtml+='    <button type="button" class="btn btn-info">配置</button>'
                    btnHtml+='    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">'
                    btnHtml+='        <span class="caret"></span>'
                    btnHtml+='        <span class="sr-only">Toggle Dropdown</span>'
                    btnHtml+='    </button>'
                    btnHtml+='    <ul class="dropdown-menu" role="menu">'
                    btnHtml+='        <li><a href="javascript:location.href=\''+data["deploy_id"]+'/databases\'" >数据库</a></li>'
                    // btnHtml+='        <li><a href="javascript:openRedisModel('+data["deploy_id"]+')" >Redis</a></li>'
                    // btnHtml+='        <li><a href="javascript:openMqModel('+data["deploy_id"]+')" >RabbitMq</a></li>'
                    btnHtml+='        <li><a href="javascript:openEsModel('+data["deploy_id"]+')" >Elasticsearch</a></li>'

                    btnHtml+='    </ul>';
                    btnHtml+='</div>';

                    btnHtml+='<div class="btn-group deploy_btn_box">'
                    btnHtml+='    <button type="button" class="btn btn-info">部署</button>'
                    btnHtml+='    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">'
                    btnHtml+='        <span class="caret"></span>'
                    btnHtml+='        <span class="sr-only">Toggle Dropdown</span>'
                    btnHtml+='    </button>'
                    btnHtml+='    <ul class="dropdown-menu" role="menu">'
                    btnHtml+='        <li><a href="javascript:executeDeploy(\'database\','+data["deploy_id"]+')">部署数据库</a></li>'
                    btnHtml+='        <li><a href="javascript:executeDeploy(\'region\','+data["deploy_id"]+')">部署地区数据</a></li>'
                    // btnHtml+='        <li><a href="javascript:executeDeploy(\'redis\','+data["deploy_id"]+')" >部署Redis</a></li>'
                    // btnHtml+='        <li><a href="javascript:executeDeploy(\'rabbitmq\','+data["deploy_id"]+')" >部署rabbitMq</a></li>'
                    btnHtml+='        <li><a href="javascript:executeDeploy(\'elasticsearch\','+data["deploy_id"]+')" >部署elasticsearch</a></li>'
                    btnHtml+='        <li class="divider"></li>'
                    btnHtml+='        <li><a href="javascript:executeDeploy(\'all\','+data["deploy_id"]+')">部署所有</a></li>'
                    btnHtml+='    </ul>';
                    btnHtml+='</div>';
                    return btnHtml;
                }
            }


        ]

    });
}
