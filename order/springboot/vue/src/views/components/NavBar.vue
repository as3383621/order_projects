<template>
  <div class="main">
    <el-row  type="flex" justify="center" align="middle" >
        <el-col :md="3" :lg="3">
            <router-link :to="{ name: 'index' }">
                <div> 食堂点餐系统 </div>
            </router-link>
        </el-col>
        <el-col :md="5" :lg="5">
            <el-menu
                    :default-active="src"
                    mode="horizontal"
                    @select="handleSelect"
                    class="el-menu-demo"
                    background-color="#292828"
                    text-color="#fff"
                    active-text-color="#ffd04b"
            >
                <el-menu-item index="/index">食堂首页</el-menu-item>
                <el-menu-item index="/NewsList">新闻公告</el-menu-item>
            </el-menu>
        </el-col>
        <el-col :md="8" :lg="10">
            <div class="layout">
                <el-input autocomplete="off"  placeholder="输入菜品名称进行搜索" v-model="FoodName"></el-input>
                <router-link :to="{path:'/select',query:{FoodName:FoodName}}">
                    <el-button type="primary" icon="el-icon-search" size="mini"  class="searchButton">搜索</el-button>
                </router-link>
            </div>
        </el-col>
        <el-col :md="8" :lg="6" :offset="2">
            <div v-if="user" class="layout">
                <div style="margin-right:10px">
                    <span>欢迎你：{{user.username}}</span>
                </div>
                <div>
                    <el-button size="mini" @click="logout">退出</el-button>
                    <el-button size="mini" type="primary" @click="$router.push('/car')">购物车</el-button>
                    <el-button size="mini" type="warning"  @click="$router.push('/SelectShipped')">订单</el-button>
                </div>
            </div>
            <div v-else>
                    <el-button size="mini" type="primary" @click="$router.push('/user/login')">登录</el-button>
                    <el-button size="mini" style="margin-left: 10px" @click="$router.push('/user/register')">注册</el-button>
            </div>
        </el-col> 
    </el-row>
  </div>
</template>

<script>
export default {
  name: '',
  data() {
    return {
        src: 'index',
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
        FoodName: ''
    };
  },
  created(){
      this.src = this.$route.path
  },
  methods: {
    handleSelect(key) {
        console.log(key);
        location.href = key;
    },
    logout() {
        this.$router.push("/user/login")
        localStorage.removeItem("user")
        this.$message.success("退出成功")
    },
    select() {
        this.$router.push({name:'select',params:{FoodName:this.FoodName}})
    },
},
  mounted(){
},
};
</script>

<style scoped>
.main{
    width: 100%;
    color: #fff;
}

a{
    color: #fff;
    text-decoration: none;
}


.searchButton{
    margin-left: 10px;
}

.layout{
    display: flex;
    align-items: center;
}
</style>
