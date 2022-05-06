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
 * sql文件执行器
 * @author kingapex
 * 2010-1-25上午11:49:50
 */
public interface ISqlFileExecutor {
	
	/**
	 * 批量执行sql语句
	 * @param sql 可以以两种形式传递sql:<br>
	 * <li>1.路径方式：file:com/enation/eop/eop_empty.sql</li>
	 * <li>2.sql内容：直接传递文件内容</li>
	 */
    void execute(String sql);
}
