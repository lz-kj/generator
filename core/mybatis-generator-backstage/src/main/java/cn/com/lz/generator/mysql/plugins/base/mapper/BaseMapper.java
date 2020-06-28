package cn.com.lz.generator.mysql.plugins.base.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<Model, E, PK> {

    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(PK id);

    int insert(Model record);

    int insertSelective(Model record);

    List<Model> selectByExample(E example);

    List<Model> selectByExampleWithBLOBs(E example);

    Model selectByPrimaryKey(PK id);

    int updateByExampleSelective(@Param("record") Model record, @Param("example") E example);

    int updateByExample(@Param("record") Model record, @Param("example") E example);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

}
