<script setup>
import { onMounted, reactive, computed } from "vue";
import { ShoppingCart, Delete, Star } from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import { ElMessage, ElMessageBox } from "element-plus";

const data = reactive({
  nowUser: null,
  cartList: [],
  allChecked: false, // 全选状态
  mony:0,
  // 适配后端 UserCart 实体类字段：productName、productPrice、productImage、productNum
});

// 加载用户信息和购物车数据
const loadData = () => {
  // 获取当前登录用户
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  console.log("当前登录用户：", data.nowUser);

  if (!data.nowUser) {
    // 未登录跳转到登录页
    ElMessage.warning('请先登录查看购物车');
    setTimeout(() => {
      location.href = '/login';
    }, 1500);
    return;
  }
  request.get("/users/getByUserName", {
    params: { username: data.nowUser }
  }).then(res => {
    console.log(res.data)
    data.mony=res.data;
  }).catch(() => {
    ElMessage.error('获取用户信息失败');
  });
  // 请求购物车数据
  request.get("/cart/getCart", {
    params: { username: data.nowUser }
  }).then(res => {
    data.cartList = res.data || [];


    // 初始化：给每个商品添加勾选状态、默认数量和价格（避免 undefined）
    data.cartList.forEach(item => {
      item.checked = true; // 默认勾选
      item.nums = item.nums ?? 1; // 数量默认1
      item.productPrice = item.productPrice ?? 0; // 价格默认0
      item.productName = item.productName ?? '未知商品'; // 商品名默认值
      item.productImage = item.productImage ?? 'https://via.placeholder.com/80x80?text=暂无图片'; // 默认图片
    });
  }).catch(() => {
    ElMessage.error('获取购物车数据失败');
  });
};

// 计算选中商品总价
const totalPrice = computed(() => {
  return data.cartList
      .filter(item => item.checked)
      .reduce((sum, item) => sum + (item.productPrice * item.nums), 0)
      .toFixed(2);
});

// 计算选中商品总数
const totalCount = computed(() => {
  return data.cartList
      .filter(item => item.checked)
      .reduce((sum, item) => sum + item.nums, 0);
});

// 全选/取消全选
const toggleAllChecked = () => {
  data.allChecked = !data.allChecked;
  data.cartList.forEach(item => {
    item.checked = data.allChecked;
  });
};

// 单个商品勾选状态切换
const toggleItemChecked = (index) => {
  data.cartList[index].checked = !data.cartList[index].checked;
  // 更新全选状态
  data.allChecked = data.cartList.every(item => item.checked);
};

// 调整商品数量（加减）
const changeCount = (index, type) => {
  const item = data.cartList[index];
  if (type === 'minus') {
    if (item.nums <= 1) return; // 数量最小为1
    item.nums--;
  } else {
    item.nums++; // 数量上限可按需调整（如：item.productNum < 99）
  }
  updateCartItem(item); // 同步到后端
};

// 直接输入数量
const inputCount = (index, value) => {
  const item = data.cartList[index];
  const num = parseInt(value);
  // 验证数量合法性
  if (isNaN(num) || num < 1) {
    item.nums = 1;
    ElMessage.warning('商品数量不能小于1');
  } else if (num > 99) { // 数量上限：99
    item.nums = 99;
    ElMessage.warning('商品数量不能超过99');
  } else {
    item.nums = num;
  }
  item.nums = num;
  console.log(item.num);
  updateCartItem(item); // 同步到后端
};

// 更新购物车商品（数量/选中状态）
const updateCartItem = (item) => {
  request.put("/cart/update", item)
      .then(() => {
        ElMessage.success('购物车已更新');
      })
      .catch(() => {
        ElMessage.error('更新失败，请重试');
      });
};

// 删除购物车商品
const deleteCartItem = (index, id) => {
  ElMessageBox.confirm(
      '确定要删除该商品吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
  ).then(() => {
    request.delete(`/cart/delete/${id}`)
        .then(() => {
          console.log(id);
          data.cartList.splice(index, 1);
          // 更新全选状态
          data.allChecked = data.cartList.every(item => item.checked);
          ElMessage.success('删除成功');
        })
        .catch(() => {
          ElMessage.error('删除失败，请重试');
        });
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
};

// 结算功能
const settleAccounts = () => {
  const checkedItems = data.cartList.filter(item => item.checked);
  if (checkedItems.length === 0) {
    ElMessage.warning('请选择要结算的商品');
    return;
  }

  // 保存选中商品到本地存储，跳转结算页
  localStorage.setItem('orderItems', JSON.stringify(checkedItems));
  ElMessage.success('正在跳转到结算页面...');
  setTimeout(() => {
    location.href = '/order-confirm'; // 订单确认页路由（确保已配置）
  }, 1500);
};

// 清空购物车
const clearCart = () => {
  if (data.cartList.length === 0) {
    ElMessage.info('购物车已为空');
    return;
  }

  ElMessageBox.confirm(
      '确定要清空购物车吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }
  ).then(() => {
    request.delete("/cart/clear", {
      params: { username: data.nowUser }
    }).then(() => {
      data.cartList = [];
      data.allChecked = false;
      ElMessage.success('购物车已清空');
    }).catch(() => {
      ElMessage.error('清空失败，请重试');
    });
  }).catch(() => {
    ElMessage.info('已取消清空');
  });
};

// 退出登录
const logout = () => {
  localStorage.removeItem('nowUser');
  location.href = '/login';
  ElMessage.success('退出登录成功');
};
const toHome =()=>{
  location.href = '/home';
};
const toList =()=>{
  location.href = '/order-list';
};
const toCharge =()=>{
  location.href = '/charge';
};
// 页面加载时初始化数据
onMounted(() => {
  loadData();
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
            <div v-if="!data.nowUser" class="login-tip">
              <span>请先登录</span>
            </div>
            <div v-else class="user-info">
              <el-avatar icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser }}</span>
              <el-button  type="text"
                          class="cart-btn active"
                          icon="el-icon-shopping-cart-full"
                          @click="toCharge"
              >余额：{{ data.mony }}</el-button>
              <el-button
                  type="text"
                  @click="logout"
                  class="cart-btn active"
                  icon="el-icon-switch-button"
              >
                退出
              </el-button>
              <el-button
                  type="text"
                  class="cart-btn active"
                  icon="el-icon-shopping-cart-full"
              >
                购物车
              </el-button>
              <el-button
                  type="text"
                  class="cart-btn active"
                  icon="el-icon-shopping-cart-full"
                  @click="toList"
              >
                我的订单
              </el-button>
            </div>
          </div>
        </div>

        <!-- 分类导航 -->
        <nav class="category-nav">
          <ul class="category-list">
            <li @click= "toHome">首页</li>

          </ul>
        </nav>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="mall-main cart-main">
      <div class="container">
        <!-- 面包屑导航 -->
        <div class="breadcrumb">
          <a href="/home">首页</a>
          <span>/</span>
          <span class="current">购物车</span>
        </div>

        <!-- 购物车标题 -->
        <div class="cart-header">
          <h2 class="cart-title">
            <el-icon class="title-icon"><ShoppingCart /></el-icon>
            我的购物车
          </h2>
          <el-button
              type="text"
              class="clear-cart-btn"
              @click="clearCart"
          >
            <el-icon><Delete /></el-icon> 清空购物车
          </el-button>
        </div>

        <!-- 购物车为空状态 -->
        <div v-if="data.cartList.length === 0" class="empty-cart">
          <el-empty
              description="您的购物车还是空的~"
              style="--el-empty-padding: 80px 0;"
          >
            <el-button
                type="primary"
                class="go-shopping-btn"
                @click="toHome"
            >
              去购物
            </el-button>
          </el-empty>
        </div>

        <!-- 购物车商品列表 -->
        <div v-else class="cart-container">
          <div class="cart-table">
            <!-- 表头 -->
            <div class="cart-thead">
              <div class="cart-th check-col">
                <el-checkbox
                    v-model="data.allChecked"
                    @change="toggleAllChecked"
                    class="all-check"
                >
                  全选
                </el-checkbox>
              </div>
              <div class="cart-th product-col">商品信息</div>
              <div class="cart-th price-col">单价</div>
              <div class="cart-th count-col">数量</div>
              <div class="cart-th total-col">小计</div>
              <div class="cart-th operate-col">操作</div>
            </div>

            <!-- 商品列表 -->
            <div class="cart-tbody">
              <div
                  v-for="(item, index) in data.cartList"
                  :key="item.id"
                  class="cart-tr"
                  :class="{ checked: item.checked }"
              >
                <!-- 勾选框 -->
                <div class="cart-td check-col">
                  <el-checkbox
                      v-model="item.checked"
                      @change="toggleItemChecked(index)"
                  />
                </div>

                <!-- 商品信息 -->
                <div class="cart-td product-col">
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

                <!-- 单价 -->
                <div class="cart-td price-col">
                  ¥{{ item.productPrice.toFixed(2) }}
                </div>

                <!-- 数量调整 -->
                <div class="cart-td count-col">
                  <div class="count-adjust">
                    <el-button
                        type="text"
                        class="count-minus"
                        @click="changeCount(index, 'minus')"
                        :disabled="item.nums <= 1"
                    >
                      -
                    </el-button>
                    <input
                        type="number"
                        v-model="item.nums"
                        @change="inputCount(index, item.nums)"
                        class="count-input"
                        min="1"
                        max="99"
                    >
                    <el-button
                        type="text"
                        class="count-plus"
                        @click="changeCount(index, 'plus')"
                    >
                      +
                    </el-button>
                  </div>
                </div>

                <!-- 小计 -->
                <div class="cart-td total-col">
                  <span class="product-subtotal">¥{{ (item.productPrice * item.nums).toFixed(2) }}</span>
                </div>

                <!-- 操作 -->
                <div class="cart-td operate-col">

                  <el-button
                      type="text"
                      class="operate-btn delete-btn"
                      icon="el-icon-delete"
                      @click="deleteCartItem(index, item.id)"
                  >
                    删除
                  </el-button>
                </div>
              </div>
            </div>
          </div>

          <!-- 结算栏 -->
          <div class="cart-settle">
            <div class="settle-left">
              <span class="selected-count">
                已选中 {{ totalCount }} 件商品
              </span>
            </div>
            <div class="settle-right">
              <div class="total-price">
                合计：
                <span class="price-value">¥{{ totalPrice }}</span>
                <span class="price-tip">（不含运费）</span>
              </div>
              <el-button
                  type="primary"
                  class="settle-btn"
                  @click="settleAccounts"
              >
               购买
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 页脚 -->

  </div>
</template>

<style scoped>
/* 全局样式 */
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

/* 顶部导航栏 */
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

.login-tip {
  font-size: 14px;
  color: #666;
}

.logout-btn, .cart-btn {
  color: #666 !important;
  transition: color 0.3s;
}

.cart-btn.active {
  color: #ff4400 !important;
  font-weight: 500;
}

.logout-btn:hover, .cart-btn:hover {
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

/* 购物车特有样式 */
.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
}

.cart-title {
  font-size: 20px;
  color: #333;
  margin: 0;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}

.title-icon {
  color: #ff4400;
  font-size: 22px;
}

.clear-cart-btn {
  color: #f56c6c !important;
  transition: color 0.3s;
}

.clear-cart-btn:hover {
  color: #e53e3e !important;
}

.empty-cart {
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

.cart-container {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.cart-table {
  width: 100%;
}

.cart-thead {
  display: flex;
  background-color: #fafafa;
  border-bottom: 1px solid #f0f0f0;
}

.cart-th {
  padding: 15px 0;
  font-size: 14px;
  color: #666;
  font-weight: 500;
  text-align: center;
}

.check-col {
  width: 100px;
}

.product-col {
  flex: 1;
  text-align: left !important;
  padding-left: 20px;
}

.price-col, .count-col, .total-col {
  width: 150px;
}

.operate-col {
  width: 120px;
}

.cart-tbody .cart-tr {
  display: flex;
  border-bottom: 1px solid #f0f0f0;
  transition: background-color 0.3s;
}

.cart-tbody .cart-tr:hover {
  background-color: #fafafa;
}

.cart-tbody .cart-tr.checked {
  background-color: #fff8f5;
}

.cart-td {
  padding: 20px 0;
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
  margin: 0 0 8px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  height: 40px;
}

.product-category {
  font-size: 12px;
  color: #999;
}

.price-col {
  color: #666;
}

.count-adjust {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0;
  width: 100px;
  margin: 0 auto;
}

.count-minus, .count-plus {
  width: 30px;
  height: 30px;
  padding: 0;
  border: 1px solid #f0f0f0;
  border-radius: 0 !important;
  color: #666 !important;
}

.count-minus:disabled {
  color: #ccc !important;
  cursor: not-allowed;
}

.count-input {
  width: 40px;
  height: 30px;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  border-left: none;
  border-right: none;
  text-align: center;
  outline: none;
  font-size: 14px;
}

.product-subtotal {
  color: #ff4400;
  font-weight: 500;
}

.operate-btn {
  display: block;
  margin: 0 auto 8px;
  color: #666 !important;
  transition: color 0.3s;
}

.collect-btn:hover {
  color: #ff4400 !important;
}

.delete-btn:hover {
  color: #f56c6c !important;
}

/* 结算栏 */
.cart-settle {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #fafafa;
}

.settled-left {
  font-size: 14px;
  color: #666;
}

.selected-count {
  margin-left: 20px;
}

.total-price {
  font-size: 16px;
  color: #333;
  margin-right: 30px;
}

.price-value {
  font-size: 20px;
  color: #ff4400;
  font-weight: 600;
  margin: 0 8px;
}

.price-tip {
  font-size: 12px;
  color: #999;
}

.settle-btn {
  background-color: #ff4400 !important;
  border-color: #ff4400 !important;
  border-radius: 20px !important;
  padding: 0 30px;
  font-size: 16px;
}

.settle-btn:hover {
  background-color: #e03c00 !important;
  border-color: #e03c00 !important;
}

/* 页脚 */
.mall-footer {
  background-color: #333;
  color: #999;
  padding: 40px 0 20px;
  margin-top: auto;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-bottom: 20px;
}

.footer-links a {
  color: #999;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.footer-links a:hover {
  color: #fff;
}

.copyright {
  text-align: center;
  font-size: 12px;
  line-height: 1.8;
}

/* 响应式调整 */
@media (max-width: 1200px) {
  .container {
    width: 100%;
  }
}

@media (max-width: 992px) {
  .search-box {
    width: 35%;
  }

  .price-col, .count-col, .total-col {
    width: 120px;
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

  .category-nav {
    height: auto;
    overflow-x: auto;
  }

  .category-list {
    width: max-content;
  }

  .cart-th, .cart-td {
    padding: 15px 5px;
  }

  .check-col {
    width: 60px;
  }

  .price-col, .count-col, .total-col {
    width: 100px;
  }

  .operate-col {
    width: 80px;
  }

  .product-img {
    width: 60px;
    height: 60px;
  }

  .cart-settle {
    flex-direction: column;
    align-items: flex-end;
    gap: 15px;
  }
}

@media (max-width: 480px) {
  .cart-thead .price-col,
  .cart-tbody .price-col {
    display: none;
  }

  .count-col, .total-col {
    width: 80px;
  }

  .product-name {
    -webkit-line-clamp: 1;
    height: 20px;
  }

  .settle-btn {
    width: 100%;
  }
}
</style>