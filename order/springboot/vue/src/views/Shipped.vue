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

        </el-container>
    </div>
</template>

<script>
    export default {
        name: "shipped",
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