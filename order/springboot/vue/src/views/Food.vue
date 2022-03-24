<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
               <navBar></navBar>
            </el-header>

            <el-main>
                <el-card>
                    <div slot="header" class="clearfix">
                        <span>{{ form.foodName }}</span>
                    </div>
                    <el-form class="details">
                        <img :src="form.url" style="width:200px;height:200px">
                        <el-row type="flex" style="flex:1">
                            <el-col :span="3">
                                <span>分类：{{form.category}}</span>
                            </el-col>
                            <el-col :span="3">
                                <span>价钱：{{form.price}}元</span>
                            </el-col>
                            <el-col :span="3">
                                <span>库存：{{form.stock}}</span>
                            </el-col>
                            <el-col :span="7">
                                <span>上架时间：{{form.time}}</span>
                            </el-col>
                            <el-col :span="5">
                                <span>数量：</span>
                                <el-button @click="sub()" :disabled="disabled1" icon="el-icon-minus"></el-button>
                                <span>{{number}}</span>
                                <el-button @click="add()" :disabled="disabled2" icon="el-icon-plus"></el-button>
                            </el-col>
                            <el-col :span="3">
                                  <div v-if="user">
                                        <el-button type="success" @click="InsertCar(form.id)" icon="el-icon-s-goods">加入购物车</el-button>
                                    </div>
                                    <div v-else>
                                        <span>登入之后将商品加入购物车</span>
                                </div>
                            </el-col>
                          
                        </el-row>
                    </el-form>
                    <div class="title">
                    <h1 >用户评论：</h1>
                </div>
                <div v-if="comment.length!==0">
                    <el-form v-for="(comment,i) in comment">
                            <el-form-item>
                                <span>{{comment.username}}</span>
                                <span>{{comment.time}}</span>
                                <span>{{comment.connect}}</span>
                            </el-form-item>
                    </el-form>
                </div>
                    <div v-else>
                        <span>当前菜品还没有评论哦！</span>
                    </div>
                    <div style="padding: 10px 0">
                        
                    </div>
                </el-card>
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
        name: "Food",
        components: {
            navBar
        },
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null,
                pageNum: 1,
                pageSize: 5,
                total: 0,
                FoodName:"",
                comment:{},
                form:{},
                food:null,
                id: null,
                number:1,
                disabled1:true,
                disabled2:false,
            };
        },
        created() {
            let id=this.$route.query.id
            this.id=id
            this.minute()
        },
        methods: {
            detection(){
                if (this.number <= 1) {
                    this.disabled1 = true
                } else {
                    this.disabled1 = false
                }
                if (this.number < this.form.stock) {
                    this.disabled2 = false
                } else {
                    this.disabled2 = true
                }
            },
            sub() {
                this.number--
                this.detection()
            },
            add() {
                this.number++
                this.detection()
            },
            minute() {
                fetch("http://localhost:9090/food/minute?id=" + this.id)
                    .then(res => res.json()).then(res => {
                    if (res.code === '200') {
                        this.form = res.data
                        this.food=this.form.foodName
                        this.load()
                        this.detection()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            load() {
                fetch("http://localhost:9090/comment/food?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
                    "&FoodName=" + this.food)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.comment = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$message.error(res.msg)
                    }

                })
            },
            InsertCar(fid) {
                fetch("http://localhost:9090/car/insert?username=" + this.user.username + "&number=" + this.number + "&fid=" + fid,{
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
                this.load(this.form.foodName)
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum
                this.load(this.form.foodName)
            },
        }
    }
</script>












<style>
    .details{
        display: flex;
        align-items: baseline;
    }
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
</style>