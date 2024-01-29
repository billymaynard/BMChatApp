# Aplicación de Chat - Cliente

## Descripción General
Este repositorio contiene el código del lado del cliente para una aplicación de chat. El cliente se conecta al servidor de chat, envía y recibe mensajes y proporciona una interfaz de usuario para la interacción.

## Componentes Clave
- `ChatClient`: Gestiona la conexión con el servidor, enviando y recibiendo mensajes.
- `ChatroomController`: Controla la interfaz de la sala de chat, mostrando mensajes y manejando la entrada del usuario.
- `LoginRegisterController`: Gestiona los procesos de inicio de sesión y registro.
- `Main`: El punto de entrada de la aplicación del cliente, configurando la interfaz de usuario.
- `ServerDiscoveryClient`: Descubre la dirección del servidor en la red para la conexión.

## Dependencias Externas
- El cliente requiere la clase `Message` de una biblioteca externa para el manejo de mensajes. Asegúrate de incluir esta biblioteca en tu proyecto para que el cliente funcione correctamente.

## Configuración y Ejecución
- Compila y construye el proyecto con dependencias, incluyendo la biblioteca `Message`.
- Ejecuta `Main` para iniciar la aplicación del cliente.

## Notas
- Asegúrate de que el servidor esté en funcionamiento y sea detectable para que el cliente pueda conectarse.
- La biblioteca externa `Message` debe añadirse como libreria externa en tu proyecto.
