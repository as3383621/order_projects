<template>
    <el-menu :default-openeds="['1', '3']" style="height: 100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse = "isCollapse"
             router
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
            <b style="color: white" v-show="logoTextShow" >后台管理系统</b>
        </div>

        <el-submenu index="user">
            <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span slot="title">用户管理</span>
            </template>
            <el-menu-item index="/user">
                <i class="el-icon-user"></i>
                <span slot="title">普通用户管理</span>
            </el-menu-item>
            <el-menu-item index="/root">
                <i class="el-icon-user-solid"></i>
                <span slot="title">管理员管理</span>
            </el-menu-item>
        </el-submenu>
            <el-menu-item index="/food">
                <i class="el-icon-food"></i>
                <span slot="title">菜品管理</span>
            </el-menu-item>
        <el-submenu index="order">
            <template slot="title">
                <i class="el-icon-document"></i>
                <span slot="title">订单管理</span>
            </template>
            <el-menu-item index="/NotShipped">
                <i class="el-icon-document-delete"></i>
                <span slot="title">未发货订单</span>
            </el-menu-item>
            <el-menu-item index="/Shipped">
                <i class="el-icon-document-checked"></i>
                <span slot="title">已发货订单</span>
            </el-menu-item>
        </el-submenu>
            <el-menu-item index="/comment">
                <i class="el-icon-chat-line-square"></i>
                <span slot="title">评论管理</span>
            </el-menu-item>
            <el-menu-item index="/news">
                <i class="el-icon-data-board"></i>
                <span slot="title">新闻管理</span>
            </el-menu-item>

    </el-menu>
</template>

<script>
    export default {
        name: "Aside",
        props: {
            isCollapse: Boolean,
            logoTextShow: Boolean
        },
    }
</script>

<style scoped>

</style>