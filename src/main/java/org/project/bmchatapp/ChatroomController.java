package org.project.bmchatapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.project.common.Message;

/**
 * Controlador para la sala de chat en la interfaz de usuario de la aplicación de chat.
 */
public class ChatroomController {

    @FXML
    private ListView<Message> messageListView; // Vista de lista para mostrar los mensajes.

    @FXML
    private TextField inputField; // Campo de texto para escribir mensajes.

    private ChatClient chatClient; // Cliente de chat para la comunicación de red.

    /**
     * Inicializa el controlador con un cliente de chat.
     * Configura la vista de lista para mostrar los mensajes y escuchar mensajes entrantes.
     *
     * @param client El cliente de chat.
     */
    public void initializeWithClient(ChatClient client) {
        this.chatClient = client;
        this.chatClient.setChatroomController(this);
        this.chatClient.listenForMessages();

        // Configura una fábrica de celdas personalizada para la ListView.
        messageListView.setCellFactory(listView -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10); // Contenedor para los elementos de la celda.
                    Label senderLabel;
                    // Determina si el remitente es el usuario actual o otro usuario.
                    if (item.getSender().equals(chatClient.getLoggedUser())){
                        senderLabel = new Label("You");
                    }else{
                        senderLabel = new Label(item.getSender());
                    }
                    senderLabel.setTextFill(Color.BLUE); // Color del texto del remitente.
                    senderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12)); // Fuente del texto del remitente.

                    Text messageText = new Text(item.getContent()); // Texto del mensaje.
                    hbox.getChildren().addAll(senderLabel, messageText); // Añade elementos al contenedor.
                    setGraphic(hbox); // Establece el contenedor como gráfico de la celda.
                }
            }
        });
    }

    /**
     * Actualiza el área de chat agregando un nuevo mensaje.
     *
     * @param message El mensaje a agregar.
     */
    public void updateChatArea(Message message) {
        messageListView.getItems().add(message); // Agrega el mensaje a la vista de lista.
    }

    /**
     * Maneja el evento de enviar mensaje.
     * Envía el mensaje escrito en el campo de entrada a través del cliente de chat.
     */
    @FXML
    private void handleSendMessage() {
        String messageContent = inputField.getText(); // Obtiene el contenido del mensaje.
        inputField.clear(); // Limpia el campo de entrada.
        Message message = new Message(chatClient.getLoggedUser(), messageContent); // Crea un nuevo mensaje.
        chatClient.send(message); // Envía el mensaje a través del cliente de chat.
    }
}
