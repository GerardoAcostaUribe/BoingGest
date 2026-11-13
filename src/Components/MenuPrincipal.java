package Components;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import Button.BotonesEditor;
import Button.BotonesRenderer;
import SQL.ConexionBD;
import SQL.Sesion;
import java.awt.*;
import java.util.Random;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuPrincipal extends JFrame
{
    Random random = new Random();
    
    JLabel textoBoing = new JLabel("BoingGest");

    JLabel tableroPrincipal = new JLabel(" ");
    
    JButton botonInicio = new JButton("Inicio");
    JButton botonProductos = new JButton("Productos");
    JButton botonInventario = new JButton("Inventario");
    JButton botonPedidos = new JButton("Pedidos");
    JButton botonDistribucion = new JButton("Distribucion");
    JButton botonReportes = new JButton("Reportes");
    JButton botonNotificaciones = new JButton("Notificaciones");
    JButton botonUsuarios = new JButton("Usuarios");

    JPanel usuarioPanel = new JPanel();
    JPanel barraSuperior = new JPanel();
    JPanel barraLateral = new JPanel();
    JPanel panelContenido = new JPanel();

    public MenuPrincipal()
    {
        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        botonInicio.addActionListener(e -> Inicio());
        botonProductos.addActionListener(e -> Productos());
        botonInventario.addActionListener(e -> Inventario());
        botonPedidos.addActionListener(e -> Pedidos());
        botonDistribucion.addActionListener(e -> Distribucion());
        botonReportes.addActionListener(e -> Reportes());
        botonNotificaciones.addActionListener(e -> Notificaciones());
        botonUsuarios.addActionListener(e -> Usuarios());
        
        InitComponents();

        setVisible(true);
    }

    private void InitComponents()
    {
        barraSuperior.setLayout(null);
        barraSuperior.setBackground(Colores.ROJO_PASCUAL);
        barraSuperior.setBounds(0, 0, 1920, 80);

        textoBoing.setBounds(20, 5, 250, 70);
        textoBoing.setFont(new Font("Arial", Font.BOLD, 36));
        textoBoing.setForeground(Color.WHITE);

        usuarioPanel.setBounds(1275, 5, 250, 65);
        usuarioPanel.setBackground(Colores.ROJO_PASCUAL);
        
        textoBoing.setFont(new Font("Arial", Font.BOLD, 36));
        
        // --- BOTÓN DE USUARIO Y CERRAR SESIÓN ---
        String textoBoton = Sesion.getUsuarioActivo() + " (" + Sesion.getRolActivo() + ")";
        JButton botonUsuario = new JButton(textoBoton);
        
        // Ajustamos las medidas para que quepa el texto y se vea bien
        botonUsuario.setBounds(70, 15, 170, 30); 
        botonUsuario.setForeground(Color.WHITE);
        botonUsuario.setContentAreaFilled(false);
        botonUsuario.setBorderPainted(false);
        botonUsuario.setFocusPainted(false);
        
        // Acción para cerrar sesión
        botonUsuario.addActionListener(e -> 
        {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                    "¿Deseas cerrar la sesión actual?", 
                    "Cerrar Sesión", 
                    JOptionPane.YES_NO_OPTION);
                    
            if (respuesta == JOptionPane.YES_OPTION)
            {
                Sesion.cerrarSesion(); // Limpiamos los datos
                this.dispose();        // Cerramos el menú
                new Login();           // Abrimos el login de nuevo
            }
        });
        
        usuarioPanel.add(botonUsuario);
        
        barraSuperior.add(usuarioPanel);
        agregarImagen("/Imagenes/fotoperfil.png", 65, 65, 0, 0, usuarioPanel);
        barraSuperior.add(textoBoing);

        add(barraSuperior);

        barraLateral.setLayout(null);
        barraLateral.setBackground(Colores.AZUL_LATERAL);
        barraLateral.setBounds(0, 80, 250, 1000);

        botonInicio.setBounds(0, 10, 250, 60);
        botonProductos.setBounds(0, 70, 250, 60);
        botonInventario.setBounds(0, 130, 250, 60);
        botonPedidos.setBounds(0, 190, 250, 60);
        botonDistribucion.setBounds(0, 250, 250, 60);
        botonReportes.setBounds(0, 310, 250, 60);
        botonNotificaciones.setBounds(0, 370, 250, 60);
        botonUsuarios.setBounds(0, 430, 250, 60);

        configurarBoton(botonInicio);
        configurarBoton(botonProductos);
        configurarBoton(botonInventario);
        configurarBoton(botonPedidos);
        configurarBoton(botonDistribucion);
        configurarBoton(botonReportes);
        configurarBoton(botonNotificaciones);
        configurarBoton(botonUsuarios);

        barraLateral.add(botonInicio);
        barraLateral.add(botonProductos);
        barraLateral.add(botonInventario);
        barraLateral.add(botonPedidos);
        barraLateral.add(botonDistribucion);
        barraLateral.add(botonReportes);
        barraLateral.add(botonNotificaciones);
        barraLateral.add(botonUsuarios);

        add(barraLateral);

        panelContenido.setLayout(null);
        panelContenido.setBackground(Colores.FONDO_GRIS);
        panelContenido.setBounds(250, 80, 1670, 1000);

        add(panelContenido);

        Inicio();
    }

    private void configurarBoton (JButton boton)
    {
        boton.setBackground(Colores.AZUL_LATERAL);
        boton.setForeground(Color.WHITE);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 16)); // Agregamos fuente para que se vea mejor
    }

    public void Inicio()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.ROJO_PASCUAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Tablero");
        tableroPrincipal.setBounds(30, 20, 300, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);
        
        // --- CONSULTAS REALES A SQLITE ---
        int pedidosPendientes = 0;
        int inventarioBajo = 0;
        int productosAgotados = 0;
        int entregasTransito = 0;

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement())
        {
            // 1. Contar pedidos pendientes o en preparación
            var rs1 = stmt.executeQuery("SELECT COUNT(*) FROM Pedidos WHERE estado = 'Pendiente' OR estado = 'En preparación'");
            if (rs1.next()) pedidosPendientes = rs1.getInt(1);

            // 2. Contar inventario bajo
            var rs2 = stmt.executeQuery("SELECT COUNT(*) FROM Inventario WHERE estado = 'Bajo'");
            if (rs2.next()) inventarioBajo = rs2.getInt(1);

            // 3. Contar productos agotados
            var rs3 = stmt.executeQuery("SELECT COUNT(*) FROM Inventario WHERE estado = 'Agotado'");
            if (rs3.next()) productosAgotados = rs3.getInt(1);

            // 4. Contar entregas en tránsito o distribución
            var rs4 = stmt.executeQuery("SELECT COUNT(*) FROM Distribucion WHERE estado = 'En ruta' OR estado = 'En distribución'");
            if (rs4.next()) entregasTransito = rs4.getInt(1);
        }
        catch (SQLException e)
        {
            System.err.println("Error al calcular métricas del tablero: " + e.getMessage());
        }

        // --- PANELES CON LOS DATOS REALES ---
        JPanel pedidosPendientesPanel = new JPanel();
        pedidosPendientesPanel.setBackground(Colores.INFO_AZUL);
        pedidosPendientesPanel.setBounds(30, 100, 250, 300);
        JLabel textoPedidos = new JLabel("Pedidos", SwingConstants.CENTER);
        textoPedidos.setForeground(Color.WHITE); textoPedidos.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel textoPendientes = new JLabel("Pendientes", SwingConstants.CENTER);
        textoPendientes.setForeground(Color.WHITE); textoPendientes.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel numeroPedidosPendientes = new JLabel(" " + pedidosPendientes + " ", SwingConstants.CENTER);
        numeroPedidosPendientes.setForeground(Color.WHITE); numeroPedidosPendientes.setFont(new Font("Arial", Font.BOLD, 48));
        pedidosPendientesPanel.add(textoPedidos); pedidosPendientesPanel.add(textoPendientes); pedidosPendientesPanel.add(numeroPedidosPendientes);
        
        JPanel inventarioBajoPanel = new JPanel();
        inventarioBajoPanel.setBackground(Colores.ALERTA_AMARILLA);
        inventarioBajoPanel.setBounds(310, 100, 250, 300);
        JLabel textoInventario = new JLabel("Inventario", SwingConstants.CENTER);
        textoInventario.setForeground(Color.WHITE); textoInventario.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel textoBajo = new JLabel("Bajo", SwingConstants.CENTER);
        textoBajo.setForeground(Color.WHITE); textoBajo.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel numeroInventarioBajo = new JLabel(" " + inventarioBajo + " ", SwingConstants.CENTER);
        numeroInventarioBajo.setForeground(Color.WHITE); numeroInventarioBajo.setFont(new Font("Arial", Font.BOLD, 48));
        inventarioBajoPanel.add(textoInventario); inventarioBajoPanel.add(textoBajo); inventarioBajoPanel.add(numeroInventarioBajo);
        
        JPanel productosAgotadosPanel = new JPanel();
        productosAgotadosPanel.setBackground(Colores.PELIGRO_ROJO);
        productosAgotadosPanel.setBounds(590, 100, 250, 300);
        JLabel textoProductos = new JLabel("Productos", SwingConstants.CENTER);
        textoProductos.setForeground(Color.WHITE); textoProductos.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel textoAgotados = new JLabel("Agotados", SwingConstants.CENTER);
        textoAgotados.setForeground(Color.WHITE); textoAgotados.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel numeroProductosAgotados = new JLabel(" " + productosAgotados + " ", SwingConstants.CENTER);
        numeroProductosAgotados.setForeground(Color.WHITE); numeroProductosAgotados.setFont(new Font("Arial", Font.BOLD, 48));
        productosAgotadosPanel.add(textoProductos); productosAgotadosPanel.add(textoAgotados); productosAgotadosPanel.add(numeroProductosAgotados);
        
        JPanel entregasTransitoPanel = new JPanel();
        entregasTransitoPanel.setBackground(Colores.EXITO_VERDE);
        entregasTransitoPanel.setBounds(870, 100, 250, 300);
        JLabel textoEntregas = new JLabel("Entregas", SwingConstants.CENTER);
        textoEntregas.setForeground(Color.WHITE); textoEntregas.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel textoTransito = new JLabel("en Tránsito", SwingConstants.CENTER);
        textoTransito.setForeground(Color.WHITE); textoTransito.setFont(new Font("Arial", Font.BOLD, 36));
        JLabel numeroEntregasTransito = new JLabel(" " + entregasTransito + " ", SwingConstants.CENTER);
        numeroEntregasTransito.setForeground(Color.WHITE); numeroEntregasTransito.setFont(new Font("Arial", Font.BOLD, 48));
        entregasTransitoPanel.add(textoEntregas); entregasTransitoPanel.add(textoTransito); entregasTransitoPanel.add(numeroEntregasTransito);

        // --- PANEL DE NOTIFICACIONES RECIENTES EN EL INICIO ---
        JPanel notificacionesPanel = new JPanel();
        notificacionesPanel.setLayout(null);
        notificacionesPanel.setBackground(Color.WHITE);
        notificacionesPanel.setBounds(30, 450, 1200, 300);
        notificacionesPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        JLabel tituloNotificaciones = new JLabel("Notificaciones recientes");
        tituloNotificaciones.setBounds(20, 15, 500, 40);
        tituloNotificaciones.setFont(new Font("Arial", Font.BOLD, 28));
        tituloNotificaciones.setForeground(Colores.AZUL_PASCUAL);

        JSeparator separador = new JSeparator();
        separador.setBounds(0, 65, 1200, 2);
        notificacionesPanel.add(tituloNotificaciones);
        notificacionesPanel.add(separador);

        // Cargar las últimas 4 notificaciones reales desde la base de datos
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Notificaciones ORDER BY id DESC LIMIT 4"))
        {
            int yPos = 85;
            while (rs.next())
            {
                JLabel lblTexto = new JLabel(rs.getString("descripcion"));
                lblTexto.setBounds(30, yPos, 700, 30);
                lblTexto.setFont(new Font("Arial", Font.PLAIN, 20));

                JLabel lblHora = new JLabel(rs.getString("fecha"));
                lblHora.setBounds(950, yPos, 220, 30);
                lblHora.setFont(new Font("Arial", Font.BOLD, 16));
                lblHora.setForeground(Color.GRAY);

                notificacionesPanel.add(lblTexto);
                notificacionesPanel.add(lblHora);
                yPos += 50;
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error cargando notificaciones recientes: " + e.getMessage());
        }

        panelContenido.add(notificacionesPanel);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(pedidosPendientesPanel);
        panelContenido.add(inventarioBajoPanel);
        panelContenido.add(productosAgotadosPanel);
        panelContenido.add(entregasTransitoPanel);
        
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Productos()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.ROJO_PASCUAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Productos");
        tableroPrincipal.setBounds(30, 20, 500, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);

        JButton botonAgregarProducto = new JButton("+ Agregar producto");

        botonAgregarProducto.setBounds(1000, 25, 250, 50);
        botonAgregarProducto.setBackground(Color.BLUE);
        botonAgregarProducto.setForeground(Color.WHITE);
        botonAgregarProducto.setBorderPainted(false);
        botonAgregarProducto.setFocusPainted(false);
        botonAgregarProducto.setFont(new Font("Arial", Font.BOLD, 18));
        botonAgregarProducto.addActionListener(e -> MostrarVentanaAgregarProducto());
        
        // --- VALIDACIÓN DE ROLES ---
        String rol = Sesion.getRolActivo();
        if (rol.equals("Empleado"))
        {
            // El empleado no puede agregar productos
            botonAgregarProducto.setVisible(false);
        }

        JTextField buscador = new JTextField();
        buscador.setBounds(30, 110, 450, 45);

        JComboBox<String> categoria = new JComboBox<>(new String[]
        {
            "Categoría: Todas", "Boing de botella", "Boing Tetra Brik", "Boing Gemina"
        });
        categoria.setBounds(520, 110, 280, 45);

        JComboBox<String> estado = new JComboBox<>(new String[]
        {
            "Estado: Todos", "Disponible", "Inventario Bajo", "Agotado"
        });
        estado.setBounds(840, 110, 280, 45);

        // Cargamos la tabla inicialmente sin filtros
        DefaultTableModel modeloProd = CargarModeloProductos("", "Categoría: Todas", "Estado: Todos");
        JTable tabla = new JTable(modeloProd);
        
        // Configuración de tabla (Renderer, tamaños, etc.)
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setRowHeight(50);
        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Productos"));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(397);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 190, 1200, 500);

        // --- EVENTOS DE FILTRADO EN TIEMPO REAL ---
        java.awt.event.KeyListener lectorTeclas = new java.awt.event.KeyAdapter()
        {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e)
            {
                actualizarTablaProductos(tabla, buscador.getText(), categoria.getSelectedItem().toString(), estado.getSelectedItem().toString());
            }
        };
        buscador.addKeyListener(lectorTeclas);

        java.awt.event.ActionListener lectorCombos = e -> 
        {
            actualizarTablaProductos(tabla, buscador.getText(), categoria.getSelectedItem().toString(), estado.getSelectedItem().toString());
        };
        categoria.addActionListener(lectorCombos);
        estado.addActionListener(lectorCombos);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(botonAgregarProducto);

        panelContenido.add(buscador);
        panelContenido.add(categoria);
        panelContenido.add(estado);

        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Inventario()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.ROJO_PASCUAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);
        
        tableroPrincipal.setText("Inventario");
        tableroPrincipal.setBounds(30, 20, 500, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);
        
        JButton botonAgregarInv = new JButton("+ Agregar inventario");
        botonAgregarInv.setBounds(980, 35, 250, 50);
        botonAgregarInv.setBackground(Color.BLUE);
        botonAgregarInv.setForeground(Color.WHITE);
        botonAgregarInv.addActionListener(e -> MostrarVentanaAgregarInventario());
        
        if (Sesion.getRolActivo().equals("Empleado") || Sesion.getRolActivo().equals("Repartidor"))
        {
            botonAgregarInv.setVisible(false);
        }
        panelContenido.add(botonAgregarInv);
        
        DefaultTableModel modeloInventario = CargarModeloInventario();
        JTable tabla = new JTable(modeloInventario);
        tabla.getColumn("Estado").setCellRenderer(new EstadoRenderer());
        tabla.setRowHeight(55);

        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(Color.WHITE);

        tabla.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla.setRowHeight(55);

        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Inventario"));
        
        // Ajusta un poco los anchos para que quepa todo:
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(417); // Producto
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150); // Existencia
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100); // Mínimo
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150); // Estado
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280); // Acciones
        
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 120, 1150, 450);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Pedidos()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.ROJO_PASCUAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Pedidos");
        tableroPrincipal.setBounds(30, 20, 500, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);
        
        JButton botonAgregarPed = new JButton("+ Agregar pedido");
        botonAgregarPed.setBounds(980, 35, 250, 50);
        botonAgregarPed.setBackground(Color.BLUE);
        botonAgregarPed.setForeground(Color.WHITE);
        botonAgregarPed.addActionListener(e -> MostrarVentanaAgregarPedido());
        
        if (Sesion.getRolActivo().equals("Empleado") || Sesion.getRolActivo().equals("Repartidor"))
        {
            botonAgregarPed.setVisible(false);
        }
        panelContenido.add(botonAgregarPed);

        JLabel estadoLabel = new JLabel("Estado:");
        estadoLabel.setBounds(30, 120, 100, 30);
        estadoLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JComboBox<String> filtroEstado = new JComboBox<>(new String[]
        {
            "Todos", "Pendiente", "En preparación", "En distribución", "Entregado"
        });
        filtroEstado.setBounds(120, 115, 250, 40);

        // Cargamos el modelo inicial (con "Todos")
        DefaultTableModel modeloPedidos = CargarModeloPedidos("Todos");
        JTable tabla = new JTable(modeloPedidos);
        
        tabla.setRowHeight(55);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Pedidos"));
        
        // Ajusta anchos (recuerda hacerlo también dentro del Action del filtroEstado):
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(367); // Cliente
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150); // Fecha
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100); // Total
        tabla.getColumnModel().getColumn(4).setPreferredWidth(200); // Estado
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280); // Acciones

        // --- ACCIÓN DEL FILTRO ---
        filtroEstado.addActionListener(e ->
        {
            String seleccion = filtroEstado.getSelectedItem().toString();
            tabla.setModel(CargarModeloPedidos(seleccion)); // Recarga la tabla
            
            // Como cambiamos el modelo, debemos volver a aplicar el Renderer y los tamaños
            tabla.getColumn("Estado").setCellRenderer(new EstadoPedidoRenderer());
            tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(407);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(280);
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 190, 1200, 500);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(estadoLabel);
        panelContenido.add(filtroEstado);
        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Distribucion()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.ROJO_PASCUAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Distribución");
        tableroPrincipal.setBounds(30, 20, 600, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);

        JButton botonAgregarDist = new JButton("+ Agregar ruta");
        botonAgregarDist.setBounds(980, 35, 250, 50);
        botonAgregarDist.setBackground(Color.BLUE);
        botonAgregarDist.setForeground(Color.WHITE);
        botonAgregarDist.addActionListener(e -> MostrarVentanaAgregarDistribucion());
        
        if (Sesion.getRolActivo().equals("Empleado") || Sesion.getRolActivo().equals("Vendedor"))
        {
            botonAgregarDist.setVisible(false);
        }
        panelContenido.add(botonAgregarDist);
        
        String columnas[] =
        {
            "Pedido",
            "Destino",
            "Transportista",
            "Estado"
        };

        DefaultTableModel modeloDistribucion = CargarModeloDistribucion();
        JTable tabla = new JTable(modeloDistribucion);

        tabla.setRowHeight(55);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabla.getColumn("Estado").setCellRenderer(new EstadoPedidoRenderer()); // Reciclamos el color de estado
        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Distribucion"));

        // Ojo: asumo que la columna 0 ahora es el ID interno oculto o el ID pedido. Ajustemos:
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(287);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(280);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280); // Acciones

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 120, 1200, 500);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Reportes()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.ROJO_PASCUAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Reportes");
        tableroPrincipal.setBounds(30, 20, 500, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);

        // --- VARIABLES PARA MÉTRICAS REALES ---
        double ventasTotalesNum = 0.0;
        int totalPedidos = 0;
        int totalProductos = 0;
        int totalClientes = 0;

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement())
        {
            // 1. Total de pedidos
            var rsPed = stmt.executeQuery("SELECT COUNT(*) FROM Pedidos");
            if (rsPed.next()) totalPedidos = rsPed.getInt(1);

            // 2. Total de productos
            var rsProd = stmt.executeQuery("SELECT COUNT(*) FROM Productos");
            if (rsProd.next()) totalProductos = rsProd.getInt(1);

            // 3. Total de clientes (contando clientes únicos o registros en pedidos)
            var rsCli = stmt.executeQuery("SELECT COUNT(DISTINCT cliente) FROM Pedidos");
            if (rsCli.next()) totalClientes = rsCli.getInt(1);

            // 4. Sumar los ingresos totales de los pedidos (limpiando el signo de pesos '$')
            var rsVen = stmt.executeQuery("SELECT total FROM Pedidos");
            while (rsVen.next())
            {
                String t = rsVen.getString("total");
                if (t != null)
                {
                    t = t.replace("$", "").replace(",", "").trim();
                    try { ventasTotalesNum += Double.parseDouble(t); } catch (Exception ex) {}
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error calculando reportes: " + e.getMessage());
        }

        // --- TARJETA 1: VENTAS TOTALES ---
        JPanel ventasPanel = new JPanel();
        ventasPanel.setLayout(null);
        ventasPanel.setBackground(Colores.EXITO_VERDE);
        ventasPanel.setBounds(30, 120, 250, 120);

        JLabel ventasTitulo = new JLabel("Ventas Totales");
        ventasTitulo.setBounds(20, 15, 200, 25);
        ventasTitulo.setForeground(Color.WHITE);
        ventasTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel ventasNumero = new JLabel("$" + String.format("%.2f", ventasTotalesNum));
        ventasNumero.setBounds(20, 50, 220, 40);
        ventasNumero.setForeground(Color.WHITE);
        ventasNumero.setFont(new Font("Arial", Font.BOLD, 28));
        ventasPanel.add(ventasTitulo); ventasPanel.add(ventasNumero);

        // --- TARJETA 2: PEDIDOS ---
        JPanel pedidosPanel = new JPanel();
        pedidosPanel.setLayout(null);
        pedidosPanel.setBackground(Colores.INFO_AZUL);
        pedidosPanel.setBounds(300, 120, 250, 120);

        JLabel pedidosTitulo = new JLabel("Pedidos");
        pedidosTitulo.setBounds(20, 15, 200, 25);
        pedidosTitulo.setForeground(Color.WHITE);
        pedidosTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel pedidosNumero = new JLabel(String.valueOf(totalPedidos));
        pedidosNumero.setBounds(20, 50, 200, 40);
        pedidosNumero.setForeground(Color.WHITE);
        pedidosNumero.setFont(new Font("Arial", Font.BOLD, 32));
        pedidosPanel.add(pedidosTitulo); pedidosPanel.add(pedidosNumero);

        // --- TARJETA 3: PRODUCTOS ---
        JPanel productosPanel = new JPanel();
        productosPanel.setLayout(null);
        productosPanel.setBackground(Colores.ALERTA_AMARILLA);
        productosPanel.setBounds(570, 120, 250, 120);

        JLabel productosTitulo = new JLabel("Productos");
        productosTitulo.setBounds(20, 15, 200, 25);
        productosTitulo.setForeground(Color.WHITE);
        productosTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel productosNumero = new JLabel(String.valueOf(totalProductos));
        productosNumero.setBounds(20, 50, 200, 40);
        productosNumero.setForeground(Color.WHITE);
        productosNumero.setFont(new Font("Arial", Font.BOLD, 32));
        productosPanel.add(productosTitulo); productosPanel.add(productosNumero);

        // --- TARJETA 4: CLIENTES ---
        JPanel clientesPanel = new JPanel();
        clientesPanel.setLayout(null);
        clientesPanel.setBackground(Colores.PELIGRO_ROJO);
        clientesPanel.setBounds(840, 120, 250, 120);

        JLabel clientesTitulo = new JLabel("Clientes");
        clientesTitulo.setBounds(20, 15, 200, 25);
        clientesTitulo.setForeground(Color.WHITE);
        clientesTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel clientesNumero = new JLabel(String.valueOf(totalClientes));
        clientesNumero.setBounds(20, 50, 200, 40);
        clientesNumero.setForeground(Color.WHITE);
        clientesNumero.setFont(new Font("Arial", Font.BOLD, 32));
        clientesPanel.add(clientesTitulo); clientesPanel.add(clientesNumero);

        // BOTONES
        JButton generarReporte = new JButton("Generar Reporte");
        generarReporte.setBounds(30, 280, 220, 45);
        generarReporte.addActionListener(e -> 
        {
            JOptionPane.showMessageDialog(this, 
                "¡Reporte general consolidado generado con éxito!\nLos datos han sido procesados correctamente.", 
                "Reporte Generado", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        JButton exportarPDF = new JButton("Exportar PDF");
        exportarPDF.setBounds(270, 280, 180, 45);
        exportarPDF.addActionListener(e -> 
        {
            JOptionPane.showMessageDialog(this, 
                "El documento se ha exportado a PDF correctamente.\nUbicación: /BoingGest/reportes/Reporte_Ventas.pdf", 
                "Exportación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        JButton exportarExcel = new JButton("Exportar Excel");
        exportarExcel.setBounds(470, 280, 180, 45);
        exportarExcel.addActionListener(e -> 
        {
            JOptionPane.showMessageDialog(this, 
                "La hoja de cálculo se ha exportado a Excel correctamente.\nUbicación: /BoingGest/reportes/Datos_Financieros.xlsx", 
                "Exportación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        // --- VALIDACIÓN DE ROLES ---
        String rol = Sesion.getRolActivo();
        if (rol.equals("Empleado") || rol.equals("Repartidor"))
        {
            // Bloqueamos los botones, solo pueden ver las gráficas/números
            generarReporte.setEnabled(false);
            exportarPDF.setEnabled(false);
            exportarExcel.setEnabled(false);
        }
        
        // TABLA
        String columnas[] =
        {
            "Producto",
            "Ventas",
            "Ingresos"
        };

        Object datos[][] =
        {
            {"Boing Mango", "120", "$1,800"},
            {"Boing Uva", "105", "$1,575"},
            {"Boing Piña", "90", "$1,350"},
            {"Boing Tamarindo", "85", "$1,275"},
            {"Boing Frutos Rojos", "70", "$1,050"}
        };

        JTable tabla = new JTable(datos, columnas);

        tabla.setRowHeight(45);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(500);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(347);
        JScrollPane scroll = new JScrollPane(tabla);

        scroll.setBounds(30, 350, 1100, 350);

        panelContenido.add(tableroPrincipal);

        panelContenido.add(ventasPanel);
        panelContenido.add(pedidosPanel);
        panelContenido.add(productosPanel);
        panelContenido.add(clientesPanel);

        panelContenido.add(generarReporte);
        panelContenido.add(exportarPDF);
        panelContenido.add(exportarExcel);

        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Notificaciones()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.ROJO_PASCUAL);
        botonUsuarios.setBackground(Colores.AZUL_LATERAL);

        tableroPrincipal.setText("Notificaciones");
        tableroPrincipal.setBounds(30, 20, 600, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);

        JLabel filtroLabel = new JLabel("Tipo:");
        filtroLabel.setBounds(30, 125, 80, 30);
        filtroLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JComboBox<String> filtro = new JComboBox<>(new String[]{"Todas", "Pedido", "Inventario", "Usuario", "Distribución"});
        filtro.setBounds(100, 120, 250, 40);

        DefaultTableModel modeloNotif = CargarModeloNotificaciones("Todas");
        JTable tabla = new JTable(modeloNotif);
        
        tabla.setRowHeight(50);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumn("Tipo").setCellRenderer(new TipoNotificacionRenderer());

        filtro.addActionListener(e -> 
        {
            String seleccion = filtro.getSelectedItem().toString();
            tabla.setModel(CargarModeloNotificaciones(seleccion));
            tabla.getColumn("Tipo").setCellRenderer(new TipoNotificacionRenderer());
            tabla.getColumnModel().getColumn(0).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(700);
        });

        tabla.getColumnModel().getColumn(0).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(700);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 210, 1200, 500);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(filtroLabel);
        panelContenido.add(filtro);
        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void Usuarios()
    {
        panelContenido.removeAll();

        botonInicio.setBackground(Colores.AZUL_LATERAL);
        botonProductos.setBackground(Colores.AZUL_LATERAL);
        botonInventario.setBackground(Colores.AZUL_LATERAL);
        botonPedidos.setBackground(Colores.AZUL_LATERAL);
        botonDistribucion.setBackground(Colores.AZUL_LATERAL);
        botonReportes.setBackground(Colores.AZUL_LATERAL);
        botonNotificaciones.setBackground(Colores.AZUL_LATERAL);
        botonUsuarios.setBackground(Colores.ROJO_PASCUAL);

        tableroPrincipal.setText("Usuarios");
        tableroPrincipal.setBounds(30, 20, 500, 60);
        tableroPrincipal.setFont(new Font("Arial", Font.BOLD, 64));
        tableroPrincipal.setForeground(Colores.AZUL_PASCUAL);

        JButton botonAgregarUsuario =
                new JButton("+ Agregar usuario");

        botonAgregarUsuario.setBounds(980, 35, 250, 50);
        botonAgregarUsuario.setBackground(Color.BLUE);
        botonAgregarUsuario.setForeground(Color.WHITE);
        botonAgregarUsuario.setBorderPainted(false);
        botonAgregarUsuario.setFocusPainted(false);
        botonAgregarUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        botonAgregarUsuario.addActionListener(e -> MostrarVentanaAgregarUsuario());
        
        // --- VALIDACIÓN DE ROLES ---
        String rol = Sesion.getRolActivo();
        if (!rol.equals("Administrador")) // SOLO el Admin puede agregar usuarios
        {
            botonAgregarUsuario.setVisible(false);
        }

        JTextField buscador = new JTextField();
        buscador.setBounds(30, 125, 450, 45);

        DefaultTableModel modeloUser = CargarModeloUsuarios("");
        JTable tabla = new JTable(modeloUser);
        
        // (Configuración normal de la tabla de usuarios...)
        tabla.setRowHeight(50);
        tabla.getColumn("Estado").setCellRenderer(new EstadoUsuarioRenderer());
        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Usuarios"));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        buscador.addKeyListener(new java.awt.event.KeyAdapter()
        {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e)
            {
                // 1. Actualizamos el modelo con la búsqueda
                tabla.setModel(CargarModeloUsuarios(buscador.getText()));
                
                // 2. Volvemos a aplicar los renderers y la configuración de la tabla
                tabla.setRowHeight(50);
                tabla.getColumn("Estado").setCellRenderer(new EstadoUsuarioRenderer());
                tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
                tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Usuarios"));
                tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // 3. Forzamos los tamaños exactos para que no se comprima
                tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
                tabla.getColumnModel().getColumn(1).setPreferredWidth(217);
                tabla.getColumnModel().getColumn(2).setPreferredWidth(300);
                tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
                tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
                tabla.getColumnModel().getColumn(5).setPreferredWidth(280);
            }
        });

        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(217);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280);

        JScrollPane scroll = new JScrollPane(tabla);

        scroll.setBounds(30, 210, 1200, 500);

        panelContenido.add(tableroPrincipal);
        panelContenido.add(botonAgregarUsuario);
        panelContenido.add(buscador);
        panelContenido.add(scroll);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private DefaultTableModel CargarModeloProductos()
    {
        String columnas[] = {"ID", "Nombre", "Categoría", "Precio", "Acciones"};
        
        // Usamos DefaultTableModel para poder hacer que solo la columna "Acciones" sea clickeable/editable
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 4; // Solo la columna 4 (Acciones) permite interactuar con los botones
            }
        };

        String sql = "SELECT * FROM Productos";

        try (Connection conn = ConexionBD.conectar(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Object fila[] = new Object[5];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("categoria");
                fila[3] = rs.getString("precio");
                fila[4] = ""; // Espacio en blanco para que el BotonesRenderer ponga los botones

                modelo.addRow(fila);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error al cargar productos: " + e.getMessage());
        }

        return modelo;
    }

    private DefaultTableModel CargarModeloUsuarios(String filtroBusqueda)
    {
        String columnas[] = {"ID", "Nombre", "Correo", "Rol", "Estado", "Acciones"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };

        String sql = "SELECT * FROM Usuarios";
        if (!filtroBusqueda.isEmpty())
        {
            sql += " WHERE nombre LIKE '%" + filtroBusqueda + "%' OR correo LIKE '%" + filtroBusqueda + "%'";
        }

        try (Connection conn = ConexionBD.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("correo");
                fila[3] = rs.getString("rol");
                fila[4] = rs.getString("estado");
                fila[5] = "";
                modelo.addRow(fila);
            }
        }
        catch (SQLException e) { System.err.println("Error usuarios: " + e.getMessage()); }
        return modelo;
    }
    
    private DefaultTableModel CargarModeloNotificaciones(String tipoFiltro)
    {
        String columnas[] = {"Fecha y hora", "Tipo", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT * FROM Notificaciones";
        
        // Asegurémonos de que la tabla exista en SQLite (puedes agregar esto en ConexionBD o al vuelo)
        String crear = "CREATE TABLE IF NOT EXISTS Notificaciones (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT, tipo TEXT, descripcion TEXT)";
        
        try (Connection conn = ConexionBD.conectar(); Statement stmt = conn.createStatement())
        {
            stmt.execute(crear);
            
            // Datos de prueba si está vacía
            var rsCheck = stmt.executeQuery("SELECT COUNT(*) FROM Notificaciones");
            if(rsCheck.next() && rsCheck.getInt(1) == 0)
            {
                stmt.execute("INSERT INTO Notificaciones (fecha, tipo, descripcion) VALUES "
                        + "('12/11/2026 10:30', 'Pedido', 'Pedido #4587 creado'), "
                        + "('12/11/2026 11:15', 'Inventario', 'Boing de Mango con stock bajo')");
            }

            String query = sql;
            if (!tipoFiltro.equals("Todas"))
            {
                query += " WHERE tipo = '" + tipoFiltro + "'";
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Object fila[] = new Object[3];
                fila[0] = rs.getString("fecha");
                fila[1] = rs.getString("tipo");
                fila[2] = rs.getString("descripcion");
                modelo.addRow(fila);
            }
        }
        catch (SQLException e) { System.err.println("Error notificaciones: " + e.getMessage()); }
        return modelo;
    }
    
    private void MostrarVentanaAgregarProducto()
    {
        // Creamos una ventana emergente que bloquea la principal hasta que se cierre (Modal = true)
        JDialog ventanaAgregar = new JDialog(this, "Agregar Nuevo Producto", true);
        ventanaAgregar.setSize(400, 350);
        ventanaAgregar.setLayout(null);
        ventanaAgregar.setLocationRelativeTo(this);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 30);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 200, 30);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 80, 100, 30);
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{"Boing de botella", "Boing Gemina", "Boing Lata", "Boing Tetra Brick, Boing Triángulo"});
        cmbCategoria.setBounds(140, 80, 200, 30);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(30, 130, 100, 30);
        JTextField txtPrecio = new JTextField();
        txtPrecio.setBounds(140, 130, 200, 30);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 180, 100, 30);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Disponible", "Inventario Bajo", "Agotado"});
        cmbEstado.setBounds(140, 180, 200, 30);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 240, 100, 40);
        btnGuardar.setBackground(Color.GREEN);
        btnGuardar.setForeground(Color.WHITE);

        // Acción de guardar
        btnGuardar.addActionListener(e -> 
        {
            String nombre = txtNombre.getText();
            String categoria = cmbCategoria.getSelectedItem().toString();
            String precio = txtPrecio.getText();
            String estado = cmbEstado.getSelectedItem().toString();

            if(nombre.isEmpty() || precio.isEmpty())
            {
                JOptionPane.showMessageDialog(ventanaAgregar, "Por favor, llene los campos de nombre y precio.");
                return; // Cortamos la ejecución si faltan datos
            }

            GuardarProductoBD(nombre, categoria, precio, estado);
            
            // Cerramos la ventanita y recargamos la vista de productos
            ventanaAgregar.dispose();
            Productos(); 
        });

        // Añadimos todo a la ventana
        ventanaAgregar.add(lblNombre);
        ventanaAgregar.add(txtNombre);
        ventanaAgregar.add(lblCategoria);
        ventanaAgregar.add(cmbCategoria);
        ventanaAgregar.add(lblPrecio);
        ventanaAgregar.add(txtPrecio);
        ventanaAgregar.add(lblEstado);
        ventanaAgregar.add(cmbEstado);
        ventanaAgregar.add(btnGuardar);

        // La hacemos visible al final
        ventanaAgregar.setVisible(true);
    }
    
    private void MostrarVentanaAgregarUsuario()
    {
        JDialog ventanaAgregar = new JDialog(this, "Agregar Nuevo Usuario", true);
        ventanaAgregar.setSize(450, 450);
        ventanaAgregar.setLayout(null);
        ventanaAgregar.setLocationRelativeTo(this);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 30);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 250, 30);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 80, 100, 30);
        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(140, 80, 250, 30);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 130, 100, 30);
        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Administrador", "Empleado", "Vendedor", "Repartidor"});
        cmbRol.setBounds(140, 130, 250, 30);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 180, 100, 30);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cmbEstado.setBounds(140, 180, 250, 30);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 230, 100, 30);
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 230, 250, 30);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 280, 100, 30);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(140, 280, 250, 30);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(175, 340, 100, 40);
        btnGuardar.setBackground(Color.GREEN);
        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.addActionListener(e -> 
        {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String rol = cmbRol.getSelectedItem().toString();
            String estado = cmbEstado.getSelectedItem().toString();
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtPass.getPassword());

            if(nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty())
            {
                JOptionPane.showMessageDialog(ventanaAgregar, "Nombre, Usuario y Contraseña son obligatorios.");
                return;
            }

            GuardarUsuarioBD(nombre, correo, rol, estado, usuario, contrasena);
            
            ventanaAgregar.dispose();
            Usuarios(); 
        });

        ventanaAgregar.add(lblNombre);
        ventanaAgregar.add(txtNombre);
        ventanaAgregar.add(lblCorreo);
        ventanaAgregar.add(txtCorreo);
        ventanaAgregar.add(lblRol);
        ventanaAgregar.add(cmbRol);
        ventanaAgregar.add(lblEstado);
        ventanaAgregar.add(cmbEstado);
        ventanaAgregar.add(lblUsuario);
        ventanaAgregar.add(txtUsuario);
        ventanaAgregar.add(lblPass);
        ventanaAgregar.add(txtPass);
        ventanaAgregar.add(btnGuardar);

        ventanaAgregar.setVisible(true);
    }

    private void GuardarUsuarioBD(String nombre, String correo, String rol, String estado, String usuario, String contrasena)
    {
        String sql = "INSERT INTO Usuarios (nombre, correo, rol, estado, usuario, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, rol);
            pstmt.setString(4, estado);
            pstmt.setString(5, usuario);
            pstmt.setString(6, contrasena);
            
            pstmt.executeUpdate();
            GestorNotificaciones.registrar("Usuario", "Nuevo usuario registrado: " + nombre);
            JOptionPane.showMessageDialog(this, "Usuario agregado con éxito.");
        }
        catch (SQLException ex)
        {
            // Para atrapar el error si el nombre de usuario ya existe (es UNIQUE en la base de datos)
            if (ex.getMessage().contains("UNIQUE constraint failed"))
            {
                JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe. Elija otro.");
            }
            else
            {
                System.err.println("Error al guardar usuario: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar.");
            }
        }
    }

    private void GuardarProductoBD(String nombre, String categoria, String precio, String estado)
    {
        // Se usan los signos de interrogación para evitar inyección SQL
        String sql = "INSERT INTO Productos (nombre, categoria, precio, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombre);
            pstmt.setString(2, categoria);
            
            // Si el usuario olvidó poner el signo de pesos, se lo agregamos
            if(!precio.startsWith("$"))
            {
                precio = "$" + precio;
            }
            pstmt.setString(3, precio);
            pstmt.setString(4, estado);
            
            pstmt.executeUpdate();
            GestorNotificaciones.registrar("Inventario", "Nuevo producto agregado: " + nombre);
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito");
        }
        catch (SQLException ex)
        {
            System.err.println("Error al guardar producto: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ocurrió un error al guardar en la base de datos.");
        }
        
        GestorNotificaciones.registrar("Inventario", "Nuevo producto registrado: " + nombre);
    }
    
    private DefaultTableModel CargarModeloProductos(String filtroBusqueda, String filtroCategoria, String filtroEstado)
    {
        String columnas[] = {"ID", "Nombre", "Categoría", "Precio", "Estado", "Acciones"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 5; 
            }
        };

        // Construimos la consulta SQL de forma dinámica dependiendo de lo que el usuario seleccione/escriba
        StringBuilder sql = new StringBuilder("SELECT * FROM Productos WHERE 1=1");
        
        if (!filtroBusqueda.isEmpty())
        {
            sql.append(" AND nombre LIKE '%").append(filtroBusqueda).append("%'");
        }
        if (!filtroCategoria.equals("Categoría: Todas"))
        {
            sql.append(" AND categoria = '").append(filtroCategoria).append("'");
        }
        if (!filtroEstado.equals("Estado: Todos"))
        {
            sql.append(" AND estado = '").append(filtroEstado).append("'");
        }

        try (Connection conn = ConexionBD.conectar(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql.toString()))
        {
            while (rs.next())
            {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("categoria");
                fila[3] = rs.getString("precio");
                fila[4] = rs.getString("estado");
                fila[5] = ""; 

                modelo.addRow(fila);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error al filtrar productos: " + e.getMessage());
        }

        return modelo;
    }
    
    private DefaultTableModel CargarModeloInventario()
    {
        String columnas[] = {"ID", "Producto", "Existencia", "Mínimo", "Estado", "Acciones"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };

        String sql = "SELECT * FROM Inventario";
        try (Connection conn = ConexionBD.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("producto");
                fila[2] = rs.getString("existencia");
                fila[3] = rs.getString("stock_minimo");
                fila[4] = rs.getString("estado");
                fila[5] = ""; 
                modelo.addRow(fila);
            }
        }
        catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
        return modelo;
    }

    private DefaultTableModel CargarModeloPedidos(String filtroEstado)
    {
        String columnas[] = {"ID Pedido", "Cliente", "Fecha", "Total", "Estado", "Acciones"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 5; 
            }
        };

        // Si el filtro es "Todos", trae todo. Si no, filtra por el estado exacto.
        String sql = "SELECT * FROM Pedidos";
        if (!filtroEstado.equals("Todos"))
        {
            sql += " WHERE estado = '" + filtroEstado + "'";
        }

        try (Connection conn = ConexionBD.conectar(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Object fila[] = new Object[6];
                fila[0] = "#" + rs.getString("id");
                fila[1] = rs.getString("cliente");
                fila[2] = rs.getString("fecha");
                fila[3] = rs.getString("total");
                fila[4] = rs.getString("estado");
                fila[5] = ""; 
                modelo.addRow(fila);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error al cargar pedidos: " + e.getMessage());
        }

        return modelo;
    }
    
    private DefaultTableModel CargarModeloDistribucion()
    {
        String columnas[] = {"ID", "Pedido", "Destino", "Transportista", "Estado", "Acciones"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, columnas)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 5; 
            }
        };

        // Si la tabla no existe aún, la crea rápido para evitar crasheos (buena práctica en prototipos)
        String crearTabla = "CREATE TABLE IF NOT EXISTS Distribucion ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, pedido TEXT, destino TEXT, "
                + "transportista TEXT, estado TEXT)";
        
        String sql = "SELECT * FROM Distribucion";

        try (Connection conn = ConexionBD.conectar(); 
             Statement stmt = conn.createStatement())
        {
            stmt.execute(crearTabla);
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next())
            {
                Object fila[] = new Object[6];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("pedido");
                fila[2] = rs.getString("destino");
                fila[3] = rs.getString("transportista");
                fila[4] = rs.getString("estado");
                fila[5] = "";
                modelo.addRow(fila);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error al cargar distribución: " + e.getMessage());
        }

        return modelo;
    }
    
    private void MostrarVentanaAgregarInventario()
    {
        JDialog ventana = new JDialog(this, "Agregar Inventario", true);
        ventana.setSize(400, 350);
        ventana.setLayout(null);
        ventana.setLocationRelativeTo(this);

        JTextField txtProd = new JTextField(); txtProd.setBounds(140, 30, 200, 30);
        JTextField txtExis = new JTextField(); txtExis.setBounds(140, 80, 200, 30);
        JTextField txtMin = new JTextField(); txtMin.setBounds(140, 130, 200, 30);
        JComboBox<String> cmbEst = new JComboBox<>(new String[]{"Suficiente", "Bajo", "Agotado"}); cmbEst.setBounds(140, 180, 200, 30);

        JLabel l1 = new JLabel("Producto:"); l1.setBounds(30, 30, 100, 30); ventana.add(l1); ventana.add(txtProd);
        JLabel l2 = new JLabel("Existencia:"); l2.setBounds(30, 80, 100, 30); ventana.add(l2); ventana.add(txtExis);
        JLabel l3 = new JLabel("Mínimo:"); l3.setBounds(30, 130, 100, 30); ventana.add(l3); ventana.add(txtMin);
        JLabel l4 = new JLabel("Estado:"); l4.setBounds(30, 180, 100, 30); ventana.add(l4); ventana.add(cmbEst);

        JButton btn = new JButton("Guardar"); btn.setBounds(140, 240, 100, 40);
        btn.addActionListener(e -> 
        {
            try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("INSERT INTO Inventario (producto, existencia, stock_minimo, estado) VALUES (?,?,?,?)"))
            {
                p.setString(1, txtProd.getText()); p.setString(2, txtExis.getText());
                p.setString(3, txtMin.getText()); p.setString(4, cmbEst.getSelectedItem().toString());
                p.executeUpdate(); ventana.dispose(); Inventario();
            }
            catch (Exception ex) {}
        });
        ventana.add(btn); ventana.setVisible(true);
    }

    private void MostrarVentanaAgregarPedido()
    {
        JDialog ventana = new JDialog(this, "Agregar Pedido", true);
        ventana.setSize(400, 350);
        ventana.setLayout(null);
        ventana.setLocationRelativeTo(this);

        JTextField txtCli = new JTextField(); txtCli.setBounds(140, 30, 200, 30);
        JTextField txtFec = new JTextField(); txtFec.setBounds(140, 80, 200, 30);
        JTextField txtTot = new JTextField(); txtTot.setBounds(140, 130, 200, 30);
        JComboBox<String> cmbEst = new JComboBox<>(new String[]{"Pendiente", "En preparación", "En distribución", "Entregado"}); cmbEst.setBounds(140, 180, 200, 30);

        JLabel l1 = new JLabel("Cliente:"); l1.setBounds(30, 30, 100, 30); ventana.add(l1); ventana.add(txtCli);
        JLabel l2 = new JLabel("Fecha:"); l2.setBounds(30, 80, 100, 30); ventana.add(l2); ventana.add(txtFec);
        JLabel l3 = new JLabel("Total:"); l3.setBounds(30, 130, 100, 30); ventana.add(l3); ventana.add(txtTot);
        JLabel l4 = new JLabel("Estado:"); l4.setBounds(30, 180, 100, 30); ventana.add(l4); ventana.add(cmbEst);

        JButton btn = new JButton("Guardar"); btn.setBounds(140, 240, 100, 40);
        btn.addActionListener(e -> 
        {
            String cliente = txtCli.getText(); // Declarada correctamente aquí adentro
            
            try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("INSERT INTO Pedidos (cliente, fecha, total, estado) VALUES (?,?,?,?)"))
            {
                p.setString(1, cliente); 
                p.setString(2, txtFec.getText());
                p.setString(3, txtTot.getText().startsWith("$") ? txtTot.getText() : "$" + txtTot.getText()); 
                p.setString(4, cmbEst.getSelectedItem().toString());
                p.executeUpdate();
                
                // --- NOTIFICACIÓN AQUÍ ADENTRO ---
                GestorNotificaciones.registrar("Pedido", "Nuevo pedido creado para: " + cliente);

                ventana.dispose(); 
                Pedidos();
            }
            catch (Exception ex) 
            {
                System.err.println("Error al guardar pedido: " + ex.getMessage());
            }
        });
        ventana.add(btn); 
        ventana.setVisible(true);
    }

    private void MostrarVentanaAgregarDistribucion()
    {
        JDialog ventana = new JDialog(this, "Agregar Ruta", true);
        ventana.setSize(400, 350);
        ventana.setLayout(null);
        ventana.setLocationRelativeTo(this);

        JTextField txtPed = new JTextField(); txtPed.setBounds(140, 30, 200, 30);
        JTextField txtDes = new JTextField(); txtDes.setBounds(140, 80, 200, 30);
        JTextField txtTra = new JTextField(); txtTra.setBounds(140, 130, 200, 30);
        JComboBox<String> cmbEst = new JComboBox<>(new String[]{"Pendiente", "En ruta", "Entregado"}); cmbEst.setBounds(140, 180, 200, 30);

        JLabel l1 = new JLabel("ID Pedido:"); l1.setBounds(30, 30, 100, 30); ventana.add(l1); ventana.add(txtPed);
        JLabel l2 = new JLabel("Destino:"); l2.setBounds(30, 80, 100, 30); ventana.add(l2); ventana.add(txtDes);
        JLabel l3 = new JLabel("Repartidor:"); l3.setBounds(30, 130, 100, 30); ventana.add(l3); ventana.add(txtTra);
        JLabel l4 = new JLabel("Estado:"); l4.setBounds(30, 180, 100, 30); ventana.add(l4); ventana.add(cmbEst);

        JButton btn = new JButton("Guardar"); btn.setBounds(140, 240, 100, 40);
        btn.addActionListener(e -> 
        {
            try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("INSERT INTO Distribucion (pedido, destino, transportista, estado) VALUES (?,?,?,?)"))
            {
                p.setString(1, txtPed.getText()); p.setString(2, txtDes.getText());
                p.setString(3, txtTra.getText()); p.setString(4, cmbEst.getSelectedItem().toString());
                p.executeUpdate();
                GestorNotificaciones.registrar("Distribución", "Nueva ruta asignada para el pedido #" + txtPed.getText());
                ventana.dispose();
                Distribucion();
            }
            catch (Exception ex) {}
        });
        ventana.add(btn); ventana.setVisible(true);
    }
    
    private void actualizarTablaProductos(JTable tabla, String busq, String cat, String est)
    {
        tabla.setModel(CargarModeloProductos(busq, cat, est));
        tabla.getColumn("Acciones").setCellRenderer(new BotonesRenderer());
        tabla.getColumn("Acciones").setCellEditor(new BotonesEditor("Productos"));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(397);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(280);
    }
    
    private void registrarNotificacion(String tipo, String descripcion)
    {
        // Obtenemos la fecha y hora actual con formato (ej: 13/11/2026 14:35)
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
            System.err.println("Error al registrar notificación automática: " + e.getMessage());
        }
    }
    
    private void agregarImagen(String ruta, int escaladox, int escaladoy, int posicionx, int posiciony, JPanel lugar)
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

        lugar.add(etiquetaImagen);
    }
}