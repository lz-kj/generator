package cn.com.lz.generator.mysql.plugins.base.service.impl;

import cn.com.lz.generator.mysql.plugins.base.controller.query.Query;
import cn.com.lz.generator.mysql.plugins.base.controller.req.Req;
import cn.com.lz.generator.mysql.plugins.base.controller.vo.Vo;
import cn.com.lz.generator.mysql.plugins.base.mapper.BaseMapper;
import cn.com.lz.generator.mysql.plugins.base.service.BaseService;
import cn.com.lz.generator.mysql.util.IDUtil;
import cn.com.lz.generator.mysql.util.ReflectUtil;
import cn.com.lz.generator.mysql.util.VoUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: BaseServiceImpl
 * @ProjectName threed
 * @Description: TODO
 * @author rayoo
 * @date 2018/7/1017:53
 */
public abstract class BaseServiceImpl<Model> implements BaseService<Vo, Req, Query,Model> {

    private BaseMapper mapper;
    Class<?> m,e,vo;

    @Override
    public long queryCount(Query query) throws Exception {
        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        long result = this.getMapper().countByExample(example);
        return result;
    }

    @Override
    public int deleteByQuery(Query query) throws Exception {
        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        int result = this.getMapper().deleteByExample(example);
        return result;
    }

    @Override
    public int deleteById(Long id) throws Exception {
        int result = this.getMapper().deleteByPrimaryKey(id);
        return result;
    }

    @Override
    public int deleteByIds(List<Long> ids) throws Exception {
        return 0;
    }

    @Override
    public int insert(Req req) throws Exception {
        Object model = m.newInstance();
//        req.setId(IDUtil.getUUID());
        req.setCreateTime(new Date());
        BeanUtils.copyProperties(req,model);
        int result = this.getMapper().insert(model);
        BeanUtils.copyProperties(model,req);
        return result;
    }

    @Override
    public int insertSelective(Req req) throws Exception {
        Object model = m.newInstance();
//        req.setId(IDUtil.getUUID());
        req.setCreateTime(new Date());
        BeanUtils.copyProperties(req,model);
        int result = this.getMapper().insertSelective(model);
        BeanUtils.copyProperties(model,req);
        return result;
    }

    @Override
    public int insertMSelective(Model model) throws Exception {
       int result = this.getMapper().insertSelective(model);
       return result;
    }

    @Override
    public List<Vo> query(Query query) throws Exception {
        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        List<Model> lst = this.getMapper().selectByExample(example);
//        Vo ov = (Vo)vo.newInstance();
        List<Vo> result = VoUtil.getVoList((Class<Vo>) vo,lst);
        return result;
    }

    @Override
    public List<Vo> queryBlob(Query query) throws Exception {
        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        List<Model> lst = this.getMapper().selectByExampleWithBLOBs(example);
//        Vo ov = (Vo)vo.newInstance();
        List<Vo> result = VoUtil.getVoList((Class<Vo>) vo,lst);
        return result;
    }

    @Override
    public Vo queryByPrimaryKey(Long id) throws Exception {
        Vo result = (Vo)vo.newInstance();
        Object model = this.getMapper().selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(model)){
            BeanUtils.copyProperties(model,result);
        }else{
            result = null;
        }
        return result;
    }

    @Override
    public int updateSelective(Req req, Query query) throws Exception {
        Object model = m.newInstance();
        req.setModifyTime(new Date());
        BeanUtils.copyProperties(req,model);

        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        int result = this.getMapper().updateByExampleSelective(model,example);
        return result;
    }

    @Override
    public int updateByExample(Req req, Query query) throws Exception {
        Object model = m.newInstance();
        req.setModifyTime(new Date());
        BeanUtils.copyProperties(req,model);

        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);
        int result = this.getMapper().updateByExample(model,example);
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(Req req) throws Exception {
        Object model = m.newInstance();
        req.setModifyTime(new Date());
        BeanUtils.copyProperties(req,model);
        int result = this.getMapper().updateByPrimaryKeySelective(model);
        return result;
    }

    @Override
    public int updateByPrimaryKeyMSelective(Model model) throws Exception {
        int result = this.getMapper().updateByPrimaryKeySelective(model);
        return result;
    }

    @Override
    public int updateByPrimaryKey(Req req) throws Exception {
        Object model = m.newInstance();
        req.setModifyTime(new Date());
        BeanUtils.copyProperties(req,model);
        int result = this.getMapper().updateByPrimaryKey(model);
        return result;
    }

    @Override
    public PageInfo<Vo> pageInfo(Query query) throws Exception {
        Page<Vo> page = PageHelper.startPage(query.getPageNum(),query.getPageSize());

        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);

        List<Model> lst = this.getMapper().selectByExample(example);
//        Vo ov = (Vo)vo.newInstance();
        List<Vo> lstVo = VoUtil.getVoList((Class<Vo>) vo,lst);
        page.clear();
        page.addAll(lstVo);
        return page.toPageInfo();
    }

    @Override
    public PageInfo<Vo> pageInfoBlob(Query query) throws Exception {
        Page<Vo> page = PageHelper.startPage(query.getPageNum(),query.getPageSize());

        Object example = e.newInstance();
        Map<String,Object> attrVal = ReflectUtil.getAttrValueForQuery(query);
        this.setQueryCriteria(example,attrVal);

        List<Model> lst = this.getMapper().selectByExampleWithBLOBs(example);
//        Vo ov = (Vo)vo.newInstance();
        List<Vo> lstVo = VoUtil.getVoList((Class<Vo>) vo,lst);
        page.clear();
        page.addAll(lstVo);
        return page.toPageInfo();
    }

    private BaseMapper getMapper() throws Exception {
        return mapper;
    }

    @Override
    public void setMevt(BaseMapper mapper,Class<?> model,Class<?> example,Class<?> criteria,Class<?> vo) {
        this.mapper = mapper;
        this.m = model;
        this.e = example;
//        this.c = criteria;
        this.vo = vo;
    }


    private void setQueryCriteria(Object example,Map<String,Object> attrVal) throws Exception{
        //排序
        Object order = attrVal.get("order");
        Method orderMethod = e.getMethod("setOrderByClause",String.class);//得到方法对象
        orderMethod.setAccessible(true);
        if(!ObjectUtils.isEmpty(order)){
            //排序重写后按照重写的排
            orderMethod.invoke(example,order);
            attrVal.remove(order);
        }else{
            //默认按照更新时间排序
            orderMethod.invoke(example,"modify_time desc");
        }
        //查询
        Method method = e.getMethod("createCriteria");//得到方法对象
        Object criteriaObj = method.invoke(example);
        Class<?> criteria = criteriaObj.getClass();
        Method methd = criteria.getMethod("baseQuery",Map.class);//得到方法对象
        methd.setAccessible(true);
        methd.invoke(criteriaObj,attrVal);
    }
}
