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
package cloud.shopfly.b2c.framework.database;

/**
 * No exception was found on the object<br>
 * Usually used according toidQuery a record, but the record does not exist
 * @author kingapex
 *2010-4-9In the morning09:09:20
 */
public class ObjectNotFoundException extends DBRuntimeException {

	public ObjectNotFoundException(String message) {
		super(message);
	}
		
	public ObjectNotFoundException(Exception e, String sql) {
		super(e, sql);
	 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403302876974180460L;

}
