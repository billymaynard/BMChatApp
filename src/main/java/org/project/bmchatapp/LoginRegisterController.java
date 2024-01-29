package org.project.bmchatapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.project.common.Message;

import java.io.IOException;

/**
 * Controlador para las interfaces de inicio de sesión y registro en la aplicación de chat.
 */
public class LoginRegisterController {

    private Stage primaryStage; // Escenario principal de la aplicación.
    private ChatClient chatClient = new ChatClient(); // Cliente de chat para la comunicación con el servidor.

    @FXML
    private TextField usernameField; // Campo para ingresar el nombre de usuario.

    @FXML
    private PasswordField passwordField; // Campo para ingresar la contraseña.

    /**
     * Espera y recibe una confirmación del servidor después de una solicitud de inicio de sesión o registro.
     *
     * @return El contenido del mensaje de respuesta.
     * @throws IOException Si ocurre un error de E/S.
     * @throws ClassNotFoundException Si la clase del objeto deserializado no se encuentra.
     */
    private String waitForConfirmation() throws IOException, ClassNotFoundException {
        Message responseMessage = (Message) chatClient.getObjectInputStream().readObject();
        return responseMessage.getContent();
    }

    /**
     * Maneja el evento de inicio de sesión.
     * Conecta con el servidor, envía las credenciales y maneja la respuesta.
     */
    @FXML
    private void handleLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            ServerDiscoveryClient discoveryClient = new ServerDiscoveryClient();
            String serverAddress = discoveryClient.discoverServerAddress();

            // Intenta conectar con el servidor y autenticar al usuario.
            if (serverAddress != null && chatClient.connect(serverAddress, 49444)) {
                chatClient.login(username, password);
                String response = waitForConfirmation();
                if ("Login successful".equals(response)) {
                    chatClient.setLoggedUser(username);
                    switchToChatroom();
                } else {
                    Platform.runLater(() -> showAlert("Login Error", "Incorrect username or password."));
                }
            } else {
                Platform.runLater(() -> showAlert("Connection Error", "Unable to find or connect to server."));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de registro.
     * Conecta con el servidor, envía las credenciales de registro y maneja la respuesta.
     */
    @FXML
    private void handleRegister() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            ServerDiscoveryClient discoveryClient = new ServerDiscoveryClient();
            String serverAddress = discoveryClient.discoverServerAddress();

            if (serverAddress != null && chatClient.connect(serverAddress, 49444)) {
                chatClient.register(username, password);
                String response = waitForConfirmation();
                if ("Registration successful".equals(response)) {
                    chatClient.setLoggedUser(username);
                    switchToChatroom();
                } else {
                    Platform.runLater(() -> showAlert("Registration Error", "Username already exists or invalid."));
                }
            } else {
                Platform.runLater(() -> showAlert("Connection Error", "Unable to connect to server."));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el cliente de chat asociado.
     *
     * @return El cliente de chat.
     */
    public ChatClient getChatClient() {
        return chatClient;
    }

    /**
     * Muestra una alerta en la interfaz de usuario.
     *
     * @param title Título de la alerta.
     * @param message Mensaje de la alerta.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Cambia la escena actual a la sala de chat.
     *
     * @throws IOException Si ocurre un error al cargar la vista de la sala de chat.
     */
    private void switchToChatroom() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatroomView.fxml")); // Actualiza la ruta.
        primaryStage.setScene(new Scene(loader.load()));
        ChatroomController chatroomController = loader.getController();
        chatroomController.initializeWithClient(chatClient);
        primaryStage.show();
    }

    /**
     * Establece el escenario principal para el controlador.
     *
     * @param stage El escenario principal.
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
}
