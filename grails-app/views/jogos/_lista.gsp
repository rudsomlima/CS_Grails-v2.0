
<g:if test="${jogadores.size() > 0}">
    <table>
        <tr>
            <th>Jogador</th>
            <th>Vítima</th>
            <th>Facadas</th>
        </tr>
        <g:each var="matador" in="${matadores}">
            <tr>
                <td>${matador}</td>

            </tr>
        </g:each>
    </table>
</g:if>
<g:else>
    Não há produtos.
</g:else>