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
package cloud.shopfly.b2c.core.pagecreate.exception;

/**
 * A system exception
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-10 In the morning7:14
 */
public enum PageCreateErrorCode {


    //ERRORCODE
    E901("Server Exception"),
    E902("progressid Cant be empty"),
    E903("Static page addresses cannot be empty"),
    E904("Error in generating static page parameters"),
    E905("A static page generation task is in progress and can be generated only after the task is complete.");
    private String describe;

    PageCreateErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get the statistical exception code
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * Get statistics error messages
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
