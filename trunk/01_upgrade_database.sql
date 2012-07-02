-- Actualización de la base de datos. Versión 01 --

-- Modificación de la tabla sections y de la tabla newspieces --

alter table newspieces
drop foreign key newspieces_ibfk_3;

drop index section on newspieces;

-- se añade un nuevo campo para contener la clave de la section --
alter table newspieces
add column npi_section_id int(11) after npi_section;

alter table sections
drop PRIMARY KEY;

-- se añade un nuevo campo autoincrementado que contendrá la clave primaria de la section --
alter table sections
add column sec_incr INT not null AUTO_INCREMENT PRIMARY KEY FIRST ;

-- se actualiza el nuevo campo npi_section_id en newspieces con el valor de la nueva clave primaria de la section correspondiente --
update newspieces set npi_section_id = (SELECT sec_incr FROM sections where sec_id = npi_section);

-- se eliminan los campos que contenían las claves en formato texto en sections y newspieces
alter table sections drop column sec_id;
alter table newspieces drop column npi_section;

-- se cambia el nombre del campo sec_incr --
alter table sections drop primary key;
alter table sections change column sec_incr sec_id INT not null ;

-- se define la foreign key para sec_id en newspieces
alter table newspieces add CONSTRAINT FOREIGN KEY (npi_section_id) REFERENCES sections (sec_id)