package com.recse4cloud.pay.api;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    /**
     * bean转map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> bean2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        String json = JSON.toJSONString(obj);
        Map map = JSON.parseObject(json, HashMap.class);
        return map;
    }

    public static String bean2XML(Object obj) {
        if (obj == null) {
            return null;
        }
        final String target = "<%s><![CDATA[%s]]></%s>";
        StringBuffer sb = new StringBuffer("<xml>");
        Class cls = obj.getClass();
        String name;
        Object value;
        String temp;
        Method[] methods = cls.getMethods();
        try {
            for (Method method : methods) {
                name = method.getName();
                if (name.startsWith("get") && !name.contains("Class")) {
                    name = name.substring(3, 4).toLowerCase() + name.substring(4);
                    value = method.invoke(obj);
                    if (value == null) {
                        continue;
                    }
                    temp = String.format(target, name, value, name);
                    sb.append(temp);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static Map<String, Object> xml2Map(String xml) {
        try {
            Map<String, Object> map = new HashMap<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            ByteArrayInputStream bai = new ByteArrayInputStream(xml.getBytes());
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            String key = "", value = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        key = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        value = parser.getText();
                        if (StringUtils.isNoneBlank(value)) {
                            map.put(key, value);
                        }
                        break;
                }
                eventType = parser.next();
            }
            return map;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param xml
     * @param cls
     * @return
     */
    public static Object xml2Bean(String xml, Class cls) {
        // 转换数据格式
        XStream xStream = new XStream(new DomDriver("UTF-8",
                new XmlFriendlyNameCoder("-_", "_")));
        xStream.alias("xml", cls);
        return xStream.fromXML(xml);
    }

    /**
     * @param xml
     * @param cls
     * @return
     */
    public static Object map2Bean(Map<String, Object> xml, Class cls) {
        return JSON.parseObject(JSON.toJSONString(xml), cls);
    }

}
