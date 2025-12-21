<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的购书记录 - 在线书店</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* 全局样式（和主页面保持一致） */
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

        /* 返回按钮区域 */
        .back-section {
            text-align: center;
            margin-bottom: 40px;
        }

        .back-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s ease;
        }

        .back-btn:hover {
            background-color: #2980b9;
        }

        /* 表格容器（适配响应式） */
        .table-container {
            max-width: 900px;
            margin: 0 auto;
            overflow-x: auto; /* 小屏横向滚动 */
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        }

        /* 表格样式 */
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 1rem;
        }

        /* 表头样式 */
        th {
            background-color: #2c3e50;
            color: white;
            padding: 15px;
            text-align: left;
            font-weight: 500;
            position: sticky;
            top: 0; /* 表头吸顶（滚动时固定） */
        }

        /* 表格行样式 */
        td {
            padding: 15px;
            color: #34495e;
            border-bottom: 1px solid #ecf0f1;
        }

        /* 行悬停效果 */
        tr:hover {
            background-color: #f8f9fa;
        }

        /* 空数据提示 */
        .empty-tip {
            text-align: center;
            padding: 40px;
            color: #7f8c8d;
            font-size: 1.1rem;
        }

        /* 响应式适配 */
        @media (max-width: 768px) {
            h1 {
                font-size: 1.8rem;
            }

            th, td {
                padding: 12px;
                font-size: 0.95rem;
            }

            .back-btn {
                padding: 10px 20px;
                font-size: 0.95rem;
            }
        }

        @media (max-width: 480px) {
            .back-btn {
                width: 100%;
                padding: 12px;
            }

            th, td {
                padding: 10px 8px;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<h1>我的购书记录</h1>

<!-- 返回按钮区域 -->
<div class="back-section">
    <form action="/bookStore" method="post">
        <input type="hidden" name="customerId" value="${customerId}">
        <button type="submit" class="back-btn">返回购书页面</button>
    </form>
</div>

<!-- 购书记录表格 -->
<div class="table-container">
    <table>
        <tr>
            <th>书名</th>
            <th>购买数量</th>
            <th>购买时间</th>
        </tr>
        <!-- 判断是否有购书记录，无数据时显示提示 -->
        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.bookName}</td>
                        <td>${order.number}</td>
                        <td>${order.time}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="3" class="empty-tip">暂无购书记录，快去选购吧～</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>
</body>
</html>