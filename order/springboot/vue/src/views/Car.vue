<template>
    <div style="height: 100%">
        <el-container >
            <el-header style="background: #292828">
                <navBar></navBar>
            </el-header>


            <el-main>
            <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange" >
                <el-table-column type="selection" width="50"></el-table-column>
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
                            <el-button type="danger" slot="reference" icon="el-icon-remove-outline">删除</el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <div class="totalPrice">
                <div style="margin-right:20px">总计：{{unit}}元</div>
                <el-button @click="dialogVisible = true" type="success" slot="reference">支付</el-button>
                <el-popconfirm
                        style="margin-left: 5px;margin-top:-4px"
                        confirm-button-text='确定'
                        cancel-button-text='不用了'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定批量删除这些数据吗？"
                        @confirm="delBatch"
                >
                    <el-button type="danger" slot="reference">批量删除</el-button>
                </el-popconfirm>
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
        name: "Car",
        components:{
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
        height: 100vh;
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

    a{
        text-decoration: none;
    }

    .el-footer{
        background: #e9eef3;
    }

    .totalPrice{
        margin-top: 20px;
        display: flex;
        float: right;
        align-items: center;
    }
</style>