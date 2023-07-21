<template>
<div class="wrapper">
  <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 350px; padding: 20px; border-radius: 10px;position: relative">
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :accuracy="5"
                    :imgs="['https://cdn.pixabay.com/photo/2022/11/09/12/23/lotus-7580478_960_720.jpg',
                    'https://cdn.pixabay.com/photo/2017/08/29/12/07/adult-2693054_960_720.jpg',
                    'https://cdn.pixabay.com/photo/2022/11/16/15/52/mushrooms-7596258_960_720.jpg']"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
    </el-card>
    <div style="margin: 10px 0; text-align: center; font-size: 24px"><b>图书管理系统</b></div>
    <div style="margin: 10px 0; text-align: center; font-size:16px"><b>管理员登录</b></div>
    <form>
    <el-form :model="admin" :rules="rules" ref="loginForm">
      <el-form-item prop="username" >
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="admin.username"></el-input>
      </el-form-item >
      <el-form-item prop="password">
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="admin.password"></el-input>
      </el-form-item>
      <el-form-item style="margin: 10px 0; text-align: center">
        <el-radio v-model="radio" label="1" >管理员</el-radio>
        <el-radio v-model="radio" label="2" @input="$router.push('/userLogin')">读者</el-radio>
      </el-form-item>
      <el-form-item style="margin: 20px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/adminRegister')">注册</el-button>
      </el-form-item>
    </el-form>
    </form>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'
export default {
  name: "adminLogin",
  data(){
    return{
      loginAdmin:{},
      admin :{},
      radio :'1',
      rules:{
        username: [
          {required :true,message: "请输入用户名",trigger:'blur'},
          {min:6,max:12,message: "长度在6~12个字符之间"}
        ],
        password: [
          {required :true,message: "请输入密码",trigger:'blur'},
          {min:6,max:12,message: "长度在6~12个字符之间"}
        ]
      }
    }
  },
  methods: {
    login(){
      this.$refs["loginForm"].validate((valid) => {
        if(valid){
          request.post('/login/adminLogin',this.admin).then(res => {
            if(res.code==='200'){
              this.loginAdmin=res.data//滑块组件
            }
            else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    onSuccess() {
      Cookies.set('admin', JSON.stringify(this.loginAdmin))
      this.$notify.success("登录成功")
      this.$router.push('/home')
    },
    onFail() {
      console.log('onFail')
    },
    onRefresh() {
      console.log('refresh')
    }
  }
}
</script>
<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>
