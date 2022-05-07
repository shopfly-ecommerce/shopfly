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
package cloud.shopfly.b2c.core.promotion.exchange.service;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeCat;

import java.util.List;

/**
 * Points exchange classification business layer
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-29 16:56:22
 */
public interface ExchangeCatManager	{

	/**
	 * Query the list of points redemption categories
	 * @param parentId The fatherID
	 * @return Page
	 */
	List<ExchangeCat> list(Integer parentId);

	/**
	 * Add the points exchange category
	 * @param exchangeCat Points exchange classification
	 * @return ExchangeCat Points exchange classification
	 */
	ExchangeCat add(ExchangeCat exchangeCat);

	/**
	* Modify the classification of points exchange
	* @param exchangeCat Points exchange classification
	* @param id Points exchange classification primary key
	* @return ExchangeCat Points exchange classification
	*/
	ExchangeCat edit(ExchangeCat exchangeCat, Integer id);

	/**
	 * Delete the points exchange category
	 * @param id Points exchange classification primary key
	 */
	void delete(Integer id);

	/**
	 * Get points redemption classification
	 * @param id Points exchange classification primary key
	 * @return ExchangeCat  Points exchange classification
	 */
	ExchangeCat getModel(Integer id);

}
