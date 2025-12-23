<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Update Profile</title>

    <!-- Embedded CSS (same as other pages) -->
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
        input[type="datetime-local"] {
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #d2d8de;
            font-size: 14px;
            outline: none;
        }

        input[type="text"]:focus,
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

        .btn-update {
            padding: 10px 24px;
            font-size: 15px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            background: #ffc107;
            color: white;
            box-shadow: 0 6px 14px rgba(255,193,7,0.18);
        }

        .btn-update:hover { opacity: 0.95; }

        a {
            display: block;
            text-align: center;
            margin-top: 16px;
            color: #007bff;
            text-decoration: none;
            font-weight: 600;
        }

        a:hover { text-decoration: underline; }

        @media (max-width: 520px) {
            .form-row { flex-direction: column; }
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Update Profile</h2>

    <form action="/updateUser" method="post">

        <div class="form-row">
            <label>ID:</label>
            <input type="text" name="id" value="${user.id}" readonly="readonly"/>
        </div>

        <div class="form-row">
            <label>Username:</label>
            <input type="text" name="username" value="${user.username}" required />
        </div>

        <div class="form-row">
            <label>Password:</label>
            <input type="text" name="password" value="${user.password}" required />
        </div>

        <div class="form-row">
            <label>Email:</label>
            <input type="text" name="email" value="${user.email}" required />
        </div>

        <div class="form-row">
            <label>Date of Creation:</label>
            <input type="datetime-local" name="createdAt" value="${user.createdAt}" />
        </div>

        <div class="form-row">
            <label>Role:</label>
            <div class="radio-group">
                <input type="radio" name="role" value="admin" id="roleAdmin"
                    <c:if test="${user.role eq 'admin'}">checked</c:if> />
                <label for="roleAdmin">Admin</label>

                <input type="radio" name="role" value="user" id="roleUser"
                    <c:if test="${user.role eq 'user'}">checked</c:if> />
                <label for="roleUser">User</label>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn-update">Update</button>
        </div>

        <a href="/">Click here to Login</a>
    </form>
</div>

</body>
</html>
