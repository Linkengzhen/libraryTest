<template>
<div>
  <!--搜素表单-->
  <div style="margin-bottom: 20px">
    <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.bookName"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入图书标准码ISBN" v-model="params.bookNo"></el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
  </div>
      <el-table
          :data="tableData"
          style="width: 100%"
          stripe row-key="id">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="描述">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="出版日期">
                <span>{{ props.row.publishDate }}</span>
              </el-form-item>
              <el-form-item label="作者">
                <span>{{ props.row.author }}</span>
              </el-form-item>
              <el-form-item label="出版社">
                <span>{{ props.row.publisher }}</span>
              </el-form-item>
              <el-form-item label="分类">
                <span>{{ props.row.category }}</span>
              </el-form-item>
              <el-form-item label="借书积分">
                <span>{{ props.row.score }}</span>
              </el-form-item>
              <el-form-item label="图书数量">
                <span>{{ props.row.bookNumber }}</span>
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
        <el-table-column
            style="margin-left: 10px"
            label="编号"
            prop="id">
        </el-table-column>
        <el-table-column
            style="margin-left: 10px;"
            label="书本名称"
            prop="bookName">
        </el-table-column>
        <el-table-column
            style="margin-left: 10px;"
            label="标准码ISBN"
            prop="bookNo">
        </el-table-column>
        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]" style="width: 50%"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300px">
          <template v-slot="scope">
            <!--scope.row当前行数据-->
            <el-button type="primary" @click="$router.push('/editBook?id=' + scope.row.id)">编辑</el-button>
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
  name: 'Book',
  data(){
    return{
      admin: Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      tableData: [],
      total: 0,
      params: {
        bookName: '',
        bookNo: '',
        pageNum:1,
        pageSize:10
      },
      rules: {
        bookName: [
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
      request.get('/book/page',{
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
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id){
      request.delete("/book/delete/" + id).then(res => {
        if(res.code ==='200'){
          this.$notify.success("删除成功")
          this.load()
        }
        else {
          this.$notify.error(msg)
        }
      })
    },
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
