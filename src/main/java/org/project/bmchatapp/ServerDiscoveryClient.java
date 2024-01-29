package org.project.bmchatapp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * La clase ServerDiscoveryClient se utiliza para descubrir la dirección del servidor en la red.
 */
public class ServerDiscoveryClient {
    public static final int DISCOVERY_PORT = 8888; // Puerto utilizado para el proceso de descubrimiento.

    /**
     * Descubre la dirección del servidor enviando una solicitud de descubrimiento y esperando una respuesta.
     *
     * @return La dirección IP del servidor si es descubierto, de lo contrario null.
     */
    public String discoverServerAddress() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true); // Habilita la transmisión de paquetes de difusión.
            byte[] sendData = "DISCOVER_SERVER_REQUEST".getBytes(); // Datos de la solicitud de descubrimiento.
            // Crea y envía un paquete de solicitud de descubrimiento a la dirección de difusión.
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), DISCOVERY_PORT);
            socket.send(sendPacket);

            // Crea un paquete para recibir la respuesta del servidor.
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(receivePacket); // Recibe la respuesta del servidor.
            // Devuelve la dirección IP del servidor basada en la respuesta recibida.
            return receivePacket.getAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorna null si ocurre un error.
        }
    }
}
