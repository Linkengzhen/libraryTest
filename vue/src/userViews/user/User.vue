<template>
<div>
  <div style="margin-bottom: 20px">
    <!-- v-model绑定的是输入框的data（）中form的数据，用于条件查询的变量 -->
    <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
  </div>
  <!-- prop是将前端显示的数据绑定后端相同名称的数据-->
  <el-table
    :data="tableData"
    style="width: 100%"
    stripe row-key="id">
  <el-table-column type="expand">
    <template slot-scope="props">
      <el-form label-position="left" inline class="demo-table-expand">
        <el-form-item label="年龄">
          <span>{{ props.row.age }}</span>
        </el-form-item>
        <el-form-item label="地址">
          <span>{{ props.row.address }}</span>
        </el-form-item>
        <el-form-item label="性别">
          <span>{{ props.row.sex }}</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ props.row.email }}</span>
        </el-form-item>
        <el-form-item label="借书余额">
          <span>{{ props.row.account }}</span>
        </el-form-item>
        <el-form-item label="创建时间">
          <span>{{ props.row.createtime }}</span>
        </el-form-item>
        <el-form-item label="更新时间">
          <span>{{ props.row.updatetime }}</span>
        </el-form-item>
      </el-form>
    </template>
  </el-table-column>
  <el-table-column prop="id" label="编号" width="80"></el-table-column>
  <el-table-column prop="username" label="用户名"></el-table-column>
  <el-table-column prop="name" label="姓名"></el-table-column>
  <el-table-column prop="phone" label="联系方式"></el-table-column>
    <el-table-column  label="状态" width="250px">
      <template v-slot="scope">
        <el-switch
            disabled
            v-model="scope.row.status"
            @change="changeStatus(scope.row)"
            active-color="#13ce66"
            inactive-color="#ff4949">
        </el-switch>
      </template>
    </el-table-column>
</el-table>
  <!--分页布局-->
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
  name: 'User',
  data() {//初始数据集
    return {
      user: Cookies.get('user')?JSON.parse(Cookies.get('user')):{},
      tableData: [],
      total: 0,
      account: 100,
      form: {},
      params: {
        name: '',
        phone: '',
        pageNum: 1,
        pageSize: 10
      },
    }
  },
  created() {
    this.load();//进入页面加载数据
  },
  methods: {
    load() {//加载页面
      request.get('/user/page', {//从后端接口获取数据
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {//重置数据
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
   changeStatus(row){
      request.put("/user/update", row).then(res => {
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
<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 100px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-left:10px;
  margin-bottom: 5px;
  width: 200%;
}
</style>