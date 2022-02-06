# Configurar la BBDD para el paquete "requerimiento1"

CREATE SCHEMA LISTACOCHES;<br />
USE LISTACOCHES;<br />

CREATE TABLE COCHES (<br />
  ID INT(11) NOT NULL AUTO_INCREMENT,<br />
  MATRICULA VARCHAR(7) NOT NULL,<br />
  MARCA VARCHAR(25) DEFAULT NULL,<br />
  MODELO VARCHAR(25) DEFAULT NULL,<br />
  COLOR VARCHAR(25) DEFAULT NULL,<br />
  PRIMARY KEY (ID)<br />
);<br />

# Configurar la BBDD para el paquete "requerimiento2"

CREATE SCHEMA CONCESIONARIO;<br />
USE CONCESIONARIO;<br />

CREATE TABLE COCHES (<br />
  ID INT(11) NOT NULL AUTO_INCREMENT,<br />
  MATRICULA VARCHAR(7) NOT NULL,<br />
  MARCA VARCHAR(25) DEFAULT NULL,<br />
  MODELO VARCHAR(25) DEFAULT NULL,<br />
  COLOR VARCHAR(25) DEFAULT NULL,<br />
  PRIMARY KEY (ID)<br />
);<br />

CREATE TABLE PASAJEROS (<br />
  ID INT(11) NOT NULL AUTO_INCREMENT,<br />
  NOMBRE VARCHAR(25) NOT NULL,<br />
  EDAD INT(2),<br />
  PESO INT(3),<br />
  PRIMARY KEY (ID),<br />
  COCHE INT(11) NOT NULL,<br />
  FOREIGN KEY (COCHE) REFERENCES COCHES(ID)<br />
);<br />