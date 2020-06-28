package cn.com.lz.generator.mysql.plugins.base.model;

import java.util.Map;

public interface IGenerate<T> {

    T baseQuery(Map<String, Object> params);

    void addCriterion(String condition, Object value, String property);

}
