<template>
  <div style="margin-bottom: 20px">
  <h1 style="text-align: center;color: deepskyblue">新品图书推荐</h1>
  <div class="index">
    <!-- 跑马灯  -->
    <div>
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  name: 'index',
  data(){
    return {
      imgList: [
        {id:0,idView:require("@/assets/book1.jpg")},
        {id:1,idView:require("@/assets/book2.jpg")},
        {id:2,idView:require("@/assets/book3.jpg")},
        {id:3,idView:require("@/assets/book4.jpg")},
        {id:3,idView:require("@/assets/book5.jpg")}
      ]
    };
  },
  methods: {
  }
}
</script>
<style>
.index{
  background: #c8cfd8;
  width: 80%;
  text-align: center;
  margin-left: 142px;
  width: 80.6%;

}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 400px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
