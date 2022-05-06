/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.database;

import java.util.HashMap;
import java.util.Map;

import cloud.shopfly.b2c.framework.database.annotation.NotDbField;


/**
 * 动态字段
 * @author kingapex
 *2012-5-5下午12:46:37
 */
public class DynamicField {
	
	private Map<String,Object> fields;
	public DynamicField(){
		fields = new HashMap<String, Object>();
	}
	
	public void addField(String name,Object value){
		fields.put(name, value);
	}
	
	@NotDbField
	public Map<String,Object> getFields(){
		return fields;
	}
}
