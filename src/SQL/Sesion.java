package SQL;

public class Sesion
{
    private static String usuarioActivo = "";
    private static String rolActivo = "";

    public static void iniciarSesion(String usuario, String rol)
    {
        usuarioActivo = usuario;
        rolActivo = rol;
    }

    public static String getUsuarioActivo()
    {
        return usuarioActivo;
    }

    public static String getRolActivo()
    {
        return rolActivo;
    }
    
    public static void cerrarSesion()
    {
        usuarioActivo = "";
        rolActivo = "";
    }
}
