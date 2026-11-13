package Button;

import Components.GestorNotificaciones;
import SQL.ConexionBD;
import SQL.Sesion;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class BotonesEditor extends AbstractCellEditor implements TableCellEditor
{
    JPanel panel = new JPanel();
    JButton ver = new JButton("Ver");
    JButton editar = new JButton("Editar");
    JButton eliminar = new JButton("Eliminar");

    String tablaBaseDatos;
    String idFilaActual;
    JTable tablaVisual;

    public BotonesEditor(String tablaBaseDatos)
    {
        this.tablaBaseDatos = tablaBaseDatos;
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        panel.add(ver);
        panel.add(editar);
        panel.add(eliminar);

        // --- VALIDACIÓN DE ROLES PARA LA TABLA ---
        String rol = Sesion.getRolActivo();
        
        if (rol.equals("Empleado"))
        {
            editar.setEnabled(false);
            eliminar.setEnabled(false);
        }
        else if (rol.equals("Vendedor"))
        {
            // Vendedor puede editar Productos, Inventario y Pedidos, pero NO Usuarios ni Distribución
            if (tablaBaseDatos.equals("Usuarios") || tablaBaseDatos.equals("Distribucion"))
            {
                editar.setEnabled(false);
                eliminar.setEnabled(false);
            }
        }
        else if (rol.equals("Repartidor"))
        {
            // Repartidor SOLO puede interactuar con Distribución
            if (!tablaBaseDatos.equals("Distribucion"))
            {
                editar.setEnabled(false);
                eliminar.setEnabled(false);
            }
        }

        // --- ACCIÓN BOTÓN EDITAR ---
        editar.addActionListener(e ->
        {
            fireEditingStopped(); 
            if (idFilaActual != null && !idFilaActual.isEmpty())
            {
                if (tablaBaseDatos.equals("Productos")) EditarProducto();
                else if (tablaBaseDatos.equals("Usuarios")) EditarUsuario();
                else if (tablaBaseDatos.equals("Inventario")) EditarInventario();
                else if (tablaBaseDatos.equals("Pedidos")) EditarPedido();
                else if (tablaBaseDatos.equals("Distribucion")) EditarDistribucion();
            }
        });

        // --- ACCIÓN BOTÓN ELIMINAR ---
        eliminar.addActionListener(e ->
        {
            fireEditingStopped(); 

            if (idFilaActual != null && !idFilaActual.isEmpty())
            {
                int respuesta = JOptionPane.showConfirmDialog(panel, 
                        "¿Estás seguro de que deseas eliminar este registro?", 
                        "Confirmar eliminación", 
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION)
                {
                    EliminarDeBaseDeDatos(idFilaActual);
                    
                    DefaultTableModel modelo = (DefaultTableModel) tablaVisual.getModel();
                    modelo.removeRow(tablaVisual.getSelectedRow());
                }
            }
        });
    }

    private void EditarProducto()
    {
        // 1. Obtener datos actuales de SQLite
        String sql = "SELECT * FROM Productos WHERE id = ?";
        String nombreActual = "";
        String categoriaActual = "";
        String precioActual = "";
        String estadoActual = "";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, idFilaActual);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                nombreActual = rs.getString("nombre");
                categoriaActual = rs.getString("categoria");
                precioActual = rs.getString("precio");
                estadoActual = rs.getString("estado");
            }
        }
        catch (SQLException ex)
        {
            System.err.println("Error al cargar datos para editar: " + ex.getMessage());
            return;
        }

        // 2. Crear ventana de edición
        // Usamos SwingUtilities para anclar el JDialog a la ventana principal
        JFrame ventanaMadre = (JFrame) SwingUtilities.getWindowAncestor(panel);
        JDialog ventanaEditar = new JDialog(ventanaMadre, "Editar Producto", true);
        
        ventanaEditar.setSize(400, 350);
        ventanaEditar.setLayout(null);
        ventanaEditar.setLocationRelativeTo(panel);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 30);
        JTextField txtNombre = new JTextField(nombreActual);
        txtNombre.setBounds(140, 30, 200, 30);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 80, 100, 30);
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{
            "Boing de botella", "Boing Tetra Brik", "Boing Gemina", "Panadería", "Galletas", "Pastelería"
        });
        cmbCategoria.setSelectedItem(categoriaActual); // Selecciona lo que viene de la base de datos
        cmbCategoria.setBounds(140, 80, 200, 30);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(30, 130, 100, 30);
        JTextField txtPrecio = new JTextField(precioActual);
        txtPrecio.setBounds(140, 130, 200, 30);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 180, 100, 30);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Disponible", "Inventario Bajo", "Agotado"});
        cmbEstado.setSelectedItem(estadoActual);
        cmbEstado.setBounds(140, 180, 200, 30);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(140, 240, 100, 40);
        btnActualizar.setBackground(Color.BLUE);
        btnActualizar.setForeground(Color.WHITE);

        // 3. Acción del botón Actualizar
        btnActualizar.addActionListener(ev ->
        {
            String nuevoNombre = txtNombre.getText();
            String nuevaCat = cmbCategoria.getSelectedItem().toString();
            String nuevoPrecio = txtPrecio.getText();
            String nuevoEstado = cmbEstado.getSelectedItem().toString();

            if (nuevoNombre.isEmpty() || nuevoPrecio.isEmpty())
            {
                JOptionPane.showMessageDialog(ventanaEditar, "Llene todos los campos requeridos.");
                return;
            }

            if (!nuevoPrecio.startsWith("$"))
            {
                nuevoPrecio = "$" + nuevoPrecio;
            }

            String sqlUpdate = "UPDATE Productos SET nombre=?, categoria=?, precio=?, estado=? WHERE id=?";
            
            try (Connection conn = ConexionBD.conectar();
                 PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
            {
                pstmt.setString(1, nuevoNombre);
                pstmt.setString(2, nuevaCat);
                pstmt.setString(3, nuevoPrecio);
                pstmt.setString(4, nuevoEstado);
                pstmt.setString(5, idFilaActual);
                
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(ventanaEditar, "Producto actualizado con éxito.");

                // Actualizamos la celda visualmente sin recargar todo de la BD
                int filaVisual = tablaVisual.getSelectedRow();
                if (filaVisual != -1)
                {
                    tablaVisual.setValueAt(nuevoNombre, filaVisual, 1);
                    tablaVisual.setValueAt(nuevaCat, filaVisual, 2);
                    tablaVisual.setValueAt(nuevoPrecio, filaVisual, 3);
                }
                
                ventanaEditar.dispose();
            }
            catch (SQLException ex)
            {
                System.err.println("Error al actualizar producto: " + ex.getMessage());
                JOptionPane.showMessageDialog(ventanaEditar, "Ocurrió un error al actualizar.");
            }
        });

        ventanaEditar.add(lblNombre);
        ventanaEditar.add(txtNombre);
        ventanaEditar.add(lblCategoria);
        ventanaEditar.add(cmbCategoria);
        ventanaEditar.add(lblPrecio);
        ventanaEditar.add(txtPrecio);
        ventanaEditar.add(lblEstado);
        ventanaEditar.add(cmbEstado);
        ventanaEditar.add(btnActualizar);

        ventanaEditar.setVisible(true);
    }
    
    private void EditarUsuario()
    {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        String nombreAct = "", correoAct = "", rolAct = "", estadoAct = "", usuarioAct = "", passAct = "";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, idFilaActual);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                nombreAct = rs.getString("nombre");
                correoAct = rs.getString("correo");
                rolAct = rs.getString("rol");
                estadoAct = rs.getString("estado");
                usuarioAct = rs.getString("usuario");
                passAct = rs.getString("contrasena");
            }
        }
        catch (SQLException ex)
        {
            System.err.println("Error al cargar usuario para editar: " + ex.getMessage());
            return;
        }

        JFrame ventanaMadre = (JFrame) SwingUtilities.getWindowAncestor(panel);
        JDialog ventanaEditar = new JDialog(ventanaMadre, "Editar Usuario", true);
        
        ventanaEditar.setSize(450, 450);
        ventanaEditar.setLayout(null);
        ventanaEditar.setLocationRelativeTo(panel);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 30);
        JTextField txtNombre = new JTextField(nombreAct);
        txtNombre.setBounds(140, 30, 250, 30);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 80, 100, 30);
        JTextField txtCorreo = new JTextField(correoAct);
        txtCorreo.setBounds(140, 80, 250, 30);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 130, 100, 30);
        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Administrador", "Empleado", "Vendedor", "Repartidor"});
        cmbRol.setSelectedItem(rolAct);
        cmbRol.setBounds(140, 130, 250, 30);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 180, 100, 30);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cmbEstado.setSelectedItem(estadoAct);
        cmbEstado.setBounds(140, 180, 250, 30);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 230, 100, 30);
        JTextField txtUsuario = new JTextField(usuarioAct);
        txtUsuario.setBounds(140, 230, 250, 30);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 280, 100, 30);
        JTextField txtPass = new JTextField(passAct); // Aquí se usa JTextField normal para que sepa qué borrar/cambiar
        txtPass.setBounds(140, 280, 250, 30);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(175, 340, 100, 40);
        btnActualizar.setBackground(Color.BLUE);
        btnActualizar.setForeground(Color.WHITE);

        btnActualizar.addActionListener(ev ->
        {
            String nuevoNombre = txtNombre.getText();
            String nuevoCorreo = txtCorreo.getText();
            String nuevoRol = cmbRol.getSelectedItem().toString();
            String nuevoEstado = cmbEstado.getSelectedItem().toString();
            String nuevoUser = txtUsuario.getText();
            String nuevaPass = txtPass.getText();

            if (nuevoNombre.isEmpty() || nuevoUser.isEmpty() || nuevaPass.isEmpty())
            {
                JOptionPane.showMessageDialog(ventanaEditar, "Nombre, Usuario y Contraseña son obligatorios.");
                return;
            }

            String sqlUpdate = "UPDATE Usuarios SET nombre=?, correo=?, rol=?, estado=?, usuario=?, contrasena=? WHERE id=?";
            
            try (Connection conn = ConexionBD.conectar();
                 PreparedStatement pstmt = conn.prepareStatement(sqlUpdate))
            {
                pstmt.setString(1, nuevoNombre);
                pstmt.setString(2, nuevoCorreo);
                pstmt.setString(3, nuevoRol);
                pstmt.setString(4, nuevoEstado);
                pstmt.setString(5, nuevoUser);
                pstmt.setString(6, nuevaPass);
                pstmt.setString(7, idFilaActual);
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(ventanaEditar, "Usuario actualizado con éxito.");

                // Reflejar cambios visuales en la tabla (Columnas: 1=Nombre, 2=Correo, 3=Rol, 4=Estado)
                int filaVisual = tablaVisual.getSelectedRow();
                if (filaVisual != -1)
                {
                    tablaVisual.setValueAt(nuevoNombre, filaVisual, 1);
                    tablaVisual.setValueAt(nuevoCorreo, filaVisual, 2);
                    tablaVisual.setValueAt(nuevoRol, filaVisual, 3);
                    
                    // Asegúrate de que aquí llame al EstadoUsuarioRenderer que ya tienes
                    tablaVisual.setValueAt(nuevoEstado, filaVisual, 4);
                }
                
                ventanaEditar.dispose();
            }
            catch (SQLException ex)
            {
                if (ex.getMessage().contains("UNIQUE constraint failed"))
                {
                    JOptionPane.showMessageDialog(ventanaEditar, "El nombre de usuario ya existe. Elija otro.");
                }
                else
                {
                    System.err.println("Error al actualizar usuario: " + ex.getMessage());
                    JOptionPane.showMessageDialog(ventanaEditar, "Ocurrió un error al actualizar.");
                }
            }
        });

        ventanaEditar.add(lblNombre);
        ventanaEditar.add(txtNombre);
        ventanaEditar.add(lblCorreo);
        ventanaEditar.add(txtCorreo);
        ventanaEditar.add(lblRol);
        ventanaEditar.add(cmbRol);
        ventanaEditar.add(lblEstado);
        ventanaEditar.add(cmbEstado);
        ventanaEditar.add(lblUsuario);
        ventanaEditar.add(txtUsuario);
        ventanaEditar.add(lblPass);
        ventanaEditar.add(txtPass);
        ventanaEditar.add(btnActualizar);

        ventanaEditar.setVisible(true);
    }

    private void EliminarDeBaseDeDatos(String id)
    {
        String sql = "DELETE FROM " + tablaBaseDatos + " WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            GestorNotificaciones.registrar(tablaBaseDatos, "Se eliminó un registro de " + tablaBaseDatos);
            JOptionPane.showMessageDialog(panel, "Registro eliminado correctamente.");
        }
        catch (SQLException ex)
        {
            System.err.println("Error al eliminar: " + ex.getMessage());
            JOptionPane.showMessageDialog(panel, "Ocurrió un error al eliminar.");
        }
    }
    
    private void EditarInventario()
    {
        String idLimpio = idFilaActual.replace("#", "");
        JFrame vMadre = (JFrame) SwingUtilities.getWindowAncestor(panel);
        JDialog vEdit = new JDialog(vMadre, "Editar Inventario", true);
        vEdit.setSize(400, 320); 
        vEdit.setLayout(null); 
        vEdit.setLocationRelativeTo(panel);

        JTextField txtProd = new JTextField(); txtProd.setBounds(140, 30, 200, 30);
        JTextField txtExis = new JTextField(); txtExis.setBounds(140, 80, 200, 30);
        JTextField txtMin = new JTextField(); txtMin.setBounds(140, 130, 200, 30);

        // Cargamos los datos actuales de la BD
        try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("SELECT * FROM Inventario WHERE id=?"))
        {
            p.setString(1, idLimpio); 
            ResultSet rs = p.executeQuery();
            if (rs.next()) 
            { 
                txtProd.setText(rs.getString("producto")); 
                txtExis.setText(rs.getString("existencia")); 
                txtMin.setText(rs.getString("stock_minimo")); 
            }
        } 
        catch (Exception ex) 
        {
            System.err.println("Error al cargar inventario: " + ex.getMessage());
        }

        vEdit.add(new JLabel("Producto:")).setBounds(30, 30, 100, 30); vEdit.add(txtProd);
        vEdit.add(new JLabel("Existencia:")).setBounds(30, 80, 100, 30); vEdit.add(txtExis);
        vEdit.add(new JLabel("Mínimo:")).setBounds(30, 130, 100, 30); vEdit.add(txtMin);

        JButton btn = new JButton("Actualizar"); 
        btn.setBounds(140, 200, 100, 40);
        
        btn.addActionListener(e -> 
        {
            try 
            {
                // Convertimos a enteros para aplicar la lógica automática
                int existencia = Integer.parseInt(txtExis.getText());
                int minimo = Integer.parseInt(txtMin.getText());
                
                // --- CÁLCULO AUTOMÁTICO DE ESTADO ---
                String estadoAutomatico = calcularEstadoInventario(existencia, minimo);

                try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("UPDATE Inventario SET producto=?, existencia=?, stock_minimo=?, estado=? WHERE id=?"))
                {
                    p.setString(1, txtProd.getText()); 
                    p.setString(2, String.valueOf(existencia)); 
                    p.setString(3, String.valueOf(minimo)); 
                    p.setString(4, estadoAutomatico); // Se guarda el estado calculado solo
                    p.setString(5, idLimpio);
                    p.executeUpdate(); 
                    
                    // --- NOTIFICACIÓN AQUÍ ---
                    GestorNotificaciones.registrar("Inventario", "Se actualizó el inventario de: " + txtProd.getText());

                    vEdit.dispose();
                    
                    // Reflejamos en la tabla visual
                    int f = tablaVisual.getSelectedRow(); 
                    if (f != -1) 
                    { 
                        tablaVisual.setValueAt(txtProd.getText(), f, 1); 
                        tablaVisual.setValueAt(String.valueOf(existencia), f, 2); 
                        tablaVisual.setValueAt(String.valueOf(minimo), f, 3); 
                        tablaVisual.setValueAt(estadoAutomatico, f, 4); 
                    }
                }
            } 
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(vEdit, "La existencia y el mínimo deben ser números enteros.");
            }
            catch (Exception ex) 
            {
                System.err.println("Error al actualizar inventario: " + ex.getMessage());
            }
        });
        
        vEdit.add(btn); 
        vEdit.setVisible(true);
    }

    private void EditarPedido()
    {
        String idLimpio = idFilaActual.replace("#", "");
        JFrame vMadre = (JFrame) SwingUtilities.getWindowAncestor(panel);
        JDialog vEdit = new JDialog(vMadre, "Editar Pedido", true);
        vEdit.setSize(400, 350); vEdit.setLayout(null); vEdit.setLocationRelativeTo(panel);

        JTextField txtCli = new JTextField(); txtCli.setBounds(140, 30, 200, 30);
        JTextField txtFec = new JTextField(); txtFec.setBounds(140, 80, 200, 30);
        JTextField txtTot = new JTextField(); txtTot.setBounds(140, 130, 200, 30);
        JComboBox<String> cmbEst = new JComboBox<>(new String[]{"Pendiente", "En preparación", "En distribución", "Entregado"}); cmbEst.setBounds(140, 180, 200, 30);

        try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("SELECT * FROM Pedidos WHERE id=?"))
        {
            p.setString(1, idLimpio); ResultSet rs = p.executeQuery();
            if (rs.next()) { txtCli.setText(rs.getString("cliente")); txtFec.setText(rs.getString("fecha")); txtTot.setText(rs.getString("total")); cmbEst.setSelectedItem(rs.getString("estado")); }
        } catch (Exception ex) {}

        vEdit.add(new JLabel("Cliente:")).setBounds(30, 30, 100, 30); vEdit.add(txtCli);
        vEdit.add(new JLabel("Fecha:")).setBounds(30, 80, 100, 30); vEdit.add(txtFec);
        vEdit.add(new JLabel("Total:")).setBounds(30, 130, 100, 30); vEdit.add(txtTot);
        vEdit.add(new JLabel("Estado:")).setBounds(30, 180, 100, 30); vEdit.add(cmbEst);

        JButton btn = new JButton("Actualizar"); btn.setBounds(140, 240, 100, 40);
        btn.addActionListener(e -> 
        {
            try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("UPDATE Pedidos SET cliente=?, fecha=?, total=?, estado=? WHERE id=?"))
            {
                String totalStr = txtTot.getText().startsWith("$") ? txtTot.getText() : "$" + txtTot.getText();
                p.setString(1, txtCli.getText()); p.setString(2, txtFec.getText()); p.setString(3, totalStr); p.setString(4, cmbEst.getSelectedItem().toString()); p.setString(5, idLimpio);
                p.executeUpdate(); vEdit.dispose();
                int f = tablaVisual.getSelectedRow(); if (f != -1) { tablaVisual.setValueAt(txtCli.getText(), f, 1); tablaVisual.setValueAt(txtFec.getText(), f, 2); tablaVisual.setValueAt(totalStr, f, 3); tablaVisual.setValueAt(cmbEst.getSelectedItem().toString(), f, 4); }
            } catch (Exception ex) {}
        });
        vEdit.add(btn); vEdit.setVisible(true);
    }

    private void EditarDistribucion()
    {
        String idLimpio = idFilaActual.replace("#", "");
        JFrame vMadre = (JFrame) SwingUtilities.getWindowAncestor(panel);
        JDialog vEdit = new JDialog(vMadre, "Editar Ruta", true);
        vEdit.setSize(400, 350); vEdit.setLayout(null); vEdit.setLocationRelativeTo(panel);

        JTextField txtPed = new JTextField(); txtPed.setBounds(140, 30, 200, 30);
        JTextField txtDes = new JTextField(); txtDes.setBounds(140, 80, 200, 30);
        JTextField txtTra = new JTextField(); txtTra.setBounds(140, 130, 200, 30);
        JComboBox<String> cmbEst = new JComboBox<>(new String[]{"Pendiente", "En ruta", "Entregado"}); cmbEst.setBounds(140, 180, 200, 30);

        try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("SELECT * FROM Distribucion WHERE id=?"))
        {
            p.setString(1, idLimpio); ResultSet rs = p.executeQuery();
            if (rs.next()) { txtPed.setText(rs.getString("pedido")); txtDes.setText(rs.getString("destino")); txtTra.setText(rs.getString("transportista")); cmbEst.setSelectedItem(rs.getString("estado")); }
        } catch (Exception ex) {}

        vEdit.add(new JLabel("ID Pedido:")).setBounds(30, 30, 100, 30); vEdit.add(txtPed);
        vEdit.add(new JLabel("Destino:")).setBounds(30, 80, 100, 30); vEdit.add(txtDes);
        vEdit.add(new JLabel("Repartidor:")).setBounds(30, 130, 100, 30); vEdit.add(txtTra);
        vEdit.add(new JLabel("Estado:")).setBounds(30, 180, 100, 30); vEdit.add(cmbEst);

        JButton btn = new JButton("Actualizar"); btn.setBounds(140, 240, 100, 40);
        btn.addActionListener(e -> 
        {
            try (Connection c = ConexionBD.conectar(); PreparedStatement p = c.prepareStatement("UPDATE Distribucion SET pedido=?, destino=?, transportista=?, estado=? WHERE id=?"))
            {
                p.setString(1, txtPed.getText()); p.setString(2, txtDes.getText()); p.setString(3, txtTra.getText()); p.setString(4, cmbEst.getSelectedItem().toString()); p.setString(5, idLimpio);
                p.executeUpdate(); vEdit.dispose();
                int f = tablaVisual.getSelectedRow(); if (f != -1) { tablaVisual.setValueAt(txtPed.getText(), f, 1); tablaVisual.setValueAt(txtDes.getText(), f, 2); tablaVisual.setValueAt(txtTra.getText(), f, 3); tablaVisual.setValueAt(cmbEst.getSelectedItem().toString(), f, 4); }
            } catch (Exception ex) {}
        });
        vEdit.add(btn); vEdit.setVisible(true);
    }

    private String calcularEstadoInventario(int existencia, int minimo)
    {
        if (existencia == 0)
        {
            return "Agotado";
        }
        else if (existencia <= minimo)
        {
            return "Bajo";
        }
        else
        {
            return "Suficiente";
        }
    }
    
    @Override
    public Object getCellEditorValue()
    {
        return "";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.tablaVisual = table;
        this.idFilaActual = table.getValueAt(row, 0).toString(); 
        return panel;
    }
}