<template>
  <el-dialog
    model-value="imageDialogVisible"
    title="商品图片更换"
    width="30%"
    @close="handleClose"
    center
  >
    <el-form
      ref="formRef"
      :model="form"
      label-width="100px"
      style="text-align: center"
    >
      <el-upload
        :headers="headers"
        class="avatar-uploader"
        :action="getServerUrl()+'goods/uploadImage'"
        :show-file-list="false"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
     <el-button type="primary" @click="handleConfirm">确认更换</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>

import {defineEmits, defineProps, ref, watch} from "vue";
import axios,{getServerUrl} from "@/util/axios";
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const tableData=ref([])



const props=defineProps(
  {
    imageDialogValue:{
      type:Object,
      default:()=>{},
      required:true
    }
  }
)

const headers=ref({
  token:window.sessionStorage.getItem("token")
})

const form=ref({
  goodsId:-1,
  image:''
})

const formRef=ref(null)

const imageUrl=ref("")

watch(
  // 图片回显
  ()=>props.imageDialogValue,
  ()=>{
    form.value=props.imageDialogValue;
    imageUrl.value=getServerUrl()+'image/'+form.value.image
  },
  // 深度监测
  {deep:true,immediate:true}
)


const emits=defineEmits(['update:modelValue','findGoodsPage'])

const handleClose=()=>{
  emits('update:modelValue',false)
}

const handleSuccess=(res)=>{
  imageUrl.value=getServerUrl()+res.data.src
  form.value.image=res.data.title;
}


const beforeUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    ElMessage.error('图片必须是jpg格式')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2M!')
  }
  return isJPG && isLt2M
}

const handleConfirm=async()=>{

  let result=await axios.post("/goods/update",form.value);
  let data=result.data;
  console.log(result)
  if (data.flag == true) {
    ElMessage.success("执行成功！");
    formRef.value.resetFields();
    emits("findGoodsPage");
    handleClose();

  } else {
    ElMessage.error(data.message);
  }

}

</script>

<style>

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>