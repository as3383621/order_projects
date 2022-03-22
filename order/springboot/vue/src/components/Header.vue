<template>
    <div style="font-size: 12px; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 22px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        </div>

        <el-dropdown style="width: 50px; cursor: pointer">
            <span>{{ root.username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        props: {
            collapseBtnClass: String,
            collapse: Function,
        },
        data() {
            return {
                root: localStorage.getItem("root") ? JSON.parse(localStorage.getItem("root")) : {}
            }
        },
        methods: {
            logout() {
                this.$router.push("/root/login")
                localStorage.removeItem("root")
                this.$message.success("退出成功")
            }
        }
    }
</script>

<style scoped>

</style>