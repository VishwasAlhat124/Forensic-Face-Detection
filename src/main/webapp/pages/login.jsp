<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login Page</title>

    <!-- Same Embedded CSS -->
    <style>
        body {
            margin: 0;
            padding: 40px;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f6f8;
            color: #333;
        }

        .card {
            max-width: 450px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            padding: 28px;
        }

        h1 {
            text-align: center;
            margin-bottom: 24px;
            color: #222;
        }

        .form-row {
            margin: 14px 0;
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: 600;
            margin-bottom: 6px;
            color: #444;
        }

        input[type="text"],
        input[type="password"] {
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #d2d8de;
            font-size: 14px;
            outline: none;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #6ea8fe;
            box-shadow: 0 0 0 4px rgba(110,168,254,0.08);
        }

        .actions {
            text-align: center;
            margin-top: 20px;
        }

        .btn-login {
            padding: 10px 24px;
            font-size: 15px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            background: #007bff;
            color: white;
            box-shadow: 0 6px 14px rgba(0,123,255,0.18);
        }

        .btn-login:hover {
            opacity: 0.95;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 16px;
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
</c:if>



<div class="card">
    <h1>Login</h1>

    <form action="/login-user" method="post">

        <div class="form-row">
            <label>Username:</label>
            <input type="text" name="username" required />
        </div>

        <div class="form-row">
            <label>Password:</label>
            <input type="password" name="password" required />
        </div>

        <div class="actions">
            <button type="submit" class="btn-login">Login</button>
        </div>

        <a href="/register-page">Click here to register</a>
    </form>
</div>

</body>
</html>
