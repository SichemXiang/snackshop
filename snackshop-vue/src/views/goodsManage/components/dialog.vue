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

<script setup>
import {defineEmits, defineProps, ref, watch} from "vue";
import axios from "@/util/axios";
import { ElMessage } from 'element-plus'

const props=defineProps(
  {
    id:{
      type:Number,
      default:-1,
      required:true
    },
    dialogTitle:{
      type:String,
      default:'',
      required:true
    },
    dialogVisible:{
      type:Boolean,
      default:false,
      required:true
    }
  }
)

const form=ref({
  goodsId:-1,
  goodsName:'',
  goodsPrice:null,
  goodsTotal:null,
  categoryId: null,
  goodsInfo:'',
  goodsSales:null
})

const categoryId=ref("")

//选择类别的列表
const categorySelectOptions=ref([]);
const queryForm=ref({
  queryString: null,
  pageNumber: 1,
  pageSize: 20
})



//表单验证规则
const rules=ref({
  goodsName:[
    {
      required: true,
      message: '请输入商品名称！',
    }
  ],
  goodsPrice:[
    { required: true, message: '请输入商品价格!' },
    { type: 'number', message: '商品价格是数值类型！',transform: (value) => Number(value) },
  ],
  goodsTotal:[
    { required: true, message: '请输入商品库存!' },
    { type: 'number', message: '商品库存是数值类型！',transform: (value) => Number(value) },
  ],
  goodsInfo:[
    {
      required: true,
      message: '请输入商品描述！',
    },
  ],
})

const formRef=ref(null)

/**
 * 点击修改时，返回未修改前的数据
 * @param id
 * @returns {Promise<void>}
 */
const initFormData=async(id)=>{
  const res=await axios.get(`/goods/${id}`);
  console.log(res);
  form.value.goodsId = res.data.data.goodsId;
  form.value.goodsName = res.data.data.goodsName;
  form.value.goodsPrice = res.data.data.goodsPrice;
  form.value.goodsTotal = res.data.data.goodsTotal;
  form.value.categoryId = res.data.data.categoryId;
  form.value.goodsInfo = res.data.data.goodsInfo;
  form.value.goodsSales = res.data.data.goodsSales;

}


//定义事件，向后端发送请求，返回category信息，并把信息赋值给categorySelectOptions
const initCategorySelectList=async()=>{
  const res = await axios.post("/category/",queryForm.value);
  categorySelectOptions.value = res.data.rows

}

initCategorySelectList();

const  categoryChaege=(cId)=>{
  form.value.categoryId = cId;
}

watch(
  ()=>props.dialogVisible,
  ()=>{
    let id=props.id;
    /**
     * id!=-1,说明有id，是修改信息
     * 否则是添加信息，把值全部置为空
     */
    if(id!=-1){
      console.log("查询后端")
      initFormData(id)
    }else{
      form.value={
        goodsId:-1,
        goodsName:'',
        goodsPrice:null,
        goodsTotal:null,
        categoryId:null,
        goodsInfo:''
      }
    }
  }
)

//调用子组件的方法
const emits=defineEmits(['update:modelValue','findPage'])

//关闭dialog
const handleClose=()=>{
  emits('update:modelValue',false)
}

/**
 * 点击确定
 * 校验表单
 * 判断，如果form.value.categoryId存在，说明是修改信息，否则是添加信息
 */
const handleConfirm=()=>{

  formRef.value.validate(async(valid)=>{
    if(valid){
      if(form.value.categoryId==" "){
        ElMessage.error("请选择商品类别");
        return;
      }
      if(form.value.goodsId == null || form.value.goodsId == -1){
        form.value.categoryId == categorySelectOptions.value.categoryId;
        await axios.post("/goods/insert",form.value).then((res)=>{
          ElMessage.success("执行成功！")
          formRef.value.resetFields();
          emits("findPage")
          handleClose();
        })
      }else {
        await axios.post("/goods/update",form.value).then((res)=>{
          ElMessage.success("执行成功！")
          console.log(form.value)
          formRef.value.resetFields();
          emits("findPage")
          handleClose();
        })
      }
    }else{
      console.log("fail")
    }
  })
}

</script>

<style scoped>

</style>