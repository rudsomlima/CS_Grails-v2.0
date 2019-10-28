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

<div></div>

<br/>

<div class="row">
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
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
                <g:each in="${nRelList.sort{-it.nTirosMatador}}" status="k" var="matador">
                    <g:if test="${matador.matador.indexOf("XXBOT")<0 & matador.nTirosMatador>0}" >
                        <div class="row">
                            <div class="col-xs-9">
                                <h4> ${matador.matador} </h4>
                            </div>
                            <div class="col-xs-3 text-right">
                                <h4>${matador.nTirosMatador}</h4>
                            </div>
                        </div>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
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
                <g:each in="${nRelList.sort{it.kd}}" status="l" var="peneira">
                    <g:if test="${peneira.matador.indexOf("XXBOT")<0 & peneira.nTirosVitima>0}" >
                        <div class="row">
                            <div class="col-xs-8">
                                <h4> ${peneira.matador} </h4>
                            </div>
                            <div class="text-center">
                                <h4>${peneira.nTirosVitima} &nbsp (${String.format("%.2f", peneira.kd)})</h4>
                            </div>
                        </div>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
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
                <g:each in="${nRelList.sort{-it.nFacadasMatador}}" status="l" var="esfaqueador">
                    <g:if test="${esfaqueador.nFacadasMatador>0 | esfaqueador.nFacadasAmiga>0}" >
                        <div class="row">
                            <div class="col-xs-9">
                                <h4>${esfaqueador.matador}
                                    <class id="facaAmiga" style="font-size:11px">${esfaqueador.ordemFacada}&deg</class>
                                    <g:if test="${esfaqueador.nFacadasAmiga>0}">
                                        <class id="facaAmiga" style="color: red"> <b> (-${esfaqueador.nFacadasAmiga}) </b></class>
                                    </g:if>
                                </h4>
                            </div>
                            <div class="col-xs-3 text-right">
                                <h4>${esfaqueador.nFacadasMatador}</h4>
                            </div>
                        </div>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: '005-magnetism.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"><h2>ÍMÃ</h2></div>
                        <div><h4>Faca no lombo</h4></div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <g:each in="${nRelList.sort{-it.nFacadasVitima}}" status="z" var="ima">
                    <g:if test="${ima.nFacadasVitima>0}" >
                        <div class="row">
                            <div class="col-xs-9">
                                <h4>${ima.matador}
                                    <class id="facaAmiga" style="font-size:11px">${ima.ordemFacadaVitima}&deg</class>
                                    <g:if test="${ima.nFacadasAmiga>0}">
                                        <class id="facaAmiga" style="color: red"> <b> (+${ima.nFacadasAmiga}) </b>
                                        </class>
                                    </g:if>
                                </h4>
                            </div>
                            <div class="col-xs-3 text-right">
                                <h4>${ima.nFacadasVitima}</h4>
                            </div>
                        </div>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
</div>
%{--------------------------------------------------------------------------------------------------------------------}%
<div class="row">
    <g:if test="${!relAmiga.isEmpty()}">
        <div class="col-md-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3 text-center">
                            <img src="${resource(dir: 'images', file: '002-knife.png')}" width="100%"/>
                        </div>
                        <div class="col-xs-9 text-right">
                            <div class="huge"> <h2>FACA AMIGA</h2></div>
                            <div><h4>Esfaqueou o amiguinho</h4></div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-xs-9"
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
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: 'flag.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"> <h2>MAPAS</h2></div>
                        <div><h4>CT x Terror</h4></div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <g:each in="${listResultMapa}" status="x" var="mapa">
                    <div class="row">
                        <div class="col-xs-8 text-lef" >
                            <h4>${mapa.Vencedor}</h4>
                        </div>
                        <div class="col-xs-4 text-right">
                            <h4>${mapa.CT} x ${mapa.Terror} </h4>
                        </div>
                    </div>
                </g:each>
                <div class="row">
                    <div class="col-xs-6 text-lef" >
                    </div>
                    <div class="col-xs-6 text-right">
                        <h4>--------------</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-8 text-lef" >
                        <h4>TOTAL</h4>
                    </div>
                    <div class="col-xs-4 text-right">
                        <h4>${listResultMapa.CT.sum()} x ${listResultMapa.Terror.sum()}</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
%{--------------------------------------------------------------------------------------------------------------------}%
    <div class="col-md-3">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-3 text-center">
                        <img src="${resource(dir: 'images', file: 'taca_campeao.png')}" width="100%"/>
                    </div>
                    <div class="col-xs-9 text-right">
                        <div class="huge"><h2>${listResultado.get(6)}</h2></div>
                        <div><h4>CT ${listResultado.get(5)} x ${5-listResultado.get(5)} TERROR</h4></div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-xs-3">
                        <h4>Matador</h4>
                    </div>
                    <div class="col-xs-9 text-right">
                        <h4>${listResultado.get(0)}</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <h4>Peneira</h4>
                    </div>
                    <div class="col-xs-9 text-right">
                        <h4>${listResultado.get(1)}</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <h4>Esfaqueador</h4>
                    </div>
                    <div class="col-xs-9 text-right">
                        <h4>${listResultado.get(2)}</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <h4>Íma</h4>
                    </div>
                    <div class="col-xs-9 text-right">
                        <h4>${listResultado.get(3)}</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <h4>Mapas</h4>
                    </div>
                    <div class="col-xs-9 text-right">
                        <h4>${listResultado.get(4)}</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
%{--------------------------------------------------------------------------------------------------------------------}%


</div>

</body>
</html>