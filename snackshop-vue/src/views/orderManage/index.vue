<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入姓名/订单号/电话号码" v-model="queryForm.queryString" clearable></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initOrderList">搜索</el-button>
    </el-row>
    <el-table :data="tableList" stripe style="width: 100%">
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="orderNum" label="订单号" width="280" />
      <el-table-column prop="totalPrice" label="总价"  width="80"/>
      <el-table-column prop="orderStatus" label="订单状态" width="100" :formatter="statusFormatter"/>
      <el-table-column prop="createTime" label="订单创建日期" width="200" />
      <el-table-column prop="payTime" label="订单支付日期" width="200" />
      <el-table-column prop="consignee" label="收货人" width="80" />
      <el-table-column prop="phoneNumber" label="联系电话" width="150" />
      <el-table-column prop="orderAddress" label="收货地址" width="400" />

      <el-table-column prop="action" label="操作" width="300" fixed="right">
        <template v-slot="scope">
          <el-button type="success" @click="handleDialogValue(scope.row.orderId)">详情</el-button>
          <el-button type="primary" @click="handleOrderStatus(scope.row.orderId,2)">发货</el-button>
          <el-button type="primary" @click="handleOrderStatus(scope.row.orderId,3)">退货</el-button>
          <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.orderId)"/>
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
  <Dialog v-model="dialogVisible"  :id="id"></Dialog>

</template>

<script setup>

import { Search , Delete} from '@element-plus/icons-vue';
import {ref} from "vue";
import axios from "@/util/axios";
import { ElMessage, ElMessageBox } from 'element-plus';
import Dialog from './components/dialog.vue';

const total = ref(0)
const tableList = ref([])
const dialogVisible=ref(false)
const id = ref(-1)

//搜索框内容
const queryForm=ref({
  queryString: null,
  pageNumber: 1,
  pageSize: 10
})

const initOrderList = async()=>{
  const res = await axios.post("/orderManage/findOrderList",queryForm.value);
  console.log(res)
  tableList.value = res.data.data.orderList;
  total.value = res.data.data.total;
}
initOrderList();



const statusFormatter=(row)=>{
  switch (row.orderStatus){
    case 1:
      return "待支付"
    case 2:
      return "待发货"
    case 3:
      return "退款/退货"
    case 4:
      return "运输中"
    case 5:
      return "已收货"
  }
}

const handleDialogValue=(orderId)=>{
  id.value = orderId;
  dialogVisible.value = true;
}


const handleOrderStatus=(orderId,orderStatus)=>{
  ElMessageBox.confirm(
    '您确定要更新该订单状态吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {
      let res=await axios.post('/orderManage/updateStatus',{orderId:orderId,orderStatus:orderStatus})
      if(res.data.flag===true){
        ElMessage({
          type: 'success',
          message: '执行成功',
        })
        await initOrderList();
      }else{
        ElMessage({
          type: 'error',
          message: res.data.message,
        })
      }
    })
    .catch(() => {
    })

}


const handleDelete=(orderId)=>{
  ElMessageBox.confirm(
    '您确定要删除这条记录吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {
      let res=await axios.deleteData('/orderManage/deleteOrder/'+orderId)
      if(res.data.flag===true){
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        await initOrderList();
      }else{
        ElMessage({
          type: 'error',
          message: res.data.message,
        })
      }

    })
    .catch(() => {

    })
}


//改变页面大小
const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNumber=1;
  queryForm.value.pageSize=pageSize;
  initOrderList();
}


//跳转指定页面
const handleCurrentChange=(pageNumber)=>{
  queryForm.value.pageNumber=pageNumber;
  initOrderList();
}

</script>

<style lang="scss" scoped>

</style>