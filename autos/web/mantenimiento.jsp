<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </head>
    <body onload="pi_load()">
        <hr style="width: 400px;color: #def"/>
        <h3 style="text-align: center">Mantenimiento de Alquileres</h3>
        <hr style="width: 400px;color: #def"/>

        <table style="margin: auto;margin-top: 20px">

            <table style="margin: auto">
                <tr>
                    <td style="text-align: right;width: 70%">Marca <input id="marca"/></td>
                    <td style="width: 30%">
                        <span class="ui-icon ui-icon-search" onclick="verGrilla()"></span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table class="parainfo" style="margin: auto;width: 700px">
                            <thead>
                                <tr>
                                    <th>Auto</th>
                                    <td>Modelo</td>
                                    <td>Fecha Inicial</td>
                                    <td>Fecha Fin</td>
                                    <td>Hora Inicial</td>
                                    <td>Hora Fin</td>
                                    <th>Alerta</th>
                                    <th class="crud">
                                        <a href="#" class="del"><span></span></a>
                                    </th>
                                    <th class="crud">
                                        <a href="#" class="upd"><span></span></a>
                                    </th>
                                </tr>
                            </thead>

                            <c:if test="${qry_alq != null}">
                                <tbody>
                                    <c:forEach var="x" items="${qry_alq}">
                                        <tr>
                                            <th>
                                                <img src="VerFoto?id=${x[1]}" alt="" width="60"/>
                                            </th>
                                            <td>${x[2]}</td>
                                            <td>${x[3]}</td>
                                            <td>${x[4]}</td>
                                            <td>${x[5]}</td>
                                            <td>${x[6]}</td>
                                            <th>${x[7]}</th>
                                            <th>
                                                <input type="checkbox" name="_del" value="${x[1]}"/>
                                            </th>
                                            <th>
                                                <input type="radio" name="_upd" value="${x[0]}"/>
                                            </th>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </td>
                </tr>
            </table>
            
            <% session.removeAttribute("qry_alq"); %>

            <p style="text-align: center">
                <a href="index.jsp">Home</a>
            </p>

            <div style="display: none">
                <div id="dupd" title="Actualizar datos de Alquiler">
                    <form action="Autos" method="post">
                        <input type="hidden" name="accion" value="UPD_ALQ">
                        <input type="hidden" name="idalquiler" id="idalquiler"/>

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
                                           id="horainicio" style="width: 60px"/> (hh:mm)
                                </td>
                            </tr>
                            <tr>
                                <td>Hora fin</td>
                                <td>
                                    <input type="text" name="horafin" maxlength="10" 
                                           id="horafin" style="width: 60px"/> (hh:mm)
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
