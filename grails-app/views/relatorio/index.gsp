
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

table {
    font: 11px/24px 'Source Sans Pro', 'Helvetica Neue', Arial, sans-serif,  Open Sans;
    border-collapse: collapse;
    width: 320px;
}

/*#tabela_pequena {*/
    /*padding: 1px;*/
    /*margin-bottom: 0px;*/
/*}*/


</style>

<body>

<div class="row">
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <img src="${resource(dir: 'images', file: '003-revolver.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">26</div>
                        <div>New Comments!</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-9">
                            <g:each in="${jogadoresList}" status="k" var="matador">
                                <tr>
                                    <td id="matador" style="vertical-align: middle">
                                        <h4> ${matador} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                        <div class="col-xs-3 text-right">
                            00
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <img src="${resource(dir: 'images', file: '001-sieve.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">12</div>
                        <div>New Tasks!</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                    <g:each in="${facadasList}" status="l" var="esfaqueador">
                        <tr>
                            <td id="peneira" style="vertical-align: middle">
                                <h4> ${esfaqueador.vitima.vitima.nome} </h4>
                            </td>
                        </tr>
                    </g:each>
                </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <img src="${resource(dir: 'images', file: '002-knife.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">124</div>
                        <div>New Orders!</div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div><g:each in="${facadasList}" status="l" var="esfaqueador">
                    <tr>
                        <td id="esfaqueador" style="vertical-align: middle">
                            <h4> ${esfaqueador.vitima.vitima.nome} </h4>
                        </td>
                    </tr>
                </g:each>
                </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3">
                        <img src="${resource(dir: 'images', file: '005-magnetism.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">13</div>
                        <div>Support Tickets!</div>
                    </div>
                </div>
            </div>
            <a href="#">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
</div>





%{--<div class="container">--}%
    <div class="row">
        <div class="col-md-4 col-md-offset-4" >
            <div class="panel panel-primary" align="center">
                <div class="panel-heading"><h2>FACADAS</h2></div>
                    <table id="tabela_grande" class="table table-bordered table-condensed table-hover" style="margin-bottom: 0px" border="1">
                        <thead>
                            <tr>
                                <th class="text-center">Jogador</th>
                                <th class="text-center">Vítimas</th>
                                <th class="text-center">&nbsp&nbspDeu&nbsp&nbsp</th>
                                <th class="text-center">Algozes</th>
                                <th class="text-center">Levou</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:set var="n" value="${nFacasList.nFacadasMatador[0]}"/>
                            <g:each in="${jogadoresList}" status="j" var="jogador">
                                <tr>
                                    <td id="jogador" style="vertical-align: middle">
                                        <h4>${jogador}</h4>
                                    </td>

                                    <td>
                                        <div>
                                            <table id="tabela_pequena" class="table table-striped" style="margin-bottom: 0px; font-size: smaller">
                                                <g:each in="${facadasList}" status="i" var="facada">
                                                    <g:if test="${jogador.nome.equals(facada.vitima.matador.nome)}">
                                                        <tr class="row">
                                                            <td class="col-md-8">
                                                                <strong>${facada.vitima.vitima.nome}</strong>
                                                            </td>
                                                            <td class="col-md-4 text-center" id="facadas">
                                                                <strong>${facada.qtdeFacadas}</strong>
                                                            </td>
                                                        </tr>
                                                    </g:if>
                                                </g:each>
                                            </table>
                                        </div>
                                    </td>
                                    <td class="text-center" id="deu" style="vertical-align: middle">
                                        %{--<span class="label label-success">  ${nFacasList.nFacadasMatador[j]}  </span>--}%
                                        <h4 style="color: green"><b>${nFacasList.nFacadasMatador[j]}</b></h4>
                                    </td>

                                    <td>
                                        <div>
                                            <table id="tabela_pequena2" class="table table-striped" style="margin-bottom: 0px; font-size: smaller">
                                                <g:each in="${facadasList}" status="i" var="facada">
                                                    <g:if test="${jogador.nome.equals(facada.vitima.vitima.nome)}">
                                                        <tr class="row">
                                                            <td class="col-md-8">
                                                                <strong>${facada.vitima.matador.nome}</strong>
                                                            </td>
                                                            <td class="col-md-4 text-center" id="facadas2">
                                                                <strong>${facada.qtdeFacadas}</strong>
                                                            </td>
                                                        </tr>
                                                    </g:if>
                                                </g:each>
                                            </table>
                                        </div>
                                    </td>

                                    <td class="text-center" id="levou" style="vertical-align: middle">
                                        %{--<span class="label label-danger">  ${nFacasList.nFacadasVitima[j]}  </span>--}%
                                        <h4 style="color: red"><b>${nFacasList.nFacadasVitima[j]}</b></h4>
                                    </td>

                                    %{--<td class="text-center"> ${soma + facada.qtdeFacadas} </td>--}%
                                </tr>
                                <g:set var="n" value="${n++}"/>
                            </tbody>
                        </g:each>
                    </table>
            </div>
        </div>

        </div>
    %{--</div>--}%

</body>
</html>