package util;

import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtil {

    /**
     * 得到属性名称和值
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String,Object> getAttrValue(Object obj) throws Exception{
        Map<String,Object> attrVal = new HashMap<String,Object>();
        //得到class
        Class cls = obj.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i=0;i<fields.length;i++){//遍历
            //得到属性
            Field field = fields[i];
            //打开私有访问
            field.setAccessible(true);
            //获取属性
            String name = field.getName();
            //获取属性值
            Object value = field.get(obj);
            if(!ObjectUtils.isEmpty(value))
                attrVal.put(field.getName(),value );
        }
        return attrVal;
    }

    /**
     * 得到属性名称和值
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String,Object> getAttrValueForQuery(Object obj) throws Exception{
        Map<String,Object> attrVal = new HashMap<String,Object>();
        //得到class
        Class cls = obj.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i=0;i<fields.length;i++){//遍历
            //得到属性
            Field field = fields[i];
            //打开私有访问
            field.setAccessible(true);
            //获取属性
            String name = field.getName();
            //获取属性值
            Object value = field.get(obj);
            if(!ObjectUtils.isEmpty(value)){
                attrVal.put(name,value );
            }
        }
        return attrVal;
    }

    /**
     *
     * @param obj 需要反射的类
     * @param methodName 切点方法名称
     * @param args 切点方法的参数
     * @param annotationClass 注解类
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T extends Annotation> T getAnotation(Object obj, String methodName, Object[] args, Class<T> annotationClass) throws ClassNotFoundException {
        T retult = null;
        //获取连接点目标类名
        String className =obj.getClass().getName() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(className);
        //拿到类里面的方法
        Method[] methods = targetClass.getMethods() ;
        String description = "" ;
        //遍历方法名，找到被调用的方法名
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes() ;
                if (clazzs.length==args.length){
                    //获取注解的说明
                    retult = method.getAnnotation(annotationClass);
                    break;
                }
            }
        }
        return retult;
    }
}
