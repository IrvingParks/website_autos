package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.dao.DaoAutos;
import parainfo.sql.ConectaDb;

public class DaoAutosImpl implements DaoAutos {

    private ConectaDb sql;

    public DaoAutosImpl() {
        this.sql = new ConectaDb();
    }

    @Override
    public byte[] getFoto(Integer idauto) {
        byte[] foto = null;
        String s = "SELECT foto FROM autos WHERE idauto=?";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ps.setInt(1, idauto);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    foto = rs.getBytes(1);
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return foto;
    }

    @Override
    public List<String> getMarcas(String cars) {
        List<String> list = null;
        String s = "SELECT tipo FROM marcas "
                + "WHERE UCASE(tipo) LIKE UCASE('%" + cars + "%') LIMIT 0,10";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(s);

                list = new ArrayList<String>();

                while (rs.next()) {
                    list.add(rs.getString(1));
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return list;
    }

    @Override
    public List<Object[]> getAutos(String marca) {
        List<Object[]> list = null;
        String s = "SELECT idauto, modelo FROM autos INNER JOIN marcas "
                + "ON autos.idmarca=marcas.idmarca "
                + "WHERE (tipo=?) AND (estado LIKE 'sinalquiler')";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ps.setString(1, marca);
                ResultSet rs = ps.executeQuery();

                list = new ArrayList<Object[]>();

                while (rs.next()) {
                    Object[] fil = new Object[2];

                    fil[0] = rs.getInt(1);
                    fil[1] = rs.getString(2);

                    list.add(fil);
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return list;
    }

    @Override
    public String alquiler(Date fini, Date ffin,
            String hini, String hfin, Integer[] idauto) {
        String result = null;
        String s1 = "INSERT INTO alquileres("
                + "idauto, "
                + "fechaalquiler, "
                + "fechafinalquiler, "
                + "horainicio, "
                + "horafin, "
                + "estado) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        String s2 = "UPDATE autos SET "
                + "estado=? "
                + "WHERE idauto=?";

        Connection cn = sql.getConnection();
        try {
            cn.setAutoCommit(false);
            PreparedStatement ps1 = cn.prepareStatement(s1);
            PreparedStatement ps2 = cn.prepareStatement(s2);

            ps1.setDate(2, fini);
            ps1.setDate(3, ffin);
            ps1.setString(4, hini);
            ps1.setString(5, hfin);
            ps1.setString(6, "novence");
            //
            ps2.setString(1, "conalquiler");
            //
            for (int i = 0; i < idauto.length; i++) {
                ps1.setInt(1, idauto[i]);
                ps2.setInt(2, idauto[i]);

                ps1.executeUpdate();
                ps2.executeUpdate();
            }
            //
            ps1.close();
            ps2.close();

            cn.commit();

        } catch (SQLException e) {
            result = e.getMessage();
            try {
                cn.rollback();
            } catch (SQLException e2) {
                result = e2.getMessage();
            }
        } finally {
            try {
                cn.setAutoCommit(true);
                cn.close();
            } catch (SQLException e) {
                result = e.getMessage();
            }
        }

        return result;
    }

    @Override
    public List<Object[]> alquileresQry(String marca) {
        List<Object[]> list = null;
        String s = "SELECT "
                + "alquileres.idalquiler,"
                + "alquileres.idauto,"
                + "autos.modelo,"
                + "DATE_FORMAT(alquileres.fechaalquiler, '%d/%m/%Y'),"
                + "DATE_FORMAT(alquileres.fechafinalquiler, '%d/%m/%Y'),"
                + "alquileres.horainicio,"
                + "alquileres.horafin,"
                + "alquileres.estado "
                + "FROM alquileres "
                + "INNER JOIN autos "
                + "ON alquileres.idauto=autos.idauto "
                + "INNER JOIN marcas "
                + "ON autos.idmarca=marcas.idmarca "
                + "WHERE UCASE(marcas.tipo) LIKE UCASE('" + marca + "')";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(s);

                list = new ArrayList<Object[]>();

                while (rs.next()) {
                    Object[] fil = new Object[8];

                    fil[0] = rs.getInt(1);
                    fil[1] = rs.getInt(2);
                    fil[2] = rs.getString(3);
                    fil[3] = rs.getString(4);
                    fil[4] = rs.getString(5);
                    fil[5] = rs.getString(6);
                    fil[6] = rs.getString(7);
                    fil[7] = alerta(
                            (fil[4] + " " + fil[6]),
                            rs.getString(8), fil[0]);

                    list.add(fil);
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return list;
    }

    @Override
    public Object[] alquileresGet(String idalquiler) {
        Object[] fila = null;
        String s = "SELECT "
                + "idalquiler,"
                + "DATE_FORMAT(fechaalquiler, '%d/%m/%Y'),"
                + "DATE_FORMAT(fechafinalquiler, '%d/%m/%Y'),"
                + "horainicio,"
                + "horafin "
                + "FROM alquileres "
                + "WHERE idalquiler=" + idalquiler;

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(s);

                if (rs.next()) {
                    fila = new Object[5];

                    fila[0] = rs.getInt(1);
                    fila[1] = rs.getString(2);
                    fila[2] = rs.getString(3);
                    fila[3] = rs.getString(4);
                    fila[4] = rs.getString(5);
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return fila;
    }

    @Override
    public String alquileresUpd(Integer idalquiler, Date fini, Date ffin,
                            String hini, String hfin) {
        String result = null;
        String s = "UPDATE alquileres SET "
                + "fechaalquiler=?,"
                + "fechafinalquiler=?,"
                + "horainicio=?,"
                + "horafin=? "
                + "WHERE idalquiler=?";

        Connection cn = sql.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(s);
            ps.setDate(1, fini);
            ps.setDate(2, ffin);
            ps.setString(3, hini);
            ps.setString(4, hfin);
            ps.setInt(5, idalquiler);

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                result = "0 filas afectadas";
            }
            ps.close();

        } catch (SQLException e) {
            result = e.getMessage();
        } finally {
            try {
                cn.close();
            } catch (SQLException e) {
            }
        }

        return result;
    }

    @Override
    public String alquileresDel(List<Integer> ids) {
        String result = null;
        String s1 = "DELETE FROM alquileres WHERE idauto=?";
        String s2 = "UPDATE autos SET estado=? WHERE idauto=?";

        Connection cn = sql.getConnection();
        try {
            cn.setAutoCommit(false);
            PreparedStatement ps1 = cn.prepareStatement(s1);
            PreparedStatement ps2 = cn.prepareStatement(s2);

            ps2.setString(1, "sinalquiler");
            //
            for (Integer idauto : ids) {
                ps1.setInt(1, idauto);
                ps2.setInt(2, idauto);

                ps1.executeUpdate();
                ps2.executeUpdate();
            }
            //
            ps1.close();
            ps2.close();

            cn.commit();

        } catch (SQLException e) {
            result = e.getMessage();
            try {
                cn.rollback();
            } catch (SQLException e2) {
                result = e2.getMessage();
            }
        } finally {
            try {
                cn.setAutoCommit(true);
                cn.close();
            } catch (SQLException e) {
                result = e.getMessage();
            }
        }

        return result;
    }

    private String alerta(String ffin, String estado, Object id) {
        String result;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            java.util.Date ff = sdf.parse(ffin);
            long tiempo = System.currentTimeMillis() - ff.getTime();

            if (tiempo > 0) {
                result = "Alq.&nbsp;Vencido";
                if (estado.compareTo("novence") == 0) {
                    result += "<br/><a href=\"Autos?accion=ALERT_ALQ&id=" 
                            + id + "\">actualizar&nbsp;DB.</a>";
                }
            } else {
                result = "";
            }

        } catch (ParseException e) {
            result = e.getMessage();
        }

        return result;
    }

    @Override
    public String alertaUpd(String idalquiler) {
        String result = null;
        String s = "UPDATE alquileres SET "
                + "estado=? "
                + "WHERE idalquiler=" + idalquiler;

        Connection cn = sql.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(s);
            ps.setString(1, "yavencio");

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                result = "0 filas afectadas";
            }
            ps.close();

        } catch (SQLException e) {
            result = e.getMessage();
        } finally {
            try {
                cn.close();
            } catch (SQLException e) {
            }
        }
        return result;
    }
}
