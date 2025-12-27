<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的购书记录 - 在线书店</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* 全局样式（和主页面保持视觉统一） */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
        }

        body {
            background-color: #f8f9fa;
            padding: 20px;
            line-height: 1.7;
            background-image: linear-gradient(to bottom, #f8f9fa, #e9ecef);
            min-height: 100vh;
        }

        /* 标题样式 */
        h1 {
            text-align: center;
            color: #212529;
            margin-bottom: 40px;
            font-size: 2.4rem;
            font-weight: 700;
            letter-spacing: 2px;
            position: relative;
            padding-bottom: 15px;
        }

        h1::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background-color: #fd7e14;
            border-radius: 3px;
        }

        /* 返回按钮区域 */
        .back-section {
            text-align: center;
            margin-bottom: 50px;
        }

        .back-btn {
            background-color: #fd7e14; /* 统一橙红色主色调 */
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-shadow: 0 3px 6px rgba(253, 126, 20, 0.2);
        }

        .back-btn:hover {
            background-color: #e67e22;
            transform: translateY(-2px); /* 轻微上浮 */
            box-shadow: 0 5px 12px rgba(253, 126, 20, 0.3);
        }

        /* 表格容器（适配响应式） */
        .table-container {
            max-width: 1000px;
            margin: 0 auto;
            overflow-x: auto; /* 小屏横向滚动 */
            background-color: white;
            border-radius: 12px; /* 更大圆角 */
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05); /* 更柔和阴影 */
            border: 1px solid #f1f3f5;
        }

        /* 表格样式 */
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 1.05rem;
        }

        /* 表头样式 */
        th {
            background-color: #212529; /* 更深的表头背景 */
            color: white;
            padding: 18px 15px; /* 加大内边距 */
            text-align: left;
            font-weight: 600;
            position: sticky;
            top: 0; /* 表头吸顶（滚动时固定） */
            border-bottom: 2px solid #fd7e14; /* 表头底部装饰线 */
        }

        /* 表格行样式 */
        td {
            padding: 18px 15px; /* 加大内边距 */
            color: #495057;
            border-bottom: 1px solid #e9ecef; /* 更浅的行分隔线 */
        }

        /* 行悬停效果 */
        tr:hover {
            background-color: #f8f9fa;
            transform: scale(1.005); /* 轻微缩放 */
            transition: background-color 0.3s ease;
        }

        /* 空数据提示 */
        .empty-tip {
            text-align: center;
            padding: 60px 20px; /* 加大内边距 */
            color: #7f8c8d;
            font-size: 1.15rem;
            font-weight: 500;
        }

        /* 响应式适配 */
        @media (max-width: 768px) {
            h1 {
                font-size: 2rem;
            }

            th, td {
                padding: 15px 10px;
                font-size: 0.95rem;
            }

            .back-btn {
                padding: 12px 25px;
                font-size: 0.95rem;
            }

            .empty-tip {
                padding: 40px 15px;
                font-size: 1rem;
            }
        }

        @media (max-width: 480px) {
            .back-btn {
                width: 100%;
                padding: 14px;
                font-size: 1.05rem;
            }

            th, td {
                padding: 12px 8px;
                font-size: 0.9rem;
            }

            h1 {
                font-size: 1.8rem;
                padding-bottom: 10px;
            }

            h1::after {
                width: 80px;
                height: 2px;
            }

            .table-container {
                border-radius: 8px;
                margin: 0 10px;
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