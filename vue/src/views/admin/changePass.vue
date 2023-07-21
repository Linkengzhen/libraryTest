<template>
  <div >
    <div style="margin-bottom: 30px">修改密码</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="formRef" style="width: 80%" label-width="100px" >
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPass">
        <el-input v-model="form.newPass" show-password placeholder="新密码"></el-input>
      </el-form-item>
      <el-form-item label="再次输入新密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" show-password placeholder="确认新密码"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="savePass" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "changePass",
  data(){
    return{
      form: {},
      rules: {
        newPass:[
          {required: true, message:"请输入新密码",trigger: "blur"},
          {min:6,max:12,message: "新密码长度为6~12个字符之间",trigger: "blur"},
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 6, max: 12, message: '确认密码长度为6~12个字符之间', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/admin/"+ id).then(res => {
      this.form =res.data
    })
  },
  methods: {
    savePass(){//修改密码
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          if (this.form.newPass !== this.form.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          request.put('/admin/password', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("修改成功")
              Cookies.remove('admin')//清空当前用户数据
              this.$router.push('/adminLogin')
            }//跳转页面
               else {
                this.$notify.error("修改失败")
              }
          })
    }
  })
},
  }
}
</script>