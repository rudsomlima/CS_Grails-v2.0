<%--
  Created by IntelliJ IDEA.
  User: rudsom.lima
  Date: 25/05/2018
  Time: 08:50
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
    <meta name="layout" content="main">

</head>
<body>

<div align="center">
    %{--<div class="container">--}%
        <div class="row">
            <div class="col-md-2 col-md-offset-5" >
                <div class="panel panel-primary" align="center">
                    <div class="panel-heading"><h2>RELATÓRIOS</h2></div>
                    <table id="tabela_grande" class="table table-bordered table-condensed table-hover" style="padding: 0px" border="1">
                        <tbody>
                            <g:each in="${datasList}" status="j" var="datas">
                            <tr class="row">
                                <td class="col-md-8 text-center">
                                    <g:link controller="relatorio" action="index" id="${datas}">
                                        %{--${datas}--}%
                                        ${datas.format("dd-MM-yyyy")}
                                    </g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        %{--</div>--}%
    </div>
</div>

</body>
</html>