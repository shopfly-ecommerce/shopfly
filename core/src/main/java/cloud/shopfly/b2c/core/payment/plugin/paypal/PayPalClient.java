package cloud.shopfly.b2c.core.payment.plugin.paypal;

import cn.hutool.json.JSONObject;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class PayPalClient {

	public PayPalClient() {
	}

	public PayPalClient(PayPalEnvironment environment) {
		this.environment = environment;
		this.client = new PayPalHttpClient(this.environment);
	}

	/**
	 * Setting up PayPal SDK environment with PayPal Access credentials. For demo
	 * purpose, we are using SandboxEnvironment. In production this will be
	 * LiveEnvironment.
	 */
	private PayPalEnvironment environment;

	/**
	 * PayPal HTTP client instance with environment which has access credentials
	 * context. This can be used invoke PayPal API's provided the credentials have
	 * the access to do so.
	 */
	PayPalHttpClient client = new PayPalHttpClient(environment);

	/**
	 * Method to get client object
	 *
	 * @return PayPalHttpClient client
	 */
	public PayPalHttpClient client() {
		return this.client;
	}

	/**
	 * Method to pretty print a response
	 *
	 * @param jo  JSONObject
	 * @param pre prefix (default="")
	 * @return String pretty printed JSON
	 */
	public String prettyPrint(JSONObject jo, String pre) {
		return null;
	}
}