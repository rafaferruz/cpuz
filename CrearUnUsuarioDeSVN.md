Para crear un usuario con acceso a SVN, primero hay que crear un usuario normal del sistema. Para ello, ejecutar como administrador:

@@@@
useradd vanesa
mkdir /home/vanesa
chown vanesa:users /home/vanesa


Y asignarle una contraseña con

@@@@
passwd vanesa


Por último hay que añadirlo al grupo ''svnusers''

@@@@
usermod -G svnusers vanesa