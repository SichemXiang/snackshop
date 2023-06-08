package com.snackshop.mapper;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsCategory;


import java.util.List;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/16 6:04
 **/
public interface SsCategoryMapper {
    /**
     * 分页查询
     * @param queryString
     * @return page
     */
    Page<SsCategory> findPage(String queryString);

    /**
     * 增加
     */
    void insert(SsCategory ssCategory);

    /**
     * 删除
     */
    void delete(int id);

    /**
     * 修改
     */
    void update(SsCategory ssCategory);

    /**
     * 查询全部分类
     */
    List<SsCategory> findList();

    String findCategoryNameById(int categoryId);





}
