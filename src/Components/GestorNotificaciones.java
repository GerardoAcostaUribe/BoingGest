package Components;

import SQL.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorNotificaciones
{
    public static void registrar(String tipo, String descripcion)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaHoraActual = dtf.format(LocalDateTime.now());

        String sql = "INSERT INTO Notificaciones (fecha, tipo, descripcion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, fechaHoraActual);
            pstmt.setString(2, tipo);
            pstmt.setString(3, descripcion);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println("Error al registrar notificación: " + e.getMessage());
        }
    }
}