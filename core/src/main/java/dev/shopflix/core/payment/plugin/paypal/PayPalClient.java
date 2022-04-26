package dev.shopflix.core.payment.plugin.paypal;

import cn.hutool.json.JSONObject;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class PayPalClient {

	/**
	 * Setting up PayPal SDK environment with PayPal Access credentials. For demo
	 * purpose, we are using SandboxEnvironment. In production this will be
	 * LiveEnvironment.
	 */
	private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
			System.getProperty("PAYPAL_CLIENT_ID") != null ? System.getProperty("PAYPAL_CLIENT_ID")
					: "Aejo36jCAH2pqkN3rbG0e3upgklt_0uZNaQ7WXAvPdvQZkShK7vsf4xdE0GU8340tbDS_qP3gkkFdnOh",
			System.getProperty("PAYPAL_CLIENT_SECRET") != null ? System.getProperty("PAYPAL_CLIENT_SECRET")
					: "EBbNOsKPsr6eNnnC1x4dFNkcgXy_yMhHxhdKIpf9Xhhj5RvyFRgaxKjwmKlwaQt1zEhmh4tgLDEZWxIv");

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