<template>
    <div style="height: 100%">
        <el-container style="height: 100%">
            <el-header style="background: #292828">
                <navBar></navBar>
            </el-header>

            <el-main>
                <div v-if="form.length!==0">
                    <el-card class="box-card" v-for="(food,i) in form">
                        <div slot="header" class="clearfix">
                            <span>{{ food.foodName}}</span>
                        </div>
                        <div class="text_item">
                            <div class="text_item_img">
                                <router-link :to="{path:'/minute',query:{id:food.id}}"><img :src="food.url" style="cursor: pointer;"></router-link>
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
    width: 169px;
    height: 235px;
    line-height: 20px;
    border-radius: 10px;
    text-align: center;
    overflow: hidden;
    margin: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    background: white;
    position: relative;
    cursor: pointer;
    font-size:14px
  }

  .text_item_img{
    width: 169px;
    height: 169px;
  }
</style>