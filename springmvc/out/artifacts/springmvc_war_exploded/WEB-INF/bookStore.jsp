<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>在线书店</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* 全局样式重置 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif; /* 优化字体 */
        }

        body {
            background-color: #f8f9fa; /* 更柔和的背景色 */
            padding: 20px;
            line-height: 1.7; /* 优化行高 */
            background-image: linear-gradient(to bottom, #f8f9fa, #e9ecef); /* 增加渐变背景 */
            min-height: 100vh; /* 铺满视口高度 */
        }

        /* 标题样式 */
        h1 {
            text-align: center;
            color: #212529; /* 更深的标题色 */
            margin-bottom: 40px;
            font-size: 2.4rem;
            font-weight: 700;
            letter-spacing: 2px;
            position: relative; /* 增加装饰线 */
            padding-bottom: 15px;
        }

        h1::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 120px;
            height: 3px;
            background-color: #fd7e14; /* 橙红色装饰线 */
            border-radius: 3px;
        }

        /* 查询按钮区域 */
        .query-section {
            text-align: center;
            margin-bottom: 50px; /* 增加间距 */
        }

        .query-btn {
            background-color: #fd7e14; /* 主色调改为橙红色 */
            color: white;
            border: none;
            padding: 12px 30px; /* 加大按钮尺寸 */
            border-radius: 8px; /* 更圆润的圆角 */
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease; /* 增加所有属性过渡 */
            box-shadow: 0 3px 6px rgba(253, 126, 20, 0.2); /* 按钮阴影 */
        }

        .query-btn:hover {
            background-color: #e67e22; /* hover加深 */
            transform: translateY(-2px); /* 轻微上浮 */
            box-shadow: 0 5px 12px rgba(253, 126, 20, 0.3);
        }

        /* 书籍容器样式 */
        .book-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 30px; /* 加大卡片间距 */
            max-width: 1300px; /* 加宽容器 */
            margin: 0 auto;
            padding-bottom: 50px;
        }

        /* 书籍卡片样式 */
        .book-card {
            background-color: white;
            border-radius: 12px; /* 更大的圆角 */
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05); /* 更柔和的阴影 */
            transition: all 0.4s ease; /* 延长过渡时间 */
            border: 1px solid #f1f3f5; /* 增加浅边框 */
            position: relative;
            overflow: hidden;
        }

        .book-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 4px;
            height: 100%;
            background-color: #fd7e14; /* 左侧装饰条 */
        }

        .book-card:hover {
            transform: translateY(-8px); /* 加大上浮距离 */
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1); /* 加深hover阴影 */
            border-color: #fd7e1433; /* 边框变色 */
        }

        /* 书籍信息文本样式 */
        .book-card p {
            margin-bottom: 15px; /* 加大文本间距 */
            color: #495057; /* 更柔和的文本色 */
            font-size: 1.05rem;
        }

        .book-card p:first-of-type {
            font-weight: 700;
            color: #212529;
            font-size: 1.2rem;
            margin-bottom: 20px; /* 书名加大间距 */
        }

        /* 表单组样式 */
        .form-group {
            margin: 20px 0; /* 加大表单组间距 */
        }

        .form-group label {
            display: inline-block;
            margin-right: 12px;
            color: #343a40;
            font-weight: 600;
            font-size: 1.05rem;
        }

        .form-group input[type="number"] {
            width: 90px; /* 加宽输入框 */
            padding: 10px 12px; /* 加大内边距 */
            border: 1px solid #dee2e6;
            border-radius: 6px; /* 更圆润 */
            font-size: 1rem;
            outline: none;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
        }

        .form-group input[type="number"]:focus {
            border-color: #fd7e14; /* 聚焦色匹配主色调 */
            background-color: white;
            box-shadow: 0 0 0 3px rgba(253, 126, 20, 0.1); /* 聚焦光晕 */
        }

        /* 购买按钮样式 */
        .add-btn {
            background-color: #28a745; /* 调整绿色系 */
            color: white;
            border: none;
            padding: 10px 24px; /* 加大按钮尺寸 */
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-shadow: 0 3px 6px rgba(40, 167, 69, 0.2);
        }

        .add-btn:hover {
            background-color: #218838;
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(40, 167, 69, 0.3);
        }

        /* 响应式适配 */
        @media (max-width: 768px) {
            .book-container {
                grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
                gap: 20px;
            }

            h1 {
                font-size: 2rem;
            }

            .book-card {
                padding: 20px;
            }
        }

        @media (max-width: 480px) {
            .book-container {
                grid-template-columns: 1fr;
            }

            .query-btn, .add-btn {
                width: 100%;
                padding: 14px;
                font-size: 1.05rem;
            }

            .form-group {
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .form-group input[type="number"] {
                width: 100%;
            }

            h1 {
                font-size: 1.8rem;
                padding-bottom: 10px;
            }

            h1::after {
                width: 80px;
                height: 2px;
            }
        }
    </style>
</head>
<body>
<h1>欢迎</h1>

<!-- 查询购书记录区域 -->
<div class="query-section">
    <form action="/queryPurchase" method="post">
        <input type="hidden" name="customerId" value="${customerId}">
        <button type="submit" class="query-btn">查询我的购书记录</button>
    </form>
</div>

<!-- 书籍展示容器 -->
<div class="book-container">
    <c:forEach items="${books}" var="book">
        <div class="book-card">
            <p>书名: ${book.name}</p>
            <p>作者: ${book.author}</p>
            <p>出版社: ${book.press}</p>
            <p>库存: ${book.amount}</p>
            <form action="/addToCart" method="post">
                <input type="hidden" name="customerId" value="${customerId}">
                <input type="hidden" name="bookId" value="${book.id}">
                <div class="form-group">
                    <label>购买数量:</label>
                    <input type="number" name="quantity" min="1" max="${book.amount}" value="1" required>
                </div>
                <button type="submit" class="add-btn">购买</button>
            </form>
        </div>
    </c:forEach>
</div>

<!-- 提示信息弹窗 -->
<c:if test="${not empty message}">
    <script>
        // 优化弹窗样式（可选，如需自定义弹窗可替换原生alert）
        alert('${message}');
    </script>
</c:if>
</body>
</html>
