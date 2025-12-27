<script setup>
import { onMounted, reactive, ref } from "vue";
import { User } from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import { ElMessage } from "element-plus";
const data = reactive({
  category: [],
  products: [],
  nowUser: null,
  money: 0,
  activeCategory: "all", // 默认选中全部分类
  searchText: "",
  sortType: "default" // 新增排序字段
})

// 加载数据 - 移除一级分类过滤，保留所有分类
const load = () => {
  request.get("/category/getAll")
      .then(res => {
        data.category = res.data; // 保留所有分类，不做过滤
        console.log("所有分类数据：", data.category);
      })
  request.get("/goods/getAll")
      .then(res => {
        data.products = res.data
        console.log("商品数据：", data.products);
      })
}

// 筛选商品（分类+搜索）
const getFilteredProducts = () => {
  let filtered = data.products.filter(product => {
    // 分类筛选：适配categoryId字段
    const categoryMatch = data.activeCategory === "all" || product.categoryId === data.activeCategory;
    // 搜索筛选
    const searchMatch = product.goodsName.toLowerCase().includes(data.searchText.toLowerCase());
    return categoryMatch && searchMatch;
  })

  // 新增排序逻辑，不影响原有筛选
  switch(data.sortType) {
    case "priceAsc":
      filtered.sort((a, b) => a.price - b.price);
      break;
    case "priceDesc":
      filtered.sort((a, b) => b.price - a.price);
      break;
    case "newest":
      filtered.sort((a, b) => new Date(b.createTime) - new Date(a.createTime));
      break;
    default:
      break;
  }

  return filtered;
}

onMounted(() => {
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'));
  data.money = JSON.parse(localStorage.getItem('money'));
  console.log("用户余额：",data.money)
  console.log("当前登录用户：", data.nowUser)
  load();
});

const login = () => {
  location.href = '/login';
}

const logout = () => {
  localStorage.removeItem('nowUser');
  location.href = '/login';
  ElMessage.success('退出登录成功');
}

// 加入购物车提示
const addToCart = (product) => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录再添加商品');
    return;
  }
  // 修复：nowUser是对象，要取username属性
  const username = data.nowUser;
  const productId = product.goodsId;
  const url = `/cart/add/${username}/${productId}`;

  console.log("请求路径：", url);

  // 直接发送POST请求，无需参数体（参数已在路径中）
  request.post(url)
      .then(response => {
        console.log("添加成功", response.data);
        ElMessage.success("添加成功"); // 移到成功回调里
      })
      .catch(error => {
        console.log("添加失败", error);
        ElMessage.error("添加失败，请重试"); // 增加失败提示
      });
}
const toCart = (product) => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录再添加商品');
    return;
  }
  location.href = '/Cart';
}
const toList = () => {
  location.href = '/order';
}

// 新增立即购买功能
const buyNow = (product) => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录再购买商品');
    return;
  }
  // 可跳转到结算页面，此处仅做演示
  ElMessage.info(`即将购买：${product.name}`);
}
</script>

<template>
  <div class="mall-app">
    <!-- 顶部导航栏 - 简约风格 -->
    <header class="mall-header">
      <div class="container header-container">
        <div class="header-left">
          <div class="logo">
            <h1>网上数码商城</h1>
          </div>
        </div>

        <div class="header-center">

        </div>

        <div class="header-right">
          <div class="user-area" v-if="!data.nowUser">
            <el-button type="text" @click="login" class="header-btn">登录</el-button>
            <el-button type="text" @click="location.href='/register'" class="header-btn">注册</el-button>
          </div>
          <div class="user-info" v-else>
            <el-avatar :src="data.nowUser.avatar || ''" icon="el-icon-user" class="user-avatar" />
            <span class="user-name">{{ data.nowUser.username || data.nowUser }}</span>
            <el-button type="text" icon="el-icon-shopping-cart-full" @click="toCart()" class="header-btn">购物车</el-button>
            <el-button type="text" icon="el-icon-menu" @click="toList()" class="header-btn">我的订单</el-button>
            <el-button type="text" icon="el-icon-switch-button" @click="logout" class="header-btn">退出</el-button>
          </div>
        </div>
      </div>
    </header>

    <!-- 主体内容区 - 侧边分类+主内容区 -->
    <div class="main-container">
      <div class="container">
        <!-- 左侧分类导航 -->
        <aside class="sidebar">
          <div class="sidebar-title">商品分类</div>
          <ul class="category-list">
            <li
                :class="data.activeCategory === 'all' ? 'active' : ''"
                @click="data.activeCategory = 'all'"
                class="category-item"
            >
              <i class="el-icon-menu"></i> 全部商品
            </li>
            <li
                v-for="item in data.category"
                :key="item.categoryId"
                :class="data.activeCategory === item.categoryId ? 'active' : ''"
                @click="data.activeCategory = item.categoryId"
                class="category-item"
            >
              {{ item.categoryName }}
            </li>
          </ul>
        </aside>

        <!-- 右侧商品内容区 -->
        <main class="main-content">
          <!-- 面包屑和排序 -->
          <div class="content-header">
            <el-breadcrumb separator="/" class="breadcrumb">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>
                {{ data.activeCategory === 'all' ? '全部商品' : (data.category.find(c => c.categoryId === data.activeCategory)?.categoryName || '商品列表') }}
              </el-breadcrumb-item>
            </el-breadcrumb>

            <div class="sort-area">
              <span class="sort-label">排序方式：</span>
              <el-radio-group v-model="data.sortType" class="sort-radio">
                <el-radio label="default">默认</el-radio>
                <el-radio label="priceAsc">价格从低到高</el-radio>
                <el-radio label="priceDesc">价格从高到低</el-radio>
                <el-radio label="newest">最新上架</el-radio>
              </el-radio-group>
            </div>
          </div>

          <!-- 商品统计 -->
          <div class="product-count">
            共找到 <span class="count-num">{{ getFilteredProducts().length }}</span> 件商品
          </div>

          <!-- 商品网格 -->
          <div class="product-grid">
            <div class="product-item" v-for="product in getFilteredProducts()" :key="product.id">
              <div class="product-card">
                <!-- 商品图片 -->
                <div class="product-img">
                  <el-image
                      :src="product.goodsImg"
                      fallback="https://via.placeholder.com/300x300?text=暂无图片"
                      :preview-src-list="[product.goodsImg]"
                      fit="cover"
                  />
                  <div class="product-tag hot" v-if="product.price > 500">热销</div>
                  <div class="product-tag new" v-if="product.isNew">新品</div>
                </div>

                <!-- 商品信息 -->
                <div class="product-info">
                  <h3 class="product-name">{{ product.name }}</h3>
                  <div class="product-category">
                    {{ data.category.find(c => c.categoryId === product.categoryId)?.categoryName || '未分类' }}
                  </div>
                  <div class="product-price">
                    <span class="price-symbol">¥</span>
                    <span class="price-num">{{ product.price.toFixed(2) }}</span>
                  </div>
                </div>

                <!-- 操作按钮 -->
                <div class="product-actions">

                  <el-button
                      type="default"
                      class="cart-btn"
                      @click="addToCart(product)"
                      icon="el-icon-shopping-cart"
                      round
                  >
                    加入购物车
                  </el-button>
                </div>
              </div>
            </div>

            <!-- 空数据提示 -->
            <div v-if="getFilteredProducts().length === 0" class="empty-product">
              <el-empty
                  description="暂无相关商品"
                  image-size="120"
                  style="--el-empty-color: #666; --el-empty-description-color: #999;"
              >
                <el-button type="primary" @click="data.activeCategory = 'all'; data.searchText = ''">
                  查看全部商品
                </el-button>
              </el-empty>
            </div>
          </div>
        </main>
      </div>
    </div>

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

          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* 全局样式 */
.mall-app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  color: #333;
}

.container {
  width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

/* 顶部导航栏 */
.mall-header {
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  height: 80px;
  display: flex;
  align-items: center;
}

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.logo h1 {
  font-size: 28px;
  color: #165DFF;
  margin: 0;
  font-weight: 700;
}

.header-center {
  flex: 1;
  display: flex;
  justify-content: center;
}

.search-box {
  width: 500px;
}

.search-input {
  height: 44px;
  border-radius: 22px !important;
  padding: 0 20px;
  border: 1px solid #e5e5e5 !important;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03);
  transition: all 0.3s;
}

.search-input:focus {
  border-color: #165DFF !important;
  box-shadow: 0 0 0 2px rgba(22, 93, 255, 0.1) !important;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-area {
  display: flex;
  gap: 10px;
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

.header-btn {
  color: #666 !important;
  transition: all 0.3s;
  font-size: 14px;
}

.header-btn:hover {
  color: #165DFF !important;
}

/* 主体内容区 */
.main-container {
  flex: 1;
  padding: 20px 0;
}

.main-container .container {
  display: flex;
  gap: 20px;
}

/* 左侧分类侧边栏 */
.sidebar {
  width: 240px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 15px 0;
}

.sidebar-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  padding: 0 20px 15px;
  border-bottom: 1px solid #f0f0f0;
}

.category-list {
  margin: 0;
  padding: 0;
}

.category-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  font-size: 14px;
  color: #666;
  cursor: pointer;
  transition: all 0.3s;
  border-left: 3px solid transparent;
}

.category-item:hover {
  background-color: #f8f9fa;
  color: #165DFF;
}

.category-item.active {
  background-color: #e8f3ff;
  color: #165DFF;
  border-left-color: #165DFF;
  font-weight: 500;
}

.category-item i {
  margin-right: 8px;
}

/* 右侧商品内容区 */
.main-content {
  flex: 1;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 20px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.breadcrumb {
  font-size: 14px;
}

.sort-area {
  display: flex;
  align-items: center;
}

.sort-label {
  font-size: 14px;
  color: #666;
  margin-right: 10px;
}

.sort-radio {
  display: flex;
  gap: 15px;
}

.product-count {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
}

.count-num {
  color: #165DFF;
  font-weight: 600;
  font-size: 16px;
}

/* 商品网格 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 20px;
}

.product-item {
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
}

.product-card {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.product-item:hover .product-card {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transform: translateY(-5px);
}

.product-img {
  height: 200px;
  position: relative;
  overflow: hidden;
  background-color: #f9f9f9;
}

.product-img img {
  width: 100%;
  height: 100%;
  transition: transform 0.5s ease;
}

.product-card:hover .product-img img {
  transform: scale(1.05);
}

.product-tag {
  position: absolute;
  top: 10px;
  left: 10px;
  color: #fff;
  font-size: 12px;
  padding: 3px 8px;
  border-radius: 4px;
  font-weight: 500;
}

.product-tag.hot {
  background-color: #ff4d4f;
}

.product-tag.new {
  background-color: #52c41a;
  left: 60px;
}

.product-info {
  padding: 15px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.product-name {
  font-size: 14px;
  color: #333;
  margin: 0 0 10px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

.product-category {
  font-size: 12px;
  color: #999;
  margin-bottom: 10px;
}

.product-price {
  font-size: 18px;
  color: #ff4d4f;
  font-weight: 700;
  margin-top: auto;
}

.price-symbol {
  font-size: 14px;
}

.price-num {
  font-size: 20px;
}

.product-actions {
  display: flex;
  gap: 10px;
  padding: 0 15px 15px;
}

.buy-btn {
  flex: 1;
  height: 36px !important;
  background-color: #ff4d4f !important;
  border-color: #ff4d4f !important;
}

.cart-btn {
  flex: 1;
  height: 36px !important;
  color: #165DFF !important;
  border-color: #165DFF !important;
}

.cart-btn:hover {
  background-color: #165DFF !important;
  color: #fff !important;
}

.empty-product {
  grid-column: 1 / -1;
  text-align: center;
  padding: 60px 0;
}

/* 页脚 */
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

/* 响应式调整 */
@media (max-width: 1200px) {
  .container {
    width: 100%;
  }

  .search-box {
    width: 400px;
  }
}

@media (max-width: 992px) {
  .sidebar {
    display: none;
  }

  .search-box {
    width: 300px;
  }

  .product-grid {
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  }
}

@media (max-width: 768px) {
  .mall-header {
    height: auto;
    padding: 15px 0;
  }

  .header-container {
    flex-wrap: wrap;
    justify-content: center;
  }

  .header-left {
    width: 100%;
    text-align: center;
    margin-bottom: 15px;
  }

  .header-center {
    width: 100%;
    margin-bottom: 15px;
  }

  .search-box {
    width: 100%;
  }

  .header-right {
    width: 100%;
    justify-content: center;
  }

  .content-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .sort-area {
    margin-top: 10px;
  }

  .sort-radio {
    flex-wrap: wrap;
  }

  .product-grid {
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  }
}

@media (max-width: 480px) {
  .product-grid {
    grid-template-columns: 1fr;
  }

  .product-actions {
    flex-direction: column;
  }

  .footer-links {
    gap: 15px;
  }
}
</style>