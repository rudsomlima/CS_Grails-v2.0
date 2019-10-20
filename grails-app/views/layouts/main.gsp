<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Mano a mano"/>
    </title>
    %{--<meta name="viewport" content="width=device-width, initial-scale=1"/>--}%
    <asset:link href="bootstrap.css" rel="stylesheet"/>
    <asset:script scr="bootstrap.js" rel="javascript"/>
    <g:layoutHead/>
</head>
<body>

<div>
    <a class="btn btn-primary btn-lg" href="/relatorio" role="button">Relatórios</a>
    <a class="btn btn-danger btn-lg" href="/teste/upload" role="button">Upload</a>


    %{--<ul class="nav nav-pills">--}%
        %{--<li class="active">--}%
            %{--<a href="/relatorio">Relatórios</a>--}%
        %{--</li>--}%
        %{--<li><a href="/teste/upload">Upload</a></li>--}%
    %{--</ul>--}%
    %{--<br/>--}%

    <g:layoutBody/>

    %{--<div class="footer" role="contentinfo"></div>--}%

    %{--<div id="spinner" class="spinner" style="display:none;">--}%
        %{--<g:message code="spinner.alt" default="Loading&hellip;"/>--}%
    %{--</div>--}%

    <asset:javascript src="application.js"/>

</body>
</html>
