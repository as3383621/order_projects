<template>
    <div class="wrapper">
        <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
            <el-form :model="root" :rules="rules" ref="rootForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="root.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="root.password"></el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right">
                    <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
                    <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/root/register')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login_Root",
        data() {
            return {
                root: {},
                //校验规则
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            //登录
            login() {
                this.$refs['rootForm'].validate((valid) => {
                    if(valid) {//前台表单校验合法，发送请求
                        fetch("http://localhost:9090/root/login",{
                            method: 'POST',
                            body: JSON.stringify(this.root),
                            headers: {
                                'content-type': 'application/json'
                            }
                        })
                            .then(res => res.json()).then(res => {
                            if(res.code === '200') {
                                localStorage.setItem("root", JSON.stringify(res.data)) //存储用户信息到浏览器
                                this.$router.push("/user")
                                this.$message.success("登入成功")
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    .wrapper {
        height: 100vh;
        background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);
        overflow: hidden;
    }
</style>