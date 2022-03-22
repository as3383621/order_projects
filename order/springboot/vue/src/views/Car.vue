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
            <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="40"></el-table-column>
                <el-table-column prop="url" label="菜品图片" width="140" align="center">
                    <router-link slot-scope="scope" :to="{path:'/minute',query:{id:scope.row.fid}}">
                        <el-image
                                style="width: 100px; height: 100px"
                                :src="scope.row.url"
                                fit="fit">
                        </el-image>
                    </router-link>
                </el-table-column>
                <el-table-column prop="foodName" label="菜品名称" align="center">
                    <router-link slot-scope="scope" :to="{path:'/minute',query:{id:scope.row.fid}}">
                        <span v-text="scope.row.foodName"></span>
                    </router-link>
                </el-table-column>
                <el-table-column prop="category" label="菜品分类" align="center"></el-table-column>
                <el-table-column prop="price" label="菜品单价" align="center">
                    <span slot-scope="scope"><span v-text="scope.row.price"></span>元</span>
                </el-table-column>
                <el-table-column prop="number" label="数量" align="center"></el-table-column>
                <el-table-column prop="sum" label="总计" align="center">
                    <span slot-scope="scope"><span v-text="scope.row.price*scope.row.number"></span>元</span>
                </el-table-column>
                <el-table-column label="操作" width="170" align="center">
                    <template slot-scope="scope">
                        <el-popconfirm
                                confirm-button-text='确定'
                                cancel-button-text='不用了'
                                icon="el-icon-info"
                                icon-color="red"
                                title="您确定删除吗？"
                                @confirm="del(scope.row.id)"
                        >
                            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                        </el-popconfirm>
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
            <div style="float: right">
                <el-button @click="dialogVisible = true" type="success" slot="reference">支付</el-button>
                <el-popconfirm
                        style="margin-left: 5px"
                        confirm-button-text='确定'
                        cancel-button-text='不用了'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定批量删除这些数据吗？"
                        @confirm="delBatch"
                >
                        <el-button type="danger" slot="reference">批量删除</el-button>
                </el-popconfirm>
                <div>
                    <span>总价：{{unit}}元</span>
                </div>
            </div>

            <el-dialog
                    title="提示"
                    :visible.sync="dialogVisible"
                    width="30%"
            >
                <span>确认支付吗？</span>
                <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="pay()">确 定</el-button>
                </span>
            </el-dialog>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Car",
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null,
                FoodName:"",
                pageNum: 1,
                pageSize: 5,
                total: 0,
                tableData:[],
                unit: 0,
                multipleSelection:null,
                dialogVisible: false,
            };
        },
        created() {
            this.load();
        },
        methods: {
            pay() {
                if (this.multipleSelection != null) {
                    let shippedList = []
                    for (let i = 0; i < this.multipleSelection.length; i++) {
                        let item = {}
                        item["username"] = this.multipleSelection[i].username,
                        item["price"] = this.multipleSelection[i].price,
                        item["number"] = this.multipleSelection[i].number,
                        item["url"] = this.multipleSelection[i].url,
                        item["foodName"] = this.multipleSelection[i].foodName
                        item["fid"] = this.multipleSelection[i].fid
                        shippedList.push(item)
                    }
                    console.log(JSON.stringify(shippedList))
                    fetch("http://localhost:9090/shipped/pay",{
                        method: 'POST',
                        body: JSON.stringify(shippedList),
                        headers: {
                            'content-type': 'application/json',
                            token: this.token,
                            author: "common"
                        }
                    })
                        .then(res => res.json()).then(res => {
                        if(res.code === '200') {
                            this.delBatch()
                            this.dialogVisible = false;
                        } else {
                            this.$message.error(res.msg)
                        }

                    })
                } else {
                    this.$message.error("请先选择需要结算的商品")
                }
            },
            load() {
                fetch("http://localhost:9090/car/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&username=" + this.user.username,{
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.gettotal()
                        this.tableData = res.data
                    } else {
                        this.$router.push("/user/login")
                        this.$message.error(res.msg)
                    }

                })
            },
            //获取total
            gettotal() {
                fetch("http://localhost:9090/car/total?username=" + this.user.username,{
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.total = res.data
                    } else {
                        this.$router.push("/user/login")
                        this.$message.error(res.msg)
                    }

                })
            },
            //删除数据
            del(id) {
                fetch("http://localhost:9090/car/" + id, {
                    method:'Delete',
                    headers: {
                        token: this.token,
                        author: "common"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("成功")
                        this.load()
                    } else {
                        this.$router.push("/user/login")
                        this.$message.error(res.msg)
                    }
                })
            },
            //批量获取数据
            handleSelectionChange(val) {
                this.multipleSelection = val
                this.unit = 0
                for (let i = 0; i < this.multipleSelection.length; i++) {
                    this.unit += this.multipleSelection[i].price*this.multipleSelection[i].number;
                }
            },
            //批量删除
            delBatch() {
                let ids = this.multipleSelection.map(v => v.id) //[{}, {}, {}] => [1,2,3]
                fetch("http://localhost:9090/car/del/batch", {
                    method: 'POST',
                    body: JSON.stringify(ids),
                    headers: {
                        'content-type': 'application/json',
                        token: this.token,
                        author: "common"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("成功")
                        this.load()
                    } else {
                        this.$router.push("/user/login")
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