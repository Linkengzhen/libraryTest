<template>
  <div >
    <!--头部区域-->
    <div style="height: 60px;line-height: 60px;background-color: azure;display: flex">
      <div style="width: 300px">
        <img src="@/assets/logo.png" alt="" style="width: 30px; position: relative;top: 10px;left: 20px">
        <span style="margin-left:30px; font-size: 25px; ">图书管理系统</span>
      </div>
      <div style="flex: 1;text-align:right;padding-right: 20px">
        <el-dropdown size="medium">
          <span class="el-dropdown-link">
            <!-- 显示当前读者信息 -->
            {{user.username}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
            <el-dropdown-item><div style="width: 90px;text-align: center;" @click="logout">退出</div></el-dropdown-item>
            <el-dropdown-item><div style="width: 90px;text-align: center;" @click="$router.push('/userEditUser?id=' + user.id)">修改个人信息</div></el-dropdown-item>
            <el-dropdown-item><div style="width: 90px;text-align: center;"@click="$router.push('/userChangePass?id=' + user.id)">修改密码</div></el-dropdown-item>
            <el-dropdown-item><div style="width: 90px;text-align: center;"@click="$router.push('/userAddAccount?id=' + user.id)">充值借书积分</div></el-dropdown-item>
            <el-dropdown-item><div style="width: 60px;align-items: center"><el-popconfirm
                style="margin-left: 5px"
                title="您确定注销该用户吗？"
                @confirm="del(user.id)"
            >
              <el-button style="align-items: center" type="danger" slot="reference">注销用户</el-button>
            </el-popconfirm></div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--侧边栏和主体-->
    <div style="display: flex">
      <!--导航栏导航-->
      <div style="width: 200px; min-height:calc(100vh - 60px);overflow: hidden;background-color: white">
        <el-menu :default-active="$route.path"  router class="el-menu-demo" style="margin-bottom: 10px" >
          <el-menu-item index="/userHome">
            <i class="el-icon-reading"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="/user">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>会员页面</span>
            </template>
            <el-menu-item index="/userUserList"><i class="el-icon-tickets"></i>会员列表</el-menu-item>
          </el-submenu>
          <el-submenu index="/category">
          <template slot="title">
            <i class="el-icon-s-operation"></i>
            <span>图书分类页面</span>
          </template>
          <el-menu-item index="/userCategoryList"><i class="el-icon-tickets"></i>图书分类列表</el-menu-item>
        </el-submenu>
          <el-submenu index="/book">
            <template slot="title">
              <i class="el-icon-reading"></i>
              <span>图书页面</span>
            </template>
            <el-menu-item index="/userBookList"><i class="el-icon-tickets"></i>图书列表</el-menu-item>
          </el-submenu>
          <el-submenu index="/Borrow">
            <template slot="title">
              <i class="el-icon-folder"></i>
              <span>借书页面</span>
            </template>
            <el-menu-item index="/userBorrowList"><i class="el-icon-tickets"></i>借书列表</el-menu-item>
            <el-menu-item index="/userAddBorrow"><i class="el-icon-folder-add"></i>借书添加</el-menu-item>
          </el-submenu>
          <el-submenu index="/returnBook">
            <template slot="title">
              <i class="el-icon-folder"></i>
              <span>还书页面</span>
            </template>
            <el-menu-item index="/userReturnBookList"><i class="el-icon-tickets"></i>还书列表</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>
      <!--主题数据-->
      <div style="flex: 1;background-color: whitesmoke;padding: 10px">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import request from "@/utils/request";
export default {
  name: "userLayout.vue",
  data(){
    return {
      //从前端Cookies中获取用户信息 cookies相当于前端浏览器页面的存储器
      user: Cookies.get("user")?JSON.parse(Cookies.get("user")):{},
    }
  },
  methods:{
    //退出方法
    logout(){
      this.$notify.success("退出成功")
      Cookies.remove('user')
      this.$router.push('/userLogin')//跳转页面
    },
    del(id){
      request.delete("/user/delete/" + id).then(res => {
        if(res.code ==='200'){
          this.$notify.success("删除成功")
          Cookies.remove('admin')//清空当前用户数据
          this.$router.push('/adminLogin')
        }
        else {
          this.$notify.error(msg)
        }
      })
    },

  }
}
</script>

<style scoped>

</style>