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
    <link href="http://hayageek.github.io/jQuery-Upload-File/4.0.11/uploadfile.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://hayageek.github.io/jQuery-Upload-File/4.0.11/jquery.uploadfile.min.js"></script>
    %{--<asset:stylesheet src="uploadfile.css"/>--}%
    %{--<asset:javascript src="jquery-2.2.0.min.js"/>--}%
    %{--<asset:javascript src="jquery.uploadfile.min.js"/>--}%
    <title>Facadas CS</title>

    <script>
        $(document).ready(function()
        {
            console.log(window.location.href)
            console.log("########################## Script OK!");
            $("#multipleupload").uploadFile({
                url:"grails-app/",            //barra sobre pro nivel de root
                multiple:true,
                dragDrop:true,
                fileName:"myfile"
            });
        });

        function callAjax(){
            var URL="${createLink(controller:'teste',action:'upload')}";
            $.get(URL);
        }
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

    <div align="center">
        <button class="testMe" onclick="callAjax();">Teste</button>
    </div>

</div>
</body>
</html>