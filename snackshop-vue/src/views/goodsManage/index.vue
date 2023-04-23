<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入商品名称" v-model="queryForm.queryString" clearable></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="findGoodsPage">搜索</el-button>
      <el-button type="primary" :icon="Plus" @click="handleDialog()">添加</el-button>
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

      <el-table-column prop="hot" label="热卖"  align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.goodsHot" @change="hotChangeHandle(scope.row)"></el-switch>
        </template>
      </el-table-column>

      <el-table-column prop="swiper" label="轮播图"  align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.goodsSwiper" @change="swiperChangeHandle(scope.row)"></el-switch>
        </template>
      </el-table-column>



      <el-table-column  label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button type="success" size="small" @click="handleImageDialogValue(scope.row)">更换图片</el-button>
          <el-button  type="primary" :icon="Edit" size="small" @click="handleDialog(scope.row.goodsId)"/>
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
  <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :id="id" :dialogTitle="dialogTitle" @findGoodsPage="findGoodsPage"></Dialog>
  <imageDialog v-model="imageDialogVisible" :imageDialogValue="imageDialogValue" @findGoodsPage="findGoodsPage"></imageDialog>

</template>

<script setup>

import { ref } from "vue";
import { Search , Plus , Edit , Delete} from '@element-plus/icons-vue';
import axios ,{getServerUrl}from "@/util/axios";
import {ElMessage} from "element-plus";
import {ElMessageBox} from "element-plus";
// noinspection ES6UnusedImports
import imageDialog from "@/views/goodsManage/components/imageDialog.vue";
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
const imageDialogVisible=ref(false)
const id=ref(-1)
const imageDialogValue = ref({})





// 页面加载时分页查询
const findGoodsPage = async()=>{
  const res = await axios.post("/goods/findPage",queryForm.value);
  tableList.value = res.data.rows;
  total.value = res.data.total;
}
findGoodsPage();


const hotChangeHandle=async (row)=>{
  let res=await axios.post("/goods/updateHot/"+row.goodsId+"/hot/"+row.goodsHot);
  if(res.data.flag===true){
    ElMessage({
      type: 'success',
      message: '执行成功!'
    })
  }else{
    ElMessage({
      type: 'error',
      message: res.data.message,
    })
     await findGoodsPage();
  }
}


const swiperChangeHandle=async (row)=>{
  let res=await axios.post("/goods/updateSwiper/"+row.goodsId+"/swiper/"+row.goodsSwiper);
  if(res.data.flag===true){
    ElMessage({
      type: 'success',
      message: '执行成功!'
    })
  }else{
    ElMessage({
      type: 'error',
      message: res.data.message,
    })
    await findGoodsPage();
  }
}


const handleDialog=(goodsId)=>{
  if(goodsId){
    id.value=goodsId;
    dialogTitle.value="商品修改"
  }else{
    id.value=-1;
    dialogTitle.value="商品添加"
  }
  dialogVisible.value=true
}

const handleImageDialogValue=(row)=>{
  console.log("===============")
  imageDialogVisible.value = true;
  imageDialogValue.value=JSON.parse(JSON.stringify(row));
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
        findGoodsPage();
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
  findGoodsPage();
}


//跳转指定页面
const handleCurrentChange=(pageNumber)=>{
  queryForm.value.pageNumber=pageNumber;
  findGoodsPage();
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