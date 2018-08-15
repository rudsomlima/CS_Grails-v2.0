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
    <title>Facadas CS</title>
    <link href="http://hayageek.github.io/jQuery-Upload-File/4.0.11/uploadfile.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <script src="http://hayageek.github.io/jQuery-Upload-File/4.0.11/jquery.uploadfile.js"></script>



</head>


<body>

<ul class="nav nav-pills">
    <li >
        <a href="/cs/teste/upload">Upload</a>
    </li>
    <li class="active"> <a href="/cs/relatorio">Relatórios</a> </li>
</ul>
<br/>

<div align="center">


    <g:form>
        <div align="center" name="processar">
            <g:actionSubmit class="btn btn-danger btn-lg" value="Processar arquivos" action="rodar"></g:actionSubmit>
        </div>
    </g:form>
    <br>
    <br>

    <div id="fileuploader">Upload</div>

        %{--<div align="center">--}%
            %{--<button class="testMe" onclick="callAjax();">Processar logs</button>--}%
        %{--</div>--}%

</div>

<script>
    $(document).ready(function()
    {
//        console.log(window.location.href)
//        console.log("########################## Script OK!");
        $("#fileuploader").uploadFile({
            url: "upload",      //barra no inicio sobre pro nivel de root
            multiple:true,
            dragDrop:true,
            fileName:"myfile",
//            onSuccess:function(files,data,xhr)
//            {
//                console.log(files)
//            },
        });
    });

    %{--function callAjax(){--}%
        %{--var URL="${createLink(controller:'teste',action:'rodar')}";--}%
        %{--$.get(URL);--}%
    %{--}--}%
</script>
</body>
</html>