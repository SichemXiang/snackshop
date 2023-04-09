<template>
  <el-dialog
    model-value="dialogVisible"
    :title="dialogTitle"
    width="50%"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item label="商品名称" prop="goodsName">
        <el-input v-model="form.goodsName" style="width: 400px"/>
      </el-form-item>

      <el-form-item label="商品价格" prop="goodsPrice">
        <el-input v-model="form.goodsPrice" style="width: 100px"/>
      </el-form-item>

      <el-form-item label="商品库存" prop="goodsTotal">
        <el-input v-model="form.goodsTotal" style="width: 100px"/>
      </el-form-item>

      <el-form-item label="商品类别">
        <el-select v-model="categoryId"  @change="categoryChaege" placeholder="请选择商品类别...">
          <el-option
            v-for="item in categorySelectOptions"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="商品描述" prop="goodsInfo">
        <el-input v-model="form.goodsInfo" type="textarea" :rows="4"/>
      </el-form-item>



    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button  @click="handleClose">取消</el-button>
     <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>