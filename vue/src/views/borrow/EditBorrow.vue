<template>
  <div>
  <div style="margin-bottom: 30px">编辑借书</div>
  <el-form :inline="true" :model="form"  style="width: 80%" label-width="100px" :rules="rules" ref="ruleForm">
    <div>
      <el-form-item label="图书姓名" prop="bookName">
        <el-select v-model="form.bookName" clearable filterable placeholder="请选择" @change="selBook">
          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookName"
              :value="item.bookName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书标准码" prop="bookNo">
        <el-input  v-model="form.bookNo" disabled ></el-input>
      </el-form-item>
      <el-form-item label="所需分数" prop="score">
        <el-input v-model="form.score" disabled ></el-input>
      </el-form-item>
      <el-form-item label="图书数量" prop="bookNumber">
        <el-input v-model="form.bookNumber" disabled ></el-input>
      </el-form-item>
    </div>
    <div>
      <el-form-item label="用户名" prop="userName">
        <el-select v-model="form.userName" filterable placeholder="请选择" @change="selUser">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.username">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-input v-model="form.name" disabled ></el-input>
      </el-form-item>
      <el-form-item label="用户联系方式" prop="userPhone">
        <el-input v-model="form.userPhone" disabled ></el-input>
      </el-form-item>
      <el-form-item style="margin-left: 30px" label="用户借书余额" prop="account">
        <el-input v-model="form.account" disabled ></el-input>
      </el-form-item>
      <el-form-item label="借书天数" prop="days">
        <el-input-number v-model="form.days" @change="handleChange" :min="1" :max="30" ></el-input-number>
      </el-form-item>
    </div>
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
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      form: {},
      categories: [],
      books: [],
      users:[],
      rules: {
        bookName: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
        ],
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ]
        }
    }
  },
  created() {
    {
      request.get('/book/list').then(res => {
        this.books = res.data;
      })

      request.get('/user/list').then(res => {
        this.users = res.data.filter(v => v.status);
      })
    }
    const id=this.$route.query.id
    request.get("/borrow/"+ id).then(res => {
      this.form =res.data
    })
  },
  methods: {
    update() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("borrow/update", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("新增成功")
              //this.$router.push("/borrowList")
              this.$refs["ruleForm"].resetFields()
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$notify.error("输入信息有误")
        }
      })
    },
    selBook() {
      const book = this.books.find(v => v.bookName === this.form.bookName)
      request.get('/book/' + book.id).then(res => {
        this.$set(this.form, 'bookNo', res.data.bookNo)
        //this.form.bookNo=book.bookNo
        this.form.score = res.data.score
        this.$set(this.form, 'bookNumber', res.data.bookNumber)
        //this.form.bookNumber = res.data.bookNumber
      })
    },
    selUser() {
      const user = this.users.find(v => v.username === this.form.userName)
      request.get('/user/' + user.id).then(res => {
        //this.form.name=user.name
        this.$set(this.form, 'name', res.data.name)
        this.form.userPhone = res.data.phone
        this.form.account = res.data.account;
      })
    },handleChange(value) {
  console.log(value);
}
  }
}
</script>