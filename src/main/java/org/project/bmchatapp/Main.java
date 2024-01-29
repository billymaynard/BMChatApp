package org.project.bmchatapp;
import org.project.common.Message;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * La clase principal que inicia la aplicación de chat.
 */
public class Main extends Application {

    private ChatClient chatClient; // Cliente de chat para manejar la comunicación.

    /**
     * Inicia la interfaz de usuario de la aplicación.
     *
     * @param primaryStage El escenario principal para la interfaz de usuario.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga la vista de inicio de sesión/registro y la configura.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginRegisterView.fxml")); // Actualiza la ruta.
        Parent root = loader.load();

        // Configura el controlador para la vista de inicio de sesión/registro.
        LoginRegisterController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        this.chatClient = controller.getChatClient();

        // Configura y muestra el escenario principal.
        primaryStage.setTitle("Chat Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                handleClose(); // Maneja el cierre de la aplicación.
            }
        });
        primaryStage.show();
    }

    /**
     * Maneja el cierre de la aplicación.
     * Cierra las conexiones y recursos del cliente de chat y sale de la aplicación.
     */
    private void handleClose() {
        if (chatClient != null) {
            chatClient.send(new Message("System","#DISCONNECT")); // Envía mensaje de desconexión al servidor.
            chatClient.close(); // Cierra los recursos del cliente.
        }
        Platform.exit();
        System.exit(0); // Asegura que la JVM se cierre.
    }

    /**
     * Método principal de la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación.
    }
}
