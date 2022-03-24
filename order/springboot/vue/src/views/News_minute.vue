<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
            </el-header>

            <el-main>
                <el-collapse v-model="activeNames">
                    <el-collapse-item :title="News.title" name="1">
                        <div>{{News.content}}</div>
                    </el-collapse-item>
                </el-collapse>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import navBar from './components/NavBar.vue'
    export default {
        name: "News_minute",
        components:{
            navBar
        },
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                FoodName:"",
                News:{},
                id:"",
                activeNames: ['1']
            };
        },
        created() {
            let id=this.$route.query.id
            this.id=id
            this.minute();
        },
        methods: {
            minute() {
                fetch("http://localhost:9090/news/minute?id=" + this.id)
                    .then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.News = res.data
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





















543566
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