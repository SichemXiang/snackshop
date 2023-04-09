<template>
  <el-menu
    active-text-color="#ffd04b"
    background-color="#2d3a4b"
    class="el-menu-vertical-demo"
    default-active="home"
    text-color="#fff"
    router
  >
    <el-menu-item index="home">
      <el-icon><home-filled /></el-icon>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="userManage">
      <el-icon><user /></el-icon>
      <span>用户管理</span>
    </el-menu-item>
    <el-menu-item index="categoryManage">
      <el-icon><tickets /></el-icon>
      <span>分类管理</span>
    </el-menu-item>
    <el-menu-item index="goodsManage">
      <el-icon><tickets /></el-icon>
      <span>商品管理</span>
    </el-menu-item>
    <el-menu-item index="orderManage">
      <el-icon><tickets /></el-icon>
      <span>订单管理</span>
    </el-menu-item>
    <el-sub-menu index="11">
      <template #title >
        <el-icon><management /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="modifyPassword">
        <el-icon><edit /></el-icon>
        <span>修改密码</span>
      </el-menu-item>
      <el-menu-item >
        <el-icon @click="logout"><switch-button /></el-icon>
        <span @click="logout">安全退出</span>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>
<script setup>
import
{HomeFilled,User,Tickets,Goods,DocumentAdd,Management,Setting,Edit,SwitchButton}
  from '@element-plus/icons-vue'
import { useStore } from "vuex";
import { ElMessage } from "element-plus";
import {ElMessageBox} from "element-plus";
import axiosUtil from "@/util/axios";

const store = useStore();

const logout=()=>{
  ElMessageBox.confirm(
    '确定退出？',
    '警告',
    { confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning', }
  )
    .then(() => {
      //调用后端接口
      axiosUtil .get('/user/logout').then((res)=>{
        store.dispatch('frontLogout');
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消退出',
      })
    })
}





</script>


<style lang="scss" scoped>

</style>

