package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.framework.util.FileUtil;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
* System Status Heartbeat function
* @Author shen
* @Date 2021/6/30 15:49
*/

@Api(description = "System Status Heartbeat function")
@RestController
@RequestMapping("/webjars/system/seller/state")
@Validated
public class GoodsStateController {

   @GetMapping
   public String  live() {
       return  FileUtil.readFile("Heartbeat.ftl");
   }
}
