<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
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
            </el-main>
            <el-footer>
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
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import navBar from './components/NavBar.vue'
    export default {
        name: "News_list",
        components:{
            navBar
        },
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
    
    .el-footer{
        background: #E9EEF3;
    }
</style>