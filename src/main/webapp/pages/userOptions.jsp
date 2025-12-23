<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Criminal Options</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 50px;
        }
        .container {
            background: white;
            width: 400px;
            margin: auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px #bbb;
            text-align: center;
        }
        h2 {
            margin-bottom: 30px;
        }
        button {
            width: 80%;
            padding: 12px;
            margin: 10px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }
        .btn-add {
            background-color: #4CAF50;
            color: white;
        }
        .btn-sketch {
            background-color: #2196F3;
            color: white;
        }
        button:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Select Action</h2>
	
	<form action="/show-userOptions">
	    <button type="submit" class="btn-add">Edit Users</button>
	</form>

    <form action="/addCriminal">
        <button type="submit" class="btn-add">Add Criminal</button>
    </form>

    <form action="/drawSketch">
        <button type="submit" class="btn-sketch">Draw Sketch of Criminal</button>
    </form>
</div>

</body>
</html>
