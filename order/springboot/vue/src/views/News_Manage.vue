<template>
    <div>
        <div style="margin-bottom: 30px">
            <el-breadcrumb>
                <el-breadcrumb-item>后台管理系统</el-breadcrumb-item>
                <el-breadcrumb-item>新闻管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入作者" suffix-icon="el-icon-user" v-model="username"></el-input>
            <el-input style="width: 200px; margin-left: 5px" placeholder="请输入标题" suffix-icon="el-icon-data-board" v-model="title"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>


        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                    style="margin-left: 5px"
                    confirm-button-text='确定'
                    cancel-button-text='不用了'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除这些数据吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>


        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="username" label="作者" width="140"></el-table-column>
            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="time" label="发布时间"></el-table-column>
            <el-table-column label="操作" width="170" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            style="margin-left: 5px"
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


        <el-dialog title="新闻信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="70px" size="small">
                <el-form-item label="作者">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="内容">
                    <textarea  v-model="form.content" autocomplete="off" maxlength="1000"  rows="25" cols="40" placeholder="可在此输入1000字以内的文章"></textarea>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "News_Manage",
        data() {
            return {
                token: localStorage.getItem("root") ? JSON.parse(localStorage.getItem("root")).token : null,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                username: "",
                title:"",
                form: {},
                dialogFormVisible:false,
                multipleSelection:[],
            }
        },
        created() {
            //请求分页查询用户信息
            this.load();
        },
        methods: {
            //请求分页查询用户信息
            load() {
                fetch("http://localhost:9090/news/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&username=" + this.username +"&title=" + this.title,{
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
            //增加数据的弹窗
            handleAdd() {
                this.dialogFormVisible = true
                this.form = {}
            },
            //修改数据的弹窗
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogFormVisible = true
            },
            //删除数据
            del(id) {
                fetch("http://localhost:9090/news/" + id, {
                    method:'Delete',
                    headers: {
                        token: this.token,
                        author: "root"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("删除成功")
                        this.load()
                    } else {
                        this.$router.push("/root/login")
                        this.$message.error(res.msg)
                    }
                })
            },
            //批量获取数据
            handleSelectionChange(val) {
                this.multipleSelection = val
            },
            //批量删除
            delBatch() {
                let ids = this.multipleSelection.map(v => v.id) //[{}, {}, {}] => [1,2,3]
                fetch("http://localhost:9090/news/del/batch", {
                    method: 'POST',
                    body: JSON.stringify(ids),
                    headers: {
                        'content-type': 'application/json',
                        token: this.token,
                        author: "root"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("删除成功")
                        this.load()
                    } else {
                        this.$router.push("/root/login")
                        this.$message.error(res.msg)
                    }
                })
            },
            //确认提交
            save() {
                fetch("http://localhost:9090/news",{
                    method: 'POST',
                    body: JSON.stringify(this.form),
                    headers: {
                        'content-type': 'application/json',
                        token: this.token,
                        author: "root"
                    }
                }).then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    } else {
                        this.$router.push("/root/login")
                        this.$message.error(res.msg)
                    }
                })
            },
            //重置
            reset() {
                this.username = ""
                this.title = ""
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