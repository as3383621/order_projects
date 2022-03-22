<template>
    <div>
        <div style="margin-bottom: 30px">
            <el-breadcrumb>
                <el-breadcrumb-item>后台管理系统</el-breadcrumb-item>
                <el-breadcrumb-item>评论管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-user" v-model="username"></el-input>
            <el-input style="width: 200px; margin-left: 5px" placeholder="请输入菜品名称" suffix-icon="el-icon-food" v-model="FoodName"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>


        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
            <el-table-column prop="username" label="用户名" width="140" ></el-table-column>
            <el-table-column prop="connect" label="评论"></el-table-column>
            <el-table-column prop="foodName" label="菜品名称"></el-table-column>
            <el-table-column prop="time" label="评论时间"></el-table-column>
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
        name: "Comment_Manage",
        data() {
            return {
                token: localStorage.getItem("root") ? JSON.parse(localStorage.getItem("root")).token : null,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                username: "",
                FoodName: "",
            }
        },
        created() {
            //请求分页查询评论信息
            this.load();
        },
        methods: {
            //请求分页查询评论信息
            load() {
                fetch("http://localhost:9090/comment/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&username=" + this.username +"&FoodName=" + this.FoodName,{
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