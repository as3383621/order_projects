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
                                :default-active=null
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
                <div v-if="form.length!==0">
                    <el-form v-for="(food,i) in form">
                        <div style="height: 200px;width: 200px;float: left;margin-right: 10px; ">
                            <el-form-item>
                                <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                <p>菜名：{{food.foodName}}</p>
                                <p>价钱：{{food.price}}元</p>
                            </el-form-item>
                        </div>
                    </el-form>
                </div>
                <div v-else>
                    <span>没有更多菜品了！</span>
                </div>
                <div style="padding: 10px 0">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="pageNum"
                            :page-sizes="[20]"
                            :page-size="pageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="total">
                    </el-pagination>
                </div>
            </el-main>



        </el-container>
    </div>
</template>

<script>
    export default {
        name: "More",
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null,
                FoodName:"",
                pageNum: 1,
                pageSize: 20,
                total: 0,
                tableData:[],
                form:{},
                category:null,
            };
        },
        created() {
            let category=this.$route.query.category
            this.category=category
            this.load()
        },

        methods: {
            load() {
                fetch("http://localhost:9090/food/more?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&category=" + this.category,{
                    headers: {
                        token: this.token,
                        author: "user"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$message.error(res.msg)
                    }

                })
            },
            logout() {
                this.$router.push("/user/login")
                localStorage.removeItem("user")
                this.$message.success("退出成功")
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load()
            },
            handleSelect(key) {
                location.href = key;
            },
        }
    }
</script>

<style scoped>

</style>