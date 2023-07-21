import Vue from 'vue'
import VueRouter from 'vue-router'
import adminLayout from '../views/adminLayout.vue'
import userLayout from '../userViews/userLayout.vue'
import Cookies from "js-cookie"

Vue.use(VueRouter)

const routes = [
    //=====登录页面======
  {
    path:'/adminLogin',
    name: 'AdminLogin',
    component: () => import('@/views/login/AdminLogin.vue'),
  },
  //=====注册页面======
  {
    path:'/adminRegister',
    name: 'AdminRegister',
    component: () => import('@/views/register/AdminRegister.vue'),
  },
    //======管理员主页面======//
  {
    path: '/Admin',
    name: 'adminLayout',
    component: adminLayout,
    //redirect:'/',
    children: [
      {
        path: '/home', name: 'Home', component: () => import('@/views/home/HomeView.vue')
      },
        //===== user页面路由 =====
      {
        path: '/userList', name: 'UserList', component: () => import('@/views/user/User.vue'),
      },
      {
        path: '/addUser', name: 'AddUser', component: () => import('@/views/user/AddUser.vue'),
      },
      {
        path: '/editUser', name: 'EditUser', component: () => import('@/views/user/EditUser.vue'),
      },
      //=======admin页面路由=======
      {
        path: '/adminList', name: 'AdminList', component: () => import('@/views/admin/Admin.vue'),
      },
      {
        path: '/addAdmin', name: 'AddAdmin', component: () => import('@/views/admin/AddAdmin.vue'),
      },
      {
        path: '/editAdmin', name: 'EditAdmin', component: () => import('@/views/admin/EditAdmin.vue'),
      },
      {
        path: '/changePass', name: 'changePass', component: () => import('@/views/admin/changePass.vue'),
      },
      //=======category页面路由=======
      {
        path: '/categoryList', name: 'CategoryList', component: () => import('@/views/category/Category.vue'),
      },
      {
        path: '/addCategory', name: 'AddCategory', component: () => import('@/views/category/AddCategory.vue'),
      },
      {
        path: '/editCategory', name: 'EditCategory', component: () => import('@/views/category/EditCategory.vue'),
      },
      //=======book页面路由=======
      {
        path: '/bookList', name: 'BookList', component: () => import('@/views/book/Book.vue'),
      },
      {
        path: '/addBook', name: 'AddBook', component: () => import('@/views/book/AddBook.vue'),
      },
      {
        path: '/editBook', name: 'EditBook', component: () => import('@/views/book/EditBook.vue'),
      },
      //=======borrow页面路由=======
      {
        path: '/borrowList', name: 'BorrowList', component: () => import('@/views/borrow/Borrow.vue'),
      },
      {
        path: '/addBorrow', name: 'AddBorrow', component: () => import('@/views/borrow/AddBorrow.vue'),
      },
      {
        path: '/editBorrow', name: 'EditBorrow', component: () => import('@/views/borrow/EditBorrow.vue'),
      },
      //=======returnBook页面路由=======
      {
        path: '/returnBookList', name: 'ReturnBookList', component: () => import('@/views/returnBook/ReturnBook.vue'),
      },]
  },
  {
    path:'/userLogin',
    name: 'UserLogin',
    component: () => import('@/userViews/login/UserLogin.vue'),
  },
  {
    path:'/userRegister',
    name: 'UserRegister',
    component: () => import('@/userViews/register/UserRegister.vue'),
  },
  //======读者主页面======//
  {
    path: '/User',
    name: 'userLayout',
    component: userLayout,
    //redirect:'/userHome',
    children: [
      {
        path: '/userHome', name: 'userHome', component: () => import('@/userViews/home/HomeView.vue')
      },
      //===== user页面路由 =====
      {
        path: '/userUserList', name: 'userUserList', component: () => import('@/userViews/user/User.vue'),
      },
      {
        path: '/userEditUser', name: 'userEditUser', component: () => import('@/userViews/user/EditUser.vue'),
      },
      {
        path: '/userChangePass', name: 'userChangePass', component: () => import('@/userViews/user/changePass.vue'),
      },
      {
        path: '/userAddAccount', name: 'userAddAccount', component: () => import('@/userViews/user/addAccount.vue'),
      },
      //=======category页面路由=======
      {
        path: '/userCategoryList', name: 'userCategoryList', component: () => import('@/userViews/category/Category.vue'),
      },
      //=======book页面路由=======
      {
        path: '/userBookList', name: 'userBookList', component: () => import('@/userViews/book/Book.vue'),
      },
      //=======borrow页面路由=======
      {
        path: '/userBorrowList', name: 'userBorrowList', component: () => import('@/userViews/borrow/Borrow.vue'),
      },
      {
        path: '/userAddBorrow', name: 'userAddBorrow', component: () => import('@/userViews/borrow/AddBorrow.vue'),
      },
      //=======returnBook页面路由=======
      {
        path: '/userReturnBookList', name: 'userReturnBookList', component: () => import('@/userViews/returnBook/ReturnBook.vue'),
      },]
  },
  {
    path:"*",
    component: ()=> import("@/404")
  }
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) => {
  if(to.path==='/adminLogin'||to.path==='/userLogin') next()
  const admin=Cookies.get("admin")//获取Cookies中的用户信息
  const user=Cookies.get("user")
  let person;
  if(admin){
    person=admin
  }
  else if(user){
    person=user
  }
  if(!person && to.path!=='/adminLogin'&& to.path!=='/adminRegister'&& to.path!=='/userRegister'&& to.path!=='/userLogin')return next('/adminLogin')//强制返回至登陆页面
  next()
})


//
export default router
