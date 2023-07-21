<template>
<div>
  <!--搜素表单-->
  <div style="margin-bottom: 20px">
    <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入邮箱" v-model="params.email"></el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
  </div>
  <el-table :data="tableData" stripe>
    <el-table-column prop="id" label="编号" width="80"></el-table-column>
    <el-table-column prop="username" label="用户名"></el-table-column>
    <el-table-column prop="phone" label="联系方式"></el-table-column>
    <el-table-column prop="email" label="邮箱"></el-table-column>
    <el-table-column prop="createtime" label="创建时间"></el-table-column>
    <el-table-column prop="updatetime" label="更新时间"></el-table-column>
    <!--
    <el-table-column label="状态" width="250px">
      <template v-slot="scope">
        <el-switch
            v-model="scope.row.status"
            @change="changeStatus(scope.row)"
            active-color="#13ce66"
            inactive-color="#ff4949">
        </el-switch>
      </template>
    </el-table-column>
    -->
  </el-table>
  <div style="margin-top: 20px">
    <el-pagination
        background
        :current-page="params.pageNum"
        :page-size="params.pageSize"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total">
    </el-pagination>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";
export default {
  name: 'Admin',
  data(){
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      tableData: [],
      total: 0,
      form: {},
      dialogFormVisible: false,
      params: {
        username: '',
        phone: '',
        email: '',
        pageNum:1,
        pageSize:10
      },
      rules: {
        newPass:[
          {required: true, message:"请输入新密码",trigger: "blur"},
          {min:6,max:12,message: "新密码长度为6~12个字符之间",trigger: "blur"},
        ]
  }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get('/admin/page',{
        params: this.params
      }).then(res=> {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset(){
      this.params={
        pageNum: 1,
        pageSize: 10,
        username:'',
        phone: '',
        email: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    changeStatus(row){
      if(this.admin.id===row.id&&!row.status){
        row.status=true
        this.$notify.warning("您的操作不合法");
        return
      }
      request.put("/admin/update", row).then(res => {
        if (res.code === '200') {
          this.$notify.success("操作成功")
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
