<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Register Page</title>

    <!-- Embedded CSS (same style as other pages) -->
    <style>
        body {
            margin: 0;
            padding: 40px;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f6f8;
            color: #333;
        }

        .card {
            max-width: 500px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            padding: 28px;
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #222;
        }

        .form-row {
            margin: 12px 0;
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: 600;
            margin-bottom: 6px;
            color: #444;
        }

        input[type="text"],
        input[type="password"],
        input[type="datetime-local"] {
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #d2d8de;
            font-size: 14px;
            outline: none;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="datetime-local"]:focus {
            border-color: #6ea8fe;
            box-shadow: 0 0 0 4px rgba(110,168,254,0.08);
        }

        .radio-group {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-top: 6px;
        }

        .actions {
            text-align: center;
            margin-top: 22px;
        }

        .btn-register {
            padding: 10px 24px;
            font-size: 15px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            background: #28a745;
            color: white;
            box-shadow: 0 6px 14px rgba(40,167,69,0.18);
        }

        .btn-register:hover { opacity: 0.95; }

        a {
            display: block;
            text-align: center;
            margin-top: 16px;
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        a:hover { text-decoration: underline; }

        /* Responsive adjustments */
        @media (max-width: 520px) {
            .form-row { flex-direction: column; }
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Register User</h2>

    <form action="/register-user" method="post">
        <div class="form-row">
            <label>Username:</label>
            <input type="text" name="username" required />
        </div>

        <div class="form-row">
            <label>Password:</label>
            <input type="password" name="password" required />
        </div>

        <div class="form-row">
            <label>Email:</label>
            <input type="text" name="email" required />
        </div>

        <div class="form-row">
            <label>Date of Registration:</label>
            <input type="datetime-local" name="createdAt" />
        </div>

        <div class="form-row">
            <label>Role:</label>
            <div class="radio-group">
                <input type="radio" name="role" value="admin" id="roleAdmin" />
                <label for="roleAdmin">Admin</label>

                <input type="radio" name="role" value="user" id="roleUser" />
                <label for="roleUser">User</label>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn-register">Register</button>
        </div>

        <a href="/">Click here to Login</a>
    </form>
</div>

</body>
</html>
