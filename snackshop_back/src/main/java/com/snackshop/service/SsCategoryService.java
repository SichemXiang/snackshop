package com.snackshop.service;

import com.snackshop.entity.SsCategory;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/16 6:16
 **/
public interface SsCategoryService {

    Result findPage(Queryinfo queryinfo);

    Result insert(SsCategory ssCategory);

    Result delete(int id);

    Result update(SsCategory ssCategory);

    List<SsCategory> findList();

    String findCategoryNameById(int id);

}
