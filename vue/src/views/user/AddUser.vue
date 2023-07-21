<template>
<div >
  <div style="margin-bottom: 30px">新增用户</div>
  <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="100px" >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="name">
      <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-radio v-model="form.sex" label="男">男</el-radio>
      <el-radio v-model="form.sex" label="女">女</el-radio>
    </el-form-item>
    <el-form-item style="margin-left: 40px" label="联系方式" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item style="margin-left: 40px" label="邮箱" prop="email">
      <el-input v-model="form.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label-width="200px" label="借书积分(新建默认100)" prop="account">
      <el-input v-model="form.account" disabled></el-input>
    </el-form-item>
  </el-form>
  <div style="text-align: center;margin-top: 30px">
    <!-- 新增按钮 在触发点击事件后触发新增函数 其他删除，编辑等操作相同-->
    <el-button type="primary" @click="save" size="medium">提交</el-button>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "AddUser",
  data(){
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
        if (!/^[0-9]+$/.test(value)) {
          callback(new Error('请输入数字值'));
        }
        if(parseInt(value)>120||parseInt(value)<0) {
          callback(new Error('请输入合理的年龄'))
        }
          callback();
    };
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3，4，5，6，7，8，9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号码'));
      }
        callback();
    };
    const checkUserName = (rule, value, callback) => {
      if (!/^[A-Za-z0-9]+$/.test(value)) {
        callback(new Error('请输入只含数字与字母的字符串'));
      }
      callback();
    };
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      form: {sex:'男',account:100},
      rules: {
        username: [
            {required: true, message: '请输入只含字母和数字的字符串', trigger: 'blur'},
          {min:6,max:12,message: "最少6个字符，最多12个字符"} ,
          { validator: checkUserName, trigger: 'blur'}
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        age: [
          { validator: checkAge, trigger: 'blur'}
        ],
        phone:[
          { validator: checkPhone,trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    //新增信息方法
    save(){
      this.$refs["ruleForm"].validate((valid) => {
        if(valid) {
          request.post("user/save",this.form).then(res => {

            if(res.code==='200'){
              this.$notify.success("新增成功")
              this.form={sex:'男'}
              this.$router.push("/userList")//新增成功后跳转至信息页面
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
