package cloud.shopfly.b2c.core.payment.plugin.paypal;

import com.paypal.base.ConfigManager;
import com.paypal.base.Constants;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

/**
 * GenerateAccessToken
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-22 20:37:00
 */
public class GenerateAccessToken {

    public static String getAccessToken() throws PayPalRESTException {

        // ###AccessToken
        // Retrieve the access token from
        // OAuthTokenCredential by passing in
        // ClientID and ClientSecret
        String clientID = ConfigManager.getInstance().getValue(Constants.CLIENT_ID);
        String clientSecret = ConfigManager.getInstance().getValue(
                Constants.CLIENT_SECRET);

        return new OAuthTokenCredential(clientID, clientSecret)
                .getAccessToken();
    }
}
