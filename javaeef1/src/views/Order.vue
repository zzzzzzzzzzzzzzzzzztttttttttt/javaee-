<script setup>
import { onMounted, reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import request from "@/utils/request.js";

// 响应式数据 - 完全匹配你的返回数据结构
const data = reactive({
  nowUser: null,       // 当前登录用户 格式：字符串 'zzzz'
  orderList: [],       // 用户订单列表 - 你的真实数据数组
  loading: false,      // 加载状态
});

// 时间格式化工具方法 - 格式化订单创建时间（你的数据里已有格式化好的时间，此方法做兼容）
const formatDate = (dateStr) => {
  if (!dateStr) return "暂无时间";
  return dateStr; // 你的后端已经返回了 2025-12-27 16:51:07 格式，直接返回即可
};

// 加载用户的全部订单数据 - 核心适配你的接口
const loadUserOrders = () => {
  // 1. 校验登录状态
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  // 你的 nowUser 是字符串格式 'zzzz'，不是对象，这里做兼容判断
  if (!data.nowUser || data.nowUser === '') {
    ElMessage.warning('请先登录查看订单');
    location.href = '/login';
    return;
  }

  // 2. 请求当前登录用户的所有订单数据
  data.loading = true;
  const nowUser = data.nowUser; // 你的用户名是字符串，直接传参

  // ✅ 你的真实订单接口地址，不用改
  request.get(`/order/getAll/${nowUser}`)
      .then(res => {
        data.orderList = res.data || []; // 你的订单数组直接赋值
        if (data.orderList.length === 0) {
          ElMessage.info('暂无订单记录');
        }
      })
      .catch(error => {
        console.error('加载订单失败：', error);
        ElMessage.error('加载订单失败，请重试');
      })
      .finally(() => {
        data.loading = false;
      });
};

// 返回首页
const backToHome = () => {
  location.href = '/';
};

// 返回购物车
const backToCart = () => {
  location.href = '/cart';
};

// 页面加载完成后获取订单数据
onMounted(() => {
  loadUserOrders();
});
</script>

<template>
  <div class="order-app">
    <!-- 顶部导航栏 -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>网上数码商城</h1>
            <p class="logo-slogan">品质数码 · 极致体验</p>
          </div>

          <!-- 用户区域 - 适配你的 nowUser 是字符串格式 -->
          <div class="user-area">
            <div v-if="!data.nowUser">
              <el-button
                  type="primary"
                  @click="location.href='/login'"
                  class="login-btn"
                  icon="el-icon-user"
              >
                登录
              </el-button>
              <el-button
                  type="default"
                  @click="location.href='/register'"
                  class="register-btn"
              >
                注册
              </el-button>
            </div>
            <div v-else class="user-info">
              <el-avatar
                  icon="el-icon-user"
                  class="user-avatar"
              />
              <span class="user-name">{{ data.nowUser }}</span>
              <el-button
                  type="text"
                  @click="backToHome"
                  class="home-btn"
                  icon="el-icon-s-home"
              >
                返回首页
              </el-button>
              <el-button
                  type="text"
                  class="order-btn"
                  icon="el-icon-refresh"
                  @click="loadUserOrders"
              >
                刷新订单
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="order-main">
      <div class="container">
        <!-- 面包屑导航 -->
        <div class="breadcrumb">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的订单</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <!-- 订单主标题 -->
        <div class="order-header">
          <h2 class="order-title">
            <i class="el-icon-document"></i> 我的全部订单
          </h2>
        </div>

        <!-- 订单列表区域 - 完全适配你的数据结构 -->
        <div class="order-content" v-loading="data.loading">
          <!-- 无订单状态 -->
          <div class="empty-order" v-if="data.orderList.length === 0 && !data.loading">
            <el-empty
                description="暂无订单记录，快去选购商品吧~"
                image-size="120"
            >
              <el-button
                  type="primary"
                  @click="backToHome"
                  class="go-home-btn"
              >
                <i class="el-icon-shopping-cart-2"></i> 去购物
              </el-button>
            </el-empty>
          </div>

          <!-- 订单列表 - 核心适配你的真实数据字段 -->
          <div class="order-list" v-else>
            <div
                v-for="(order, index) in data.orderList"
                :key="order.orderId"
                class="single-order"
            >
              <!-- 订单头部信息：订单号、下单时间、订单总价 -->
              <div class="order-header-item">
                <div class="order-left-info">
                  <span class="order-no">订单编号：{{ order.orderId }}</span>
                  <span class="order-time">{{ formatDate(order.createTime) }}</span>
                </div>
                <div class="order-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-num">{{ order.totalPrice.toFixed(2) }}</span>
                </div>
              </div>

              <!-- 订单商品信息 - 适配你的 goodsImg/goodsName/num 字段 -->
              <div class="goods-section">
                <div class="goods-list">
                  <div class="goods-item">
                    <div class="goods-col">
                      <div class="goods-img">
                        <el-image
                            :src="order.goodsImg || 'https://via.placeholder.com/300x300?text=暂无图片'"
                            fit="cover"
                            preview-src-list="[order.goodsImg]"
                        />
                      </div>
                      <div class="goods-name">{{ order.goodsName }}</div>
                    </div>
                    <div class="price-col">
                      <span class="price-symbol">¥</span>
                      <span class="price-num">{{ (order.totalPrice / order.num).toFixed(2) }}</span>
                    </div>
                    <div class="quantity-col">{{ order.num }}</div>
                    <div class="total-col">
                      <span class="price-symbol">¥</span>
                      <span class="price-num">{{ order.totalPrice.toFixed(2) }}</span>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 页脚 -->
    <footer class="mall-footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-links">
            <a href="/about">关于我们</a>
            <a href="/service">售后服务</a>
            <a href="/contact">联系我们</a>
            <a href="/privacy">隐私政策</a>
          </div>
          <div class="copyright">
            <p>© 2025 数码优选 版权所有 | 营业执照：XXXXXX</p>
            <p>客服热线：400-123-4567 | 工作时间：9:00-22:00</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* 全局样式 */
.order-app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f8fa;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  color: #333;
}

.container {
  width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

/* 顶部导航栏 - 样式不变 */
.mall-header {
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 999;
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 90px;
}

.logo {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.logo h1 {
  font-size: 28px;
  color: #165DFF;
  margin: 0;
  font-weight: 700;
  letter-spacing: 1px;
}

.logo-slogan {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  background-color: #e8f3ff;
  color: #165DFF;
  border: 1px solid #d1e7ff;
}

.user-name {
  font-size: 14px;
  color: #333;
  max-width: 80px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.login-btn {
  border-radius: 20px !important;
  padding: 8px 24px !important;
  background-color: #165DFF !important;
  border-color: #165DFF !important;
  font-weight: 500;
}

.register-btn {
  border-radius: 20px !important;
  padding: 8px 20px !important;
  color: #165DFF !important;
  border-color: #165DFF !important;
}

.home-btn, .order-btn {
  color: #666 !important;
  transition: all 0.3s;
  font-size: 14px;
}

.home-btn:hover, .order-btn:hover {
  color: #165DFF !important;
}

/* 主内容区样式 */
.order-main {
  flex: 1;
  padding: 30px 0 50px;
}

.breadcrumb {
  margin-bottom: 20px;
  padding: 10px 0;
}

.el-breadcrumb {
  font-size: 14px;
}

.el-breadcrumb__item:last-child .el-breadcrumb__inner {
  color: #165DFF !important;
  font-weight: 500;
}

.order-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.order-title {
  font-size: 22px;
  color: #333;
  margin: 0;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}

.order-title i {
  color: #165DFF;
  font-size: 24px;
}

.order-content {
  width: 100%;
}

/* 无订单样式 */
.empty-order {
  padding: 60px 0;
  text-align: center;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
}

.go-home-btn {
  background-color: #165DFF !important;
  border-color: #165DFF !important;
  border-radius: 20px !important;
  padding: 8px 30px !important;
  margin-top: 20px;
}

/* 订单列表样式 */
.order-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.single-order {
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

/* 订单头部 */
.order-header-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px 20px;
  background-color: #f8f9ff;
  border-bottom: 1px solid #f0f0f0;
}

.order-left-info {
  display: flex;
  gap: 20px;
  font-size: 14px;
}

.order-no {
  color: #333;
  font-weight: 500;
}

.order-time {
  color: #999;
}

.order-price {
  font-size: 16px;
  color: #ff4d4f;
  font-weight: 700;
  display: flex;
  align-items: baseline;
}

/* 商品区域 */
.goods-section {
  padding: 20px;
}

.goods-item {
  display: grid;
  grid-template-columns: 1fr 100px 80px 100px;
  align-items: center;
  padding: 10px 15px;
}

.goods-col {
  display: flex;
  gap: 15px;
  align-items: center;
}

.goods-img {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  overflow: hidden;
}

.goods-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.goods-name {
  font-size: 14px;
  color: #333;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

.price-col, .total-col {
  font-size: 14px;
  color: #165DFF;
  font-weight: 600;
  display: flex;
  align-items: baseline;
}

.price-symbol {
  font-size: 12px;
  margin-right: 2px;
}

.quantity-col {
  font-size: 14px;
  color: #666;
  text-align: center;
}

/* 页脚样式 */
.mall-footer {
  background-color: #1D2129;
  color: #b2bec3;
  padding: 40px 0 20px;
  margin-top: auto;
}

.footer-content {
  text-align: center;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-bottom: 25px;
  flex-wrap: wrap;
}

.footer-links a {
  color: #b2bec3;
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s;
  padding: 5px 0;
}

.footer-links a:hover {
  color: #165DFF;
  border-bottom: 1px solid #165DFF;
}

.copyright {
  font-size: 12px;
  line-height: 1.8;
  color: #7f8c8d;
}

/* 响应式适配 */
@media (max-width: 1200px) {
  .container {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .goods-item {
    grid-template-columns: 1fr 80px 60px 80px;
  }
  .goods-img {
    width: 50px;
    height: 50px;
  }
}

@media (max-width: 480px) {
  .goods-item {
    display: flex;
    flex-wrap: wrap;
  }
  .goods-col {
    width: 100%;
    margin-bottom: 10px;
  }
  .price-col, .quantity-col, .total-col {
    width: 33.33%;
    text-align: center;
  }
  .order-header-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  .order-left-info {
    flex-direction: column;
    gap: 5px;
  }
}
</style>