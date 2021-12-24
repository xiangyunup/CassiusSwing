package org.lxy.swing.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * @author Cassius
 * @date 2021/12/20 6:37 PM
 * @description mac
 */
public class PropertiesUtil {

    // 静态块中不能有非静态属性，所以加static
    private static Properties prop = null;

    //静态块中的内容会在类别加载的时候先被执行
    static {
        try {
            prop = new Properties();
            ClassPathResource resource = new ClassPathResource("application.properties");
            prop.load(resource.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //静态方法可以被类名直接调用
    public static String getValueByFileName(String key, String propertiesFileName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesFileName);
            return properties.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    //静态方法可以被类名直接调用
    public static String getValue(String key) {
        try {
            return prop.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean eqValue(String param,String key) {
        return param.equals(getValue(key));
    }

    public static boolean boValue(String key) {
        return Boolean.parseBoolean(getValue(key));
    }
}
