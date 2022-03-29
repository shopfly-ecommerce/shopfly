<!DOCTYPE html>

<html lang="en">
<header>
 <script type="text/javascript" src="${jquery_path}"></script>

 <style>

  div{
   border: grey 1px solid;
  }


  #main{
   width: 100%;
   display: block;
  }

  #main .left-box{
   display: block;
   width: 40%;
   height: 1200px;
   float: left;
  }

  #main .right-box{
   display: block;
   width: 58%;
   height: 1200px;
   float: left;
   overflow-y:auto;

  }
 </style>

</header>
<body>

<div id="main">

 <div class="left-box">

  <h2>支付</h2>
  <hr>
  <#include 'payment.ftl' />

<#--  <h2>退款</h2>-->
<#--  <hr>-->
<#--  <#include 'refund.ftl' />-->



<#--  <h2>第三方登录</h2>-->
<#--  <hr>-->
<#--  <#include 'passport-connect.ftl' />-->


<#--  <h2>短信</h2>-->
<#--  <hr>-->
<#--  <#include 'sms.ftl' />-->


<#--  <h2>邮件</h2>-->
<#--  <hr>-->
<#--  <#include 'email.ftl' />-->

<#--  <h2>静态页</h2>-->
<#--  <hr>-->
<#--  <#include 'page-create.ftl' />-->


<#--  <h2>索引</h2>-->
<#--  <hr>-->
<#--  <#include 'index-create.ftl' />-->


  <h2>限时抢购</h2>
  <hr>
  <#include 'seckill.ftl' />

 </div>

 <div class="right-box">
 </div>

</div>
<div>
 <input  type="button" value="清空日志" id="clear-log-btn">
</div>


<script>
 function checkStatus(){
  var rightBox = $(".right-box");

  $.ajax({
   url:"log",
   type:"get",
   success:function(result){

    rightBox.html(result).scroll();
    rightBox.scrollTop( rightBox[0].scrollHeight);

   }
  })
 }
 setInterval(checkStatus,1000);
 $(function () {
  $("#clear-log-btn").click(function () {
   $.ajax({
    url:"log",
    type:"DELETE",
    success:function(result){


    }
   })
  });
 })
</script>

</body>

</html>
 