# Backup de seguridad #

  * Para realizar la copia de seguridad de la base de datos:

`mysqldump - u root -p cpuz > cpuz_dump.sql`.

El comando solicitará la contraseña de la base de datos para el usuario 'root'. Se generará el fichero de salida 'cpuz\_dump.sql' en el directorio donde se encuentre situado el usuario que ejecuta el comando. Se puede indicar una ruta y nombre diferente para el fichero de salida.

El fichero de salida genera la copia siguiendo el orden alfabético de los nombres de las tablas. Al intentar recuperar la base de datos desde dicho volcado, se producen errores por fallo en los constraints de las claves externas ya que intenta eliminar filas en algunas tablas que no pueden eliminarse por tener datos otras tablas que hacen referencia a las claves que se quieren borrar.
MySql debe ofrecer alguna condición de ejecución del script de recuperación para que "pase" de constraints por foreign keys. Revisar.