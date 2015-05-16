Para permitir el acceso de un usuario al entorno dev.dialsl.net:

`htpasswd2 /var/www/dev.dialsl.net/passwords/passwords usuario`

También hay que añadir el usuario donde pone ''Require user'' en el archivo

`/etc/apache2/vhosts.d/dev.dialsl.net.conf`

Y finalmente reiniciar Apache

`/etc/init.d/apache2 graceful`