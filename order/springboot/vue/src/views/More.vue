<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
            </el-header>

            <el-main>
                <div v-if="form.length!==0">
                    <!-- <el-form v-for="(food,i) in form">
                        <div style="height: 200px;width: 200px;float: left;margin-right: 10px; ">
                            <el-form-item>
                                <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                                <p>菜名：{{food.foodName}}</p>
                                <p>价钱：{{food.price}}元</p>
                            </el-form-item>
                        </div>
                    </el-form> -->
                    <el-card class="box-card" v-for="(food,i) in form">
                        <div slot="header" class="clearfix">
                            <span>{{ food.foodName}}</span>
                        </div>
                        <div class="text_item">
                            <div style="flex:1">
                                <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="width: 200px;height: 200px;cursor: pointer;"></router-link>
                            </div>
                            <p>价钱：{{food.price}}元</p>
                        </div>
                    </el-card>
                </div>
                
                <div v-else>
                    <span>没有更多菜品了！</span>
                </div>
            </el-main>
            <el-footer>
                <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="pageNum"
                            :page-sizes="[20]"
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
        name: "More",
        components:{
            navBar
        },
        data() {
            return {
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null,
                token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null,
                FoodName:"",
                pageNum: 1,
                pageSize: 20,
                total: 0,
                tableData:[],
                form:{},
                category:null,
            };
        },
        created() {
            let category=this.$route.query.category
            this.category=category
            this.load()
        },

        methods: {
            load() {
                fetch("http://localhost:9090/food/more?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&category=" + this.category,{
                    headers: {
                        token: this.token,
                        author: "user"
                    }
                })
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form = res.data.records
                        this.total = res.data.total
                    } else {
                        this.$message.error(res.msg)
                    }

                })
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
            handleSelect(key) {
                location.href = key;
            },
        }
    }
</script>

<style scoped>
.el-footer{
    background: #ffffff;
    text-align: center;
}

.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 100%
  }
  .text_item{
      display: flex;
      align-items: center;
      font-size: 20px;
  }
</style>