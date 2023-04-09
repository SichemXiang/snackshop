<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar shape="square" :size="40" :src="squareUrl" />
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="logout">安全退出</el-dropdown-item>
        </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import {ref} from "vue";
const squareUrl=ref("http://www.java1234.com/gg/avatar.jpg")
import { useStore } from "vuex";
import { ElMessage, ElMessageBox } from "element-plus";
const store = useStore();
import axiosUtil from '@/util/axios'

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