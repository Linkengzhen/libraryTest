<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">编辑图书</div>
    <el-form :inline="true" :model="form"  label-width="100px" :rules="rules" ref="ruleForm" >
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="form.bookName" placeholder="请输入图书名称"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input  style="width: 400px" type="textarea" v-model="form.description" placeholder="请输入图书描述"></el-input>
      </el-form-item>
      <el-form-item label="出版时间" prop="publishDate">
        <span class="出版时间"></span>
        <el-date-picker
            style="width: 220px"
            v-model="form.publishDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择出版时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="form.author" placeholder="请输入作者"></el-input>
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input v-model="form.publisher" placeholder="请输入出版社"></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-cascader
            style="width: 220px"
            :props="{value: 'name', label: 'name'}"
            v-model="form.categories"
            :options="categories"
            ></el-cascader>
      </el-form-item>
      <el-form-item label="标准码ISBN" prop="bookNo">
        <el-input v-model="form.bookNo" placeholder="请输入标准码ISBN"></el-input>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-input v-model="form.cover" placeholder="请输入封面链接"></el-input>
      </el-form-item>
      <el-form-item label="所需积分" prop="score">
        <el-input-number v-model="form.score" @change="handleChange" :min="10" :max="30" label="输入所需积分"></el-input-number>
    </el-form-item>
      <el-form-item label="图书数量" prop="bookNumber">
        <el-input-number v-model="form.bookNumber" @change="handleChange" :min="0" :max="100" label="输入图书数量"></el-input-number>
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
  name: "EditBook",
  data(){
    const checkNumber = (rule, value, callback) => {
      if(value===null||value===undefined){
        callback(new Error("请输入数字"))
      }
      if (!Number.isInteger(value)|| value<0) {
        callback(new Error('请输入不小于0的整数'));
      }
      callback()
    };
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      form: {},
      categories: [],
      rules: {
        bookName: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
        ],
        bookNo: [
          { required: true, message: '请输入图书标准码ISBN', trigger: 'blur' },
        ],
        score:[
          { required: true, message: '请输入图书所学积分', trigger: 'blur' },
          { validator: checkNumber,trigger: 'blur'}
        ],
        bookNumber:[
          { required: true, message: '请输入图书数量', trigger: 'blur' },
          { validator: checkNumber,trigger: 'blur'}
        ]
        }
    }
  },
  created() {
    request.get('/category/tree').then(res => {
      this.categories =res.data
    })
    const id=this.$route.query.id
    request.get("/book/"+ id).then(res => {
      this.form =res.data
      if(this.form.category) {
        this.form.categories = this.form.category.split(' > ')
        console.log(this.form.categories)
      }
    })
  },
  methods: {
    update(){
      this.$refs["ruleForm"].validate((valid) => {
        if (valid){
          request.put("book/update", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("更新成功")
              this.$router.push("/bookList")
            } else {
              this.$notify.error(res.msg)
            }
          })
      }
        else {
          this.$notify.error("输入信息有误")
        }
      })
    },
    handleChange(value) {
      console.log(value);
    }
  }
}
</script>