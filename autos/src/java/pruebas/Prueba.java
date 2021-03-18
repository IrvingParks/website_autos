package pruebas;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import parainfo.sql.ConectaDb;

public class Prueba {

    public static void main(String[] args) {
        try {
            ConectaDb sql = new ConectaDb();
            Connection cn = sql.getConnection();
            
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 2; j++) {
                    PreparedStatement ps = cn.prepareStatement(
                            "INSERT INTO autos(idmarca, modelo, foto) "
                            + "VALUES(" + (1 + i) + ", '" + marca[i] + " m-" + (1 + j) + "', ?)");

                    InputStream in = Prueba.class.getResourceAsStream(marca[i] + (1 + j) + ".jpg");
                    byte[] foto = new byte[in.available()];
                    in.read(foto, 0, in.available());
                    ps.setBytes(1, foto);
                    ps.executeUpdate();
                    ps.close();
                }
            }

            cn.close();
            System.out.println("Ok!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //
    static private String[] marca = {"chevrolet", "ferrari", "hyundai", "toyota"};
}
