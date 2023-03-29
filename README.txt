# AlohaAndes

El presente documento tiene como objetivo explicar los pasos a seguir para instalar la aplicacion y algunas intrucciones basicas para el correcto uso de esta.

INSTALACION DE LA APLICACION
1. Dentro de la seccion donde se encuentra el codigo del repositorio seleccionar la opcion de 'code' y luego dar clic en 'Download ZIP'.
2. Descomprimir el archivo.
3. Abrir el archivo denominado 'AlohaAndes' en el IDE de preferencia (se recomienda importarlo a un espacio de trabajo en Eclipse).
4. Dirigirse a la carpeta 'src/main/java/uniandes/isis2304/parranderos/interfazApp/'.
5. Ejecutar el codigo que se encuentra en el archivo 'InterfazParranderosApp.java'.
6. Finalmente, tras unos segundos deberia aparecer la interfaz de la aplicacion. En caso de que esto no suceda se recomienda verificar que se este 
usando la version 11 del JDK provisto por Oracle.

INSTRUCCIONES DE USO
1. En caso de que se desee utilizar una cuenta propia de Oracle para SQL developer se recomienda dirigirse al archivo 'persistence.xml' que se encuentra 
ubicado en la ruta 'src/main/resources/META-INF/'. Posteriomente, se deben modificar las valores de las propiedades 'javax.jdo.option.ConnectionUserName' y
'javax.jdo.option.ConnectionPassword'para que concuerden con las nuevas credenciales.
2. Para agregar el esquema de la base de datos, se debe abrir una conexion con SQL developer con las credenciales dadas y luego ejecutar el archivo denominado
'Esquema - iteracion2.sql' que se encuentra en la carpeta 'Docs' del proyecto.
3. Para eliminar la informacion de las tablas y/o el esquema se puede hacer el procedimiento descrito en el numeral 2 pero con las sentencias del archivo
'LimpiezaAlohaAndes.sql' ubicado en la misma carpeta anteriormente mencionada.
4. Para realizar, ya sea una insercción de un nuevo elemento o para obtener el resultado de una consulta, primero se debe iniciar sesion. Esto se hace dentro
de la interfaz dando click en 'Inicio de sesion' y luego en iniciar sesion. Este procedimiento solo se tendra que volver a realizar en caso de que se cierre
la ventana correspondiente a la interfaz.
5. Para obtener los resultados esperados, es necesario suministrar toda la informacion requrida explicitamente por la aplicacion.
6. Para crear un nuevo alojamiento es necesario que ya se haya creado el operador a cargo de este.
7. Para crear una reserva es necesario ya haber creado previamente tanto el alojamiento como el cliente.
8. Para relacionar un servicio con un alojamiento es indispensable que estos ya hayan sido creados.
9. Para los diferentes tipos de alojamiento (Apartamento, hostal, etc) es necesario asociarlo con una tupla de alojamiento ya existente por medio de su ID. 
Esto mismo tambien aplica para los tipos de cliente (miembros activos y miembros secundarios) y para los tipos de operadores de alojamientos (propieatorios
y empresas).
