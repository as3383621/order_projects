<template>
    <div class="wrapper">
        <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 500px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item prop="address">
                    <el-input placeholder="请输入地址" size="medium" style="margin: 5px 0" prefix-icon="el-icon-s-promotion"  v-model="user.address"></el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input placeholder="请输入邮箱" size="medium" style="margin: 5px 0" prefix-icon="el-icon-message"  v-model="user.email"></el-input>
                </el-form-item>
                <el-form-item prop="phone">
                    <el-input placeholder="请输入电话" size="medium" style="margin: 5px 0" prefix-icon="el-icon-phone"  v-model="user.phone"></el-input>
                </el-form-item>
                <el-form-item style="margin: 5px 0; text-align: right">
                    <el-button type="primary" size="small"  autocomplete="off" @click="register">确认注册</el-button>
                    <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/user/login')">返回登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Register_User",
        data() {
            var validateName = (rule, value, callback) => {
                fetch("http://localhost:9090/user/one?username=" + value)
                    .then(res => res.json()).then(res => {
                    if(res.code === '200') {
                        callback()
                    } else {
                        callback(new Error(res.msg))
                    }
                })
            }
            var validatePass = (rule, value, callback) => {
                if (value !== this.user.password) {
                    callback(new Error('两次密码输入不一致'))
                } else {
                    callback()
                }
            }
            return {
                user: {},
                //校验规则
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' },
                        {
                            required: true,
                            pattern: /^[0-9a-zA-Z]+$/,
                            message: '用户名不支持特殊字符',
                            trigger: 'blur'
                        },
                        {
                            validator:validateName,
                            required: true,
                            trigger: 'blur'
                        }

                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' },
                        {
                            required: true,
                            pattern: /^[0-9a-zA-Z]+$/,
                            message: '密码不支持特殊字符',
                            trigger: 'blur'
                        },
                        {
                            validator:validatePass,
                            required: true,
                            trigger: 'blur'
                        }
                    ],
                    confirmPassword: [
                        { required: true, message: '请再次输入密码', trigger: 'blur' },
                        {
                            validator:validatePass,
                            required: true,
                            trigger: 'blur'
                        }
                    ],
                    address: [
                        { required: true, message: '请输入地址', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        {
                            required: true,
                            pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/,
                            message: '请输入正确的邮箱',
                            trigger: 'blur'
                        }
                    ],
                    phone: [
                        { required: true, message: '请输入电话', trigger: 'blur' },
                        {
                            required: true,
                            pattern: /^1[3456789]\d{9}$/,
                            message: '请输入正确的电话号码',
                            trigger: 'blur'
                        }
                    ],
                }
            }
        },
        methods: {
            //注册
            register() {
                this.$refs['userForm'].validate((valid) => {
                    if(valid) {//前台表单校验合法，发送请求
                        fetch("http://localhost:9090/user/register",{
                            method: 'POST',
                            body: JSON.stringify(this.user),
                            headers: {
                                'content-type': 'application/json'
                            }
                        })
                            .then(res => res.json()).then(res => {
                            if(res.code === '200') {
                                this.$router.push("/user/login")
                                this.$message.success("注册成功")
                            } else {
                                this.$message.error("注册失败")
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
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