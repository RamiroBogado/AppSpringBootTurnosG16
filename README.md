# Proyecto de TURNOS grupo16 para la materia Programación Orientada a Objetos 2 de la UNLA.

Pasos a seguir para levantar el proyecto:
 * Versión de Java 17 o superior.
 * Maven 3 o superior.
 * Plugin de Lombok configurado en su IDE.
 * MySQL como Base de Datos.
 * Crear una Base de Datos con la siguiente instrucción **create database nombre;** 
 * Abrir el proyecto y revisar que se descarguen las dependencias, en caso de que no abrir una terminal en la raíz del proyecto y ejecutar esta instrucción: **mvn clean install**
 * Configurar las variables de entorno para que el archivo application.properties las reconozca antes de iniciar la aplicación:
   * DB_URL -> colocar la url de la base de datos.
   * DB_USERNAME -> colocar tu usuario de la base de datos.
   * DB_PASSWORD -> colocar tu password de la base de datos.
   * EMAIL_SENDER -> colocar un mail desde el cual se van a enviar los mails de la app
   * EMAIL_PASSWORD -> colocar la contraseña de dicho mail (Tiene que ser una configurada para app externa, no sirve con la que se inicia sesión normalmente)
 * Ejecutar el proyecto. Si todo esta correcto aparece que la aplicación inicio en x segundos en el puerto 8080.
 * Abrir el navegador e ir a la siguiente url: **http://localhost:8080/**
 * En la carpeta de configurations/seeder una vez creadas las tablas de la bd se ejecuta la clase UsersSeeder que inicializa la bd con un usuario:
   * **ADMIN: username: admin@gmail.com password: foo1234.**(va poder controlar el panel de admin), pero para el funcionamiento de la app se va a tener que registrar, luego iniciar la sesión con el usuario registrado 
