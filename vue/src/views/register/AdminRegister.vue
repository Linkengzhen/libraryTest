<template>
  <div class="wrapper">
    <div style="margin: 100px auto; background-color: #fff; width: 350px; height: 500px; padding: 20px; border-radius: 10px">
      <div style="margin: 10px 0; text-align: center; font-size: 24px"><b>图书管理系统</b></div>
      <div style="margin: 10px 0; text-align: center; font-size:16px"><b>管理员注册</b></div>
      <el-form :model="admin" :rules="rules" ref="adminForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="admin.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="admin.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input placeholder="请输入联系方式" size="medium" style="margin: 5px 0" prefix-icon="el-icon-phone" v-model="admin.phone"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input placeholder="请输入邮箱" size="medium" style="margin: 5px 0" prefix-icon="el-icon-message" v-model="admin.email"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: center">
          <el-radio v-model="radio" label="1" >管理员</el-radio>
          <el-radio v-model="radio" label="2" @input="$router.push('/userRegister')">读者</el-radio>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="register">注册</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/adminLogin')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'
export default {
  name: "adminRegister",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3，4，5，6，7，8，9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号码格式'));
      }
      callback();
    };
    const checkEmail = (rule, value, callback) => {
      if (!/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)) {
        callback(new Error('请输入合法的邮箱格式'));
      }
      callback();
    };
    return {
      admin: {},
      radio:'1',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur'}
        ],
        phone:[
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone,trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['adminForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.admin.password !== this.admin.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          request.post("/admin/save", this.admin).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功")
              this.$router.push("/adminLogin")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
        else{
          this.$notify.error("输入信息有误")
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);
  overflow: hidden;
}
</style>
