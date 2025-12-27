<script setup>
import { onMounted, reactive, ref, computed } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import request from "@/utils/request.js";

// 响应式数据
const data = reactive({
  nowUser: null,
  money: 0,
  cartList: [], // 购物车列表
  selectAll: false, // 是否全选
  loading: false // 加载状态
});

// 计算属性 - 选中商品的总价
const selectedTotalPrice = computed(() => {
  return data.cartList
      .filter(item => item.checked)
      .reduce((total, item) => total + item.totalPrice * item.num, 0)
      .toFixed(2);
});

// 计算属性 - 选中商品的数量
const selectedCount = computed(() => {
  return data.cartList
      .filter(item => item.checked)
      .reduce((total, item) => total + item.num, 0);
});

// 时间格式化工具函数 - UTC转本地时间
const formatTime = (utcTime) => {
  if (!utcTime) return '';
  const date = new Date(utcTime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};
const toHome=()=>{
  location.href = '/';
}
const toOrder=()=>{
  location.href = '/order';
}
// 加载购物车数据
const loadCartData = () => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录');
    location.href = '/login';
    return;
  }

  data.loading = true;
  const userId = data.nowUser.userId || data.nowUser.id || data.nowUser;

  // 请求购物车数据
  request.get(`/cart/getAll/${userId}`)
      .then(res => {
        console.log('加载购物车数据成功：', res.data);
        // 数据映射+补全字段，完全匹配后端返回结构
        data.cartList = res.data.map(item => ({
          ...item,
          checked: true, // 默认选中
          goodsImg: item.goodsImg || 'https://via.placeholder.com/300x300?text=暂无图片',
          num: item.num || 1,
          stock: 999, // 默认库存
          createTime: formatTime(item.createTime)
        }));
        data.selectAll = data.cartList.length > 0;
      })
      .catch(error => {
        console.error('加载购物车失败：', error);
        ElMessage.error('加载购物车失败，请重试');
      })
      .finally(() => {
        data.loading = false;
      });
};

// 全选/取消全选
const toggleSelectAll = () => {
  data.cartList.forEach(item => {
    item.checked = data.selectAll;
  });
};

// 单个商品选中状态切换
const toggleItemCheck = (index) => {
  data.cartList[index].checked = !data.cartList[index].checked;
  data.selectAll = data.cartList.every(item => item.checked);
};

// 修改商品数量
const changeQuantity = (index, type) => {
  const item = data.cartList[index];
  if (type === 'minus') {
    if (item.num <= 1) return;
    item.num--;
  } else {
    if (item.num >= item.stock) {
      ElMessage.warning(`库存最多只有${item.stock}件`);
      return;
    }
    item.num++;
  }
  // 同步修改后端
  updateCartQuantity(item.goodsId, item.num);
};

// 同步修改购物车数量到后端
const updateCartQuantity = (goodsId, num) => {
  const userId = data.nowUser.userId || data.nowUser.id || data.nowUser;
  request.post(`/cart/update/${userId}/${goodsId}/${num}`)
      .catch(error => {
        console.error('更新数量失败：', error);
        ElMessage.error('更新数量失败，请重试');
      });
};

// 删除购物车商品
const deleteCartItem = (cartId, index) => {
  ElMessageBox.confirm(
      '确定要删除该商品吗？',
      '温馨提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        confirmButtonClass: '!bg-[#165DFF]',
      }
  ).then(() => {
    const userId = data.nowUser;
    request.delete(`/cart/delete/${cartId}`)
        .then(() => {
          data.cartList.splice(index, 1);
          ElMessage.success('删除成功');
          data.selectAll = data.cartList.every(item => item.checked);
        })
        .catch(error => {
          console.error('删除失败：', error);
          ElMessage.error('删除失败，请重试');
        });
  });
};

// 清空购物车
const clearCart = () => {
  if (data.cartList.length === 0) {
    ElMessage.info('购物车已为空');
    return;
  }

  ElMessageBox.confirm(
      '确定要清空购物车吗？',
      '温馨提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        confirmButtonClass: '!bg-[#165DFF]',
      }
  ).then(() => {
    const userId = data.nowUser.userId || data.nowUser.id || data.nowUser;
    request.delete(`/cart/clear/${userId}`)
        .then(() => {
          data.cartList = [];
          data.selectAll = false;
          ElMessage.success('清空购物车成功');
        })
        .catch(error => {
          console.error('清空失败：', error);
          ElMessage.error('清空失败，请重试');
        });
  });
};

// 去结算
const toCheckout = () => {
  const selectedItems = data.cartList.filter(item => item.checked);
  if (selectedItems.length === 0) {
    ElMessage.warning('请选择要结算的商品');
    return;
  }
  const nowUser=data.nowUser;
  request.get(`/order/add/${nowUser}`)
      .then(res => {
        ElMessage.success('购买成功！');
        data.money = res.data;
        localStorage.setItem('money', JSON.stringify(res.data));
      })
      // 必加：捕获请求失败的异常（比如接口报错、网络问题、登录失效）
      .catch(err => {
        ElMessage.error('购买失败，请稍后重试！');
        console.log('下单请求异常：', err);
      })
  location.href = '/order';
};

// 返回商品列表
const backToProductList = () => {
  location.href = '/';
};

// 页面加载获取用户信息并加载购物车
onMounted(() => {
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  data.money = JSON.parse(localStorage.getItem('money'));
  if (data.nowUser) {
    loadCartData();
  } else {
    ElMessage.warning('请先登录');
    location.href = '/login';
  }
});
</script>

<template>
  <div class="cart-app">
    <!-- 顶部导航栏 -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>网上数码商城</h1>
            <p class="logo-slogan">品质数码 · 极致体验</p>
          </div>
          <div class="user-area">
            <div v-if="!data.nowUser">
              <el-button type="primary" @click="location.href='/login'" class="login-btn" icon="el-icon-user">登录</el-button>
              <el-button type="default" @click="location.href='/register'" class="register-btn">注册</el-button>
            </div>
            <div v-else class="user-info">
              <el-avatar :src="data.nowUser.avatar || ''" icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser.username || data.nowUser }}</span>
              <el-button type="text"  class="home-btn" icon="el-icon-s-home" @click="toHome">返回首页</el-button>
              <el-button type="text" class="order-btn" icon="el-icon-menu" @click="toOrder">我的订单</el-button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="cart-main">
      <div class="container">
        <div class="breadcrumb">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的购物车</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="cart-header">
          <h2 class="cart-title">
            <i class="el-icon-shopping-cart-full"></i> 我的购物车
          </h2>
          <div class="header-actions">
            <el-checkbox v-model="data.selectAll" @change="toggleSelectAll" class="select-all">全选</el-checkbox>
            <el-button type="text" class="clear-cart-btn" @click="clearCart" icon="el-icon-delete">清空购物车</el-button>
          </div>
        </div>

        <div class="cart-content" v-loading="data.loading" element-loading-text="加载购物车中...">
          <div v-if="data.cartList.length === 0" class="empty-cart">
            <el-empty description="您的购物车还是空空如也～" image-size="160">
              <el-button type="primary" @click="backToProductList" class="go-shopping-btn">
                <i class="el-icon-shopping-cart"></i> 立即去选购
              </el-button>
            </el-empty>
          </div>

          <div v-else class="cart-grid">
            <div
                v-for="(item, index) in data.cartList"
                :key="item.cartId"
                class="cart-card"
                :class="{ 'cart-card-selected': item.checked }"
            >
              <!-- 选中状态标记 -->
              <div class="card-check">
                <el-checkbox v-model="item.checked" @change="toggleItemCheck(index)" size="large"></el-checkbox>
              </div>

              <!-- 商品图片 -->
              <div class="card-img">
                <el-image :src="item.goodsImg" fit="cover" preview-src-list="[item.goodsImg]" />
              </div>

              <!-- 商品信息 -->
              <div class="card-info">
                <h4 class="goods-name">{{ item.goodsName }}</h4>
                <p class="goods-category">
                  <i class="el-icon-folder-opened"></i> {{ item.categoryName || '数码配件' }}
                </p>
                <div class="goods-price">
                  <span class="price-symbol">¥</span>
                  <span class="price-num">{{ item.totalPrice.toFixed(2) }}</span>
                </div>
              </div>

              <!-- 数量控制 -->
              <div class="card-quantity">
                <div class="quantity-control">
                  <el-button type="text" size="small" @click="changeQuantity(index, 'minus')" icon="el-icon-minus" :disabled="item.num <= 1"></el-button>
                  <input type="number" v-model="item.num" min="1" :max="item.stock" @change="updateCartQuantity(item.goodsId, item.num)" class="quantity-input">
                  <el-button type="text" size="small" @click="changeQuantity(index, 'plus')" icon="el-icon-plus" :disabled="item.num >= item.stock"></el-button>
                </div>
              </div>

              <!-- 小计和操作 -->
              <div class="card-footer">
                <div class="subtotal">
                  小计：<span class="subtotal-price">¥{{ (item.totalPrice * item.num).toFixed(2) }}</span>
                </div>
                <el-button type="text" class="delete-btn" @click="deleteCartItem(item.cartId, index)" icon="el-icon-delete">删除</el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 结算栏 - 固定在底部 -->
        <div v-if="data.cartList.length > 0" class="cart-footer-fixed">
          <div class="container">
            <div class="footer-content">
              <div class="select-info">
                <span class="selected-count">已选 <strong>{{ selectedCount }}</strong> 件商品</span>
              </div>
              <div class="price-summary">
                <span class="balance-text">账户余额：<em>¥{{ data.money?.toFixed(2) || 0.00 }}</em></span>
                <span class="total-price">
                  合计：<b>¥{{ selectedTotalPrice }}</b>
                </span>
              </div>
              <el-button type="primary" class="checkout-btn" @click="toCheckout" icon="el-icon-circle-check" :disabled="selectedCount === 0">立即结算</el-button>
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
            <p>© 2025 数码优选 版权所有 | 专注高品质数码产品销售</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* 全局基础样式 */
.cart-app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f2f5f8;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  color: #2d3748;
}
.container {
  width: 1240px;
  margin: 0 auto;
  padding: 0 20px;
}
* {margin:0;padding:0;box-sizing: border-box;}
a{text-decoration: none;color: inherit;}

/* 顶部导航栏 */
.mall-header {
  background-color: #ffffff;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.04);
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
  color: #94a3b8;
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
  width: 42px;
  height: 42px;
  background-color: #e8f3ff;
  color: #165DFF;
  border: 1px solid #dbeafe;
}
.user-name {
  font-size: 14px;
  color: #334155;
  max-width: 90px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.login-btn {
  border-radius: 22px !important;
  padding: 8px 26px !important;
  background-color: #165DFF !important;
  border-color: #165DFF !important;
  font-weight: 500;
}
.register-btn {
  border-radius: 22px !important;
  padding: 8px 22px !important;
  color: #165DFF !important;
  border-color: #165DFF !important;
}
.home-btn, .order-btn {
  color: #64748b !important;
  transition: all 0.3s ease;
  font-size: 14px;
}
.home-btn:hover, .order-btn:hover {
  color: #165DFF !important;
}

/* 主体区域 */
.cart-main {
  flex: 1;
  padding: 30px 0 100px; /* 底部留出固定结算栏的空间 */
}
.breadcrumb {
  margin-bottom: 25px;
  padding: 10px 0;
}
.el-breadcrumb__item:last-child .el-breadcrumb__inner {
  color: #165DFF !important;
  font-weight: 600;
}

/* 购物车头部 */
.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  padding-bottom: 18px;
  border-bottom: 1px solid #e2e8f0;
}
.cart-title {
  font-size: 22px;
  color: #1e293b;
  margin: 0;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}
.cart-title i {
  color: #165DFF;
  font-size: 24px;
}
.header-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}
.select-all {
  font-size: 14px;
  color: #64748b;
}
.clear-cart-btn {
  color: #ef4444 !important;
  font-size: 14px;
}
.clear-cart-btn:hover {
  color: #f87171 !important;
}

/* 购物车主内容 */
.cart-content {
  min-height: 400px;
}
.empty-cart {
  padding: 100px 0;
  text-align: center;
}
.go-shopping-btn {
  background-color: #165DFF !important;
  border-color: #165DFF !important;
  border-radius: 24px !important;
  padding: 10px 36px !important;
  margin-top: 25px;
  font-weight: 500;
}

/* 卡片式网格布局 */
.cart-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
  gap: 20px;
}
.cart-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transition: all 0.3s ease;
}
.cart-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
}
.cart-card-selected {
  border: 2px solid #165DFF;
  box-shadow: 0 0 0 4px rgba(22, 93, 255, 0.1);
}

/* 选中状态标记 */
.card-check {
  position: absolute;
  top: 15px;
  right: 15px;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  padding: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 商品图片 */
.card-img {
  height: 220px;
  overflow: hidden;
}
.card-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}
.cart-card:hover .card-img img {
  transform: scale(1.05);
}

/* 商品信息 */
.card-info {
  padding: 20px;
  flex: 1;
}
.goods-name {
  font-size: 16px;
  color: #1e293b;
  margin: 0 0 10px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.5;
}
.goods-category {
  font-size: 12px;
  color: #94a3b8;
  margin: 0 0 15px 0;
  display: flex;
  align-items: center;
}
.goods-price {
  font-size: 18px;
  color: #ef4444;
  font-weight: 700;
  display: flex;
  align-items: baseline;
}
.price-symbol {
  font-size: 14px;
}
.price-num {
  font-size: 20px;
}

/* 数量控制 */
.card-quantity {
  padding: 0 20px 15px;
}
.quantity-control {
  display: flex;
  align-items: center;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  width: 120px;
  height: 38px;
  overflow: hidden;
}
.quantity-control .el-button {
  width: 38px;
  height: 36px;
  padding: 0;
  color: #64748b;
  transition: all 0.2s;
}
.quantity-control .el-button:hover:not(:disabled) {
  color: #165DFF;
  background-color: #f1f5f9;
}
.quantity-input {
  flex: 1;
  height: 36px;
  border: none;
  text-align: center;
  font-size: 14px;
  outline: none;
  color: #1e293b;
}

/* 卡片底部 */
.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-top: 1px solid #f1f5f9;
}
.subtotal {
  font-size: 14px;
  color: #64748b;
}
.subtotal-price {
  color: #ef4444;
  font-weight: 600;
  font-size: 16px;
  margin-left: 5px;
}
.delete-btn {
  color: #94a3b8 !important;
  font-size: 14px;
  transition: all 0.3s;
}
.delete-btn:hover {
  color: #ef4444 !important;
}

/* 固定底部结算栏 */
.cart-footer-fixed {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #ffffff;
  box-shadow: 0 -5px 20px rgba(0, 0, 0, 0.08);
  padding: 15px 0;
  z-index: 998;
}
.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.select-info {
  font-size: 14px;
  color: #64748b;
}
.select-info strong {
  color: #165DFF;
  font-weight: 700;
  font-size: 16px;
}
.price-summary {
  display: flex;
  align-items: center;
  gap: 30px;
}
.balance-text {
  color: #64748b;
  font-size: 14px;
}
.balance-text em {
  color: #10b981;
  font-style: normal;
  font-weight: 600;
  font-size: 15px;
}
.total-price {
  color: #64748b;
  font-size: 16px;
}
.total-price b {
  color: #ef4444;
  font-size: 22px;
  font-weight: 700;
  margin-left: 8px;
}
.checkout-btn {
  background-color: #165DFF !important;
  border-color: #165DFF !important;
  border-radius: 24px !important;
  padding: 12px 45px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
}
.checkout-btn:disabled {
  background-color: #cbd5e1 !important;
  border-color: #cbd5e1 !important;
  cursor: not-allowed;
}

/* 页脚 */
.mall-footer {
  background-color: #1e293b;
  color: #cbd5e1;
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
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.footer-links a {
  color: #cbd5e1;
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s;
}
.footer-links a:hover {
  color: #165DFF;
}
.copyright {
  font-size: 12px;
  color: #94a3b8;
}

/* 响应式适配 */
@media (max-width: 1240px) {
  .container { width: 100%; }
}
@media (max-width: 992px) {
  .cart-grid { grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); }
  .price-summary { gap: 15px; }
  .total-price b { font-size: 18px; }
  .checkout-btn { padding: 10px 30px !important; }
}
@media (max-width: 768px) {
  .cart-grid { grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); }
  .footer-content { flex-wrap: wrap; gap: 15px; }
  .select-info { width: 100%; text-align: center; }
  .price-summary { width: 100%; justify-content: center; }
  .checkout-btn { width: 100%; }
}
@media (max-width: 480px) {
  .cart-grid { grid-template-columns: 1fr; }
  .card-img { height: 180px; }
  .header-actions { gap: 10px; }
  .select-all { font-size: 12px; }
  .clear-cart-btn { font-size: 12px; }
}
</style>