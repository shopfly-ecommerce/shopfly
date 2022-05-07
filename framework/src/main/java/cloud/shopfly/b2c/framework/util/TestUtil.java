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

/**
 * Test generic classes
 * Provides a unified printing method for test switches
 * @author Sylow
 * @version v1.0 , 2015-08-24
 * @since v4.0
 * 
 */
public class TestUtil {
	
	/**
	 * Print the switch
	 */
	public final static boolean TEST_OPEN = false;
	
	
	/**
	 * Print error message
	 * @param e <b>Exception</b>
	 */
	public static void print(Exception e){
		if (TEST_OPEN) {
			e.printStackTrace();
		}
	}

}
