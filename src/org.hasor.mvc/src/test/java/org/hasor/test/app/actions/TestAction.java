/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hasor.test.app.actions;
import org.hasor.context.AppContext;
import org.hasor.mvc.controller.Controller;
import org.hasor.mvc.controller.HeaderParam;
import org.hasor.mvc.controller.InjectParam;
import org.hasor.mvc.controller.Path;
import org.hasor.mvc.controller.PathParam;
import org.hasor.mvc.controller.QueryParam;
import org.hasor.mvc.controller.plugins.result.core.Json;
import org.hasor.mvc.controller.plugins.result.core.Redirect;
/**
 * 
 * @version : 2013-7-23
 * @author ������ (zyc@byshell.org)
 */
@Controller("/action")
public class TestAction {
    @Redirect
    public String test(@InjectParam AppContext appContext) {
        String[] strs = appContext.getSettings().getStringArray("framework.loadPackages");
        System.out.println("invoke test" + strs);
        return "/index.htm";
    }
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }
    //
    @Json
    @Path("/user/{uid}/")
    public Object userInfo(@PathParam("uid") String uid, @HeaderParam("User-Agent") String[] userAgent, @QueryParam("age") int age, @QueryParam("ns") String[] ns) {
        System.out.println("hello");
        return new String[] { "abc", "cde" };
    }
}