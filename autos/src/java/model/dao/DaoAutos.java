package model.dao;

import java.sql.Date;
import java.util.List;

public interface DaoAutos {

    public List<String> getMarcas(String cars);

    public List<Object[]> getAutos(String marca);

    public byte[] getFoto(Integer idauto);

    public String alquiler(Date fini, Date ffin,
            String hini, String hfin, Integer[] idauto);

    public List<Object[]> alquileresQry(String marca);

    public Object[] alquileresGet(String idalquiler);

    public String alquileresUpd(Integer idalquiler,
            Date fini, Date ffin,
            String hini, String hfin);

    public String alquileresDel(List<Integer> ids);
    
    public String alertaUpd(String idalquiler);
}
