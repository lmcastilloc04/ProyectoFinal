import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileEncryptionApp extends JFrame {
    private JTextField filePathField; // Campo de texto para mostrar la ruta del archivo seleccionado
    private JRadioButton encryptRadioButton; // Botón de opción para seleccionar la operación de encriptado
    private JRadioButton decryptRadioButton; // Botón de opción para seleccionar la operación de desencriptado

    public FileEncryptionApp() {
        setTitle("File Encryption App"); // Establecer el título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        setSize(400, 200); // Establecer el tamaño de la ventana
        setLayout(new BorderLayout()); // Establecer el diseño de la ventana

        // Panel de selección de archivo
        JPanel filePanel = new JPanel(new GridLayout(2, 1)); // Crear un panel con diseño de cuadrícula de 2x1
        JLabel instructionLabel = new JLabel("Seleccione la ruta del archivo:"); // Etiqueta de instrucción
        filePanel.add(instructionLabel); // Añadir la etiqueta al panel

        filePathField = new JTextField(); // Campo de texto para la ruta del archivo
        filePanel.add(filePathField); // Añadir el campo de texto al panel

        JButton browseButton = new JButton("Browse"); // Botón para explorar archivos
        browseButton.addActionListener(new BrowseButtonListener(filePathField)); // Añadir el escuchador de eventos al botón
        filePanel.add(browseButton); // Añadir el botón al panel

        add(filePanel, BorderLayout.NORTH); // Añadir el panel al norte del diseño de la ventana

        // Panel de opciones de encriptado/desencriptado
        JPanel optionsPanel = new JPanel(new GridLayout(2, 1)); // Crear un panel con diseño de cuadrícula de 2x1

        encryptRadioButton = new JRadioButton("Encriptar"); // Crear el botón de opción para encriptar
        decryptRadioButton = new JRadioButton("Desencriptar"); // Crear el botón de opción para desencriptar
        ButtonGroup group = new ButtonGroup(); // Crear un grupo de botones
        group.add(encryptRadioButton); // Añadir el botón de encriptar al grupo
        group.add(decryptRadioButton); // Añadir el botón de desencriptar al grupo

        optionsPanel.add(encryptRadioButton); // Añadir el botón de encriptar al panel de opciones
        optionsPanel.add(decryptRadioButton); // Añadir el botón de desencriptar al panel de opciones

        add(optionsPanel, BorderLayout.CENTER); // Añadir el panel de opciones al centro del diseño de la ventana

        // Panel de botones de acción
        JPanel actionPanel = new JPanel(); // Crear un panel para los botones de acción
        JButton processButton = new JButton("Procesar"); // Crear el botón de procesar
        processButton.addActionListener(new ProcessButtonListener()); // Añadir el escuchador de eventos al botón
        actionPanel.add(processButton); // Añadir el botón al panel de acción

        add(actionPanel, BorderLayout.SOUTH); // Añadir el panel de acción al sur del diseño de la ventana

        setVisible(true); // Hacer visible la ventana
    }

    // Clase interna para manejar la acción del botón de explorar archivos
    private class BrowseButtonListener implements ActionListener {
        private JTextField textField; // Campo de texto donde se mostrará la ruta del archivo seleccionado

        public BrowseButtonListener(JTextField textField) {
            this.textField = textField; // Inicializar el campo de texto
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(); // Crear un selector de archivos
            int result = fileChooser.showOpenDialog(FileEncryptionApp.this); // Mostrar el diálogo de selección de archivos
            if (result == JFileChooser.APPROVE_OPTION) { // Si se selecciona un archivo
                File selectedFile = fileChooser.getSelectedFile(); // Obtener el archivo seleccionado
                textField.setText(selectedFile.getAbsolutePath()); // Establecer la ruta del archivo en el campo de texto
            }
        }
    }

    // Clase interna para manejar la acción del botón de procesar
    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText(); // Obtener la ruta del archivo desde el campo de texto
            if (filePath.isEmpty()) { // Si no se ha seleccionado un archivo
                JOptionPane.showMessageDialog(FileEncryptionApp.this, "Por favor seleccione un archivo primero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!encryptRadioButton.isSelected() && !decryptRadioButton.isSelected()) { // Si no se ha seleccionado una opción
                JOptionPane.showMessageDialog(FileEncryptionApp.this, "Por favor seleccione una opción: Encriptar o Desencriptar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                File inputFile = new File(filePath); // Crear un objeto File con la ruta del archivo
                SecretKey secretKey = null;

                if (encryptRadioButton.isSelected()) { // Si se selecciona la opción de encriptar
                    secretKey = Cifrador.generateSecretKey(); // Generar una clave secreta
                    Cifrador.saveSecretKey(secretKey, "secret.key"); // Guardar la clave secreta en un archivo
                    File encryptedFile = new File("archivo_cifrado.txt"); // Crear un archivo para el resultado encriptado
                    Cifrador.encryptFile(secretKey, inputFile, encryptedFile); // Encriptar el archivo
                    JOptionPane.showMessageDialog(FileEncryptionApp.this, "Archivo encriptado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else if (decryptRadioButton.isSelected()) { // Si se selecciona la opción de desencriptar
                    secretKey = Cifrador.loadSecretKey("secret.key"); // Cargar la clave secreta desde un archivo
                    File decryptedFile = new File("archivo_descifrado.txt"); // Crear un archivo para el resultado desencriptado
                    Cifrador.decryptFile(secretKey, inputFile, decryptedFile); // Desencriptar el archivo
                    JOptionPane.showMessageDialog(FileEncryptionApp.this, "Archivo desencriptado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) { // Manejo de excepciones
                ex.printStackTrace();
                JOptionPane.showMessageDialog(FileEncryptionApp.this, "Error al procesar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileEncryptionApp(); // Crear y mostrar la ventana de la aplicación
            }
        });
    }
}