<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请输入想要查询的用户ID</title>
    <style>
        /* 全局样式重置 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
        }

        /* 页面整体布局 */
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            background-image: linear-gradient(120deg, #f5f7fa 0%, #e9ecef 100%);
        }

        /* 容器样式 */
        .container {
            background-color: white;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            width: 100%;
            max-width: 450px;
            border: 1px solid #f1f3f5;
        }

        /* 标题样式 */
        h2 {
            text-align: center;
            color: #212529;
            margin-bottom: 30px;
            font-size: 1.8rem;
            font-weight: 700;
            position: relative;
            padding-bottom: 15px;
        }

        h2::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background-color: #fd7e14;
            border-radius: 3px;
        }

        /* 表单组样式 */
        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 10px;
            color: #495057;
            font-weight: 600;
            font-size: 1.05rem;
        }

        .form-group input[type="number"] {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            font-size: 1rem;
            outline: none;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
        }

        .form-group input[type="number"]:focus {
            border-color: #fd7e14;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(253, 126, 20, 0.1);
        }

        /* 提交按钮样式 */
        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #fd7e14;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1.05rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 3px 6px rgba(253, 126, 20, 0.2);
        }

        button[type="submit"]:hover {
            background-color: #e67e22;
            transform: translateY(-2px);
            box-shadow: 0 5px 12px rgba(253, 126, 20, 0.3);
        }

        /* 响应式适配 */
        @media (max-width: 480px) {
            .container {
                padding: 30px 25px;
            }

            h2 {
                font-size: 1.5rem;
            }

            button[type="submit"] {
                padding: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>请输入您的ID</h2>
    <form action="/bookStore" method="post">
        <div class="form-group">
            <label for="customerId">用户ID:</label>
            <input type="number" id="customerId" name="customerId" required>
        </div>
        <button type="submit">进入购书页面</button>
    </form>
</div>
</body>
</html>