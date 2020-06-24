package org.mybatis.generator.plugins.base.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mybatis.generator.plugins.base.common.R;
import org.mybatis.generator.plugins.base.service.BaseService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

public abstract class BaseConroller<T,Model,Q> implements InitializingBean {

    @Autowired
    protected  HttpServletRequest request;

    @Autowired
    protected  HttpServletResponse response;

    protected BaseService baseService;

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "查询列表", response = R.class, httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "查询成功")})
    protected R list(@Valid Q query) throws Exception{
        List<T> result = baseService.query(query);
        return R.ok("查询成功",result);
    }
    /**
     * 分页查询
     * @param query
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "回显:根据ID", response = R.class, httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "回显成功")})
    protected R page(@Valid Q query) throws Exception{
        PageInfo<T> result = baseService.pageInfo(query);
        return R.ok("查询成功",result);
    }

    /**
     * 添加
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加:无选择", response = R.class, httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R add(@RequestBody @Valid Model req) throws Exception {
        int result = baseService.insert(req);
        return R.ok("添加成功",result);
    }
    /**
     * 添加
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add/selective", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加:有选择", response = R.class, httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R addSelective(@RequestBody @Valid Model req) throws Exception {
        int result = baseService.insertSelective(req);
        return R.ok("添加成功",result);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/del/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "删除:通过ID", response = R.class, httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "删除成功")})
    protected R del(@ApiParam(value = "ID", required = true) @PathVariable("id") String id) throws Exception {
        int result = baseService.deleteById(id);
        return R.ok("删除成功",result);
    }

    /**
     * 删除
     *
     * @param query
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/del", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "删除:通过条件", response = R.class, httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "删除成功")})
    protected R del(@Valid Q query) throws Exception {
        int result = baseService.deleteByQuery(query);
        return R.ok("删除成功",result);
    }

    /**
     * 修改
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "修改:通过ID:无选择", response = R.class, httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R update(@RequestBody @Valid Model req) throws Exception {
        int result = baseService.updateByPrimaryKey(req);
        return R.ok("修改成功",result);
    }

    /**
     * 修改
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update/selective", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "修改:通过ID:有选择", response = R.class, httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R updateSelective(@RequestBody @Valid Model req) throws Exception {
        int result = baseService.updateByPrimaryKeySelective(req);
        return R.ok("修改成功",result);
    }

    /**
     * 修改
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update/byqry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "修改:通过查询条件:无选择", response = R.class, httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R updateByQuery(@Valid Model req,@Valid Q query) throws Exception {
        int result = baseService.updateByExample(req,query);
        return R.ok("修改成功",result);
    }

    /**
     * 修改
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update/byqry/selective", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "修改:通过查询条件:有选择", response = R.class, httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "添加成功")})
    protected R updateByQuerySelective(@Valid Model req,@Valid Q query) throws Exception {
        int result = baseService.updateSelective(req,query);
        return R.ok("修改成功",result);
    }

    /**
     * 回显
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "回显:根据ID", response = R.class, httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "回显成功")})
    protected R view(@ApiParam(value = "ID", required = true) @PathVariable("id") String id) throws Exception{
        Object result = baseService.queryByPrimaryKey(id);
        return R.ok("回显成功",result);
    }

}
