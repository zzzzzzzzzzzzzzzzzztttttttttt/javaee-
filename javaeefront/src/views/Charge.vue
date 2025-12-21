<script setup>
import { onMounted, reactive } from "vue";
import request from "@/utils/request.js";
import { ElMessage, ElMessageBox } from "element-plus";

// 响应式数据
const data = reactive({
  nowUser: null,
  currentBalance: 0, // 当前余额
  rechargeAmount: 0, // 充值金额
  // 快捷充值金额选项
  quickAmounts: [10, 20, 50, 100, 200, 500],
  selectedQuickAmount: null, // 选中的快捷金额
  isLoading: false // 充值加载状态
});

// 页面加载时获取用户信息和余额
const loadUserData = () => {
  // 获取当前登录用户
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));

  if (!data.nowUser) {
    ElMessage.warning('请先登录后再进行充值');
    setTimeout(() => {
      location.href = '/login';
    }, 1500);
    return;
  }

  // 获取用户当前余额
  request.get("/users/getByUserName", {
    params: { username: data.nowUser }
  }).then(res => {
    data.currentBalance = res.data || 0;
  }).catch(() => {
    ElMessage.error('获取用户余额失败');
  });
};

// 选择快捷充值金额
const selectQuickAmount = (amount) => {
  data.selectedQuickAmount = amount;
  data.rechargeAmount = amount;
};

// 自定义金额输入验证
const inputCustomAmount = (value) => {
  const num = parseFloat(value);
  // 清空选中的快捷金额
  data.selectedQuickAmount = null;

  // 金额验证规则
  if (isNaN(num) || num <= 0) {
    data.rechargeAmount = 0;
    ElMessage.warning('充值金额必须大于0');
    return;
  }

  if (num > 10000) {
    data.rechargeAmount = 10000;
    ElMessage.warning('单次充值金额不能超过10000元');
    return;
  }

  // 保留两位小数
  data.rechargeAmount = num.toFixed(2);
};

// 提交充值请求
const submitRecharge = () => {
  if (data.rechargeAmount <= 0) {
    ElMessage.warning('请输入有效的充值金额');
    return;
  }

  data.isLoading = true;

  // 模拟充值确认（实际项目中可对接支付接口）
  ElMessageBox.confirm(
      `确认充值 ${data.rechargeAmount} 元到账户？`,
      '充值确认',
      {
        confirmButtonText: '确认充值',
        cancelButtonText: '取消',
        type: 'info'
      }
  ).then(() => {
    // 调用充值接口
    request.post("/users/recharge", {
      username: data.nowUser,
      amount: data.rechargeAmount
    }).then(() => {
      ElMessage.success('充值成功！');
      // 更新当前余额
      data.currentBalance = (parseFloat(data.currentBalance) + parseFloat(data.rechargeAmount)).toFixed(2);
      // 重置充值金额
      data.rechargeAmount = 0;
      data.selectedQuickAmount = null;
    }).catch(() => {
      ElMessage.error('充值失败，请稍后重试');
    }).finally(() => {
      data.isLoading = false;
    });
  }).catch(() => {
    ElMessage.info('已取消充值');
    data.isLoading = false;
  });
};

// 返回上一页
const goBack = () => {
  window.history.back();
};

// 跳转到购物车
const toCart = () => {
  location.href = '/cart';
};

// 页面加载初始化
onMounted(() => {
  loadUserData();
});
</script>

<template>
  <div class="mall-app">
    <!-- 顶部导航栏（和购物车页面保持一致） -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>优购商城</h1>
          </div>

          <div class="user-area">
            <div v-if="!data.nowUser" class="login-tip">
              <span>请先登录</span>
            </div>
            <div v-else class="user-info">
              <el-avatar icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser }}</span>
              <span class="user-name">当前余额：¥{{ data.currentBalance.toFixed(2) }}</span>
              <el-button
                  type="text"
                  class="cart-btn"
                  icon="el-icon-shopping-cart-full"
                  @click="toCart"
              >
                购物车
              </el-button>
            </div>
          </div>
        </div>

        <!-- 分类导航 -->
        <nav class="category-nav">
          <ul class="category-list">
            <li @click="goBack">返回</li>
            <li @click="toCart">购物车</li>
          </ul>
        </nav>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="mall-main recharge-main">
      <div class="container">
        <!-- 面包屑导航 -->
        <div class="breadcrumb">
          <a href="/home">首页</a>
          <span>/</span>
          <span class="current">账户充值</span>
        </div>

        <!-- 充值卡片 -->
        <div class="recharge-card">
          <div class="recharge-header">
            <h2 class="recharge-title">账户充值</h2>
            <p class="balance-tip">
              充值金额将实时到账，可用于商城内所有商品消费
            </p>
          </div>

          <!-- 快捷金额选择 -->
          <div class="quick-amounts">
            <h3 class="amount-title">快捷充值</h3>
            <div class="amount-buttons">
              <el-button
                  v-for="amount in data.quickAmounts"
                  :key="amount"
                  :class="{ 'selected': data.selectedQuickAmount === amount }"
                  @click="selectQuickAmount(amount)"
                  type="default"
              >
                ¥{{ amount }}
              </el-button>
            </div>
          </div>

          <!-- 自定义金额 -->
          <div class="custom-amount">
            <h3 class="amount-title">自定义金额</h3>
            <div class="amount-input-box">
              <el-input
                  v-model="data.rechargeAmount"
                  @change="inputCustomAmount(data.rechargeAmount)"
                  placeholder="请输入充值金额（最低1元，最高10000元）"
                  type="number"
                  step="0.01"
                  min="0.01"
                  max="10000"
                  class="amount-input"
                  prefix="¥"
              />
            </div>
          </div>

          <!-- 充值按钮 -->
          <div class="recharge-submit">
            <el-button
                type="primary"
                class="recharge-btn"
                @click="submitRecharge"
                :loading="data.isLoading"
                :disabled="data.rechargeAmount <= 0 || data.isLoading"
            >
              确认充值 ¥{{ data.rechargeAmount > 0 ? data.rechargeAmount : 0 }}
            </el-button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* 继承购物车页面基础样式，仅补充充值页面特有样式 */
.mall-app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
  font-family: "Microsoft YaHei", sans-serif;
}

.container {
  width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 顶部导航栏样式（复用购物车页面） */
.mall-header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 999;
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
}

.logo h1 {
  font-size: 24px;
  color: #ff4400;
  margin: 0;
  font-weight: 700;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-avatar {
  width: 36px;
  height: 36px;
  background-color: #ffebe4;
  color: #ff4400;
}

.user-name {
  font-size: 14px;
  color: #333;
}

.login-tip {
  font-size: 14px;
  color: #666;
}

.cart-btn {
  color: #666 !important;
  transition: color 0.3s;
}

.cart-btn:hover {
  color: #ff4400 !important;
}

/* 分类导航 */
.category-nav {
  background-color: #ff4400;
  height: 40px;
}

.category-list {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
  height: 100%;
}

.category-list li {
  flex: 0 0 auto;
  padding: 0 25px;
  color: #fff;
  font-size: 14px;
  font-weight: 500;
  line-height: 40px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.category-list li:hover {
  background-color: #e03c00;
}

/* 主内容区 */
.mall-main {
  flex: 1;
  padding: 30px 0;
}

.breadcrumb {
  font-size: 13px;
  color: #666;
  margin-bottom: 20px;
}

.breadcrumb a {
  color: #666;
  text-decoration: none;
}

.breadcrumb a:hover {
  color: #ff4400;
}

.breadcrumb span {
  margin: 0 8px;
}

.breadcrumb .current {
  color: #ff4400;
  font-weight: 500;
}

/* 充值页面特有样式 */
.recharge-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  padding: 40px;
  max-width: 600px;
  margin: 0 auto;
}

.recharge-header {
  text-align: center;
  margin-bottom: 30px;
}

.recharge-title {
  font-size: 22px;
  color: #333;
  margin: 0 0 10px 0;
  font-weight: 600;
}

.balance-tip {
  font-size: 14px;
  color: #999;
  margin: 0;
}

.quick-amounts, .custom-amount {
  margin-bottom: 30px;
}

.amount-title {
  font-size: 16px;
  color: #333;
  margin: 0 0 15px 0;
  font-weight: 500;
}

.amount-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.amount-buttons .el-button {
  width: calc(33.33% - 10px);
  height: 50px;
  font-size: 16px;
  border-radius: 6px;
  transition: all 0.3s;
}

.amount-buttons .el-button.selected {
  background-color: #fff8f5;
  border-color: #ff4400;
  color: #ff4400;
}

.amount-input-box {
  width: 100%;
}

.amount-input {
  height: 50px;
  font-size: 16px;
  border-radius: 6px;
}

.recharge-submit {
  text-align: center;
  margin-top: 20px;
}

.recharge-btn {
  width: 100%;
  height: 50px;
  font-size: 18px;
  background-color: #ff4400 !important;
  border-color: #ff4400 !important;
  border-radius: 6px !important;
}

.recharge-btn:hover {
  background-color: #e03c00 !important;
  border-color: #e03c00 !important;
}

/* 响应式调整 */
@media (max-width: 1200px) {
  .container {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .header-top {
    flex-direction: column;
    height: auto;
    padding: 15px 0;
    gap: 15px;
  }

  .recharge-card {
    padding: 20px;
    margin: 0 10px;
  }

  .amount-buttons .el-button {
    width: calc(50% - 7.5px);
  }
}

@media (max-width: 480px) {
  .amount-buttons .el-button {
    width: 100%;
  }
}
</style>