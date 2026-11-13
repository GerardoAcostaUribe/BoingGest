package boinggest;

import Components.Login;
import SQL.ConexionBD;

public class BoingGest
{
    public static void main(String[] args)
    {
        // 1. Inicializa la base de datos (crea el archivo y tablas)
        ConexionBD.inicializarBaseDeDatos();
        
        // 2. Lanza la ventana de login
        new Login(); 
    }
}