<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DESARROLLO DE APLICACIONES WEB II</title>
        <link href="css/main.css" type="text/css" rel="stylesheet"/>
        <link href="jq/redmond/jquery-ui-1.10.0.custom.css" type="text/css" rel="stylesheet"/>
        <link href="jq/parainfo/crud.css" type="text/css" rel="stylesheet"/>

        <script src="jq/jquery-1.9.0.js" type="text/javascript"></script>
        <script src="jq/jquery-ui-1.10.0.custom.js" type="text/javascript"></script>
        <script src="jq/i18n/jquery.ui.datepicker-es.js" type="text/javascript"></script>

        <script src="jq/parainfo/mantenimiento.js" type="text/javascript"></script>
        <script src="jq/parainfo/crud.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#mensaje").dialog({
                    modal: true,
                    autoOpen: true
                });
                  $("#inicio").button({
                                   }); 
                 }
               );
        </script>
        
    </head>
    <body>
        <div id="mensaje" title="Mnesaje de confirmación">
        <h3 style="text-align: center">Mensaje del Sistema</h3>

        <h4 style="text-align: center;color: #900">${msg}</h4>
       
        <p style="text-align: center">
            <a id="inicio" href="index.jsp">home</a>
        </p>
    </div>
</body>
</html>
