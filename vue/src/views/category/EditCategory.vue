<template>
  <div >
    <div style="margin-bottom: 30px">编辑分类</div>
    <el-form :inline="true" :model="form"  style="width: 80%" label-width="100px" :rules="rules" ref="ruleForm" >
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输备注"></el-input>
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
  name: "EditCategory",
  data(){
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      form: {},
      rules: {
          name: [
            { required: true, message: '请输入图书名称', trigger: 'blur' },
          ],
        bookNo: [
            { required: true, message: '请输入图书标准码', trigger: 'blur'}
    ]
        }
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/category/"+ id).then(res => {
      this.form =res.data
    })
  },
  methods: {
    update(){
      this.$refs["ruleForm"].validate((valid) => {
        if (valid){
          request.put("category/update", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("更新成功")
              this.$router.push("/categoryList")
            } else {
              this.$notify.error(res.msg)
            }
          })
      }
        else {
          this.$notify.error("输入信息有误")
        }
      })
    }
  }
}
</script>