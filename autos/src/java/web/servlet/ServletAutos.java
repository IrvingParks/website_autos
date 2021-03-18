package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.DaoAutos;
import model.dao.impl.DaoAutosImpl;

@WebServlet(name = "ServletAutos", urlPatterns = {"/Autos"})
public class ServletAutos extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String result = null;
        String target = null;

        if (accion == null) {
            result = "Solicitud no recibida";

        } else if (accion.equals("GET_MARCAS")) {
            String cars = request.getParameter("cars");

            DaoAutos daoAutos = new DaoAutosImpl();
            List<String> list = daoAutos.getMarcas(cars);

            String marcas = "";
            for (String m : list) {
                marcas += m + ",";
            }

            if (marcas.length() > 0) {
                marcas = marcas.substring(0, marcas.length() - 1);
            }

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(marcas);
            out.close();

        } else if (accion.equals("GET_FOTOS")) {
            String marca = request.getParameter("marca");

            DaoAutos daoAutos = new DaoAutosImpl();
            List<Object[]> list = daoAutos.getAutos(marca);

            StringBuilder sb = new StringBuilder();
            sb.append("<div class=\"ui-widget ui-helper-clearfix\">");
            sb.append("<ul id=\"gallery\" class=\"gallery ui-helper-reset ui-helper-clearfix\">");

            for (Object[] fil : list) {
                sb.append("<li class=\"ui-widget-content ui-corner-tr\" id=\"auto").append(fil[0]).append("\">");
                sb.append("<h5 class=\"ui-widget-header\">").append(fil[1]).append("</h5>");
                sb.append("<img src=\"VerFoto?id=").append(fil[0]).append("\" width=\"96\" height=\"72\"/>");
                sb.append("<input type=\"hidden\" name=\"id\" value=\"").append(fil[0]).append("\">");
                sb.append("</li>");

                sb.append("<script type=\"text/javascript\">");
                sb.append("$(\"#auto").append(fil[0]).append("\").tooltip().attr(\"title\", \"Modelo: ").append(fil[1]).append(" (ID: ").append(fil[0]).append(")\");");
                sb.append("</script>");
            }
            sb.append("</ul>");
            sb.append("");
            sb.append("</div>");

            sb.append("<script type=\"text/javascript\">");
            sb.append("var $gallery = $( \"#gallery\" ),");
            sb.append("$trash = $( \"#trash\" ),");
            sb.append("$delete = $( \"#delete\" );");

            sb.append("$(\"li\", $gallery).draggable({");
            sb.append("cancel: \"a.ui-icon\",");
            sb.append("revert: \"invalid\",");
            sb.append("containment: \"document\",");
            sb.append("helper: \"clone\",");
            sb.append("cursor: \"move\"");
            sb.append("});");

            sb.append("$trash.droppable({");
            sb.append("accept: \"#gallery > li\",");
            sb.append("activeClass: \"ui-state-highlight\",");
            sb.append("drop: function( event, ui ) {");
            sb.append("deleteImage( ui.draggable );");
            sb.append("}");
            sb.append("});");

            sb.append("$delete.droppable({");
            sb.append("accept: \"#trash li\",");
            sb.append("activeClass: \"custom-state-active\",");
            sb.append("drop: function( event, ui ) {");
            sb.append("recycleImage( ui.draggable );");
            sb.append("}");
            sb.append("});");
            sb.append("");
            sb.append("var recycle_icon = \"<a href='#recycle'></a>\";");
            sb.append("var trash_icon = \"<a href='#trash'></a>\";");
            sb.append("</script>");

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(sb);
            out.close();

        } else if (accion.equals("INS_ALQ")) {
            String ids = request.getParameter("ids");
            String fechaalquiler = request.getParameter("fechaalquiler");
            String fechafinalquiler = request.getParameter("fechafinalquiler");
            String horainicio = request.getParameter("horainicio");
            String horafin = request.getParameter("horafin");

            String fini = fechaalquiler + " " + horainicio;
            String ffin = fechafinalquiler + " " + horafin;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date fecha1 = null, fecha2 = null;
            try {
                fecha1 = sdf.parse(fini);
            } catch (ParseException e) {
                result = "Inicio de alquiler mal ingresado";
            }

            if (result == null) {
                try {
                    fecha2 = sdf.parse(ffin);
                } catch (ParseException e) {
                    result = "Fin de alquiler mal ingresado";
                }
            }

            if (result == null) {
                if (fecha2.getTime() <= fecha1.getTime()) {
                    result = "Fin de alquiler debe ser mayor que inicio";
                }
            }

            if (result == null) {
                String[] idautos = ids.split(",");
                Integer[] idauto = new Integer[idautos.length];

                for (int i = 0; i < idautos.length; i++) {
                    idauto[i] = Integer.valueOf(idautos[i]);
                }

                DaoAutos daoAutos = new DaoAutosImpl();
                result = daoAutos.alquiler(
                        new java.sql.Date(fecha1.getTime()),
                        new java.sql.Date(fecha2.getTime()),
                        horainicio, horafin, idauto);
            }

            if (result == null) {
                result = "Su alquiler ha sido registrado";
            }

        } else if (accion.equals("QRY_ALQ")) {
            String marca = request.getParameter("marca");

            DaoAutos daoAutos = new DaoAutosImpl();
            List<Object[]> list = daoAutos.alquileresQry(marca);

            if (list != null) {
                request.getSession().setAttribute("qry_alq", list);
                target = "mantenimiento.jsp";
            } else {
                result = "No se tiene acceso para Alquileres";
            }

        } else if (accion.equals("GET_ALQ")) {
            String idalquiler = request.getParameter("id");

            DaoAutos daoAutos = new DaoAutosImpl();
            Object[] fila = daoAutos.alquileresGet(idalquiler);

            if (fila != null) {
                String data = fila[0] + "+++"
                        + fila[1] + "+++"
                        + fila[2] + "+++"
                        + fila[3] + "+++"
                        + fila[4];
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(data);
                out.close();
            } else {
                result = "No se tiene acceso para Alquileres";
            }

        } else if (accion.equals("UPD_ALQ")) {
            String idalquiler = request.getParameter("idalquiler");
            String fechaalquiler = request.getParameter("fechaalquiler");
            String fechafinalquiler = request.getParameter("fechafinalquiler");
            String horainicio = request.getParameter("horainicio");
            String horafin = request.getParameter("horafin");

            String fini = fechaalquiler + " " + horainicio;
            String ffin = fechafinalquiler + " " + horafin;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date fecha1 = null, fecha2 = null;
            try {
                fecha1 = sdf.parse(fini);
            } catch (ParseException e) {
                result = "Inicio de alquiler mal ingresado";
            }

            if (result == null) {
                try {
                    fecha2 = sdf.parse(ffin);
                } catch (ParseException e) {
                    result = "Fin de alquiler mal ingresado";
                }
            }

            if (result == null) {
                if (fecha2.getTime() <= fecha1.getTime()) {
                    result = "Fin de alquiler debe ser mayor que inicio";
                }
            }

            if (result == null) {
                DaoAutos daoAutos = new DaoAutosImpl();
                result = daoAutos.alquileresUpd(
                        Integer.valueOf(idalquiler),
                        new java.sql.Date(fecha1.getTime()),
                        new java.sql.Date(fecha2.getTime()),
                        horainicio, horafin);
            }

            if (result == null) {
                request.getSession().removeAttribute("qry_alq");
                result = "Actualización exitosa de Alquiler";
            }

        } else if (accion.equals("DEL_ALQ")) {
            String ids = request.getParameter("ids");
            String[] idautos = ids.split(",");
            List<Integer> list = new ArrayList<Integer>();

            for (int i = 0; i < idautos.length; i++) {
                list.add(Integer.valueOf(idautos[i]));
            }

            DaoAutos daoAutos = new DaoAutosImpl();
            result = daoAutos.alquileresDel(list);

            if (result == null) {
                request.getSession().removeAttribute("qry_alq");
                result = "Eliminación exitosa de Alquileres";
            }

        } else if (accion.equals("ALERT_ALQ")) {
            String idalquiler = request.getParameter("id");
            
            DaoAutos daoAutos = new DaoAutosImpl();
            result = daoAutos.alertaUpd(idalquiler);

            if (result == null) {
                request.getSession().removeAttribute("qry_alq");
                result = "Actualización exitosa de Alerta";
            }
            
        } else {
            result = "Solicitud no reconocida";
        }

        if (result != null) {
            request.getSession().setAttribute("msg", result);
            target = "mensaje.jsp";
        }

        if (target != null) {
            response.sendRedirect(target);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
