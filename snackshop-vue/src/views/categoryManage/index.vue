<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-button type="primary" :icon="Plus" @click="dialogVisible = true">添加分类</el-button>
<!--      添加的对话框-->
      <el-dialog
        v-model="dialogVisible"
        title="添加分类"
        width="30%"
        @close="insertHandleClose"
      >
        <el-form
          ref="insertFormRef"
          :model="insertForm"
          :rules="insertRules"
          label-width="100px"
        >
          <el-form-item label="分类名称" prop="categoryName">
            <el-input v-model="insertForm.categoryName" style="width: 480px"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="insertHandleClose">取消</el-button>
        <el-button type="primary" @click="insertConfirm">确定</el-button>
      </span>
        </template>
      </el-dialog>
    </el-row>
    <el-table :data="tableList"  style="width: 100%">
      <el-table-column type="index" label="序号" width="180" />
      <el-table-column prop="categoryName" label="名称"  />
      <el-table-column label="排序" >
        <template #default="scope">
          <el-row>
            <el-button  type="default" :icon="ArrowUp " size="small" @click="sortUp(scope.row,scope.$index)"/>
          </el-row>
          <el-row>
            <el-button  type="default" :icon="ArrowDown" size="small" @click="sortDown(scope.row,scope.$index)"/>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column  label="操作"  fixed="right">
        <template #default="scope">
          <el-button  type="primary" :icon="Edit" size="small" @click="updateDialog(scope.row)"/>
          <el-button  type="danger" :icon="Delete" size="small" @click="deleteById(scope.row.categoryId)"/>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
<!--  修改分类的对话框-->
  <el-dialog
    v-model="updateDialogVisible"
    title="修改分类"
    width="30%"
    @close="updateHandleClose"

  >
    <el-form
      ref="updateFormRef"
      :model="updateForm"
      :rules="updateRules"
      label-width="100px"
    >
      <el-form-item label="修改" prop="categoryName">
        <el-input v-model="updateForm.categoryName" style="width: 480px"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateHandleClose">取消</el-button>
        <el-button type="primary" @click="updateConfirm">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import {   Plus , Edit , Delete , ArrowUp , ArrowDown} from '@element-plus/icons-vue';
import axios from "@/util/axios";
import {ElMessage} from "element-plus";
import {ElMessageBox} from "element-plus";

const tableList = ref([])//整个表格的数据
// 添加分类绑定的model
const insertForm = ref({
  categoryName: '',
  categorySort: ''
})
// 修改分类绑定的model
const updateForm = ref({
  categoryId : -1,
  categoryName:'',
  categorySort: ''
})
const nowCategorySort=ref() //最新的categorySort的值
const insertFormRef=ref(null) //添加分类的表单
const updateFormRef = ref(null) //修改分类的表单
const dialogVisible = ref(false)
const updateDialogVisible=ref(false)
const maxIndex=ref(-1) //表格的最大index
// 加载页面时向后端发送的数据
const queryForm=ref({
  queryString: null,
  pageNumber: 1,
  pageSize: 200
})


const findPage = async()=>{
  const res = await axios.post("/category/",queryForm.value);
  tableList.value = res.data.rows;
  nowCategorySort.value = res.data.rows[res.data.total-1].categorySort;
  maxIndex.value = res.data.total-1;

}
findPage();

// 添加表单验证规格
const insertRules=ref({
  categoryName:[
    {
      required: true,
      message: '请输入分类名称！',
    }
  ]
})

// 修改表单验证规格
const updateRules=ref({
  categoryName:[
    {
      required: true,
      message: '请输入分类名称！',
    }
  ]
})


/**
 * 添加对话框的点击确定事件
 */
const insertConfirm=()=>{
  insertFormRef.value.validate(async (valid)=>{
    if(valid ){
      insertForm.value.categorySort = nowCategorySort.value+1
      await axios.post("/category/insert",insertForm.value).then((res)=>{
        ElMessage.success(res.data.message)
        insertFormRef.value.resetFields();
        findPage();
        insertHandleClose();
      })
    }
  })
}

/**
 * 根据categoryId删除分类
 */
const deleteById=(categoryId)=>{
  ElMessageBox.confirm(
    '您确定要删除此分类吗?',
    '系统提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async() => {
    await axios.deleteData(`/category/delete/${categoryId}`).then((res)=>{
      ElMessage.success(res.data.message);
      findPage();
    }).catch((error)=>{
      ElMessage.warning(error)
    })
  })

}

/**
 * 添加对话框的点击取消事件
 */
const insertHandleClose=()=>{
  dialogVisible.value = false
  insertFormRef.value.resetFields();
}

/**
 * 点击修改
 * 把当前行的数据赋值给updateForm
 * @param row
 */
const updateDialog=(row)=>{
  updateDialogVisible.value = true;
  updateForm.value.categoryName = row.categoryName;
  updateForm.value.categoryId = row.categoryId;
  updateForm.value.categorySort = row.categorySort;
}

/**
 * 修改对话框的点击确定事件
 */
const updateConfirm=()=>{
  updateFormRef.value.validate(async (valid)=>{
    if(valid ){

      await axios.post("/category/update",updateForm.value).then((res)=>{
        ElMessage.success(res.data.message)
        updateFormRef.value.resetFields(); //重置表单数据
        findPage();
        updateHandleClose();
      })
    }
  })
}

/**
 * 修改对话框的点击取消事件
 */
const updateHandleClose=()=>{
  updateDialogVisible.value = false;
  updateFormRef.value.resetFields();
}

/**
 * 上移 根据index交换当前行和上一行的categorySort
 * @param row 当前行的数据
 * @param index 当前行的index
 * @returns {Promise<void>}
 */
const sortUp=async (row,index)=>{
  if (index != 0){
    let temp = tableList.value[index].categorySort;
    tableList.value[index].categorySort = tableList.value[index-1].categorySort;
    tableList.value[index-1].categorySort = temp;
    await axios.post("/category/update",tableList.value[index]).then(async (res)=>{
      await axios.post("/category/update",tableList.value[index-1]).then((res)=>{
        findPage();
      })
    })

  }
}

/**
 * 下移 根据index交换当前行和下一行的categorySort
 * @param row 当前行的数据
 * @param index 当前行的index
 * @returns {Promise<void>}
 */
const sortDown=async (row,index)=>{
  // 判断谁否是最后一行
  if (index != maxIndex.value){
    let temp = tableList.value[index].categorySort;
    tableList.value[index].categorySort = tableList.value[index+1].categorySort;
    tableList.value[index+1].categorySort = temp;
    await axios.post("/category/update",tableList.value[index]).then(async (res)=>{
      await axios.post("/category/update",tableList.value[index+1]).then((res)=>{
        findPage();
      })
    })
  }
}


</script>

<style lang="scss" scoped>

.header{
  padding-bottom: 16px;
  box-sizing: border-box;

}
.dialog-footer button:first-child {
  margin-right: 10px;
}

</style>