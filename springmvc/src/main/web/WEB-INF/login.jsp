<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请输入想要查询的用户ID</title>
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