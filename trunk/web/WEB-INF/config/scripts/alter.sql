alter table userroles ADD FOREIGN KEY user (usu_user) REFERENCES users (usu_user);
alter table userroles ADD FOREIGN KEY role (usr_role) REFERENCES roles (rol_role);