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
package org.platform.context.setting;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import org.more.core.global.Global;
/**
 * 配置对象
 * @version : 2013-3-28
 * @author 赵永春 (zyc@byshell.org)
 */
public interface Config {
    /**获取{@link ServletContext}对象。*/
    public ServletContext getServletContext();
    /**获取一个指定的初始化参数.*/
    public String getInitParameter(String name);
    /**获取初始化参数名称集合.*/
    public Enumeration<String> getInitParameterNames();
    /**获取应用程序配置。*/
    public Global getSettings();
}