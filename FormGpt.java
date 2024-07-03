import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormGpt extends JFrame {

    private JTextField inputField;
    private JButton searchButton;
    private JTextArea outputArea;
    private JButton promptButton;

    public FormGpt() {
        createUi();
    }

    private void createUi() {
        setTitle("FormGpt");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inputField = new JTextField(20);
        searchButton = new JButton("Buscar");
        promptButton = new JButton("Escriba su pregunta");
        outputArea = new JTextArea(10, 30);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(promptButton, BorderLayout.NORTH);
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(searchButton);
        topPanel.add(inputPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(panel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                String response = chatGpt(inputText);
                outputArea.setText(response);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormGpt formGpt = new FormGpt();
                formGpt.setVisible(true);
            }
        });
    }
    public static String chatGpt(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "sk-proj-dE4QOIe653ZInSiy8gJ9T3BlbkFJB8LDfysCBg9SWwtJ0DZm"; // API key goes here
        String model = "gpt-3.5-turbo"; // current model of chatgpt api

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11;
        int endMarker = response.indexOf("\"", startMarker);
        return response.substring(startMarker, endMarker);
    }
}