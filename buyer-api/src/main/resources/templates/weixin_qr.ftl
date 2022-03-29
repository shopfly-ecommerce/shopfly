<!DOCTYPE html>

<html lang="en">
<header>
<script type="text/javascript" src="${jquery_path}"></script>
</header>
<body>
 <img src='${default_gateway_url}/order/pay/weixin/qr/${pr}' style="width: 200px"/>
 
 
 <script>
 function checkStatus(){
	 $.ajax({
		 url:"${default_gateway_url}/order/pay/weixin/status/${weixinTradeSn}",
		 type:"get",
		 success:function(result){
			 if("SUCCESS"==result){
				 window.parent.location.href="${paySuccessUrl}";
			 }
		 }
	 })
 }
 setInterval(checkStatus,2000);
 </script>
 
</body>

</html>
 