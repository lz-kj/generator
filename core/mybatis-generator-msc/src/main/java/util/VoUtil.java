package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class VoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoUtil.class);

    public static <T> List<T> getVoList(Class<T> voClass, List<?> lst){
        List<T> lstVo = new ArrayList<T>();
        if (!CollectionUtils.isEmpty(lst) ) {
            lst.forEach( i -> {
                T vo = null;
                try {
                    vo = voClass.newInstance();
                    BeanUtils.copyProperties(i,vo);
                    Class<?> voCls = vo.getClass();
                    lstVo.add(vo);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return lstVo;
    }

    public static <T> List<T> getVoList(T vo, List<?> lst){
        List<T> lstVo = new ArrayList<T>();
        if (!CollectionUtils.isEmpty(lst) ) {
            lst.forEach( i -> {
                BeanUtils.copyProperties(i,vo);
                Class<?> voCls = vo.getClass();
                lstVo.add(vo);
            });
        }
        return lstVo;
    }

}
