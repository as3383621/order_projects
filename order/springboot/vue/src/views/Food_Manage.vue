<template>
    <div>
        <div style="margin-bottom: 30px">
            <el-breadcrumb>
                <el-breadcrumb-item>后台管理系统</el-breadcrumb-item>
                <el-breadcrumb-item>菜品管理</el-breadcrumb-item>

            </el-breadcrumb>
        </div>

        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入菜品名称" suffix-icon="el-icon-food" v-model="FoodName"></el-input>
            <el-select style="width: 200px; margin-left: 5px" v-model="value" placeholder="请选择菜品类别" @change="load">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
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
            <el-table-column prop="url" label="菜品图片" width="140">
                <el-image slot-scope="scope"
                    style="width: 100px; height: 100px"
                    :src="scope.row.url"
                    fit="fit">
                </el-image>
            </el-table-column>
            <el-table-column prop="foodName" label="菜品名称" ></el-table-column>
            <el-table-column prop="category" label="菜品分类" ></el-table-column>
            <el-table-column prop="price" label="菜品价格">
                <span slot-scope="scope"><span v-text="scope.row.price"></span>元</span>
            </el-table-column>
            <el-table-column prop="stock" label="库存"></el-table-column>
            <el-table-column prop="time" label="创建时间"></el-table-column>
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


        <el-dialog title="菜品信息" :visible.sync="dialogFormVisible" width="30%">

            <el-form label-width="70px" size="small">
                <el-form-item label="菜品图片">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:9090/file/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                    >
                        <img v-if="form.url" :src="form.url" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="菜品名">
                    <el-input v-model="form.foodName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="菜品类别">
                    <el-select v-model="form.category" placeholder="请选择菜品类别">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="菜品价格">
                    <el-input v-model="form.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="库存">
                    <el-input v-model="form.stock" autocomplete="off"></el-input>
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
        name: "Food_Manage",
        data() {
            return {
                token: localStorage.getItem("root") ? JSON.parse(localStorage.getItem("root")).token : null,
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                FoodName: "",
                form: {},
                dialogFormVisible:false,
                multipleSelection:[],
                value:'',
                options: [{
                    value: '小炒',
                    label: '小炒'
                }, {
                    value: '面食',
                    label: '面食'
                }, {
                    value: '甜点',
                    label: '甜点'
                }, {
                    value: '饮料',
                    label: '饮料'
                }],
            }
        },
        created() {
            //请求分页查询菜品信息
            this.load();
        },
        methods: {
            //请求分页查询菜品信息
            load() {
                fetch("http://localhost:9090/food/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&FoodName=" + this.FoodName +"&category=" + this.value,{
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
                this.form = {}
                this.dialogFormVisible = true
            },
            //修改数据的弹窗
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogFormVisible = true
            },
            //删除数据
            del(id) {
                fetch("http://localhost:9090/food/" + id, {
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
                fetch("http://localhost:9090/food/del/batch", {
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
                fetch("http://localhost:9090/food",{
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
                this.FoodName = ""
                this.value = ""
                this.load()
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load()
            },
            handleAvatarSuccess(res) {
                this.form.url = res
            }
        }

    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

</style>