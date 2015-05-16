!Actualización inmediata!
Para actualizar inmediatamente (en caliente) hay que conectarse al servidor ''lunanueva'' y teclear lo siguiente, suponiendo que hay que realizar cambios en la base de datos y están en un archivo ''/tmp/cambios.sql''.

@@@@
sudo su - javauser
cd codexpro
svn update
ant clean dist
mysql -u root -P 3307 -h 127.0.0.1 codexpro -p < /tmp/cambios.sql
ant deploy

Cuando hacemos una actualización inmediata se caen todas las sesiones de usuario que estén abiertas y se interrumpen los procesos en curso (como podría ser un pago on-line), por lo que es poco recomendable realizar este tipo de actualización.

!Actualización nocturna!
Para actualizar a las 4 de la mañana hay que conectarse al servidor ''lunanueva'' y teclear lo siguiente:

@@@@
sudo su - javauser
cd codexpro
svn update
ant clean dist
at -f deploy.txt 04:00


Para consultar el trabajo programado usar `atq` y para eliminar un trabajo programado `atrm N` donde N es el número del trabajo (que se ve con atq).

En caso de que en una actualización nocturna haya que aplicar cambios en la base de datos, editar el archivo deploy.txt (hay un ejemplo comentado).

!Notas!
  * Cuando hay cambios en la base de datos es conveniente hacer una copia justo antes de aplicarlos, por si acaso.
  * Hay que actualizar también la demo, que se encuentra en el directorio codexproDemo. Es bueno actualizarla antes y así nos aseguramos de que todo funciona correctamente.