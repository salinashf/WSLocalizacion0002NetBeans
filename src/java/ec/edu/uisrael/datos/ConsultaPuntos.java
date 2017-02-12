package ec.edu.uisrael.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ConsultaPuntos extends ConexionSQLSERVER {
    public List<Punto> ConsultaPuntosCercanos(Punto puntoSup, Punto puntoInf) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String sql;
        List<Punto> puntos = new ArrayList<>();
        try {
            conn = ConectaBDD();
            sql = "Select SystemID,SiteName,Latitude,Longitude from SourceSite where "
                    + " Latitude >="+puntoSup.getLatitude()
                    + " and Latitude <="+puntoInf.getLatitude()
                    + " and Longitude >="+puntoSup.getLongitude()
                    + " and Longitude <="+puntoInf.getLongitude() ;
                    
                    
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
            ResultSetMetaData rsMd = resultSet.getMetaData();
            while (resultSet.next()) {
                puntos.add(
                        new Punto(
                                resultSet.getString("SystemID").trim(),
                                resultSet.getString("SiteName").trim(),
                                resultSet.getString("Latitude").trim(),
                                resultSet.getString("Longitude").trim()
                        ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultaPuntos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(resultSet);
            close(pstmt);
            close(conn);
        }
        return puntos;
    }

}
