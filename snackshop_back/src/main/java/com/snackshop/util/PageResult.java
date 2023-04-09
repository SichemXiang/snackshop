package com.snackshop.util;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/24 23:10
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult extends Result implements Serializable {

    //总记录数
    @ApiModelProperty(value = "总条数")
    private long total;

    //分页的数据
    @ApiModelProperty(value = "分页的数据列表")
    private List<?> rows;


    public PageResult(long total, List<?> rows) {
        super();
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.total = total;
        this.rows = rows;
    }

    /**
     * 直接返回分页数据
     * @param total 总条数
     * @param list  分页列表
     */
    public static Result pageResult(long total,List<?> list){
        return new PageResult(total,list);
    }
}
