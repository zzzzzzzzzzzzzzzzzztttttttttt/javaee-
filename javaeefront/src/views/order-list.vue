<script setup>
import { onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox, ElPagination } from "element-plus";
import request from "@/utils/request.js";

const data = reactive({
  nowUser: null,
  orderList: [], // 订单列表
  loading: false, // 加载状态

});
// 在<script setup>中添加
const formatShortTime = (timeStr) => {
  if (!timeStr) return '未知时间';
  // 处理时间字符串（兼容后端返回的ISO格式）
  const date = new Date(timeStr);
  // 格式化：年-月-日 时:分:秒（补零处理）
  const y = date.getFullYear();
  const m = (date.getMonth() + 1).toString().padStart(2, '0');
  const d = date.getDate().toString().padStart(2, '0');
  const hh = date.getHours().toString().padStart(2, '0');
  const mm = date.getMinutes().toString().padStart(2, '0');
  const ss = date.getSeconds().toString().padStart(2, '0');
  // 返回短格式（也可以根据需求去掉秒，比如 `${y}-${m}-${d} ${hh}:${mm}`）
  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`;
};
// 订单详情弹窗
const detailDialog = ref(false);
const currentOrder = reactive({});

// 初始化订单列表
const loadOrderList = () => {
  data.loading = true;
  request.get("/order/list", {
    params: {
      username: data.nowUser,
    }
  }).then(res => {
    data.orderList = res.data || [];
    data.loading = false;
    console.log(data.orderList)
  }).catch(() => {
    ElMessage.error('获取订单列表失败');
    data.loading = false;
  });
};

// 初始化页面
const initData = () => {
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  if (!data.nowUser) {
    ElMessage.warning('请先登录');
    setTimeout(() => {
      location.href = '/login';
    }, 1500);
    return;
  }
  loadOrderList();
};



// 取消订单
const cancelOrder = (orderId) => {
  ElMessageBox.confirm(
      '确定要取消该订单吗？',
      '订单取消',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
  ).then(() => {
    request.put(`/order/cancel/${orderId}`)
        .then(() => {
          ElMessage.success('订单已取消');
          loadOrderList(); // 刷新订单列表
        })
        .catch(() => {
          ElMessage.error('取消订单失败，请重试');
        });
  }).catch(() => {
    ElMessage.info('已取消操作');
  });
};

// 分页切换
const toCart = () => {
  location.href = '/cart';
};
const toOrderList = () => {
  location.href = '/order-list';
};
const toHome = () => {
  location.href = '/home';
};

// 返回首页
const backToHome = () => {
  location.href = '/';
};
const toChange =()=>{
  location.href = '/change';
};
onMounted(() => {
  initData();
});
</script>

<template>
  <div class="mall-app">
    <!-- 顶部导航栏 -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>优购商城</h1>
          </div>

          <div class="user-area">
            <div class="user-info">
              <el-avatar icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser }}</span>
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
            <li class="active" @click="location.href='/order-list'">我的订单</li>
          </ul>
        </nav>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="mall-main order-list-main">
      <div class="container">
        <div class="breadcrumb">
          <a href="/">首页</a>
          <span>/</span>
          <span class="current">我的订单</span>
        </div>

        <!-- 订单列表标题 -->
        <div class="order-list-header">
          <h2 class="order-list-title">我的订单</h2>
        </div>

        <!-- 订单列表为空 -->
        <div v-if="data.orderList.length === 0 " class="empty-order">
          <el-empty
              description="您暂无已下单的商品~"
              style="--el-empty-padding: 80px 0;"
          >
            <el-button
                type="primary"
                class="go-shopping-btn"
                @click="backToHome"
            >
              去购物
            </el-button>
          </el-empty>
        </div>

        <!-- 订单列表 -->
        <div v-else class="order-list-container">
          <el-table
              v-loading="data.loading"
              :data="data.orderList"
              border
              stripe
              class="order-table"
              style="width: 100%;"
              :empty-text="data.loading ? '加载中...' : '暂无订单数据'"
          >

            <el-table-column
                label="商品信息"
                min-width="300"
            >
              <template #default="scope">
                <!-- 关键修改：scope.row 代表当前行的订单数据 -->
                <!-- 假设订单行有 orderItems 字段存商品列表，若无则兼容当前行 -->
                <div
                    v-for="(item, idx) in scope.row.orderItems || [scope.row]"
                    :key="idx"
                    class="order-item-info"
                >
                  <!-- 图片添加默认值，避免空白 -->
                  <el-image
                      :src="item.productImage || 'https://via.placeholder.com/40x40?text=暂无图片'"
                      class="item-img"
                      fit="cover"
                  />
                  <!-- 商品名和数量添加默认值 -->
                  <span class="item-name">
            {{ item.productName || '未知商品' }} x {{ item.nums || 1 }}
          </span>
                </div>
              </template>
            </el-table-column>

            <!-- 订单总价 - 格式化显示（可选，优化体验） -->
            <el-table-column
                prop="totalPrice"
                label="订单总价"
                width="120"
            >
              <template #default="scope">
                ¥{{ Number(scope.row.price).toFixed(2) }}
              </template>
            </el-table-column>

            <!-- 订单状态列 - 修复：显示状态文本 + 样式优化 -->
            <el-table-column
                label="订单状态"
                width="120"
            >
              <template #default="scope">
                <el-tag
                    :type="scope.row.orderStatus === '待付款' ? 'warning' :
                  scope.row.orderStatus === '已取消' ? 'danger' :
                  scope.row.orderStatus === '已完成' ? 'success' : 'info'"
                >
                  {{ scope.row.orderStatus || '已付款' }}
                </el-tag>
              </template>
            </el-table-column>

            <!-- 下单时间 -->
            <el-table-column
                prop="createTime"
                label="下单时间"
                width="200"
            >
              <template #default="scope">
                {{ formatShortTime(scope.row.date) }}
              </template>
            </el-table-column>


          </el-table>
        </div>
      </div>
    </main>

    <!-- 订单详情弹窗 -->
    <el-dialog
        v-model="detailDialog"
        title="订单详情"
        width="800px"
        center
    >
      <div v-if="currentOrder.id" class="order-detail">
        <div class="detail-item">
          <span class="label">订单编号：</span>
          <span class="value">{{ currentOrder.orderNo }}</span>
        </div>
        <div class="detail-item">
          <span class="label">下单用户：</span>
          <span class="value">{{ currentOrder.username }}</span>
        </div>
        <div class="detail-item">
          <span class="label">收货地址：</span>
          <span class="value">{{ currentOrder.address }}</span>
        </div>
        <div class="detail-item">
          <span class="label">支付方式：</span>
          <span class="value">{{ currentOrder.payType === 'online' ? '在线支付' : '货到付款' }}</span>
        </div>
        <div class="detail-item">
          <span class="label">订单状态：</span>
          <el-tag
              :type="currentOrder.orderStatus === '待付款' ? 'warning' : currentOrder.orderStatus === '已取消' ? 'danger' : 'success'"
          >
            {{ currentOrder.orderStatus }}
          </el-tag>
        </div>
        <div class="detail-item">
          <span class="label">下单时间：</span>
          <span class="value">{{ currentOrder.createTime }}</span>
        </div>

        <h3 class="detail-title">商品清单</h3>
        <el-table
            :data="currentOrder.orderItems"
            border
            style="width: 100%;"
        >
          <el-table-column
              label="商品图片"
              width="100"
          >
            <template #default="scope">
              <el-image :src="scope.row.productImage" class="detail-img" />
            </template>
          </el-table-column>
          <el-table-column
              prop="productName"
              label="商品名称"
              min-width="200"
          />
          <el-table-column
              prop="productPrice"
              label="单价"
              width="100"
              formatter="(row) => `¥${row.productPrice}`"
          />
          <el-table-column
              prop="nums"
              label="数量"
              width="80"
          />
          <el-table-column
              label="小计"
              width="100"
              formatter="(row) => `¥${(row.productPrice * row.nums).toFixed(2)}`"
          />
        </el-table>

        <div class="detail-total">
          <span class="total-label">订单总价：</span>
          <span class="total-value">¥{{ currentOrder.totalPrice }}</span>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
/* 复用基础样式 */
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

.category-list li.active {
  background-color: #e03c00;
}

.category-list li:hover {
  background-color: #e03c00;
}

/* 订单列表页面特有样式 */
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

.order-list-header {
  margin-bottom: 30px;
}

.order-list-title {
  font-size: 20px;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.empty-order {
  background-color: #fff;
  border-radius: 8px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.go-shopping-btn {
  background-color: #ff4400 !important;
  border-color: #ff4400 !important;
  margin-top: 20px;
  border-radius: 20px !important;
  padding: 0 24px;
}

.order-list-container {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.order-table {
  margin-bottom: 20px;
}

.order-item-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 5px;
}

.item-img {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 4px;
}

.item-name {
  font-size: 14px;
  color: #333;
}

.pagination-container {
  display: flex;
  justify-content: center;
  padding: 10px 0;
}

/* 订单详情弹窗样式 */
.order-detail {
  padding: 10px 0;
}

.detail-item {
  display: flex;
  margin-bottom: 15px;
  font-size: 14px;
}

.detail-item .label {
  width: 100px;
  color: #666;
  text-align: right;
  padding-right: 10px;
}

.detail-item .value {
  flex: 1;
  color: #333;
}

.detail-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin: 20px 0 10px;
}

.detail-img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.detail-total {
  text-align: right;
  margin-top: 20px;
  font-size: 16px;
  font-weight: 600;
}

.total-label {
  color: #666;
  margin-right: 10px;
}

.total-value {
  color: #ff4400;
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

  .order-table {
    font-size: 12px;
  }

  .el-table-column {
    min-width: 80px !important;
  }
}
</style>