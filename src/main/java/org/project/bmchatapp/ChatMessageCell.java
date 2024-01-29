package org.project.bmchatapp;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.project.common.Message;

/**
 * La clase ChatMessageCell extiende de ListCell para personalizar la visualización de los mensajes en la lista.
 */
public class ChatMessageCell extends ListCell<Message> {

    /**
     * Actualiza la celda de la lista con la información del mensaje.
     *
     * @param message El mensaje a mostrar en la celda.
     * @param empty Indica si la celda está vacía (sin mensaje).
     */
    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        if (empty || message == null) {
            // Si la celda está vacía o el mensaje es nulo, no muestra nada.
            setGraphic(null);
            setText(null);
        } else {
            // Crea un nuevo contenedor para los elementos gráficos del mensaje.
            HBox hbox = new HBox(10); // Contenedor con espacio horizontal de 10.

            // Crea y configura la etiqueta para el remitente del mensaje.
            Label senderLabel = new Label(message.getSender());
            senderLabel.setTextFill(Color.BLUE); // Establece el color del texto.
            senderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12)); // Establece la fuente del texto.

            // Crea el texto para el contenido del mensaje.
            Text messageText = new Text(message.getContent());

            // Añade la etiqueta del remitente y el texto del mensaje al contenedor.
            hbox.getChildren().addAll(senderLabel, messageText);

            // Establece el contenedor como el gráfico de la celda.
            setGraphic(hbox);
        }
    }
}
