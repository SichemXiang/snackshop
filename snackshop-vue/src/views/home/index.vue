<template>
  <div>
    <el-menu
      mode="horizontal"
      active-text-color="#409EFF"
      class="menuContainer"
      default-active="salesStatistic"
      router
      >
      <el-menu-item index="salesStatistic">商品销量历史统计</el-menu-item>
      <el-menu-item index="orderStatistic">订单历史统计</el-menu-item>

    </el-menu>
    <el-main>
      <router-view/>
    </el-main>
  </div>
</template>


<script setup>

</script>

<style lang="scss" scoped>

.menuContainer{
  margin-top: -30px;
  margin-right: -15px;
  margin-left: -15px;
}


</style>