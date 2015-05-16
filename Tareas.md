# Tareas en curso #

  * **24/04/2012: Poner en marcha, de nuevo, el servidor Tomcat de la bodega para desplegar la aplicación CPUZ y hacer pruebas de despliegue, configuración de Tomcat, logs, prácticas de Linux, etc. A definir en próximas tareas.**
    * 29/04/2012: El servidor está de nuevo funcionando. Para poder desplegar la aplicación es necesario actualizar a Tomcat 7 y JDK 7. También debo actualizar la versión de Fedora a la más reciente.
    * 29/04/2012: Se hacen necesarias las siguientes fases:
      * **Actualizar Fedora Core a la versión 16 desde DVD de instalación.**
        * Se ha descargado la imagen ISO y se va a hacer un DVD desde el que instalar.
        * 06/05/2012: Debido a las características del hardware sobre el que está montado Fedora, no es posible instalar desde un DVD bootable con la versión integra de Fedora. Se ha descargado una ISO netinst de la versión 10 y se ha instalado Fedora 10 integramente. El siguiente paso es instalar desde una ISO netinst de Fedora 16.
        * 12/05/2012: La solución ha sido instalar Fedora 16 desde cero a partir de una ISO netinst en disco de 60 Gb. Se guardan en el disco original las bases de datos. Desde este punto se deben instalar el resto de aplicaciones y bases de datos.
        * 16/05/2012: Se ha puesto Mysql 5.5 en marcha en el equipo 'bodega' y se han cargado las bases de datos a partir de los ficheros backup.
        * 17/05/2012: Se ha puesto en marcha SSH en 'bodega' y ya se puede conectar desde portatil mediante putty.
      * **Instalar Netbeans 7.1 y JDK 7**
        * Se han descargado he instalado el JDK7 y se ha instalado pero hay que esperar a la actualización de Fedora para ver si esta actualización es correcta.
      * Actualizar a Tomcat 7
        * Esta descargada la versión 7 y pendiente de instalar
      * Actualizar a MySql 5.5

  * **23/04/2012. Comenzar a cambiar estilos css de tablas de listados de objetos de dominio.**
    * 24/04/2012: Se ha comenzado ha modificar las clases en los ficheros css base.css, basemod.css, content.css y cpuz\_styles.css, teniendo en este momento definidos los estilos de la página de inicio.
    * Se ha modificado ligeramente el estilo de las vistas jsp de listados de objetos de dominio. Hay que modificar el texto 'Listado de...' por 'Relación de ...' e internacionalizar dicho texto.

# Tareas pendientes #

  * **23/04/2012. Integrar los ficheros de texto con copia de seguridad de tablas de mysql.cpuz database en base de datos local.**
    * 24/04/2012. Esta tarea no podrá realizarse con los ficheros TXT que se disponen actualmente ya que no son de la aplicación CPUZ como se suponía.


  * 