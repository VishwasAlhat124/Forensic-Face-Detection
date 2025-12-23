<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Add Criminal Data</title>

    <!-- Embedded CSS -->
    <style>
        /* Page background and font */
        body {
            margin: 0;
            padding: 40px;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: #f4f6f8;
            color: #333;
        }

        /* Centered card/container */
        .card {
            max-width: 600px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            padding: 28px;
        }

        h1 {
            margin: 0 0 18px 0;
            font-size: 26px;
            text-align: center;
            color: #222;
        }

        /* Form row layout */
        .form-row {
            display: flex;
            align-items: center;
            gap: 16px;
            margin: 12px 0;
            flex-wrap: wrap;
        }

        label {
            width: 160px;
            min-width: 120px;
            font-weight: 600;
            color: #444;
        }

        input[type="text"],
        input[type="datetime-local"],
        input[type="file"] {
            flex: 1 1 auto;
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

        /* Image preview */
        .preview {
            display: flex;
            align-items: center;
            gap: 18px;
            margin-top: 8px;
        }

        #output {
            border-radius: 8px;
            max-width: 220px;
            max-height: 180px;
            object-fit: cover;
            border: 1px solid #e6e9ee;
            box-shadow: 0 2px 8px rgba(0,0,0,0.04);
        }

        /* Buttons */
        .actions {
            text-align: center;
            margin-top: 22px;
        }

        button {
            padding: 10px 20px;
            font-size: 15px;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            background: #28a745;
            color: white;
            box-shadow: 0 6px 14px rgba(40,167,69,0.18);
        }

        button:hover { opacity: 0.95; }

        /* Responsive adjustments */
        @media (max-width: 520px) {
            .form-row { flex-direction: column; align-items: stretch; }
            label { width: 100%; }
        }
    </style>
</head>
<body>
    <div class="card">
        <h1>Add Criminal Data</h1>

        <!-- enctype required for file upload -->
        <form action="/register-person" method="post" enctype="multipart/form-data">
            <div class="form-row">
                <label for="name">Username:</label>
                <input type="text" id="name" name="name" required />
            </div>

            <div class="form-row">
                <label for="note">Note:</label>
                <input type="text" id="note" name="note" />
            </div>

            <div class="form-row">
                <label for="createdAt">Date of Registration:</label>
                <input type="datetime-local" id="createdAt" name="createdAt" />
            </div>

            <div class="form-row">
                <label for="imgInput">Add Image:</label>
                <input type="file" id="imgInput" name="photo" accept="image/*" onchange="previewImage(event)" />
            </div>

            <div class="preview">
                <div style="min-width:160px; font-weight:600; color:#666;">Selected Preview:</div>
                <img id="output" alt="No image selected" />
            </div>

            <div class="actions">
                <button type="submit">Save</button>
				<a href="/user-options">Back</a>
            </div>
        </form>
    </div>

    <!-- Image preview script -->
    <script>
        function previewImage(event) {
            const file = event.target.files && event.target.files[0];
            const image = document.getElementById('output');
            if (!file) {
                image.src = '';
                image.alt = 'No image selected';
                return;
            }
            image.src = URL.createObjectURL(file);
            image.alt = file.name;
        }
    </script>
</body>
</html>
