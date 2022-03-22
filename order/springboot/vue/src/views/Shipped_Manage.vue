<template>
    <div>
        <div style="margin-bottom: 30px">
            <el-breadcrumb>
                <el-breadcrumb-item>后台管理系统</el-breadcrumb-item>
                <el-breadcrumb-item>订单管理</el-breadcrumb-item>
                <el-breadcrumb-item>已发货订单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-user" v-model="username"></el-input>
            <el-input style="width: 200px;margin-left: 5px" placeholder="请输入菜品名称" suffix-icon="el-icon-food" v-model="FoodName"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
            <el-table-column prop="username" label="下单用户"></el-table-column>
            <el-table-column prop="url" label="菜品图片" width="140">
                <el-image slot-scope="scope"
                          style="width: 100px; height: 100px"
                          :src="scope.row.url"
                          fit="fit">
                </el-image>
            </el-table-column>
            <el-table-column prop="foodName" label="菜品名称" ></el-table-column>
            <el-table-column prop="price" label="菜品价格">
                <span slot-scope="scope"><span v-text="scope.row.price"></span>元</span>
            </el-table-column>
            <el-table-column prop="number" label="数量"></el-table-column>
            <el-table-column prop="sum" label="总计">
                <span slot-scope="scope"><span v-text="scope.row.price*scope.row.number"></span>元</span>
            </el-table-column>
            <el-table-column prop="state" label="状态"></el-table-column>
            <el-table-column prop="time" label="下单时间"></el-table-column>
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
    </div>
</template>

<script>
    export default {
        name: "NotShipped_Manage",
        data() {
            return {
                token: localStorage.getItem("root") ? JSON.parse(localStorage.getItem("root")).token : null,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                username: "",
                FoodName: "",
            }
        },
        created() {
            //请求分页查询信息
            this.load();
        },
        methods: {
            //请求分页查询信息
            load() {
                fetch("http://localhost:9090/shipped/findpage?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&username=" + this.username +"&FoodName=" + this.FoodName + "&state=已出餐",{
                    headers: {
                        token: this.token,
                        author: "root"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.tableData = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$router.push("/root/login")
                        this.$message.error(res.msg)
                    }

                })
            },
            //重置
            reset() {
                this.username = ""
                this.FoodName = ""
                this.load()
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load()
            }
        }
    }
</script>


<style>
    <!-- 表单头部样式 -->
    .headerBg {
        background: #eee!important;
    }
</style>