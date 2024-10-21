import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class Main extends JFrame {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static SesionDAO sesionDAO = new SesionDAO();

    private JTextArea textArea;
    private JButton insertarButton, mostrarUsuariosButton, mostrarSesionesButton, salirButton;

    public Main() {
        setTitle("Gestión de Usuarios y Sesiones");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(textArea);

        insertarButton = new JButton("Insertar Usuario y Sesión");
        mostrarUsuariosButton = new JButton("Mostrar Usuarios");
        mostrarSesionesButton = new JButton("Mostrar Sesiones");
        salirButton = new JButton("Salir");

        insertarButton.addActionListener(e -> insertarUsuarioYSesion());
        mostrarUsuariosButton.addActionListener(e -> mostrarUsuarios());
        mostrarSesionesButton.addActionListener(e -> mostrarSesiones());
        salirButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(insertarButton);
        buttonPanel.add(mostrarUsuariosButton);
        buttonPanel.add(mostrarSesionesButton);
        buttonPanel.add(salirButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void insertarUsuarioYSesion() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del usuario:");
        String correo = JOptionPane.showInputDialog(this, "Ingrese el correo del usuario:");

        if (nombre != null && correo != null) {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);

            Sesion sesion = new Sesion();
            sesion.setFechaInicioSesion(LocalDateTime.now());
            sesion.setUsuario(usuario);

            usuario.getSesiones().add(sesion);

            usuarioDAO.insertarUsuario(usuario);
            sesionDAO.insertarSesion(sesion);

            textArea.append("Usuario y sesión insertados exitosamente.\n");
        } else {
            textArea.append("Operación cancelada.\n");
        }
    }

    private void mostrarUsuarios() {
        textArea.setText("");
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            textArea.append("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Correo: " + usuario.getCorreo() + "\n");
        }
    }

    private void mostrarSesiones() {
        textArea.setText("");
        List<Sesion> sesiones = sesionDAO.obtenerSesiones();
        for (Sesion sesion : sesiones) {
            textArea.append("ID Sesión: " + sesion.getId() + ", ID Usuario: " + sesion.getUsuario().getId() + ", Fecha Inicio: " + sesion.getFechaInicioSesion() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
