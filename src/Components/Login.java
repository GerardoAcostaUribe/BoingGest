package Components;

import SQL.ConexionBD;
import SQL.Sesion;
import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame
{
    Button confirmButton = new Button("Confirmar");
    JLabel text1 = new JLabel("Usuario");
    JLabel text2 = new JLabel("Contraseña");
    JButton botonOlvido = new JButton("¿Olvidaste la contraseña?"); // Cambiado a botón
    JLabel text4 = new JLabel(" ", SwingConstants.CENTER);
    JTextField user = new JTextField(20);
    JPasswordField pass = new JPasswordField(20);

    public Login()
    {
        this.setLayout(null);
        this.getContentPane().setBackground(Colores.FONDO_GRIS);
        this.setSize(720, 480);
        this.setTitle("BoingGest");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        confirmButton.addActionListener(e -> validarCredenciales());
        
        // --- EVENTO DE OLVIDO DE CONTRASEÑA ---
        botonOlvido.addActionListener(e -> SolicitarCambioContrasena());
        
        InitializeComponents();
        this.setVisible(true);
    }
    
    private void InitializeComponents()
    {
        text4.setBounds(350, 70, 300, 30);
        text4.setForeground(Colores.PELIGRO_ROJO); // Le damos color rojo para que resalte el error
        this.add(text4);
        
        text1.setBounds(350, 90, 300, 30);
        this.add(text1);
        
        user.setBounds(350, 120, 300, 30);
        this.add(user);
        
        text2.setBounds(350, 150, 300, 30);
        this.add(text2);
        
        pass.setBounds(350, 180, 300, 30);
        this.add(pass);
        
        // --- DISEÑO DEL BOTÓN DE OLVIDO ---
        botonOlvido.setBounds(345, 210, 200, 30);
        botonOlvido.setForeground(Color.RED);
        botonOlvido.setContentAreaFilled(false);
        botonOlvido.setBorderPainted(false);
        botonOlvido.setFocusPainted(false);
        botonOlvido.setHorizontalAlignment(SwingConstants.LEFT);
        botonOlvido.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muestra la manita al pasar el ratón
        this.add(botonOlvido);
        
        confirmButton.setBounds(440, 250, 120, 30);
        confirmButton.setBackground(Colores.AZUL_PASCUAL);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(confirmButton);
        
        agregarImagen("/Imagenes/rapido.png", 50, 40, 10, 10);
        agregarImagen("/Imagenes/pato-guapo.png", 303, 354, 0, 110);
        agregarImagen("/Imagenes/boinggest.png", 189, 45, 70, 10);
    }

    private void SolicitarCambioContrasena()
    {
        String usuarioTexto = GetUsername();

        // 1. Validamos que no esté vacío
        if (usuarioTexto.isEmpty())
        {
            LabelChange("Ingrese un usuario para recuperar contraseña");
            return;
        }

        // 2. Buscamos el usuario en la BD
        String sql = "SELECT 1 FROM Usuarios WHERE usuario = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, usuarioTexto);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            { 
                // Sí existe en la base de datos
                JOptionPane.showMessageDialog(this, 
                    "El administrador ha recibido la solicitud de cambio de contraseña para el usuario: " + usuarioTexto, 
                    "Solicitud Enviada", 
                    JOptionPane.INFORMATION_MESSAGE);
                GestorNotificaciones.registrar("Usuario", "Solicitud de cambio de contraseña: " + usuarioTexto);
                LabelChange(" "); // Limpiamos el texto de error si había uno
            }
            else
            {
                // No existe en la base de datos
                LabelChange("El usuario ingresado no existe");
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al verificar usuario: " + ex.getMessage());
        }
    }
    
    private void validarCredenciales()
    {
        String usuarioTexto = GetUsername();
        String contrasenaTexto = GetPassword();

        String sql = "SELECT * FROM Usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, usuarioTexto);
            pstmt.setString(2, contrasenaTexto);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            { 
                String rolBD = rs.getString("rol"); 
                Sesion.iniciarSesion(usuarioTexto, rolBD);
                this.dispose(); 
                new MenuPrincipal(); 
            }
            else
            {
                LabelChange("Usuario o contraseña incorrectos");
                EraseTexts();
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al iniciar sesión: " + ex.getMessage());
        }
    }
    
    public String GetUsername()
    {
        return user.getText();
    }
    
    public String GetPassword()
    {
        return new String(pass.getPassword());
    }
    
    public void LabelChange(String string)
    {
        text4.setText(string);
    }
    
    public void EraseTexts()
    {
        pass.setText(null);
    }
    
    private void agregarImagen(String ruta, int escaladox, int escaladoy, int posicionx, int posiciony)
    {
        java.net.URL url = getClass().getResource(ruta);
        if (url == null)
        {
            System.err.println("No se encontró la imagen: " + ruta);
            return;
        }

        ImageIcon icono = new ImageIcon(url);
        Image imagenEscalada = icono.getImage().getScaledInstance(escaladox, escaladoy, Image.SCALE_SMOOTH);
        icono = new ImageIcon(imagenEscalada);

        JLabel etiquetaImagen = new JLabel(icono);
        etiquetaImagen.setBounds(posicionx, posiciony, escaladox, escaladoy);
        this.add(etiquetaImagen);
    }
}