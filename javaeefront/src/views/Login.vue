

<template>
<div class="login">

  <div class="login-box" style="border-radius: 5px">
    <div class="login-form">
      <div style="margin-bottom: 30px ;font-size: 20px; color: #7f859a;font-weight: bold; width: 400px" >欢迎登录后台管理系统</div>
      <el-form :model="data.form" ref="formRef" :rules="data.rules" style="width: 380px;margin-left: 10px ">
        <el-form-item prop="username">
          <el-input v-model="data.form.username" size="large" placeholder="请输入账号" prefix-icon="User" ></el-input>
        </el-form-item >
        <el-form-item prop="password" >
          <el-input v-model="data.form.password" size="large" placeholder="请输入密码" prefix-icon="Lock" show-password></el-input>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%" size="large" placeholder="登录" prefix-icon="Lock" @click="login" >登录</el-button>
        </el-form-item>
      </el-form>
      <div>
        <router-link to="/register"> <!-- 替换为你的目标路由路径 -->
          <el-button type="text">注册</el-button>
        </router-link>

      </div>
    </div>

  </div>
</div>
</template>

<style scoped>
.login {
  overflow: hidden;
  height: 100vh;
  background-image: url("https://crmebgw.oss-cn-hangzhou.aliyuncs.com/2d6c5202211241751576563.png");
  text-align: center;
  background-size: 100%;

}
.login-box {
  position: absolute;
  width: 50vh;
  height: 30vh;

  display: flex;

  padding-top: 300px;
  padding-left: 1200px;

}
.login-form {
  background-color: #f4f4f4;
  border-radius: 10px;

}
</style>
<script setup>
import {reactive,ref} from "vue";
import {User} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
const data = reactive({
 form:{
   username: '',
   password: '',
 },
  rules:{
    username: [
      {required: true,message: '请输入用户名',trigger: 'blur'},
      {min: 3,max: 10,message: '长度在3-5个字符',trigger: 'blur'}
    ],
    password: [
      {required: true,message: '请输入密码',trigger: 'blur'},
      {min: 3,max: 10,message: '长度在3-5个字符',trigger: 'blur'}
    ]
  }
})
const  formRef = ref(null)
const login=()=>{
  formRef.value.validate(valid=>{
    if(valid){
      request.post("/common/login",data.form).then(res=>{
        if(res.code==="200"){
          localStorage.removeItem('nowUser')
          localStorage.setItem("nowUser",JSON.stringify(data.form.username))

          ElMessage.success("登录成功")
          location.href = '/home'
        }else{
          ElMessage.error("用户名或密码错误")
        }
      })

    }
  })
}
</script>