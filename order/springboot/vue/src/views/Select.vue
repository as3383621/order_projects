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
                <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
                    <el-table-column prop="url" label="菜品图片" width="140" align="center">
                        <router-link slot-scope="scope" :to="{path:'/minute',query:{id:scope.row.id}}">
                            <el-image
                                  style="width: 100px; height: 100px"
                                  :src="scope.row.url"
                                  fit="fit">
                            </el-image>
                        </router-link>
                    </el-table-column>
                    <el-table-column prop="foodName" label="菜品名称" align="center">
                        <router-link slot-scope="scope" :to="{path:'/minute',query:{id:scope.row.id}}">
                            <span v-text="scope.row.foodName"></span>
                        </router-link>
                    </el-table-column>
                    <el-table-column prop="category" label="菜品分类" align="center"></el-table-column>
                    <el-table-column prop="price" label="菜品价格" align="center">
                        <span slot-scope="scope"><span v-text="scope.row.price"></span>元</span>
                    </el-table-column>
                    <el-table-column prop="stock" label="库存" align="center"></el-table-column>
                    <el-table-column label="操作" width="170" align="center">
                        <el-button slot-scope="scope" type="success" @click="InsertCar(scope.row.id)">加入购物车<i class="el-icon-s-goods"></i></el-button>
                    </el-table-column>
                </el-table>
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
        name: "Select",
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null,
                FoodName:"",
                pageNum: 1,
                pageSize: 5,
                total: 0,
                tableData:[],
                number:1,
            };
        },
        created() {
            let name=this.$route.query.FoodName
            this.FoodName=name
            this.select()
        },

        methods: {
            //搜索
            select() {
                fetch("http://localhost:9090/food/select?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&FoodName=" + this.FoodName)
                    .then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.tableData = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            InsertCar(fid) {
                fetch("http://localhost:9090/car/insert?username=" + this.user.username + "&number=" + this.number + "&fid=" + fid, {
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("成功加入购物车")
                    } else {
                        this.$router.push("/user/login")
                        this.$message.error(res.msg)
                    }
                })
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
                this.select()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.select()
            },
        }
    }
</script>
























<style>
    <!-- 表单头部样式 -->
    .headerBg {
        background: #eee!important;
    }
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