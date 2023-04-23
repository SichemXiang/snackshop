<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入用户昵称/电话号码" v-model="queryForm.queryString" clearable></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />
      <el-table-column prop="wxNickName" label="用户昵称" width="200" />
      <el-table-column prop="wxAvatarUrl" label="头像" width="200">
        <template v-slot="scope">
          <img :src="scope.row.wxAvatarUrl" width="50" height="50">
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话号码" />
      <el-table-column prop="openId" label="openID" />
      <el-table-column prop="registerTime" label="注册日期" width="200"/>
      <el-table-column prop="lastLoginTime" label="最后登录日期" width="200"/>
    </el-table>
    <el-pagination
      v-model:currentPage="queryForm.pageNumber"
      v-model:page-size="queryForm.pageSize"
      :page-sizes="[10, 20, 30, 40,50]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </el-card>
</template>

<script setup>
import { Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from "@/util/axios";

const queryForm=ref({
  queryString:'',
  pageNumber:1,
  pageSize:10
})

const total=ref(0)

const tableData = ref([])

const initUserList=async()=>{
  const res=await axios.post("/userManage/findPage",queryForm.value)
  tableData.value = res.data.rows;
  total.value=res.data.total;
  console.log("========")
  console.log(res)
}


initUserList();

const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initUserList();
}

const handleCurrentChange=(pageNumber)=>{
  queryForm.value.pageNum=pageNumber;
  initUserList();
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
}



</style>