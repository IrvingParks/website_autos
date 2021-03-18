<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DESARROLLO DE APLICACIONES WEB II</title>
        <link href="css/main.css" type="text/css" rel="stylesheet"/>
        <link href="jq/redmond/jquery-ui-1.10.0.custom.css" type="text/css" rel="stylesheet"/>
        <link href="jq/parainfo/autos.css" type="text/css" rel="stylesheet"/>

        <script src="jq/jquery-1.9.0.js" type="text/javascript"></script>
        <script src="jq/jquery-ui-1.10.0.custom.js" type="text/javascript"></script>
        <script src="jq/i18n/jquery.ui.datepicker-es.js" type="text/javascript"></script>

        <script src="jq/parainfo/autos.js" type="text/javascript"></script>
    </head>
    <body>
        <hr style="width: 400px;color: #def"/>
        <h3 style="text-align: center">Alquiler de Autos - La Fontanera del Oriente</h3>
        <hr style="width: 400px;color: #def"/>
        <p style="text-align: center">
            <a href="mantenimiento.jsp">Mantenimiento de Alquileres</a>
        </p>
        <hr style="width: 400px;color: #def"/>
        
        <table style="margin: auto;margin-top: 20px">
            <tr>
                <td style="vertical-align: top;padding-top: 32px">
                    <table style="width: 260px">
                        <tr>
                            <td style="text-align: right">Marca <input id="marca"/></td>
                            <td style="padding-right: 20px">
                                <span class="ui-icon ui-icon-search" onclick="verFotos()"></span>
                            </td>
                        </tr>
                        <tr>
                            <td id="cajafotos" colspan="2"></td>
                        </tr>
                    </table>
                </td>
                <td style="vertical-align: top">
                    <div id="delete" style="text-align: right;padding-right: 20px">
                        <img src="images/delete.png" alt="Retirar Auto"/>
                    </div>
                    <div id="trash" class="ui-widget-content ui-state-default" style="width: 260px">
                        <h4 class="ui-widget-header">&nbsp;Selección de Autos para Alquiler</h4>
                    </div>
                    <div style="text-align: center">
                        <input type="button" value="Alquilar Autos" 
                               id="alquiler" style="margin-top: 10px"/>
                    </div>
                </td>
            </tr>
        </table>

        <div style="display: none">
            <div id="dins" title="Alquiler de Autos">
                <form action="Autos" method="post" onsubmit="return valida()">
                    <input type="hidden" name="accion" value="INS_ALQ">
                    <input type="hidden" name="ids" id="ids"/>

                    <table>
                        <tr>
                            <td>Inicio&nbsp;Alquiler</td>
                            <td>
                                <input type="text" id="fechaalquiler" 
                                       name="fechaalquiler" readonly="readonly"
                                       style="margin-right: 5px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fin&nbsp;Alquiler</td>
                            <td>
                                <input type="text" id="fechafinalquiler" 
                                       name="fechafinalquiler" readonly="readonly"
                                       style="margin-right: 5px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Hora inicio</td>
                            <td>
                                <input type="text" name="horainicio" maxlength="10" 
                                       style="width: 60px"/> (hh:mm)
                            </td>
                        </tr>
                        <tr>
                            <td>Hora fin</td>
                            <td>
                                <input type="text" name="horafin" maxlength="10" 
                                       style="width: 60px"/> (hh:mm)
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <input type="submit" value="Enviar Datos"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
