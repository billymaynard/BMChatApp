package org.project.bmchatapp;

import javafx.application.Platform;
import org.project.common.Message;

import java.io.*;
import java.net.Socket;

/**
 * La clase ChatClient gestiona la conexión y comunicación del cliente con el servidor de chat.
 */
public class ChatClient {
    private Socket socket;                          // Socket para la conexión con el servidor.
    private ObjectInputStream objectInputStream;    // Flujo de entrada para recibir mensajes.
    private ObjectOutputStream objectOutputStream;  // Flujo de salida para enviar mensajes.
    private Thread listenerThread;                  // Hilo para escuchar mensajes entrantes.
    private ChatroomController chatroomController;  // Controlador para la sala de chat.
    private String loggedUser;                      // Usuario conectado en el cliente.

    /**
     * Intenta conectar con el servidor de chat.
     *
     * @param hostname Nombre del host del servidor.
     * @param port Puerto del servidor.
     * @return true si la conexión es exitosa, false en caso contrario.
     */
    public boolean connect(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Establece el controlador de la sala de chat.
     *
     * @param controller El controlador de la sala de chat.
     */
    public void setChatroomController(ChatroomController controller) {
        this.chatroomController = controller;
    }

    /**
     * Envia un mensaje al servidor.
     *
     * @param message El mensaje a enviar.
     */
    public void send(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el flujo de entrada del objeto.
     *
     * @return El flujo de entrada del objeto.
     */
    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    /**
     * Inicia la escucha de mensajes entrantes del servidor.
     * Actualiza la interfaz de usuario con los mensajes recibidos.
     */
    public void listenForMessages() {
        listenerThread = new Thread(() -> {
            try {
                while (true) {
                    Message message = (Message) objectInputStream.readObject();
                    Platform.runLater(() -> {
                        if (chatroomController != null) {
                            chatroomController.updateChatArea(message);
                        }
                    });
                }
            } catch (IOException | ClassNotFoundException e) {
                if (!"Socket closed".equals(e.getMessage())) {
                    e.printStackTrace();
                }
            }
        });
        listenerThread.start();
    }

    /**
     * Cierra todas las conexiones y recursos.
     */
    public void close() {
        try {
            if (listenerThread != null) listenerThread.interrupt();
            if (objectInputStream != null) objectInputStream.close();
            if (objectOutputStream != null) objectOutputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía una solicitud de inicio de sesión al servidor.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void login(String username, String password) {
        send(new Message("System", "#LOGIN " + username + " " + password));
    }

    /**
     * Envía una solicitud de registro al servidor.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void register(String username, String password) {
        send(new Message("System", "#REGISTER " + username + " " + password));
    }

    /**
     * Obtiene el usuario que ha iniciado sesión en el cliente.
     *
     * @return Nombre del usuario conectado.
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * Establece el usuario que ha iniciado sesión en el cliente.
     *
     * @param loggedUser Nombre del usuario conectado.
     */
    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
