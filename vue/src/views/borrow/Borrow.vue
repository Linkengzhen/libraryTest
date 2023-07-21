<template>
<div>
  <!--搜素表单-->
  <div style="margin-bottom: 20px">
    <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.bookName"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入图书标准码ISBN" v-model="params.bookNo"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入借书者用户名" v-model="params.userName"></el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
  </div>
  <el-table :data="tableData" stripe row-key="id">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="借书者联系方式">
            <span>{{ props.row.userPhone }}</span>
          </el-form-item>
          <el-form-item label="所需积分">
            <span>{{ props.row.score}}</span>
          </el-form-item>
          <el-form-item label="借出日期">
            <span>{{ props.row.createtime }}</span>
          </el-form-item>
          <el-form-item label="借出天数">
            <span>{{ props.row.days }}</span>
          </el-form-item>
          <el-form-item label="归还日期">
            <span>{{ props.row.returnDate }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column prop="id" label="编号" width="80"></el-table-column>
    <el-table-column prop="bookName" label="图书名称"></el-table-column>
    <el-table-column prop="bookNo" label="图书标准码ISBN"></el-table-column>
    <el-table-column prop="userName" label="借书者用户名"></el-table-column>
    <el-table-column prop="name" label="借书者姓名"></el-table-column>
    <el-table-column prop="status" label="借出状态"></el-table-column>
    <el-table-column prop="note" label="归还提醒">
    <template v-slot="scope">
      <el-tag type="success" v-if="scope.row.note==='正常'">{{scope.row.note}}</el-tag>
      <el-tag type="primary" v-if="scope.row.note==='即将到期'">{{scope.row.note}}</el-tag>
      <el-tag type="warning" v-if="scope.row.note==='已到期'">{{scope.row.note}}</el-tag>
      <el-tag type="danger" v-if="scope.row.note==='已过期'">{{scope.row.note}}</el-tag>
    </template>
    </el-table-column>
    <el-table-column label="管理">
      <template v-slot="scope">
        <el-popconfirm
            style="margin-left: 5px"
            title="您确定归还图书吗？"
            @confirm="returnBook(scope.row)"
        >
          <el-button v-if="scope.row.status==='已借出'" type="primary" slot="reference">还书</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>

    <el-table-column label="操作" >
      <template v-slot="scope">
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
  name: 'Borrow',
  data(){
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      tableData: [],
      total: 0,
      params: {
        name: '',
        bookNo: '',
        pageNum:1,
        pageSize:10
      },
      rules: {
        name: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get('/borrow/page',{
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
        bookName:'',
        bookNo:'',
        userName:'',
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id){
      request.delete("/borrow/delete/" + id).then(res => {
        if(res.code ==='200'){
          this.$notify.success("删除成功")
          this.load()
        }
        else {
          this.$notify.error(msg)
        }
      })
    },
    returnBook(row){
      request.post("/borrow/ReturnBookSave",row).then(res => {
        if(res.code ==='200'){
          this.$notify.success("还书成功")
          this.load()
        }
        else {
          this.$notify.error(msg)
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
  width: 110px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-left:10px;
  margin-bottom: 5px;
  width: 200%;
}
</style>
