<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
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
            </el-main>

            <el-footer>
                <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="pageNum"
                            :page-sizes="[5, 10, 15, 20]"
                            :page-size="pageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="total">
                    </el-pagination>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
import navBar from './components/NavBar.vue'
    export default {
        name: "Select",
        components: {
            navBar
        },
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

    .el-footer{
        background: #e9eef3;
    }

    a{
        text-decoration: none;
    }
</style>