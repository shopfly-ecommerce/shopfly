/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


/**
 * JSON The relevant operation
 * @author Sylow
 * Need to beGJSON
 * 2015-07-14 
 */
public class JsonUtil {

	protected final static Log logger = LogFactory.getLog(JsonUtil.class);

	/**
	 * thejsonFormat string to convert tomapOn the line
	 * @param json
	 * @return LinkedHashMap 
	 */
	public static LinkedHashMap<String, Object> toMap(String json) {
		return toMap(parseJson(json));
	}

	/**
	 * thejsonArray format string conversion toList
	 * @return List<Object>
	 */
	public static List<Map<String,Object>> toList(String jsonArr){

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JsonArray jsonArray = parseJsonArray(jsonArr);
		for (int i = 0; i < jsonArray.size(); i++) {
			Object value = jsonArray.get(i);
			Map<String,Object> map = toMap(value.toString());
			list.add(map);
		}
		return list;
	}


	public static String objectToJson(Object object){
		
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonStr = "";
		try {
			jsonStr  = mapper.writeValueAsString(object);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/*public static void main(String[] args){
		String json = "[{'name':'1'},{'name':'2'}]";
		List<Map> list = new ArrayList<Map>();
		JsonArray jsonArray = parseJsonArray(json);
		for (int i = 0; i < jsonArray.size(); i++) {
			Object value = jsonArray.get(i);
			Map<String,Object> map = toMap(value.toString());
			list.add(map);
		}

		for(Map map : list){
			System.out.println(map.get("name"));
		}

	}*/

	/**
	 * To obtainJsonObject
	 * 
	 * @param json
	 * @return
	 */
	private static JsonObject parseJson(String json) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		return jsonObj;
	}

	private static JsonArray parseJsonArray(String jsonArr){
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(jsonArr).getAsJsonArray();
		return jsonArray;
	}

	/**
	 * willJSONObjecObject converted toMap-ListA collection of
	 * 
	 * @param json
	 * @return
	 */
	private static LinkedHashMap<String, Object> toMap(JsonObject json) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
		for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter
				.hasNext();) {
			Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof JsonArray) {
				map.put(key, toList((JsonArray) value));
			} else if (value instanceof JsonObject) {
				map.put(key, toMap((JsonObject) value));
			} else if (value instanceof JsonNull) {
				map.put(key, "");
			} else {
				String str = value.toString();
				if (str.startsWith("\"")) {
					str = str.substring(1, str.length() - 1);
					Object obj = str;
					map.put(key, obj);
				} else {
					map.put(key, value);
				}

			}

		}
		return map;
	}

	/**
	 * willJSONArrayObject converted toListA collection of
	 * 
	 * @param json
	 * @return
	 */
	private static List<Object> toList(JsonArray json) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < json.size(); i++) {
			Object value = json.get(i);
			if (value instanceof JsonArray) {
				list.add(toList((JsonArray) value));
			} else if (value instanceof JsonObject) {
				list.add(toMap((JsonObject) value));
			} else if (value instanceof JsonNull) {
				list.add("");
			} else {
				String str = value.toString();
				if (str.startsWith("\"")) {
					str = str.substring(1, str.length() - 1);
					Object obj = str;
					list.add(obj);
				} else {
					list.add(value);
				}
			}
		}
		return list;
	}

	/**
	 * willjsonString tolist
	 * @param jsonData
	 * @param clz
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData,Class<T> clz){
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		JavaType javaType =  mapper.getTypeFactory().constructParametricType(ArrayList.class, clz);
		
		try {
			return mapper.readValue(jsonData, javaType);
		} catch (Exception e) {
			logger.error("conversionObject abnormal",e);
		}
		
		return null;
	}

	/**
	 * willjsonString toObject
	 * @param jsonData
	 * @param clz
	 * @return
	 */
	public static <T> T jsonToObject(String jsonData,Class<T> clz){
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(jsonData,clz);
		} catch (IOException e) {
			logger.error("conversionObject abnormal",e);
		}
		return null;
	}

}
