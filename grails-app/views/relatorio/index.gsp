
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

body {
    %{--background-image:url('${resource(dir: "images/", file: "fundo.jpg")}');height: 250px;background-repeat: no-repeat;background-position: center;width:1000px;--}%
    background-color: #002a80;
}

levou {
    style: "vertical-align: middle"
}

table {
    font: 11px/24px 'Source Sans Pro', 'Helvetica Neue', Arial, sans-serif,  Open Sans;
    border-collapse: collapse;
    width: 320px;
}

</style>

<body style="background-color: #122b40" >

%{--<ul class="nav nav-pills">--}%
    %{--<li class="active">--}%
        %{--<a href="/cs/relatorio">Relatórios</a>--}%
    %{--</li>--}%
    %{--<li><a href="/cs/teste/upload">Upload</a></li>--}%
    %{--<li>${data.format('dd-MM-yyyy')}</li>--}%
%{--</ul>--}%


    <g:link controller="relatorio" action="index" params="[id: data.format('yyyy-MM-dd 00:00:00'), tipo: 'facadas']"><span class="btn btn-success btn-lg">Mano a mano: ${data.format('dd-MM-yyyy')}</span></g:link>
</div>
<br/>

%{--<div class="bg-primary text-white">Nullam id dolor id nibh ultricies vehicula ut id elit.</div>--}%

<div class="row">
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: '003-revolver.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"> <h2>MATADOR</h2></div>
                        <div><h4>TIROS + FACADAS</h4></div>
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
                        <div class="huge"><h2>PENEIRA</h2></div>
                        <div><h4>TIROS + FACADAS</h4></div>
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
                        <div class="huge"><h2>BOA TARDE</h2></div>
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
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: '005-magnetism.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"><h2>ÍMÃ</h2></div>
                        <div></div>
                    </div>
                </div>
            </div>
                <div class="panel-footer">
                    <div class="clearfix"></div>
                    <div class="row">
                        <div class="col-xs-9">
                        <g:each in="${nRelList.sort{-it.nFacadasVitima}}" status="l" var="esfaqueador">
                            <tr>
                                <td id="ima" style="vertical-align: middle">
                                    <h4>${esfaqueador.matador}</h4>
                                </td>
                            </tr>
                        </g:each>
                        </div>
                        <div class="col-xs-3 text-center">
                            <g:each in="${nRelList.sort{-it.nFacadasVitima}}" status="l" var="esfaqueador">
                                <tr>
                                    <td id="nIma" style="vertical-align: middle">
                                        <h4> ${esfaqueador.nFacadasVitima} </h4>
                                    </td>
                                </tr>
                            </g:each>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>

<div class="row">
    <g:if test="${!relAmiga.isEmpty()}">
        <div class="col-lg-3 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3 text-center">
                            <img src="${resource(dir: 'images', file: '002-knife.png')}" width="100%"/>
                        </div>
                        <div class="col-xs-9 text-right">
                            <div class="huge"> <h2>FACADA AMIGA</h2></div>
                            <div><h4>Esfaqueou o amiguinho</h4></div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-9" >
                            <g:each in="${relAmiga}" status="x" var="faca">
                                    <tr>
                                        <td id="facaAmiga" style="vertical-align: middle">
                                            <h4> ${faca.facaAmiga} </h4>
                                        </td>
                                    </tr>
                            </g:each>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </g:if>

    <g:if test="${!relAmiga.isEmpty()}">
        <div class="col-lg-2 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3 text-center">
                            <img src="${resource(dir: 'images', file: 'flag.png')}" width="100%"/>
                        </div>
                        <div class="col-xs-9 text-right">
                            <div class="huge"> <h2>MAPAS</h2></div>
                            <div><h4>CT x TERROR</h4></div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-12 text-center" >

                                    <td id="facaAmiga" style="vertical-align: middle">
                                        %{--<h4> ${mapaFinal} </h4>--}%
                                        <h4>
                                            <g:each in="${mapaFinal}">
                                                ${it}<br>
                                            </g:each>
                                        </h4>
                                    </td>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </g:if>

</div>

</body>
</html>