package com.snackshop.controller;

import com.snackshop.entity.SsGoods;
import com.snackshop.service.SsGoodsService;
import com.snackshop.util.DateUtil;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: xsz
 * @Description: 商品数据控制层
 * @DateTime: 2023/3/14 1:40
 **/
@RestController
@Api(tags = "商品管理")
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private SsGoodsService goodsService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody Queryinfo queryinfo){
        return goodsService.findPage(queryinfo);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/insert")
    public Result insert(@RequestBody SsGoods ssGoods){
        return goodsService.insert(ssGoods);
    }

    @ApiOperation(value = "修改商品信息")
    @PostMapping("/update")
    public Result update(@RequestBody SsGoods ssGoods){
        return goodsService.update(ssGoods);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/delete/{id}")
    public Result delete( @PathVariable(value = "id") int id){
        return goodsService.delete(id);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable(value = "id") int id){
        return goodsService.findById(id);
    }


    @PostMapping ("/uploadImage")
    public Map<String,Object> uploadImage(MultipartFile file) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        if(!file.isEmpty()){
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    new File("C://Users/1972454587/Desktop/finally/code/images/goodsImages/"+newFileName));
            resultMap.put("flag",true);
            resultMap.put("message","上传成功！");
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

    /**
     * 设置热卖
     * @param id 商品id
     * @param hot 是否热卖
     * @return result
     */
    @PostMapping("/updateHot/{id}/hot/{hot}")
    public Result updateHot(@PathVariable(value = "id")int id,@PathVariable(value = "hot") boolean hot){
        return goodsService.updateHot(id,hot);
    }

    @PostMapping("/updateSwiper/{id}/swiper/{swiper}")
    public Result updateSwiper(@PathVariable(value = "id")int id,@PathVariable(value = "swiper")boolean swiper){
        return goodsService.updateSwiper(id,swiper);
    }

    @GetMapping("/defaultHot")
    public Result defaultHot(){
        goodsService.changeHotToDefault();
        return Result.success("ok");
    }

}
