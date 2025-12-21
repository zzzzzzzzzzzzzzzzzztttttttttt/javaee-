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
            font-family: 'Microsoft YaHei', Arial, sans-serif;
        }

        body {
            background-color: #f5f7fa;
            padding: 20px;
            line-height: 1.6;
        }

        /* 标题样式 */
        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
            font-size: 2.2rem;
            font-weight: 600;
            letter-spacing: 1px;
        }

        /* 查询按钮区域 */
        .query-section {
            text-align: center;
            margin-bottom: 40px;
        }

        .query-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s ease;
        }

        .query-btn:hover {
            background-color: #2980b9;
        }

        /* 书籍容器样式 */
        .book-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
            max-width: 1200px;
            margin: 0 auto;
        }

        /* 书籍卡片样式 */
        .book-card {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .book-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.12);
        }

        /* 书籍信息文本样式 */
        .book-card p {
            margin-bottom: 12px;
            color: #34495e;
            font-size: 1rem;
        }

        .book-card p:first-of-type {
            font-weight: 600;
            color: #2c3e50;
            font-size: 1.1rem;
        }

        /* 表单组样式 */
        .form-group {
            margin: 15px 0;
        }

        .form-group label {
            display: inline-block;
            margin-right: 10px;
            color: #34495e;
            font-weight: 500;
        }

        .form-group input[type="number"] {
            width: 80px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 0.95rem;
            outline: none;
            transition: border-color 0.3s ease;
        }

        .form-group input[type="number"]:focus {
            border-color: #3498db;
        }

        /* 购买按钮样式 */
        .add-btn {
            background-color: #2ecc71;
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 0.95rem;
            transition: background-color 0.3s ease;
        }

        .add-btn:hover {
            background-color: #27ae60;
        }

        /* 响应式适配 */
        @media (max-width: 768px) {
            .book-container {
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            }

            h1 {
                font-size: 1.8rem;
            }

            .book-card {
                padding: 15px;
            }
        }

        @media (max-width: 480px) {
            .book-container {
                grid-template-columns: 1fr;
            }

            .query-btn, .add-btn {
                width: 100%;
                padding: 12px;
            }

            .form-group {
                display: flex;
                flex-direction: column;
                gap: 8px;
            }

            .form-group input[type="number"] {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<h1>欢迎来到在线书店</h1>

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