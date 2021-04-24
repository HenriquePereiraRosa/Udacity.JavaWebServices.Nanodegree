package com.example.demo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;

public class TestUtils {

    private static Log log = LogFactory.getLog(TestUtils.class);

    public static void injectObjects(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if (!f.isAccessible()) {
//                if (!f.canAccess(target)) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if (wasPrivate) {
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            log.error(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error(e);
        }
    }
}
