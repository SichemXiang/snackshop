<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入商品名称" v-model="queryForm.queryString" clearable></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="findPage">搜索</el-button>
      <el-button type="primary" :icon="Plus" @click="hadleDialog()">添加</el-button>
    </el-row>
    <el-table :data="tableList" stripe style="width: 100%">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column prop="goodsName" label="名称" width="100" />
      <el-table-column prop="categoryId" label="分类id" width="100" />
      <el-table-column prop="image" label="图片" width="100" align="center">
        <template #default="scope">
          <img :src="getServerUrl()+'image/'+scope.row.image" width="80" height="80"/>
        </template>
      </el-table-column>
      <el-table-column prop="goodsInfo" label="信息"  width="200"/>
      <el-table-column prop="goodsPrice" label="价格" width="100" />
      <el-table-column prop="goodsTotal" label="库存" width="100" />
      <el-table-column prop="goodsSales" label="销量" width="100" />
      <el-table-column  label="操作"  fixed="right">
        <template #default="scope">
          <el-button  type="primary" :icon="Edit" size="small" @click="hadleDialog(scope.row.goodsId)"/>
          <el-button  type="danger" :icon="Delete" size="small" @click="deleteById(scope.row.goodsId)"/>
        </template>
      </el-table-column>

    </el-table>
    <el-pagination
      v-model:current-page="queryForm.pageNumber"
      v-model:page-size="queryForm.pageSize"
      :page-sizes="[10, 20, 30, 40,50]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </el-card>
  <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :id="id" :dialogTitle="dialogTitle" @findPage="findPage"></Dialog>

</template>

<script setup>

import { ref } from "vue";
import { Search , Plus , Edit , Delete} from '@element-plus/icons-vue';
import axios ,{getServerUrl}from "@/util/axios";
import {ElMessage} from "element-plus";
import {ElMessageBox} from "element-plus";
import Dialog from "@/views/goodsManage/components/dialog.vue";

//搜索框内容
const queryForm=ref({
  queryString: null,
  pageNumber: 1,
  pageSize: 10
})
const squareUrl=ref("http://www.java1234.com/gg/avatar.jpg")
const total = ref(0)
const tableList = ref([])
const dialogVisible=ref(false)
const dialogTitle=ref('')
const id=ref(-1)

// 页面加载时分页查询
const findPage = async()=>{
  const res = await axios.post("/goods/findPage",queryForm.value);
  tableList.value = res.data.rows;
  total.value = res.data.total;
}
findPage();



const hadleDialog=(goodsId)=>{
  if(goodsId){
    id.value=goodsId;
    dialogTitle.value="商品修改"
  }else{
    id.value=-1;
    dialogTitle.value="商品添加"
  }
  dialogVisible.value=true
}

/**
 * 删除事件
 * @param goodsId
 */
const deleteById=(goodsId)=>{
  ElMessageBox.confirm(
    '您确定要删除这件商品吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async() => {
      await axios.deleteData(`/goods/delete/${goodsId}`).then((res)=>{
        ElMessage.success(res.data.message);
        queryForm.value.pageNumber = 1;
        findPage();
      }).catch((error)=>{
        ElMessage.warning(error)
      })
    })
    .catch(() => {
    })
}


//改变页面大小
const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNumber=1;
  queryForm.value.pageSize=pageSize;
  findPage();
}


//跳转指定页面
const handleCurrentChange=(pageNumber)=>{
  queryForm.value.pageNumber=pageNumber;
  findPage();
}

</script>

<style lang="scss" scoped>

.header{
  padding-bottom: 16px;
    box-sizing: border-box;

}

.el-pagination{
  padding-top: 15px;
  box-sizing: border-box;
  text-align: right;
}

</style>