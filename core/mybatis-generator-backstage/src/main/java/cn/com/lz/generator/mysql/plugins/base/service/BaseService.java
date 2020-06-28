package cn.com.lz.generator.mysql.plugins.base.service;

import cn.com.lz.generator.mysql.plugins.base.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author houds
 * @Title: BaseService
 * @ProjectName threed
 * @Description: 基类
 * @date 2018/7/1017:29
 */
public interface BaseService<T,R,Q,Model> extends InitializingBean {

    Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    void setMevt(BaseMapper mapper, Class<?> model, Class<?> example, Class<?> criteria, Class<?> vo);

    long queryCount(Q query) throws Exception;

    int deleteByQuery(Q query) throws Exception;

    int deleteById(String id) throws Exception;

    int insert(R req) throws Exception;

    int insertSelective(R req) throws Exception;

    int insertMSelective(Model model) throws Exception;

    List<T> query(Q query) throws Exception;

    List<T> queryBlob(Q query) throws Exception;

    T queryByPrimaryKey(String id) throws Exception;

    int updateSelective(R req, Q query) throws Exception;

    int updateByExample(R req, Q query) throws Exception;

    int updateByPrimaryKeySelective(R req) throws Exception;

    int updateByPrimaryKeyMSelective(Model req) throws Exception;

    int updateByPrimaryKey(R req) throws Exception;

    PageInfo<T> pageInfo(Q query) throws Exception;

    PageInfo<T> pageInfoBlob(Q query) throws Exception;

}
