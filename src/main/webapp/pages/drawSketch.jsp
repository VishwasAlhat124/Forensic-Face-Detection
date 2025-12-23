<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Face Sketch Creator</title>
    <style>
        body { margin:0; font-family: Arial; background:#d9f8ff; }
        .left-menu { width:120px; background:#222; color:white; height:100vh; float:left; padding-top:30px; text-align:center; }
        .left-menu div { margin:20px 0; cursor:pointer; }
        .canvas-area { margin-left:120px; width:60%; float:left; display:flex; justify-content:center; align-items:center; height:100vh; }
        #sketchCanvas { width:500px; height:500px; border:1px solid #ccc; position:relative; background:white; }
        .item { width:90px; height:90px; margin:10px; cursor:pointer; border:1px solid #eee; }
        .right-panel { width:320px; background:#0b2b4c; height:100vh; float:right; padding:20px; }
        .right-panel img { width:90px; height:90px; }
        .draggable { position:absolute; cursor:move; }
        .btn-box { margin-bottom:20px; }
        .btn { padding:10px; color:white; background:black; border:none; margin-right:10px; cursor:pointer; }
    </style>
</head>
<body>
<div class="left-menu">
    <div onclick="loadCategory('head')">Head</div>
    <div onclick="loadCategory('hair')">Hair</div>
    <div onclick="loadCategory('eyes')">Eyes</div>
    <div onclick="loadCategory('eyebrows')">Eyebrows</div>
    <div onclick="loadCategory('nose')">Nose</div>
    <div onclick="loadCategory('lips')">Lips</div>
    <div onclick="loadCategory('mustache')">Mustache</div>
</div>

<div class="canvas-area">
    <div id="sketchCanvas"></div>
</div>

<div class="right-panel">
    <div class="btn-box">
        <button class="btn" onclick="saveSketch()">SAVE</button>
        <button class="btn" onclick="resetCanvas()">RESET</button>
    </div>
    <div id="imageList"></div>
</div>

<script>
function loadCategory(cat) {
    let container = document.getElementById("imageList");
    container.innerHTML = "";
    for (let i = 1; i <= 8; i++) {
        let img = document.createElement("img");
        img.className = "item";
        // Fixed path for Tomcat: webapp/images/<category>/<index>.png
		img.src = "<c:url value='/static/images/" + cat + "/" + i + ".png' />";
        img.draggable = true;
        img.addEventListener("dragstart", function(e){
            e.dataTransfer.setData("src", this.src);
        });
        container.appendChild(img);
    }
}

let canvas = document.getElementById("sketchCanvas");
canvas.addEventListener("dragover", function(e){ e.preventDefault(); });
canvas.addEventListener("drop", function(e){
    e.preventDefault();
    let src = e.dataTransfer.getData("src");
    let newImg = document.createElement("img");
    newImg.src = src;
    newImg.className = "draggable";
    newImg.style.left = e.offsetX + "px";
    newImg.style.top = e.offsetY + "px";
    makeDraggable(newImg);
    canvas.appendChild(newImg);
});

function makeDraggable(el) {
    let offsetX, offsetY;
    el.onmousedown = function(e){
        offsetX = e.clientX - el.offsetLeft;
        offsetY = e.clientY - el.offsetTop;
        document.onmousemove = function(e){
            el.style.left = (e.clientX - offsetX) + "px";
            el.style.top = (e.clientY - offsetY) + "px";
        }
        document.onmouseup = function(){ document.onmousemove = null; }
    };
}

function resetCanvas() { canvas.innerHTML = ""; }

function saveSketch() {
    html2canvas(document.querySelector("#sketchCanvas")).then(canvasImg => {
        let imgData = canvasImg.toDataURL("image/png");
        let form = document.createElement("form");
        form.method = "POST";
        form.action = "saveImage.jsp";
        let input = document.createElement("input");
        input.type = "hidden";
        input.name = "imgData";
        input.value = imgData;
        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    });
}
</script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
</body>
</html>
