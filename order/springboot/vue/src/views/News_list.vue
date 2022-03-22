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
                <div v-if="News.length!==0">
                    <el-form v-for="(News,i) in News">
                        <el-form-item>
                            <span>{{News.username}}</span>
                            <router-link :to="{path:'/NewsMinute',query:{id:News.id}}">
                                <span>{{News.title}}</span>
                            </router-link>
                            <span>{{News.time}}</span>
                        </el-form-item>
                    </el-form>
                </div>
                <div v-else>
                    <span>当前没有人发布新闻公告。</span>
                </div>
                <div style="padding: 10px 0">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="pageNum"
                            :page-sizes="[5, 10, 15, 20]"
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
        name: "News_list",
        data() {
            return {
                src: 'NewsList',
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                FoodName:"",
                pageNum: 1,
                pageSize: 5,
                total: 0,
                News:{},
            };
        },
        created() {
            this.load();
        },
        methods: {
            load() {
                fetch("http://localhost:9090/news/list?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.News = res.data.records
                        this.total = res.data.total
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
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load()
            },
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