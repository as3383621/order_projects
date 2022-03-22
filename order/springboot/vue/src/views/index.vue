<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <el-row style="height: 100%;" type="flex" justify="center" align="middle">
                    <el-col :span="3">
                        <div style="color: white; text-align: center; font-size: 24px;">
                            食堂点餐系统
                        </div>
                    </el-col>
                    <el-col :span="5">
                        <el-menu
                                :default-active="src"
                                mode="horizontal"
                                @select="handleSelect"
                                class="el-menu-demo"
                                background-color="#292828"
                                text-color="#fff"
                                active-text-color="#ffd04b"
                        >
                            <el-menu-item index="index">食堂首页</el-menu-item>
                            <el-menu-item index="NewsList">新闻公告</el-menu-item>
                        </el-menu>
                    </el-col>
                    <el-col :span="10">
                        <el-input autocomplete="off" style="width: 500px;" placeholder="输入菜品名称进行搜索" v-model="FoodName"></el-input>
                        <router-link :to="{path:'/select',query:{FoodName:FoodName}}"><el-button style="margin-left: 5px" type="primary">搜索</el-button></router-link>
                    </el-col>
                    <el-col :span="7">
                        <div v-if="user">
                            <span style="color: white">欢迎你：{{user.username}}&nbsp;&nbsp;</span>
                            <el-button size="mini" style="margin-left: 10px" @click="logout">退出</el-button>
                            <el-button size="mini" type="primary" @click="$router.push('/car')">购物车</el-button>
                            <el-button size="mini" type="warning" style="margin-left: 10px" @click="$router.push('/SelectShipped')">订单</el-button>
                        </div>
                        <div v-else>
                                <el-button size="mini" type="primary" @click="$router.push('/user/login')">登录</el-button>
                                <el-button size="mini" style="margin-left: 10px" @click="$router.push('/user/register')">注册</el-button>

                        </div>
                    </el-col>
                </el-row>
            </el-header>


            <el-main>
                <el-row type="flex" justify="center" :gutter="20">
                    <el-col :span="20">
                        <div>
                            <el-carousel  height="400px">
                                <el-carousel-item v-for="item in items" :key="item.id">
                                    <el-image :src="item.img" style="height: 350px;width: 1100px"></el-image>
                                </el-carousel-item>
                            </el-carousel>
                        </div>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20">
                    <el-col :span="20">
                        <div>
                            <span style="float: left;height: 100px;margin-top:-100px;">小炒</span>
                            <router-link :to="{path:'/more',query:{category:'小炒'}}"><span style="float: right;height: 100px;margin-top:-100px;cursor:pointer">更多<i class="el-icon-caret-right"></i></span></router-link>
                            <hr>
                                <el-form v-for="(food,i) in form1">
                                    <div style="height: 200px;width: 200px;float: left;margin-right: 10px;  ">
                                        <el-form-item>
                                            <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                            <p>菜名：{{food.foodName}}</p>
                                            <p>价钱：{{food.price}}元</p>
                                        </el-form-item>
                                    </div>
                                </el-form>
                        </div>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 140px">
                    <el-col :span="20">
                        <div>
                            <span style="float: left;height: 100px;margin-top:-100px;">面食</span>
                            <router-link :to="{path:'/more',query:{category:'面食'}}"><span style="float: right;height: 100px;margin-top:-100px;cursor:pointer">更多<i class="el-icon-caret-right"></i></span></router-link>
                            <hr>
                            <el-form v-for="(food,i) in form2">
                                <div style="height: 200px;width: 200px;float: left;margin-right: 10px;  ">
                                    <el-form-item>
                                        <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </div>
                            </el-form>
                        </div>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 140px">
                    <el-col :span="20">
                        <div>
                            <span style="float: left;height: 100px;margin-top:-100px;">甜品</span>
                            <router-link :to="{path:'/more',query:{category:'甜品'}}"><span style="float: right;height: 100px;margin-top:-100px;cursor:pointer">更多<i class="el-icon-caret-right"></i></span></router-link>
                            <hr>
                            <el-form v-for="(food,i) in form3">
                                <div style="height: 200px;width: 200px;float: left;margin-right: 10px; ">
                                    <el-form-item>
                                        <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </div>
                            </el-form>
                        </div>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 140px">
                    <el-col :span="20">
                        <div>
                            <span style="float: left;height: 100px;margin-top:-100px;">饮料</span>
                            <router-link :to="{path:'/more',query:{category:'甜品'}}"><span style="float: right;height: 100px;margin-top:-100px;cursor:pointer">更多<i class="el-icon-caret-right"></i></span></router-link>
                            <hr>
                            <el-form v-for="(food,i) in form4">
                                <div style="height: 200px;width: 200px;float: left;margin-right: 10px;  ">
                                    <el-form-item>
                                        <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </div>
                            </el-form>
                        </div>
                    </el-col>
                </el-row>
            </el-main>

        </el-container>
    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                items:[
                    {id:1,
                        img:require('../img/lun1.jpg')},
                    {id:2,
                        img:require('../img/lun2.jpg')},
                ],
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                src: 'index',
                FoodName:"",
                form1:{},
                form2:{},
                form3:{},
                form4:{},
            };
        },
        created() {
            //推荐食物
            this.findFood1("小炒");
            this.findFood2("面食");
            this.findFood3("甜点");
            this.findFood4("饮料");
        },
        methods: {
            findFood1(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form1 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood2(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form2 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood3(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form3 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood4(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form4 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            //搜索
            select() {
                this.$router.push({name:'select',params:{FoodName:this.FoodName}})
            },
            handleSelect(key) {
                location.href = key;
            },
            logout() {
                this.$router.push("/user/login")
                localStorage.removeItem("user")
                this.$message.success("退出成功")
            }
        },
    }
</script>





















<style>

    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
</style>