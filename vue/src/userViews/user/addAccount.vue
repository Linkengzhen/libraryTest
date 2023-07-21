<template>
  <div >
    <div style="margin-bottom: 30px">充值借书积分</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" style="width: 80%" label-width="100px" >
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="当前账户积分" prop="account" >
        <el-input v-model="form.account" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="充值积分" prop="score" >
        <el-input v-model="form.score" autocomplete="off" ></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="addAccount" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "addAccount",
  data(){
    const checkNumber = (rule, value, callback) => {
      value = parseInt(value)
      if (value === null || value === undefined) {
        callback(new Error("请输入数字"))
      }
      if (value < 10 || value > 200) {
        callback(new Error('请输入大于10，小于于200的整数'));
      }
      callback()
    };
    return{
      form: {},
      rules: {
        score: [
          {required: true, message: '请输入积分', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/user/"+ id).then(res => {
      this.form =res.data
    })
  },
  methods: {
    addAccount() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/user/account", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("新增借书积分成功")
              this.$router.push("/userUserList")
            } else {
              this.$notify.error(msg)
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