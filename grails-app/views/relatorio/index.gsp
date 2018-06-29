
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
                    <div class="col-xs-3 text-center">
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
                            <g:each in="${nRelList.sort{-it.nTirosMatador}}" status="k" var="matador">
                                <tr>
                                    <td id="matador" style="vertical-align: middle">
                                        <h4> ${matador.matador} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                        <div class="col-xs-3 text-center">
                            <g:each in="${nRelList.sort{-it.nTirosMatador}}" status="l" var="matador">
                                <tr>
                                    <td id="nMatador" style="vertical-align: middle">
                                        <h4> ${matador.nTirosMatador} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: '001-sieve.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge">12</div>
                        <div><h4>New Tasks!</h4></div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="clearfix"></div>
                                <g:each in="${nRelList.sort{it.kd}}" status="l" var="peneira">
                                    <tr>
                                        <td id="peneira" style="vertical-align: middle">
                                            <h4> ${peneira.matador} </h4>
                                        </td>
                                    </tr>
                                </g:each>
                        </div>
                        <div class="col-xs-4 text-center">
                            <g:each in="${nRelList.sort{it.kd}}" status="l" var="peneira">
                                <tr>
                                    <td id="nPeneira" style="vertical-align: middle">
                                        <h4> ${peneira.nTirosVitima} &nbsp (${String.format("%.3f", peneira.kd)}) </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <g:link controller="relatorio" action="index" params="[id: data.format('yyyy-MM-dd 00:00:00'), tipo: 'facadas']">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: '002-knife.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"><h3>BOA TARDE</h3></div>
                        <div><h4>${boaTarde}</h4></div>
                    </div>
                </div>
                </g:link>
            </div>
                <div class="panel-footer">
                    <div class="clearfix"></div>
                    <div class="row">
                        <div class="col-xs-9">
                            <g:each in="${nRelList.sort{-it.nFacadasMatador}}" status="l" var="esfaqueador">
                                <tr>
                                    <td id="esfaqueador" style="vertical-align: middle">
                                        <h4> ${esfaqueador.matador} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                        <div class="col-xs-3 text-center">
                            <g:each in="${nRelList.sort{-it.nFacadasMatador}}" status="l" var="esfaqueador">
                                <tr>
                                    <td id="nEsfaqueador" style="vertical-align: middle">
                                        <h4> ${esfaqueador.nFacadasMatador} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                    </div>
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
                <div class="panel-footer">
                    <div class="clearfix"></div>
                    <table id="tabela_ima" class="table table-hover" style="margin-bottom: 0px" border="0">
                        <g:each in="${nRelList.sort{-it.nFacadasVitima}}" status="l" var="esfaqueador">
                            <tr class="row">
                                <td class="col-xs-9">
                                    ${esfaqueador.matador}
                                </td>
                                <td class="col-xs-3 text-center" id="facadas3">
                                    ${esfaqueador.nFacadasVitima}
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
        </div>
    </div>
</div>

</body>
</html>