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
  <el-table-column label="操作">
    <template v-slot="scope">
      <!--scope.row当前行数据-->
      <el-button type="warning" @click="handleAccountAdd(scope.row)">充值积分</el-button>
      <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>
      <el-popconfirm
          style="margin-left: 5px"
          title="您确定删除这行数据吗？"
          @confirm="del(scope.row.id)"
      >
        <el-button type="danger" slot="reference">删除</el-button>
      </el-popconfirm>
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
  <el-dialog title="充值借书积分" :visible.sync="dialogFormVisible" width="30%">
    <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules" style="width: 85%">
      <el-form-item label="当前账户积分" prop="account" >
        <el-input v-model="form.account" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="充值积分" prop="score" >
        <el-input v-model="form.score" autocomplete="off" ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="addAccount">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'User',
  data() {//初始数据集
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
    return {
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
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
      dialogFormVisible: false,
      rules: {
        score: [
          {required: true, message: '请输入积分', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ]
      }

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
    handleAccountAdd(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    del(id) {//通过id删除数据
      request.delete("/user/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success("删除成功")
          this.load()
        } else {
          this.$notify.error(msg)
        }
      })
    },
    addAccount() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/user/account", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("新增借书积分成功")
              this.dialogFormVisible=false;
              this.load()
            } else {
              this.$notify.error(msg)
            }
          })
        }
        else{
          this.$notify.error("输入信息有误")
        }
      })
    },changeStatus(row){
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