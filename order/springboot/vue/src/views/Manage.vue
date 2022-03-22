<template>
  <div style="height: 100%">
    <el-container style="height: 100%;">

      <!-- 侧边栏 -->
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); height: 100%">
       <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
      </el-aside>

      <el-container>
        <!-- 头部 -->
        <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <!-- 内容 -->
        <el-main>
          <!-- 当前页面的子路由会在<router-view>展示 -->
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>

  </div>
</template>

<script>

  import Aside from "../components/Aside";
  import Header from "../components/Header";

export default {
  name: 'Home',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,

    }
  },
  components: {
    Aside,
    Header
  },
  methods: {
    //侧边栏收缩
    collapse() {  //点击收缩触发按钮
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) { //缩
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.sideWidth = 64
        this.logoTextShow = false
      } else { //展开
        this.collapseBtnClass = 'el-icon-s-fold'
        this.sideWidth = 200
        this.logoTextShow = true
      }
    },
  }
}
</script>

