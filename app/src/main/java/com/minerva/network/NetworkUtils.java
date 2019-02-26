package com.minerva.network;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NetworkUtils {
    /**
     * 生成Get请求参数
     * @param object
     * @return
     */
    public static String handlerURLParams(Object object) {
        if(object instanceof HashMap){
            return handlerURLParams((HashMap)object);
        }
        return handlerURLParams(getSerializeFromObject(object));
    }

    /**
     * 生成Get请求参数
     * @param map
     * @return
     */
    public static String handlerURLParams(HashMap<String, Object> map) {
        if(map != null && map.size() != 0) {
            StringBuilder sb = new StringBuilder();
            Iterator iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                String value = getString(entry.getValue());
                if(!TextUtils.isEmpty(value)) {
                    sb.append((String)entry.getKey() + "=" + value + "&");
                }
            }

            if(sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static final HashMap<String, Object> getSerializeFromObject(Object obj) {
        if(obj == null) {
            return new HashMap();
        } else {
            Field[] fields = obj.getClass().getFields();
            HashMap hashMap = new HashMap(fields.length);
            if(fields != null) {
                Field[] mFields = fields;
                int length = fields.length;

                for(int i = 0; i < length; ++i) {
                    Field field = mFields[i];
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = null;

                    try {
                        fieldValue = field.get(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    hashMap.put(fieldName, fieldValue);
                }
            }

            return hashMap;
        }
    }


    public static String getString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
