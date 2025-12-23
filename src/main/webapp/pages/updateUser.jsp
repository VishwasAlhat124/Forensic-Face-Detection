<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home Page</title>

    <style>
        body {
            margin: 0;
            padding: 40px;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f6f8;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
        }

        h1 {
            text-align: center;
            color: #222;
            margin-bottom: 22px;
        }

        .table-card {
            background: #fff;
            padding: 22px;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #e2e3e5;
        }

        th {
            background: #f0f2f5;
            text-align: left;
            font-weight: 600;
        }

        tr:hover {
            background: #f9fafb;
        }

        .actions a {
            margin-right: 10px;
            text-decoration: none;
            font-size: 14px;
            padding: 6px 12px;
            border-radius: 6px;
            color: white;
        }

        .delete-btn {
            background: #dc3545;
        }

        .update-btn {
            background: #007bff;
        }

        .links {
            text-align: center;
            margin-top: 24px;
        }

        .links a {
            text-decoration: none;
            color: #007bff;
            font-weight: 600;
            margin: 0 10px;
        }

        .links a:hover { text-decoration: underline; }
		
		.actions a {
		    display: inline-block;     /* prevents overlap */
		    margin-right: 10px;        /* spacing between buttons */
		    padding: 6px 12px;
		    border-radius: 6px;
		    color: #fff;
		}
    </style>
</head>

<body>

<c:if test="${not empty message}">
    <script>alert("${message}");</script>
</c:if>

<div class="container">
    <h1>User Details</h1>

    <div class="table-card">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.username}</td>
                        <td>${u.password}</td>
                        <td>${u.email}</td>
                        <td>${u.role}</td>
                        <td>${u.createdAt}</td>
                        <td class="actions">
                            <a href="/delete-user/${u.id}" class="delete-btn">Delete</a>
                            <a href="/show-user/${u.id}" class="update-btn">Update</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="links">
        <a href="/">Login</a>
        <a href="/register-page">Register</a>
		<a href="/user-options">Back</a>
    </div>
</div>

</body>
</html>
