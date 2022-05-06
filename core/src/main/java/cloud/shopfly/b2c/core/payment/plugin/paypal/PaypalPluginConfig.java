package cloud.shopfly.b2c.core.payment.plugin.paypal;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import com.paypal.core.PayPalEnvironment;
import cloud.shopfly.b2c.core.payment.service.AbstractPaymentPlugin;
import cloud.shopfly.b2c.framework.logs.Debugger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * PaypalConfig
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-22 20:22:00
 */
public class PaypalPluginConfig extends AbstractPaymentPlugin {

    protected final String siteName = "shopflix商城";

    @Autowired
    private Debugger debugger;

    @Override
    protected String getPluginId() {
        return "paypalPlugin";
    }


    /**
     * 构建alipay client
     * @param clientType
     * @return
     */
    protected PayPalClient buildClient(ClientType clientType) {

        Map<String, String> config = this.getConfig(clientType );

        return buildClient(config);
    }


    protected PayPalClient buildClient(Map<String, String> config ){
        String paypalClientId = config.get("PAYPAL_CLIENT_ID");
        String paypalClientSecret = config.get("PAYPAL_CLIENT_SECRET");

        debugger.log("使用如下参数构建client:");
        debugger.log(config.toString());

        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(paypalClientId, paypalClientSecret );
        PayPalClient payPalClient = new PayPalClient(environment);
        return payPalClient;
    }

}
