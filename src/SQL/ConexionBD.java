package SQL;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD
{
    // Esta ruta creará un archivo llamado "boinggest.db" en la carpeta de tu proyecto
    private static final String URL = "jdbc:sqlite:boinggest.db";
    private static Connection conexion = null;

    public static Connection conectar()
    {
        try
        {
            if (conexion == null || conexion.isClosed())
            {
                // Cargar el driver de SQLite
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL);
                System.out.println("Conexión a SQLite establecida.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void desconectar()
    {
        try
        {
            if (conexion != null && !conexion.isClosed())
            {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    public static void inicializarBaseDeDatos()
    {
        // ... (Aquí van los Strings de Usuarios y Productos que ya tenías)
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, "
                + "correo TEXT, rol TEXT, estado TEXT, usuario TEXT UNIQUE, contrasena TEXT NOT NULL)";

        String sqlInsertAdmin = "INSERT OR IGNORE INTO Usuarios (nombre, correo, rol, estado, usuario, contrasena) "
                + "VALUES ('Administrador', 'admin@boinggest.com', 'Administrador', 'Activo', 'admin', 'admin')";

        String sqlProductos = "CREATE TABLE IF NOT EXISTS Productos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, "
                + "categoria TEXT, precio TEXT, estado TEXT)";

        // --- NUEVAS TABLAS ---
        String sqlInventario = "CREATE TABLE IF NOT EXISTS Inventario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "producto TEXT NOT NULL, "
                + "existencia INTEGER, "
                + "stock_minimo INTEGER, "
                + "estado TEXT)";

        String sqlPedidos = "CREATE TABLE IF NOT EXISTS Pedidos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "cliente TEXT NOT NULL, "
                + "fecha TEXT, "
                + "total TEXT, "
                + "estado TEXT)";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlInsertAdmin);
            stmt.execute(sqlProductos);
            stmt.execute(sqlInventario);
            stmt.execute(sqlPedidos);
            
            // Insertar datos de prueba SOLO si las tablas están vacías
            var rsInv = stmt.executeQuery("SELECT COUNT(*) FROM Inventario");
            if (rsInv.next() && rsInv.getInt(1) == 0)
            {
                stmt.execute("INSERT INTO Inventario (producto, existencia, stock_minimo, estado) VALUES "
                        + "('Boing de Tamarindo', 150, 50, 'Suficiente'), "
                        + "('Boing de Uva', 32, 50, 'Bajo'), "
                        + "('Boing de Frutos Rojos', 0, 20, 'Agotado')");
            }
            
            var rsPed = stmt.executeQuery("SELECT COUNT(*) FROM Pedidos");
            if (rsPed.next() && rsPed.getInt(1) == 0)
            {
                stmt.execute("INSERT INTO Pedidos (cliente, fecha, total, estado) VALUES "
                        + "('Super Abarrotes', '12/11/2026', '$560.00', 'En preparación'), "
                        + "('La Esquina', '12/11/2026', '$320.00', 'En distribución'), "
                        + "('Mini Súper', '11/11/2026', '$480.00', 'Entregado')");
            }

            System.out.println("Todas las tablas listas.");
        }
        catch (SQLException e)
        {
            System.err.println("Error creando las tablas: " + e.getMessage());
        }
    }
}