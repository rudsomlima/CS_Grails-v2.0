<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
    <asset:stylesheet src="datepicker/css/bootstrap-datepicker.css"/>
    <asset:javascript src="datepicker/js/bootstrap-datepicker.min.js"/>
    <asset:javascript src="datepicker/locales/bootstrap-datepicker.pt-BR.min.js"/>
    %{--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>--}%
    %{--<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />--}%
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    %{--<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>--}%
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <g:form id="formOcorrencia" class="form-inline">
                <div class="form-group input-group date" id="calendario">
                    DATA:
                    <input required=true id="data" type="text" class="input-small form-control" name="data">
                    <span class="add-on" style="height:20px"></span>
                </div>
            </g:form>
        </div>
        <div class="col-md-6">
            xxxxxxxx
        </div>
    </div>
</div>

<g:javascript>

    $(document).ready(function () {
        // alert("funcionou");
        $('#data').datepicker({
            language: "pt-BR",
            format: "dd/mm/yyyy",
            clearBtn: true,
            todayHighlight: true,
            orientation: "bottom left"
        });

        $('#data').on('change', function () {
            var periodo = $('#calendario').val();
            // alert(periodo);
        });
    });

</g:javascript>

</body>
</html>
