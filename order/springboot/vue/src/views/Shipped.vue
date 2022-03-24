<template>
    <div style="height: 100vh">
        <el-container style="height: 10vh%">
            <el-header style="background: #292828">
               <navBar></navBar>
            </el-header>

            <el-main>
                <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
                    <el-table-column prop="url" label="菜品图片" width="140" align="center">
                            <el-image slot-scope="scope"
                                    style="width: 100px; height: 100px"
                                    :src="scope.row.url"
                                    fit="fit">
                            </el-image>
                    </el-table-column>
                    <el-table-column prop="foodName" label="菜品名称" align="center"></el-table-column>
                    <el-table-column prop="price" label="单价" align="center">
                        <span slot-scope="scope"><span v-text="scope.row.price"></span>元</span>
                    </el-table-column>
                    <el-table-column prop="number" label="数量" align="center"></el-table-column>
                    <el-table-column prop="sum" label="总计" align="center">
                        <span slot-scope="scope"><span v-text="scope.row.price*scope.row.number"></span>元</span>
                    </el-table-column>
                    <el-table-column prop="time" label="下单时间" align="center"></el-table-column>
                    <el-table-column prop="state" label="订单状态" align="center"></el-table-column>
                    <el-table-column label="操作" width="170" align="center">
                        <template slot-scope="scope">
                            <div v-if="scope.row.state==='待出餐'">

                            </div>
                            <div v-else>
                                <div v-if="scope.row.comment===1">
                                    <el-button type="success" @click="comment(scope.row.foodName,scope.row.id)">评论</el-button>
                                </div>
                                <div v-else>
                                    <span>已评论</span>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                

                <el-dialog title="评论" :visible.sync="dialogFormVisible" width="30%">
                    <el-form label-width="70px" size="small">
                        <el-form-item label="评论内容：">
                            <textarea  v-model="content" autocomplete="off" maxlength="100"  rows="20" cols="40" placeholder="可在此输入100字以内的评论"></textarea>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="submit">确 定</el-button>
                    </div>
                </el-dialog>

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
        name: "shipped",
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
                dialogFormVisible:false,
                content:"",
                name:"",
                id:null,
            };
        },
        created() {
            this.load();
        },

        methods: {
            load() {
                fetch("http://localhost:9090/shipped/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&username=" + this.user.username,{
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.total = res.data.total
                        this.tableData = res.data.records
                    } else {
                        this.$router.push("/user/login")
                        this.$message.error(res.msg)
                    }

                })
            },
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
            //增加数据的弹窗
            comment(foodName,id) {
                this.dialogFormVisible = true
                this.name = foodName
                this.id = id;
            },
            submit() {
                fetch("http://localhost:9090/comment/add?username=" + this.user.username + "&connect=" + this.content + "&FoodName=" + this.name + "&id=" + this.id,{
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.dialogFormVisible = false
                        this.$message.success("评论成功")
                        this.content = ""
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            }
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
    .el-header,  {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-footer{
        text-align: center;
        background: #e9eef3;
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