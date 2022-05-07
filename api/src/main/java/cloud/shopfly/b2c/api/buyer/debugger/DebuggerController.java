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
package cloud.shopfly.b2c.api.buyer.debugger;

import cloud.shopfly.b2c.framework.logs.Debugger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Debug controller
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-17
 */
@Controller
@RequestMapping("/debugger")
@ConditionalOnProperty(value = "shopfly.debugger", havingValue = "true")
public class DebuggerController {

    @Autowired
    private Debugger debugger;


    /**
     * To obtaindebuggerinterface
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping()
    public String ui(Model model, HttpServletRequest request) {
        if (!request.getServletPath().endsWith("/")) {
            return "debugger-301";
        }
        model.addAttribute("jquery_path", "../jquery.min.js");

        return "debugger";
    }


    /**
     * Access to the log
     *
     * @return
     */
    @GetMapping(value = "/log")
    @ResponseBody
    public String log() {
        return debugger.getLog();
    }


    /**
     * Clear the log
     */
    @DeleteMapping(value = "/log")
    @ResponseBody
    public void delLog() {
        debugger.clear();
    }
}
