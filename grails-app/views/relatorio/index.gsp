
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">

</head>
<body>


<table border="1">
    <tr>
        <th>Matador</th>
        <th>Vítimas</th>

    </tr>

    <g:each in="${jogadoresList}" status="j" var="jogador">
        <tr>
            <td id="jogador">
                ${jogador}
            </td>

            <td>
                <table>
                    <g:set var="soma" value="${0}"/>
                    <g:each in="${facadasList}" status="i" var="facada">

                        <g:if test="${jogador.nome.equals(facada.vitima.matador.nome)}">
                            <tr>
                                <td>
                                    ${facada.vitima.vitima.nome}
                                </td>
                                <td id="facadas">
                                    ${facada.qtdeFacadas}
                                </td>
                            </tr>
                            <g:set var="soma" value="${soma + facada.qtdeFacadas}"/>
                        </g:if>
                    </g:each>
                    <tr>
                        <td id="total">Total:</td>
                        <td id="soma">
                            ${soma}
                        </td>
                    </tr>
                </table>
            </td>
        </tr>


    </g:each>

</table>
</body>
</html>