<template>
  <div class="app">
    <el-card class="box-card">
      <el-form
        class="passwordForm"
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="150px"
      >
        <el-form-item label="电话号码" prop="userName">
          <el-input v-model="form.phoneNumber" disabled/>
        </el-form-item>
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" type="password"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" type="password"/>
        </el-form-item>
        <el-form-item label="确认新密码" prop="newPassword2">
          <el-input v-model="form.newPassword2" type="password"/>
        </el-form-item>
        <div class="submitButton">
          <el-form-item>
            <el-button type="primary" @click="onSubmit" >确认</el-button>
          </el-form-item>
        </div>
      </el-form>
    </el-card>
  </div>

</template>

<script setup>
import { ref } from 'vue'
import {ElMessage} from 'element-plus'
import axios from '@/util/axios'

const form=ref({
  phoneNumber:"",
  oldPassword:"",
  newPassword:"",
  newPassword2:""
})

const rules=ref({
  phoneNumber:[
    { required: true, message: '请输入电话号码'}
  ],
  oldPassword:[
    { required: true, message: '请输入原密码',trigger: 'blur'},
    { min: 6, max: 15, message: '密码位数只能在6~15之间', trigger: 'blur' }
  ],
  newPassword:[
    { required: true, message: '请输入新密码',trigger: 'blur'},
    { min: 6, max: 15, message: '密码位数只能在6~15之间', trigger: 'blur' }
  ],
  newPassword2:[
    { required: true, message: '请输入确认新密码',trigger: 'blur'},
    { min: 6, max: 15, message: '密码位数只能在6~15之间', trigger: 'blur' }
  ]
})

const formRef=ref(null)

const initFormData=()=>{
  let phoneNumber=window.sessionStorage.getItem("phone");
  form.value.phoneNumber=phoneNumber;
}

initFormData();

const onSubmit=()=>{
  formRef.value.validate(async(valid)=>{
    if(valid){
     if(form.value.newPassword!==form.value.newPassword2){
        ElMessage.error("两次密码不相同！");
      }else{
        try{
          let result=await axios.post("/admin/modifyPassword",form.value);
          console.log(result)
          let data=result.data;
          if(data.flag===true){
            ElMessage.success("密码修改成功，请重新登录后生效")
            formRef.value.resetFields();
          }else{
            ElMessage.error(data.message);
          }
        }catch(err){
          console.log(err)
          ElMessage.error("系统运行出错，请联系管理员");
         }
      }

    }else{
      console.log("fail")
    }
  })
}

</script>

<style lang="scss" scoped>

.app {
  display:flex;
  justify-content: center;
  align-items: center;
  margin-top: 130px;
}

.passwordForm{
  margin-right: 90px;
  margin-top: 40px;
}

.submitButton{
  margin-left: 90px;
}

.el-input{
  width: 300px;
}
</style>