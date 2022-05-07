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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The database operation is abnormal
 * @author kingapex
 * 2010-1-6In the afternoon06:16:47
 */
public class DBRuntimeException extends RuntimeException {

 
	private static final long serialVersionUID = -368646349014580765L;
	
	private static final Log loger = LogFactory
			.getLog(DBRuntimeException.class);

	
	public DBRuntimeException(String message){
		super(message);
	}
	public DBRuntimeException(Exception e,String sql) {
		
		super("The database is abnormal. Procedure");
		e.printStackTrace();
		if(loger.isErrorEnabled()){
			loger.error("The database is abnormalsqlStatement for"+ sql);
		}
	}
	
	
}
