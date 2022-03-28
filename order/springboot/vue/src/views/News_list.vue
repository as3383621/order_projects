<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
            </el-header>

            <el-main>
                <div v-if="News.length!==0">
                    <el-table :data="News" :header-cell-style="{ color: '#515a6e'}" >
                        <el-table-column label="作者" align="center" key="username" prop="username"  />
                        <el-table-column label="标题" align="center" key="title" prop="title"  />
                        <el-table-column label="创建时间" align="center" prop="time" width="160" />
                        <el-table-column
                            label="操作"
                            align="center"
                            width="160"
                            class-name="small-padding fixed-width"
                        >
                            <template slot-scope="scope">
                            <el-button
                                size="mini"
                                type="text"
                                icon="el-icon-edit"
                                @click="toPage(scope.row)"
                            >查看</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
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
                News:[],
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
                        console.log(res);
                        this.News = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$message.error(res.msg)
                    }

                })
            },
            toPage(row){
                this.$router.push(`/NewsMinute/?id=${row.id}`)
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
        line-height: 40px;
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

    .el-table__header tr,
        .el-table__header th {
            padding: 0;
            height: 40px;
    }
</style>