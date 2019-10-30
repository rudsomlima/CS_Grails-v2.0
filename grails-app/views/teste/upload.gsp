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

<br/>

    <div align="center">

        <g:form>
            <div align="center" name="processar">
                <g:actionSubmit class="btn btn-danger btn-lg" value="Processar arquivos" action="rodar"></g:actionSubmit>
            </div>
        </g:form>
        <br>
        <g:form>
            <div align="center" name="processar">
                <g:actionSubmit class="btn btn-lg" value="Processar rapido" action="rodarRapido"></g:actionSubmit>
            </div>
        </g:form>
        <br>

        <div id="divStatus">
            <g:render template="status" model="[n__linha: n_linha]"></g:render>
        </div>

        <div id="fileuploader">Upload</div>



    </div>


        %{--<div align="center">--}%
            %{--<button class="testMe" onclick="callAjax();">Processar logs</button>--}%
        %{--</div>--}%


<script>
    $(document).ready(function()
    {

        $('#valorInput').on('change', function() {
            var value = $(this).val();
            $('#valor b').text('R$ '+value);
        });



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