<template>
<div >
  <div style="margin-bottom: 30px">新增管理员</div>
  <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="100px" >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入手机号码"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
    </el-form-item>
  </el-form>
  <div style="text-align: center;margin-top: 30px">
    <el-button type="primary" @click="save" size="medium">提交</el-button>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddUser",
  data(){
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
    }
    return{
      form: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {min:6,max:12,message: "最少6个字符，最多12个字符"}
        ],
        email: [
          { validator: checkEmail, trigger: 'blur'}
        ],
        phone:[
          { validator: checkPhone,trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    save(){
      this.$refs["ruleForm"].validate((valid) => {
        if(valid) {
          request.post("admin/save",this.form).then(res => {
            if(res.code==='200'){
              this.$notify.success("新增成功")
              this.$router.push("/adminList")
            }
            else{
              this.$notify.error(res.msg)
            }
          })
        }
        else{
          this.$notify.error("输入信息有误")
        }
      })
    },
  }
}
</script>
