<%--
  Created by IntelliJ IDEA.
  User: rudsom.lima
  Date: 25/05/2018
  Time: 08:50
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    %{--<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>--}%
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.standalone.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.js"></script>

    %{--<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>--}%
    %{--<!------ Include the above in your HEAD tag ---------->--}%

    %{--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>--}%
    %{--<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>--}%
    <meta name="layout" content="main">

</head>
<body>

</div>
<br/>

<div align="center">
    %{--<div class="container">--}%
        <div class="row">
            <div class="col-md-3 col-md-offset-4" >

                <g:form id="formOcorrencia" class="form-inline">
                    <div class="form-group input-group input-daterange" id="calendario">
                        <input required=true id="dataInicio" type="text" class=" form-control" name="dataI">
                        %{--<div id="data"></div>--}%
                        %{--<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>--}%
                        <div class="input-group-addon">até</div>
                        <input type="text" id="dataFim" class="form-control" name="dataF">
                    </div>
                    <div></div>
                    <div align="center" name="processar">
                        <br/>
                        <g:actionSubmit class="btn btn-danger btn-lg" value="Gerar" action="periodo"/>
                    </div>
                </g:form>

                <br/>

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
                                <td>
                                    <div align="center">
                                        <g:link action="excluirData" id="${datas}" onclick="return confirm('Quer mesmo excluir o registro?')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></i></g:link>
                                    </div>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <g:form>
                    <div align="center" name="processar">
                        <g:actionSubmit class="btn btn-danger btn-lg" value="Excluir tudo" action="excluir"></g:actionSubmit>
                    </div>
                </g:form>
            </div>

        </div>
    </div>
</div>

<g:javascript>

    // alert("O campo data deve ser preenchido!");

    $(document).ready(function () {

        // alert("O campo data deve ser preenchido!");

        $('#alertaData').hide()
        $('#gerarBtn').on('click', function (e) {
            alert("O campo data deve ser preenchido!");
            if ($('#dataInicio').val().length == 0) {
                e.preventDefault()
                $('#alertaData').show()
                alert("O campo data deve ser preenchido!");
            }
        });

        $('.input-daterange').datepicker({
            language: "pt-BR",
            format: "dd/mm/yyyy",
            clearBtn: true,
            todayHighlight: true,
            toggleActive: true,
            orientation: "botton"
        });

        $('#dataInicio').on('change', function () {
            var dataInicio = $('#dataInicio').val();
            console.log(dataInicio);
        });

        $('#dataFim').on('change', function () {
            var dataFim = $('#dataFim').val();
            console.log(dataFim);
        });

    });

</g:javascript>

</body>

</html>