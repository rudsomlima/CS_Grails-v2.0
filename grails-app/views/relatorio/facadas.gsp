
<!DOCTYPE html>
<html>
<head>
    %{--<link href="maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--}%
    %{--<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>--}%
    %{--<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>--}%
    <!------ Include the above in your HEAD tag ---------->

    %{--<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>--}%
    %{--<script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>--}%
    <meta name="layout" content="main">

</head>

<style>

#deu, levou {
    style: "vertical-align: middle"
}

/*table {*/
    /*font: 11px/24px 'Source Sans Pro', 'Helvetica Neue', Arial, sans-serif,  Open Sans;*/
    /*border-collapse: collapse;*/
    /*width: 320px;*/
/*}*/

/*#tabela_pequena {*/
    /*padding: 1px;*/
    /*margin-bottom: 0px;*/
/*}*/


</style>

<body>

%{--<g:link controller="relatorio" action="periodo" params="[dataI: dataI.format('dd/MM/yyyy 00:00:00'), dataF: dataF.format('dd/MM/yyyy 00:00:00'), tipo: 'facadas']"><span class="btn btn-success btn-lg">Mano a mano: ${dataI.format('dd-MM-yyyy')} a ${dataF.format('dd-MM-yyyy')}</span></g:link>--}%
</div>
<br/>

%{--<div class="container">--}%
    <div class="row">
        <div class="col-md-5 col-md-offset-3" >
            <div class="panel panel-primary" align="center">
                <g:if test="${dataF == null}">
                    <div class="panel-heading"><h2>FACADAS - ${dataView}</h2></div>
                </g:if>
                <g:else>
                    <div class="panel-heading"><h2>FACADAS - ${dataI.format('dd-MM-yyyy')} a ${dataF.format('dd-MM-yyyy')}</h2></div>
                </g:else>

                    <table id="tabela_grande" class="table table-bordered table-condensed table-hover" style="margin-bottom: 0px" border="1">
                        <thead>
                            <tr>
                                <th class="text-center">Jogador</th>
                                <th class="text-center">Vítimas</th>
                                <th class="text-center">&nbsp Deu &nbsp</th>
                                <th class="text-center">Algozes</th>
                                <th class="text-center">Levou</th>
                            </tr>
                        </thead>
                        <tbody>
                            %{--<g:set var="n" value="${nFacasList.nFacadasMatador[0]}"/>--}%
                            <g:each in="${jogadoresList}" status="j" var="jogador">
                                %{--<g:if test="${jogador.nome.indexOf("XXBOT")<0}">--}%
                                <tr>
                                    <td id="jogador" style="vertical-align: middle">
                                        <h4>${jogador}
                                        <g:if test="${nRelList.nFacadasAmiga[j]>0}">
                                            <class id="facaAmiga" style="color: red"> (-${nRelList.nFacadasAmiga[j]})
                                            </class>
                                        </g:if>
                                        </h4>
                                    </td>

                                    <td>
                                        <div>
                                            <table id="tabela_pequena" class="table table-condensed table-striped" style="margin-bottom: 0px; font-size: smaller">
                                                <g:each in="${relFacas}" status="i" var="facada">
                                                    <g:if test="${jogador.nome.equals(facada.matador)}">
                                                        <tr class="row">
                                                            <td class="col-md-8">
                                                                <strong>${facada.vitima}</strong>
                                                            </td>
                                                            <td class="col-md-4 text-center" style="vertical-align: middle" id="facadas">
                                                                <strong>${facada.somaFacas}</strong>
                                                            </td>
                                                        </tr>
                                                    </g:if>
                                                </g:each>
                                            </table>
                                        </div>
                                    </td>
                                    <td class="text-center" id="deu" style="vertical-align: middle">
                                        %{--<span class="label label-success">  ${nFacasList.nFacadasMatador[j]}  </span>--}%
                                        %{--<h4 style="color: green"><b>${nFacasList.nFacadasMatador[j]}</b></h4>--}%
                                        <h4 style="color: green"><b>${nRelList.nFacadasMatador[j]}</b></h4>
                                    </td>

                                    <td>
                                        <div>
                                            <table id="tabela_pequena2" class="table table-condensed table-striped" style="margin-bottom: 0px; font-size: smaller">
                                                <g:each in="${relFacas}" status="i" var="facada">
                                                    <g:if test="${jogador.nome.equals(facada.vitima)}">
                                                        <tr class="row">
                                                            <td class="col-md-8">
                                                                <strong>${facada.matador}</strong>
                                                            </td>
                                                            <td class="col-md-4 text-center" id="facadas2">
                                                                <strong>${facada.somaFacas}</strong>
                                                            </td>
                                                        </tr>
                                                    </g:if>
                                                </g:each>
                                            </table>
                                        </div>
                                    </td>

                                    <td class="text-center" id="levou" style="vertical-align: middle">
                                        %{--<span class="label label-danger">  ${nFacasList.nFacadasVitima[j]}  </span>--}%
                                        <h4 style="color: red"><b>${nRelList.nFacadasVitima[j]}</b></h4>
                                    </td>

                                    %{--<td class="text-center"> ${soma + facada.qtdeFacadas} </td>--}%
                                </tr>
                                %{--<g:set var="n" value="${n++}"/>--}%
                            </tbody>
                                %{--</g:if>--}%
                        </g:each>
                    </table>
            </div>
        </div>

        </div>
    %{--</div>--}%

</body>
</html>