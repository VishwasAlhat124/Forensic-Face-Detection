<%@ page import="java.io.*, java.util.Base64"%>

<%
    String imgData = request.getParameter("imgData");

    imgData = imgData.replace("data:image/png;base64,", "");

    byte[] imageBytes = Base64.getDecoder().decode(imgData);

    String savePath = "C:/sketches/";
    File dir = new File(savePath);
    if(!dir.exists()) dir.mkdirs();

    String fileName = "face_" + System.currentTimeMillis() + ".png";

    FileOutputStream fos = new FileOutputStream(savePath + fileName);
    fos.write(imageBytes);
    fos.close();

    out.println("<h2>Sketch Saved Successfully!</h2>");
    out.println("<p>Saved at: " + savePath + fileName + "</p>");
%>
