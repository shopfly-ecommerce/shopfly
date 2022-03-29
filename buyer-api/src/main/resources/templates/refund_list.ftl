<#list billList as bill>
<li>支付账单号：${bill.outTradeNo!''}---第三方交易号：${bill.returnTradeNo!''}--支付方式：${bill.paymentName!''}---支付金额:${bill.tradePrice}--
    <a href="javascript:;" returnTradeNo="${bill.returnTradeNo!''}" tradePrice="${bill.tradePrice}" >退款</a></li>
</#list>