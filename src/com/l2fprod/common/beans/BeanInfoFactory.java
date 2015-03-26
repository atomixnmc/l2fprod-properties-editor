/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.l2fprod.common.beans;


import com.l2fprod.common.annotations.Browsable;
import com.l2fprod.common.annotations.Categorization;
import com.l2fprod.common.annotations.DirectoryProperty;
import com.l2fprod.common.annotations.FileProperty;
import com.l2fprod.common.beans.editor.DirectoryPropertyEditor;
import com.l2fprod.common.beans.editor.FilePropertyEditor;
import com.l2fprod.common.model.DefaultBeanInfoResolver;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matta
 */
public class BeanInfoFactory {

    private BeanInfoFactory() {

    }

    public static BeanInfo createBeanInfo(Class<? extends Object> c) {
        BeanInfo bi = new ConfigBeanInfo(c);
        DefaultBeanInfoResolver.addBeanInfo(c, bi);
        return bi;
    }

    public static class ConfigBeanInfo extends BaseBeanInfo {

        public ConfigBeanInfo(Class<? extends Object> c) {
            super(c);
            try {
                PropertyDescriptor[] props = Introspector.getBeanInfo(c).getPropertyDescriptors();
                for (PropertyDescriptor prop : props) {
                    if (prop.getReadMethod().getDeclaringClass() != Object.class) {
                        Categorization cat = prop.getReadMethod().getAnnotation(Categorization.class);
                        Browsable browse = prop.getReadMethod().getAnnotation(Browsable.class);
//                        EnumeratedType et = prop.getReadMethod().getAnnotation(EnumeratedType.class);
                        FileProperty fp = prop.getReadMethod().getAnnotation(FileProperty.class);
                        DirectoryProperty dir_p = prop.getReadMethod().getAnnotation(DirectoryProperty.class);

                        if (browse == null || browse.enabled()) {
                            ExtendedPropertyDescriptor epd = addProperty(prop.getName()).setCategory(cat == null ? "General" : cat.category());
                            if (fp != null) {
                                epd.setPropertyEditorClass(FilePropertyEditor.class);
                            } else if (dir_p != null) {
                                epd.setPropertyEditorClass(DirectoryPropertyEditor.class);
                            } else {
//                                Reflections ref = buildReflections("com.artistech");
//                                Set<Class<?>> typesAnnotatedWith = ref.getTypesAnnotatedWith(EditorRegistry.class);
//                                for (Class<?> t : typesAnnotatedWith) {
//                                    EditorRegistry er = t.getAnnotation(EditorRegistry.class);
//                                    for (Class<?> t2 : er.type()) {
//                                        if (t2.isAssignableFrom(prop.getPropertyType())) {
//                                            epd.setPropertyEditorClass(t);
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                Set<Class<?>> typesAnnotatedWith1 = ref.getTypesAnnotatedWith(RendererRegistry.class);
//                                for (Class<?> t : typesAnnotatedWith1) {
//                                    RendererRegistry rr = t.getAnnotation(RendererRegistry.class);
//                                    for (Class<?> t2 : rr.type()) {
//                                        if (t2.isAssignableFrom(prop.getPropertyType())) {
//                                            epd.setPropertyTableRendererClass(t);
//                                            break;
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
            } catch (IntrospectionException ex) {
                Logger.getLogger(BeanInfoFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
