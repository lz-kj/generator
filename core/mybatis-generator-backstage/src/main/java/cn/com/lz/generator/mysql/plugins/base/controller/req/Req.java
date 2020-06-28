package cn.com.lz.generator.mysql.plugins.base.controller.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Req implements Serializable {

    @ApiModelProperty("id")
//    @JsonIgnore
    private String id;

    @JsonIgnore
    @ApiParam(hidden = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiParam(hidden = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date modifyTime = new Date();

    @ApiParam(hidden = true)
    @JsonIgnore
    private Boolean del = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }
}
