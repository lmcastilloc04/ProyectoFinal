import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserForm extends JFrame {

    private static final String URL = "jdbc:mysql://localhost:3306/usuarios";
    private static final String USER = "root";
    private static final String PASSWORD = "Caldas14";

    private JTextField idField, nameField, ipField, emailField, encryptionKeyField, decryptionKeyField, deleteIdField;
    private JCheckBox paymentStatusCheckBox;
    private JButton submitButton, deleteButton, showUsersButton;
    private JTextArea usersTextArea;

    public UserForm() {
        setTitle("Formulario de Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField(20);
        JLabel ipLabel = new JLabel("IP:");
        ipField = new JTextField(20);
        JLabel emailLabel = new JLabel("Correo Electrónico:");
        emailField = new JTextField(20);
        JLabel encryptionKeyLabel = new JLabel("Llave de Cifrado:");
        encryptionKeyField = new JTextField(20);
        JLabel decryptionKeyLabel = new JLabel("Llave de Descifrado:");
        decryptionKeyField = new JTextField(20);
        JLabel paymentStatusLabel = new JLabel("Estado de Pago:");
        paymentStatusCheckBox = new JCheckBox("Pagado");
        JLabel deleteIdLabel = new JLabel("ID a Eliminar:");
        deleteIdField = new JTextField(20);
        submitButton = new JButton("Enviar");
        deleteButton = new JButton("Eliminar");
        showUsersButton = new JButton("Mostrar Usuarios");
        usersTextArea = new JTextArea(10, 50);
        usersTextArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(ipLabel, gbc);
        gbc.gridx = 1;
        add(ipField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(encryptionKeyLabel, gbc);
        gbc.gridx = 1;
        add(encryptionKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(decryptionKeyLabel, gbc);
        gbc.gridx = 1;
        add(decryptionKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(paymentStatusLabel, gbc);
        gbc.gridx = 1;
        add(paymentStatusCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(deleteIdLabel, gbc);
        gbc.gridx = 1;
        add(deleteIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        add(showUsersButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        add(new JScrollPane(usersTextArea), gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        showUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUsers();
            }
        });
    }

    private void saveUser() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String ip = ipField.getText();
        String email = emailField.getText();
        String encryptionKey = encryptionKeyField.getText();
        String decryptionKey = decryptionKeyField.getText();
        boolean paymentStatus = paymentStatusCheckBox.isSelected();

        Usuario usuario = new Usuario(id, name, email, ip, encryptionKey, decryptionKey, paymentStatus);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO usuarios (id, nombre, correo_electronico, ip, llave_cifrado, llave_descifrado, estado_pago) VALUES (?, ?, ?, ?, ?, ?, ?)"
             )) {

            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setString(2, usuario.getNombre());
            preparedStatement.setString(3, usuario.getCorreoElectronico());
            preparedStatement.setString(4, usuario.getIp());
            preparedStatement.setString(5, usuario.getLlaveCifrado());
            preparedStatement.setString(6, usuario.getLlaveDescifrado());
            preparedStatement.setBoolean(7, usuario.isEstadoPago());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario guardado con éxito.");
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        int idToDelete = Integer.parseInt(deleteIdField.getText());

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM usuarios WHERE id = ?"
             )) {

            preparedStatement.setInt(1, idToDelete);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún usuario con ese ID.");
            }

            clearDeleteField();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el usuario: " + ex.getMessage());
        }
    }

    private void showUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuarios");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            usersTextArea.setText("");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nombre");
                String email = resultSet.getString("correo_electronico");
                String ip = resultSet.getString("ip");
                String encryptionKey = resultSet.getString("llave_cifrado");
                String decryptionKey = resultSet.getString("llave_descifrado");
                boolean paymentStatus = resultSet.getBoolean("estado_pago");

                Usuario usuario = new Usuario(id, name, email, ip, encryptionKey, decryptionKey, paymentStatus);

                String userInfo = String.format("ID: %d, Nombre: %s, Correo Electrónico: %s, IP: %s, Llave de Cifrado: %s, Llave de Descifrado: %s, Estado de Pago: %s%n",
                        usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(), usuario.getIp(), usuario.getLlaveCifrado(), usuario.getLlaveDescifrado(), usuario.isEstadoPago() ? "Pagado" : "No Pagado");

                usersTextArea.append(userInfo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los usuarios: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ipField.setText("");
        emailField.setText("");
        encryptionKeyField.setText("");
        decryptionKeyField.setText("");
        paymentStatusCheckBox.setSelected(false);
        deleteIdField.setText("");
    }

    private void clearDeleteField() {
        deleteIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserForm userForm = new UserForm();
                userForm.setVisible(true);
            }
        });
    }
}
