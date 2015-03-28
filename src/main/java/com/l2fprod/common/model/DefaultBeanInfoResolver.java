/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.model;

import com.l2fprod.common.beans.BeanInfoFactory;
import com.l2fprod.common.beans.BeanInfoResolver;

import java.beans.BeanInfo;
import java.util.HashMap;

/**
 * DefaultBeanInfoResolver. <br>
 *
 */
public class DefaultBeanInfoResolver implements BeanInfoResolver {

    private static final HashMap<String, BeanInfo> map = new HashMap<>();

    public static synchronized void addBeanInfo(Class<?> clazz, BeanInfo bi) {
        map.put(clazz.getName(), bi);
    }
    
    public static synchronized BeanInfo getBeanInfoHelper(Class<?> clazz) {
        if (map.containsKey(clazz.getName())) {
            return map.get(clazz.getName());
        }
        return null;
    }
    
    public DefaultBeanInfoResolver() {
        super();
    }

    @Override
    public BeanInfo getBeanInfo(Object object) {
        if (object == null) {
            return null;
        }

        return getBeanInfo(object.getClass());
    }

    @Override
    public BeanInfo getBeanInfo(Class clazz) {
        if (clazz == null) {
            return null;
        }

        String classname = clazz.getName();

        if (map.containsKey(classname)) {
            return map.get(classname);
        }
        else {
            return BeanInfoFactory.createBeanInfo(clazz);
        }
    }
}
