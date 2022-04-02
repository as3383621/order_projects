<template>
    <div class="main">
        <el-container style="height: 100%">
            <el-header style="background: #292828;width: 100%;">
                <navBar></navBar>
            </el-header>

            <el-main>
                <el-row type="flex" justify="center" :gutter="20">
                    <el-col :span="20">
                        <div class="banner">
                            <el-carousel :interval="4000">
                                <el-carousel-item v-for="item in items" :key="item.id">
                                    <el-image :src="item.img"  class="mainImg"></el-image>
                                </el-carousel-item>
                            </el-carousel>
                        </div>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 40px">
                    <el-col :span="20">
                        <el-card class="box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <span>小炒</span>
                                <router-link :to="{path:'/more',query:{category:'小炒'}}">
                                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                                </router-link>
                            </div>
                            <div class="foodBox">
                                <el-form v-for="(food,i) in form1" class="text_item">
                                    <router-link :to="{path:'/minute',query:{id:food.id}}">
                                        <el-form-item class="text_item_img">
                                            <img :src="food.url"  class="img">
                                        </el-form-item>
                                    </router-link>
                                    <el-form-item class="text_item_content">
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 40px">
                    <el-col :span="20">
                        <el-card class="box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <span>面食</span>
                                <router-link :to="{path:'/more',query:{category:'面食'}}">
                                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                                </router-link>
                            </div>
                           <div class="foodBox">
                                <el-form v-for="(food,i) in form2" class="text_item">
                                     <router-link :to="{path:'/minute',query:{id:food.id}}">
                                        <el-form-item class="text_item_img">
                                            <img :src="food.url"  class="img">
                                        </el-form-item>
                                    </router-link>
                                    <el-form-item class="text_item_content">
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 40px">
                    <el-col :span="20">
                        <el-card class="box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <span>甜品</span>
                                <router-link :to="{path:'/more',query:{category:'甜品'}}">
                                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                                </router-link>
                            </div>
                            <div class="foodBox">
                                <el-form v-for="(food,i) in form3" class="text_item">
                                     <router-link :to="{path:'/minute',query:{id:food.id}}">
                                        <el-form-item class="text_item_img">
                                            <img :src="food.url"  class="img">
                                        </el-form-item>
                                    </router-link>
                                    <el-form-item class="text_item_content">
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="center" :gutter="20" style="margin-top: 40px">
                    <el-col :span="20">
                        <el-card class="box-card" shadow="hover">
                            <div slot="header" class="clearfix">
                                <span>饮料</span>
                                <router-link :to="{path:'/more',query:{category:'饮料'}}">
                                    <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
                                </router-link>
                            </div>
                            <div class="foodBox">
                                <el-form v-for="(food,i) in form4" class="text_item">
                                     <router-link :to="{path:'/minute',query:{id:food.id}}">
                                        <el-form-item class="text_item_img">
                                            <img :src="food.url"  class="img">
                                        </el-form-item>
                                    </router-link>
                                    <el-form-item class="text_item_content">
                                        <p>菜名：{{food.foodName}}</p>
                                        <p>价钱：{{food.price}}元</p>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-main>
            
        </el-container>
        

    </div>
</template>

<script>
import navBar from './components/NavBar.vue'
export default {
        name: "index",
        components: {
            navBar
        },
        data() {
            return {
                items:[
                    {id:1,
                        img:require('../img/lun1.jpg')},
                    {id:2,
                        img:require('../img/lun2.jpg')},
                ],
                form1:{},
                form2:{},
                form3:{},
                form4:{},
            };
        },
        created() {
            //推荐食物
            this.findFood1("小炒");
            this.findFood2("面食");
            this.findFood3("甜点");
            this.findFood4("饮料");
        },
        methods: {
            findFood1(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form1 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood2(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form2 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood3(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form3 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            findFood4(category) {
                fetch("http://localhost:9090/food/recommend?category=" + category)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        this.form4 = res.data.records
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            //搜索
            select() {
                this.$router.push({name:'select',params:{FoodName:this.FoodName}})
            },
        },
    }
</script>

<style>
.main{
    height: 100vh;
    overflow-x: hidden;
}
.mainImg{
    height: 350px;
    width: 1100px;
}

.banner{
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
}

.el-form-item__content{
    font-size: 16px;
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

    /* .el-main {
        background-color: #E9EEF3;
        color: #333;
        text-align: center;
        line-height: 160px;
    } */

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

    .text {
    font-size: 14px;
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
    width: 100%;
      overflow:hidden;
  }

  .foodBox{
      display: -webkit-box;
      height: 100%;
      overflow-x: auto;
      -webkit-overflow-scrolling: touch;
  }
  .foodBox::-webkit-scrollbar {
  display: none;
}

.text_item {
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
    font-size:14px;
}
.text_item_img{
    width: 169px;
    height: 120px;
}
.text_item_content{
    padding-top: 15px;
}
.img{
    width: 169px;
    height: 160px;
    cursor: pointer;
}
p{
    margin: 10px;
}

</style>