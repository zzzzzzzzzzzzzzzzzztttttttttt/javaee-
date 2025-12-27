<template>
  <div class="login-container">
    <!-- 左侧商城品牌展示区 -->
    <div class="login-brand">
      <div class="brand-content">
        <h1 class="brand-logo">
          <i class="el-icon-shopping-cart-2"></i> 数码优选
        </h1>
        <p class="brand-desc">品质数码 · 极致体验</p>
        <div class="brand-features">
          <div class="feature-item">
            <i class="el-icon-truck"></i>
            <span>极速配送</span>
          </div>
          <div class="feature-item">
            <i class="el-icon-shield"></i>
            <span>正品保障</span>
          </div>
          <div class="feature-item">
            <i class="el-icon-refresh-left"></i>
            <span>7天无理由退换</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧登录表单区 -->
    <div class="login-form-wrapper">
      <div class="login-box">
        <div class="login-header">
          <h2>欢迎回来</h2>
          <p>登录账号，开启您的购物之旅</p>
        </div>

        <el-form
            :model="data.form"
            ref="formRef"
            :rules="data.rules"
            class="login-form"
            autocomplete="off"
        >
          <el-form-item prop="username">
            <el-input
                v-model="data.form.username"
                size="large"
                placeholder="请输入账号/手机号"
                prefix-icon="User"
                class="form-input"
            >
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="data.form.password"
                size="large"
                placeholder="请输入密码"
                prefix-icon="Lock"
                show-password
                class="form-input"
            >
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                v-model="data.form.cpassword"
                size="large"
                placeholder="确认密码"
                prefix-icon="Lock"
                show-password
                class="form-input"
            >
            </el-input>
          </el-form-item>
          <el-form-item class="form-actions">
            <el-button
                type="primary"
                style="width: 100%"
                size="large"
                @click="login"
                class="login-btn"
            >
              <i class="el-icon-user"></i> 登录
            </el-button>
          </el-form-item>
        </el-form>

        <div class="login-footer">
          <router-link to="/login" class="register-link">
            <el-button type="text">
              <i class="el-icon-user-plus"></i> 返回登录
            </el-button>
          </router-link>
        </div>
      </div>

      <div class="copyright">
        © 2025 数码优选 版权所有 | 客服热线：400-123-4567
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { User } from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import { ElMessage } from "element-plus";

const data = reactive({
  form: {
    username: '',
    password: '',
    cpassword: ''
  },
  rules: {
    username: [
      { required: true, message: '请输入账号/手机号', trigger: 'blur' },
      { min: 3, max: 20, message: '长度在3-20个字符', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 3, max: 20, message: '长度在3-20个字符', trigger: 'blur' }
    ]
  }
});

const formRef = ref(null);

const login = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.post("/user/register", data.form).then(res => {
        if (res.code === "200") {
          localStorage.removeItem('nowUser');
          localStorage.setItem("nowUser", JSON.stringify(data.form.username));
          ElMessage.success("注册成功！");
          location.href = '/login'; // 跳转到商城首页
        } else {
          ElMessage.error(res.message);
        }
      }).catch(error => {
        ElMessage.error("登录请求失败，请稍后重试");
        console.error("登录错误：", error);
      });
    }
  });
};
</script>

<style scoped>
/* 全局容器样式 */
.login-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
}

/* 左侧品牌区域 - 商城风格 */
.login-brand {
  flex: 1;
  background: linear-gradient(135deg, #e63946 0%, #f77f00 100%); /* 电商橙红色渐变 */
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 60px;
  position: relative;
  overflow: hidden;
}

/* 背景装饰效果 */
.login-brand::before {
  content: '';
  position: absolute;
  width: 400px;
  height: 400px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  top: -100px;
  right: -100px;
}

.login-brand::after {
  content: '';
  position: absolute;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.08);
  bottom: -80px;
  left: -80px;
}

.brand-content {
  position: relative;
  z-index: 2;
  text-align: center;
}

.brand-logo {
  font-size: 42px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  font-weight: 700;
  letter-spacing: 2px;
}

.brand-logo i {
  font-size: 48px;
}

.brand-desc {
  font-size: 20px;
  line-height: 1.6;
  margin-bottom: 80px;
  opacity: 0.9;
  font-weight: 300;
}

.brand-features {
  display: flex;
  gap: 50px;
  justify-content: center;
}

.feature-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.feature-item i {
  font-size: 32px;
  margin-bottom: 8px;
}

.feature-item span {
  font-size: 16px;
  opacity: 0.9;
}

/* 右侧登录表单区域 */
.login-form-wrapper {
  flex: 0 0 500px;
  background-color: #f8f9fa;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.login-box {
  width: 100%;
  max-width: 400px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  padding: 50px 40px;
  margin-bottom: 30px;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.login-header h2 {
  font-size: 28px;
  color: #333;
  margin: 0 0 10px 0;
  font-weight: 600;
}

.login-header p {
  font-size: 16px;
  color: #999;
  margin: 0;
}

.login-form {
  margin-bottom: 20px;
}

.form-input {
  border-radius: 10px !important;
  height: 54px !important;
  font-size: 16px;
  border: 1px solid #e5e7eb !important;
  transition: all 0.3s;
}

.form-input:focus {
  border-color: #e63946 !important;
  box-shadow: 0 0 0 2px rgba(230, 57, 70, 0.1) !important;
}

.form-actions {
  margin-top: 20px;
}

.login-btn {
  height: 54px !important;
  border-radius: 10px !important;
  background-color: #e63946 !important;
  border-color: #e63946 !important;
  font-size: 18px !important;
  font-weight: 500 !important;
  transition: all 0.3s;
}

.login-btn:hover {
  background-color: #d12f3c !important;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(230, 57, 70, 0.2);
}

.login-footer {
  text-align: center;
  margin-top: 10px;
}

.register-link {
  text-decoration: none;
}

.register-link .el-button {
  color: #e63946 !important;
  font-size: 16px;
  font-weight: 500;
}

.register-link .el-button:hover {
  color: #d12f3c !important;
}

.copyright {
  font-size: 13px;
  color: #999;
  text-align: center;
  line-height: 1.6;
}

/* 响应式适配 */
@media (max-width: 992px) {
  .login-brand {
    display: none;
  }

  .login-form-wrapper {
    flex: 1;
  }
}

@media (max-width: 480px) {
  .login-form-wrapper {
    padding: 20px;
  }

  .login-box {
    padding: 40px 25px;
  }

  .brand-logo {
    font-size: 32px;
  }

  .login-header h2 {
    font-size: 24px;
  }
}
</style>