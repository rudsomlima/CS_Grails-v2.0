
<!DOCTYPE html>
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

<style>

.table {
    /*text-align: center;*/
    /*align-items: center;*/
    /*text-align: center;*/
    /*vertical-align: middle;*/
    /*horiz-align: center;*/
    padding: 1px;
    margin-bottom: 0px;
}

#tabela_pequena {
    padding: 1px;
    margin-bottom: 0px;
}


</style>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-6" >
            <div class="panel panel-primary" align="center">
                <div class="panel-heading"><h2>FACADAS</h2></div>
                    <table id="tabela_grande" class="table table-bordered" border="1">
                        <thead>
                            <tr>
                                <th class="text-center">Matador</th>
                                <th class="text-center">Vítimas</th>
                                <th class="text-center">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${jogadoresList}" status="j" var="jogador">
                                <tr>
                                    <td id="jogador">
                                        <h4> ${jogador} </h4>
                                    </td>

                                    <td>
                                        <div>
                                            <table id="tabela_pequena" class="table table-striped table-condensed" style="margin-bottom: 0px">
                                                <g:set var="soma" value="${0}"/>
                                                <g:each in="${facadasList}" status="i" var="facada">
                                                    <g:if test="${jogador.nome.equals(facada.vitima.matador.nome)}">
                                                        <tr class="row">
                                                            <td class="col-md-8">
                                                                ${facada.vitima.vitima.nome}
                                                            </td>
                                                            <td class="col-md-4 text-center" id="facadas">
                                                                ${facada.qtdeFacadas}
                                                            </td>
                                                        </tr>
                                                        <g:set var="soma" value="${soma + facada.qtdeFacadas}"/>
                                                    </g:if>
                                                </g:each>
                                                <tr class="row">
                                                    <td class="col-md-8" id="total">Total:</td>
                                                    <td class="col-md-4 text-center bg-primary" id="soma">
                                                        ${soma}
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                    <td>
                                        %{--<g:set var="soma" value="${0}"/>--}%
                                        %{--<g:each in="${facadasList}" status="i" var="facada">--}%
                                            %{--<g:set var="soma" value="${soma + facada.qtdeFacadas}"/>--}%
                                            %{--${soma}--}%
                                        %{--</g:each>--}%
                                    </td>
                                    %{--<td class="text-center"> ${soma + facada.qtdeFacadas} </td>--}%
                                </tr>
                            </tbody>
                        </g:each>
                    </table>
            </div>
        </div>

        </div>
    </div>

</body>
</html>