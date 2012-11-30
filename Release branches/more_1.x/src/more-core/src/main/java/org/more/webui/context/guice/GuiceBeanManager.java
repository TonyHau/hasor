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
package org.more.webui.context.guice;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.more.util.ClassUtil;
import org.more.webui.context.BeanManager;
import org.more.webui.context.FacesConfig;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
/**
 * 
 * @version : 2012-5-15
 * @author ������ (zyc@byshell.org)
 */
public class GuiceBeanManager extends AbstractMap<String, Object> implements BeanManager {
    private Injector     injector     = null;
    private GucieBeanSet gucieBeanSet = null;
    //
    public void init(final FacesConfig environment) {
        final GuiceBeanManager $this = this;
        this.gucieBeanSet = new GucieBeanSet();
        this.injector = Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {
                String scanPackageStr = environment.getScanPackages();
                String[] scanPackages = scanPackageStr.split(",");
                Set<Class<?>> beanSet = ClassUtil.newInstance(scanPackages).getClassSet(Bean.class);
                if (beanSet != null)
                    for (Class<?> cls : beanSet) {
                        Bean beanClass = cls.getAnnotation(Bean.class);
                        String key = beanClass.value();
                        if (StringUtils.isBlank(key) == true)
                            key = cls.getSimpleName();
                        gucieBeanSet.add(new GucieEntry(key, cls, $this, true));
                    }
            }
        });
    }
    @Override
    public Object put(String key, Object value) {
        Object returnData = this.remove(key);
        this.gucieBeanSet.add(new GucieEntry(key, value, this, false));
        return returnData;
    }
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.gucieBeanSet;
    }
    @Override
    public <T> T getBean(Class<T> type) {
        return this.injector.getInstance(type);
    }
}
class GucieBeanSet extends AbstractSet<Entry<String, Object>> {
    private ArrayList<Entry<String, Object>> dataList = new ArrayList<Entry<String, Object>>();
    @Override
    public boolean add(Entry<String, Object> e) {
        return this.dataList.add(e);
    }
    @Override
    public Iterator<Entry<String, Object>> iterator() {
        return this.dataList.iterator();
    }
    @Override
    public int size() {
        return this.dataList.size();
    }
}
class GucieEntry implements Entry<String, Object> {
    private GuiceBeanManager guiceBeanManager = null;
    private String           key              = null;
    private Object           value            = null;
    private boolean          typeMode         = false;
    // 
    public GucieEntry(String key, Object value, GuiceBeanManager guiceBeanManager, boolean typeMode) {
        this.guiceBeanManager = guiceBeanManager;
        this.key = key;
        this.value = value;
        this.typeMode = typeMode;
    }
    @Override
    public String getKey() {
        return this.key;
    }
    @Override
    public Object getValue() {
        if (typeMode == true)
            return this.guiceBeanManager.getBean((Class) value);
        return value;
    }
    @Override
    public Object setValue(Object value) {
        Object returnData = this.value;
        this.value = value;
        return returnData;
    }
}