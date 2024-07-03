import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JButton button1;


    public static void main(String[] args) {
        // Crear el frame (ventana)
        JFrame frame = new JFrame("VaMiSeMa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Crear los botones
        JButton btnVictimas = new JButton("Victimas");
        JButton btnCifrado = new JButton("Cifrado");
        JButton btnGPT = new JButton("GPT");

        // Añadir ActionListener al botón btnVictimas
        btnVictimas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el formulario UserForm cuando se hace clic en el botón
                UserForm userForm = new UserForm();
                userForm.setVisible(true);
            }
        });
        // Añadir ActionListener al botón btnCifrado
        btnCifrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el formulario FileEncryptionApp cuando se hace clic en el botón
                FileEncryptionApp fileEncryptionApp = new FileEncryptionApp();
                fileEncryptionApp.setVisible(true);
            }
        });

        // Añadir ActionListener al botón btnGPT
        btnGPT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar el formulario FormGpt cuando se hace clic en el botón
                FormGpt formGpt = new FormGpt();
                formGpt.setVisible(true);
            }
        });

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Hacer transparente el fondo del panel
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnVictimas);
        buttonPanel.add(btnCifrado);
        buttonPanel.add(btnGPT);

        // Cargar la imagen de fondo
        ImageIcon backgroundImage = new ImageIcon("fondo.gif");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        // Crear un panel para la imagen de fondo y establecer el layout
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new FlowLayout());

        // Agregar el panel de botones al label de fondo
        backgroundLabel.add(buttonPanel);

        // Agregar el panel de fondo al frame
        frame.add(backgroundPanel);

        // Hacer visible la ventana
        frame.setVisible(true);
    }
}