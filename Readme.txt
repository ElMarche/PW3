Inicio del archivo Readme.

Utilizamos XAMPP para base de datos MYSQL, crear la base de datos figusdb y utilizar el archivo phpmyadminSQL_Dump_figusdb.sql para crear las tablas. 

Utilizamos SpringToolSuite4 para importar o abrir el proyecto (sts-4.14.0.RELEASE). Idealmente descargar todo dentro de una carpeta llamada Album.

Una vez el proyecto es visible en el Package Explorer:

Normalmente sobre la carpeta principal del proyecto hago clic derecho y en el desplegable: Maven > Update Project...
Eso debería descargar las dependencias que tiene el proyecto.

Siguiente paso, nuevamente clic derecho y en el desplegable: Build Project.
En este punto debería poder verse el proyecto en el Boot Dashboard.

Amtes de iniciarlo, vale aclarar algunas configuraciones en application.properties:

-Para la base de datos:

spring.datasource.url=jdbc:mysql://localhost:3306/figusdb?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=


Puerto 3306, se aprecia el nombre de la base de datos y establecida una timezone
username es root
No hay password

-Para la subida de imágenes:

#Habilitamos subida de archivos?
spring.servlet.multipart.enabled=true
#Directorio temporal para subir archivos (windows)
spring.servlet.multipart.location=c:/tmp
#Maximo tamanio de archivos que se pueden subir
spring.servlet.multipart.max-file-size= 2MB
albumapp.ruta.imagenes=D:/Users/Marcelo/Documents/workspace-spring-tool-suite-4-4.14.0.RELEASE/Album/src/main/resources/static/images/

Atención con albumapp.ruta.imagenes! Tiene una ruta que apunta a la carpeta images del proyecto, dentro del workspace en mi PC, por lo tanto la unidad (D:/) y la ruta deben variar necesariamente para apuntar correctamente en tu equipo (nótese que los archivos están todos dentro de una carpeta llamada Album). Esto es utilizado en la clase WebConfig.java dentro del paquete config.
Además hay un directorio temporal c:/tmp que se usa de intermediario y una configuración para el máximo tamaño de archivo de imagen.


Si no he olvidado algo, podemos iniciarlo con (Re)Start, o clic derecho y en el desplegable (Re)Start.

La base de datos tiene usuarios creados, hay 3 roles y de menor a mayor jerarquía son: Usuario, Supervisor, Administrador.
Como las passwords están encriptadas, para probar los 3 roles conviene registrar usuarios nuevos y cambiarles el rol desde phpmyadmin.

Tener presente que los usuarios se loguean con username y password (no con email).

Cualquier otra duda contactarme!
