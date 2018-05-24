
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">

</head>
<body>
<div align="center">
    <ul>

        <g:each in="${datasList}" status="j" var="datas">
            <li>
                <g:link controller="relatorio" action="index" id="${datas}">
                    ${datas}
                </g:link>
            </li>
            principal
        </g:each>


    </ul>
</div>
</body>
</html>