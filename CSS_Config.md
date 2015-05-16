# Introducción #

En esta página se expone el árbol de directorios y ficheros donde se encuentran los ficheros de estilos CSS utilizados en la aplicación, tanto en los directorios de desarrollo como en el servidor de contenidos estáticos.

Los estilos CSS utilizados están soportados en su mayor parte por el framework YAML complementados por otros ficheros css propios de la aplicación.

# Directorios de desarrollo #

Los ficheros de estilos CSS se encuentran en la carpeta css que se encuentra bajo la carpeta WEB-INF. El contenido de la carpeta css es el siguiente:
  * yaml (carpeta): Contiene todos los ficheros originales del framework YAML.
    * core (carpeta): Ficeros css fundamentales del framework YAML. Estos ficheros no deberían modificarse y se deben usar tal cual. Las modificaciones a los valores contenidos en este fichero deberán realizarse en el fichero screen/basemod.css
  * screen (carpeta): Contiene dos ficheros procedentes del YAML que se utilizan para modificar contenidos de los fichero base.css y conten.css originales del framework. Cualquier modificación sobre los valores originales de los fichero YAML deberán realizarse en estos ficheros.
  * patches (carpeta): Utilizado para modificar valores originales del framework referentes a los patches a aplicar para el correcto funcionamiento de estilos en los diferentes navegadores.
  * cpuz\_menus.css : Clases css para uso en los menús de la aplicación.
  * cpuz\_styles.css : Clases css específicas de la aplicación CPUZ.
  * my\_layout.css : Este fichero contiene sentencias @import de los demás ficheros css de uso general o adaptados a CPUZ procedentes de YAML y que han sido modificados para obtener los resultados deseados a aplicar.