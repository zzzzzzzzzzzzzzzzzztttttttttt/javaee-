<script setup>
import { onMounted, reactive, ref } from "vue";
import { User } from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import { ElMessage } from "element-plus";
const data = reactive({
  category: [],
  products: [],
  nowUser: null,
  activeCategory: "all", // 默认选中全部分类
  searchText: ""
})

// 加载数据
const load = () => {
  request.get("/category/getAll")
      .then(res => {
        data.category = res.data
      })
  request.get("/product/getAll")
      .then(res => {
        data.products = res.data
      })
}

// 筛选商品（分类+搜索）
const getFilteredProducts = () => {
  return data.products.filter(product => {
    // 分类筛选
    const categoryMatch = data.activeCategory === "all" || product.categoryId === data.activeCategory
    // 搜索筛选
    const searchMatch = product.name.toLowerCase().includes(data.searchText.toLowerCase())
    return categoryMatch && searchMatch
  })
}

onMounted(() => {
  data.nowUser = JSON.parse(localStorage.getItem('nowUser'))
  load();
});

const login = () => {
  location.href = '/login'
}

const logout = () => {
  localStorage.removeItem('nowUser')
  location.href = '/login'
  ElMessage.success('退出登录成功')
}

// 加入购物车提示
const addToCart = (product) => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录再添加商品')
    return
  }
  // 关键：把 username 和 productId 拼在路径中（顺序要和后端一致）
  const username = data.nowUser;
  const productId = product.id;
  const url = `/cart/add/${username}/${productId}`; // 路径格式：/cart/add/用户名/商品ID

  console.log("请求路径：", url); // 示例：/cart/add/zhangsan/1

  // 直接发送POST请求，无需参数体（参数已在路径中）
  request.post(url)
      .then(response => {
        console.log("添加成功", response.data);
      })
      .catch(error => {
        console.log("添加失败", error);
      });
  ElMessage.success("添加成功")
}
const toCart = (product) => {
  if (!data.nowUser) {
    ElMessage.warning('请先登录再添加商品')
    return
  }
  location.href = '/Cart'
}
const toList = () => {
  location.href = '/order-list'
}
</script>
<template>
  <div class="mall-app">
    <!-- 顶部导航栏 -->
    <header class="mall-header">
      <div class="container">
        <div class="header-top">
          <div class="logo">
            <h1>网上数码商城</h1>
          </div>



          <!-- 用户区域 -->
          <div class="user-area">
            <div v-if="!data.nowUser">
              <el-button
                  type="primary"
                  @click="login"
                  class="login-btn"
                  icon="el-icon-user"
              >
                登录
              </el-button>
            </div>
            <div v-else class="user-info">
              <el-avatar icon="el-icon-user" class="user-avatar" />
              <span class="user-name">{{ data.nowUser }}</span>
              <el-button
                  type="text"
                  @click="logout"
                  class="logout-btn"
                  icon="el-icon-switch-button"
              >
                退出
              </el-button>
              <el-button
                  type="text"
                  class="cart-btn"
                  icon="el-icon-shopping-cart-full"
                  @click="toCart()"
              >
                购物车
              </el-button>
              <el-button
                  type="text"
                  class="cart-btn"
                  icon="el-icon-shopping-cart-full"
                  @click="toList()"
              >
                我的订单
              </el-button>
            </div>
          </div>
        </div>

        <!-- 分类导航 -->
        <nav class="category-nav">
          <ul class="category-list">
            <li
                :class="data.activeCategory === 'all' ? 'active' : ''"
                @click="data.activeCategory = 'all'"
            >
              全部商品
            </li>
            <li
                v-for="item in data.category"
                :key="item.id"
                :class="data.activeCategory === item.id ? 'active' : ''"
                @click="data.activeCategory = item.id"
            >
              {{ item.name }}
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="mall-main">
      <div class="container">
        <!-- 面包屑导航 -->
        <div class="breadcrumb">
          <a href="/">首页</a>
          <span>/</span>
          <span class="current">
            {{ data.activeCategory === 'all' ? '全部商品' : (data.category.find(c => c.id === data.activeCategory)?.name || '商品列表') }}
          </span>
        </div>

        <!-- 商品列表标题 -->
        <div class="product-header">
          <h2 class="product-title">
            {{ data.activeCategory === 'all' ? '全部商品' : (data.category.find(c => c.id === data.activeCategory)?.name || '商品列表') }}
          </h2>
          <span class="product-count">共 {{ getFilteredProducts().length }} 件商品</span>
        </div>

        <!-- 商品网格 -->
        <div class="product-grid">
          <div v-for="product in getFilteredProducts()" :key="product.id" class="product-item">
            <div class="product-card">
              <!-- 商品图片 -->
              <div class="product-img">
                <el-image
                    :src="product.img"
                    fallback="https://via.placeholder.com/300x300?text=暂无图片"
                    :preview-src-list="[product.img]"
                />
                <!-- 新品/热销标签 -->
                <div class="product-tag" v-if="product.price > 500">热销</div>
              </div>

              <!-- 商品信息 -->
              <div class="product-info">
                <h3 class="product-name">{{ product.name }}</h3>
                <div class="product-category">
                  分类：{{ data.category.find(c => c.id === product.categoryId)?.name || '未分类' }}
                </div>
                <div class="product-price">¥{{ product.price.toFixed(2) }}</div>
              </div>

              <!-- 操作按钮 -->
              <div class="product-actions">
                <el-button
                    type="primary"
                    class="add-cart-btn"
                    @click="addToCart(product)"
                    icon="el-icon-shopping-cart"
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
                style="--el-empty-padding: 80px 0;"
            />
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

.login-btn {
  border-radius: 20px !important;
  padding: 0 24px;
  background-color: #ff4400 !important;
  border-color: #ff4400 !important;
}

.logout-btn, .cart-btn {
  color: #666 !important;
  transition: color 0.3s;
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

.category-list li:hover, .category-list li.active {
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

.product-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
}

.product-title {
  font-size: 20px;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.product-count {
  font-size: 14px;
  color: #666;
}

/* 商品网格 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 25px;
}

.product-item {
  transition: transform 0.3s;
}

.product-item:hover {
  transform: translateY(-5px);
}

.product-card {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.3s;
}

.product-item:hover .product-card {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.product-img {
  position: relative;
  height: 280px;
  overflow: hidden;
}

.product-img el-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-tag {
  position: absolute;
  top: 10px;
  left: 10px;
  background-color: #ff4400;
  color: #fff;
  font-size: 12px;
  padding: 3px 8px;
  border-radius: 4px;
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 15px;
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
  margin-bottom: 10px;
}

.product-price {
  font-size: 18px;
  color: #ff4400;
  font-weight: 600;
}

.product-actions {
  display: flex;
  padding: 0 15px 15px;
  gap: 10px;
}

.add-cart-btn {
  flex: 1;
  background-color: #fff !important;
  color: #ff4400 !important;
  border-color: #ff4400 !important;
  border-radius: 4px !important;
  transition: all 0.3s;
}

.add-cart-btn:hover {
  background-color: #ff4400 !important;
  color: #fff !important;
}

.collect-btn {
  color: #999 !important;
  transition: color 0.3s;
}

.collect-btn:hover {
  color: #ff4400 !important;
}

.empty-product {
  grid-column: 1 / -1;
  text-align: center;
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

  .category-list li {
    padding: 0 18px;
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

  .product-grid {
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 15px;
  }

  .product-img {
    height: 220px;
  }
}

@media (max-width: 480px) {
  .product-grid {
    grid-template-columns: 1fr;
  }

  .footer-links {
    flex-wrap: wrap;
    gap: 15px;
  }
}
</style>