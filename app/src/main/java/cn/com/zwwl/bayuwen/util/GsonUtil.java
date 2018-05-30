package cn.com.zwwl.bayuwen.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * gson解析封装
 */
public class GsonUtil {
    /**
     *解析 Json对象
     */
    public static <T> T parseJson(Class<T> type,String jsonStr){
        return new Gson().fromJson(jsonStr,type);
    }
    /**
     *将Json数组解析成相应的映射对象列表
     */
    public static <T> ArrayList<T> parseJsonArray(Class<T> myClass,String jsonStr){
        Type type = new ListParameterizedType(myClass);
        return new Gson().fromJson(jsonStr, type);
    }

    private static class ListParameterizedType implements ParameterizedType {
        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
