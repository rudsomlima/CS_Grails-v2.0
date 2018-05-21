<%--
  Created by IntelliJ IDEA.
  User: rudsom.lima
  Date: 20/05/2018
  Time: 14:06
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
    <asset:stylesheet src="uploadfile.css"/>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <asset:javascript src="jquery.uploadfile.min.js"/>
    <title>Facadas CS</title>

    <script>
        $(document).ready(function()
        {
            console.log("########################## Script OK!");
            $("#multipleupload").uploadFile({
                url:"http://hayageek.com/examples/jquery/ajax-multiple-file-upload/upload.php",
                multiple:true,
                dragDrop:true,
                fileName:"myfile"
            });
        });
    </script>

</head>



<body>
<div align="center">
    %{--<form id="myForm" action="comment.php" method="post">--}%
        %{--Name: <input type="text" name="name" />--}%
        %{--Comment: <textarea name="comment"></textarea>--}%
        %{--<input type="submit" value="Submit Comment" />--}%
    %{--</form>--}%
    <div id="multipleupload">Upload</div>



</div>
</body>
</html>