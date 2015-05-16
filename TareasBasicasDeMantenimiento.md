!!Tareas básicas de mantenimiento!!

CreacionDeUnUsuarioDeSvn
AccesoADevDialslNet
TimeOutDeApache

'''Exportar una base de datos de MySQL'''

`mysqldump -u icentros_fantito -p -h 127.0.0.1 -P 3309 icentros_elviraCAT | gzip > icentros_elviraCAT.sql.gz`
`mysqldump -u root -p -P 3307 -h 127.0.0.1 codexpro | gzip > /tmp/codexpro.sql.gz`

con el puerto a 3007 si está en el MySQL de Codex.pro, a 3008 si está en el MySQL Aurora y 3309 si está en el MySQL Abismo.


'''Mirar los logs de Nerea+ y el resto de aplicaciones Java'''

`less /var/log/java/nerea.log`

(el resto de los de las aplicaciones Java están en el mismo directorio).


'''Mirar logs de Apache para ver si hay alguien usando la aplicación'''
@@@@
sudo su -
tail -f /var/log/apache2/codex.pro-access\_log


'''Actualizar Nerea+ u otra aplicación Java'''
@@@@
sudo su - javauser
cd nereaplus
svn update
ant clean dist
ant deploy


'''Reiniciar Tomcat'''
@@@@
sudo su -
/etc/init.d/tomcat-codexpro stop
/etc/init.d/tomcat-codexpro start

(lo mismo con ''tomcat-expressio'' o ''tomcat-centrosnet'')


'''Reiniciar Tomcat borrando temporales'''
@@@@
sudo su -
/etc/init.d/tomcat-codexpro fullstop
/etc/init.d/tomcat-codexpro start


'''Reiniciar todos los MySQL'''

`/etc/init.d/mysqld_multi.server restart`

o bien
@@@@
/etc/init.d/mysqld\_multi.server stop
/etc/init.d/mysqld\_multi.server start

comprobando que ha parado bien (ps -e u |grep mysql) antes de arrancarlo.


'''Reiniciar un MySQL'''
El parámetro N indica la instancia de MySQL que se usa (1=codexpro, 2=aurora, 3=abismo).

`/etc/init.d/mysqld_multi.server restart N`

o bien
@@@@
/etc/init.d/mysqld\_multi.server stop N
/etc/init.d/mysqld\_multi.server start N

comprobando que ha parado bien (ps -e u |grep mysql) antes de arrancarlo.


'''Ver la ocupación del disco duro'''

`df -h`

Si la partición de ''var'' se llena habrá que [| borrar binlogs de MySQL](http://dev.mysql.com/doc/refman/5.1/en/purge-binary-logs.html).


'''Reiniciar el servidor'''
Solo en caso de emergencia total
@@@@
sudo su -
reboot

'''Revisar Slow Query Log'''
@@@@
less /var/lib/mysql1/slow.log