<script setup>
import { onMounted, reactive, computed } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from "@/utils/request.js";

const data = reactive({
  nowUser: null,
  orderItems: [], // 从购物车选中的商品
  totalPrice: 0,  // 订单总价
  mony:0, //用户余额
});

// 初始化页面数据
const initData = () => {
  // 获取当前登录用户
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  if (!data.nowUser) {
    ElMessage.warning('请先登录');
    setTimeout(() => {
      location.href = '/login';
    }, 1500);
    return;
  }

  // 获取购物车选中的商品
  const selectedItems = JSON.parse(localStorage.getItem('orderItems'));
  if (!selectedItems || selectedItems.length === 0) {
    ElMessage.warning('暂无结算商品');
    setTimeout(() => {
      location.href = '/cart';
    }, 1500);
    return;
  }
  data.orderItems = selectedItems;
  // 获取用户余额
  request.get("/users/getByUserName", {
    params: { username: data.nowUser }
  }).then(res => {
    console.log(res.data)
    data.mony=res.data;
  }).catch(() => {
    ElMessage.error('获取用户信息失败');
  });
};

// 计算订单总价
const calcTotalPrice = computed(() => {
  return data.orderItems
      .reduce((sum, item) => sum + (item.productPrice * item.nums), 0)
      .toFixed(2);
});

// 生成订单
const createOrder = () => {

  if (data.mony < calcTotalPrice.value) {
    ElMessage.warning('余额不足，无法下单');
    return;
  }

  // 构造订单数据
  const orderData = {
    username: data.nowUser,
    orderItems: data.orderItems,
    totalPrice: calcTotalPrice.value,
  };
console.log(orderData)
  ElMessageBox.confirm(
      `确认下单？订单总价：¥${calcTotalPrice.value}`,
      '订单确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'info'
      }
  ).then(() => {
    request.post("/order/add", orderData)
        .then(res => {

          localStorage.removeItem('orderItems');
          // 扣减余额
          request.post("/users/updateMoney", {
            username: data.nowUser,
            money: data.mony - calcTotalPrice.value
          }).then(res => {
            ElMessage.success('下单成功！');
            setTimeout(() => {
              location.href = '/order-list'; // 跳转到已购订单列表
            }, 1500);
          }).catch(() => {
            ElMessage.error('扣减余额失败，请联系管理员');
          });
        })
        .catch(() => {
          ElMessage.error('下单失败，请重试');
        });
  }).catch(() => {
    ElMessage.info('已取消下单');
  });
};

// 返回购物车
const backToCart = () => {
  location.href = '/cart';
};

onMounted(() => {
  initData();
});
const toCart = () => {
  location.href = '/cart';
};
const toOrderList = () => {
  location.href = '/order-list';
};
const toHome = () => {
  location.href = '/home';
};
const toChange =()=>{
  location.href = '/change';
};
</script>

<template>
  <div class="mall-app">
    <!-- 顶部导航栏（复用购物车的导航栏） -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>优购商城</h1>
          </div>
          <div class="search-box">

          </div>
          <div class="user-area">
            <div class="user-info">
              <el-avatar icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser }}</span>
              <span class="user-name">余额：{{ data.mony }}</span>
              <el-button
                  type="text"
                  @click="location.href='/cart'"
                  class="cart-btn"
                  icon="el-icon-shopping-cart-full"
              >
                购物车
              </el-button>
            </div>
          </div>
        </div>
        <nav class="category-nav">
          <ul class="category-list">
            <li @click="toHome">首页</li>
            <li @click="toCart">购物车</li>
            <li @click="toOrderList">我的订单</li>
          </ul>
        </nav>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="mall-main order-confirm-main">
      <div class="container">
        <div class="breadcrumb">
          <a href="/">首页</a>
          <span>/</span>
          <a href="/cart">购物车</a>
          <span>/</span>
          <span class="current">订单确认</span>
        </div>

        <!-- 订单确认标题 -->
        <div class="order-confirm-header">
          <h2 class="order-confirm-title">确认订单信息</h2>
        </div>



        <!-- 订单商品列表 -->
        <div class="order-items-section">
          <h3 class="section-title">商品清单</h3>
          <div class="order-items-table">
            <!-- 表头 -->
            <div class="order-thead">
              <div class="order-th product-col">商品信息</div>
              <div class="order-th price-col">单价</div>
              <div class="order-th count-col">数量</div>
              <div class="order-th total-col">小计</div>
            </div>
            <!-- 商品列表 -->
            <div class="order-tbody">
              <div
                  v-for="(item, index) in data.orderItems"
                  :key="item.id"
                  class="order-tr"
              >
                <div class="order-td product-col">
                  <div class="product-info">
                    <el-image
                        :src="item.productImage"
                        :preview-src-list="[item.productImage]"
                        class="product-img"
                    />
                    <div class="product-detail">
                      <h3 class="product-name">{{ item.productName }}</h3>
                    </div>
                  </div>
                </div>
                <div class="order-td price-col">¥{{ item.productPrice.toFixed(2) }}</div>
                <div class="order-td count-col">{{ item.nums }}</div>
                <div class="order-td total-col">¥{{ (item.productPrice * item.nums).toFixed(2) }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 订单结算 -->
        <div class="order-settle-section">
          <div class="order-settle-card">
            <div class="settle-item">
              <span class="label">商品总价：</span>
              <span class="value">¥{{ calcTotalPrice }}</span>
            </div>
            <div class="settle-item">
              <span class="label">运费：</span>
              <span class="value">¥0.00</span>
            </div>
            <div class="settle-item total">
              <span class="label">订单总价：</span>
              <span class="value">¥{{ calcTotalPrice }}</span>
            </div>
            <div class="settle-btns">
              <el-button type="default" @click="backToCart" class="back-btn">返回购物车</el-button>
              <el-button type="primary" @click="createOrder" class="submit-btn">提交订单</el-button>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* 复用购物车的基础样式 */
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

.search-box {
  width: 40%;
}

.search-input {
  height: 40px;
  border-radius: 20px !important;
  padding: 0 20px;
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

.cart-btn {
  color: #666 !important;
  transition: color 0.3s;
}

.cart-btn:hover {
  color: #ff4400 !important;
}

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

/* 订单确认页面特有样式 */
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

.order-confirm-header {
  margin-bottom: 30px;
}

.order-confirm-title {
  font-size: 20px;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.section-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}





.pay-type-options {
  padding: 10px 0;
}

.el-radio {
  margin-right: 20px;
}

.order-items-table {width: 100%;
}

.order-thead {
  display: flex;
  background-color: #fafafa;
  border-bottom: 1px solid #f0f0f0;
}

.order-th {
  padding: 15px 0;
  font-size: 14px;
  color: #666;
  font-weight: 500;
  text-align: center;
}

.product-col {
  flex: 1;
  text-align: left !important;
  padding-left: 20px;
}

.price-col, .count-col, .total-col {
  width: 150px;
}

.order-tbody .order-tr {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
  padding: 15px 0;
}

.order-td {
  font-size: 14px;
  color: #333;
  text-align: center;
  align-self: center;
}

.product-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.product-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #f0f0f0;
}

.product-detail {
  text-align: left;
}

.product-name {
  font-size: 14px;
  color: #333;
  margin: 0;
}

.order-settle-section {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.order-settle-card {
  max-width: 400px;
  margin-left: auto;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  padding: 20px;
}

.settle-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  font-size: 14px;
  border-bottom: 1px dashed #f0f0f0;
}

.settle-item.total {
  font-size: 16px;
  font-weight: 600;
  border-bottom: none;
  margin-top: 10px;
}

.settle-item.total .value {
  color: #ff4400;
  font-size: 18px;
}

.settle-btns {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.back-btn {
  border-color: #dcdcdc !important;
  color: #666 !important;
}

.submit-btn {
  background-color: #ff4400 !important;
  border-color: #ff4400 !important;
  border-radius: 20px !important;
  padding: 0 30px;
}

.submit-btn:hover {
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

  .search-box {
    width: 100%;
  }

  .order-settle-card {
    max-width: 100%;
  }

  .price-col, .count-col, .total-col {
    width: 100px;
  }
}
</style>