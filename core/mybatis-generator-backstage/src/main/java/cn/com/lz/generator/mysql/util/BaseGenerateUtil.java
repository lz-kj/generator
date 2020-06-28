package cn.com.lz.generator.mysql.util;


import cn.com.lz.generator.mysql.plugins.base.model.IGenerate;

import java.util.Map;
import java.util.Set;

public class BaseGenerateUtil {

    public static void andEqualToQuery(Map<String, Object> params , IGenerate baseGenerate){
        Set<String> keys = params.keySet();
        for (String key: keys ) {
            Object val = params.get(key);
            StringBuilder sbd = new StringBuilder();
            char[] chars = key.toCharArray();
            for (char chr: chars ) {
                if(Character.isUpperCase(chr)){
                    sbd.append("_");
                    sbd.append(Character.toLowerCase(chr));
                }else{
                    sbd.append(chr);
                }
            }
            baseGenerate.addCriterion(sbd.toString() + "=", val, key);
        }
    }

}
