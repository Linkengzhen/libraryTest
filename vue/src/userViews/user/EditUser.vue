<template>
  <div >
    <div style="margin-bottom: 30px">编辑用户</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleFrom"  style="width: 80%" label-width="100px" >
      <el-form-item label="会员号">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
      </el-form-item>
      <el-form-item style="margin-left: 30px" label="联系方式">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item style="margin-left: 30px" label="邮箱">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="update" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "EditUser",
  data() {
    //检查年龄的逻辑方法
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      if (!/^[0-9]+$/.test(value)) {
        callback(new Error('请输入数字值'));
      }
      if (parseInt(value) > 120 || parseInt(value) < 0) {
        callback(new Error('请输入合理的年龄'))
      }
      callback();
    };
    //检查电话的逻辑方法
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3，4，5，6，7，8，9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号码'));
      }
      callback();
    };
    return {
      user: Cookies.get('user')?JSON.parse(Cookies.get('user')):{},
      form: {},
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        age: [
          {validator: checkAge, trigger: 'blur'}//调用年龄逻辑方法
        ],
        phone: [
          {validator: checkPhone, trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/user/" + id).then(res => {
      //获取当前Id的信息，res从后端接口中获取数据,通过赋值给this.form实现数据从后端传入前端
      this.form = res.data
    })
  },
  methods: {
    update() {
     this.$refs["ruleFrom"].validate((valid)=> {//逻辑检查器方法，elementUI上有具体使用方法
       if(valid){
         request.put("/user/update", this.form).then(res => {//后端更新接口
           if (res.code === '200') {
             this.$notify.success("更新成功")
             this.$router.push("/userUserList")//更新完成跳转页面
           } else {
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