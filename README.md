## Dolar observado

Entrega la paridad para el dolar en el dia y país entregado, ej:

Double valor = dolar.para("01-01-2019", "cl");

El sistema utiliza otros servicios web o scrapea las páginas oficiales de los paises usando selenium, los paises soportados son:

Chile

El sistema ejecuta diariamente a primera hora local o en cuanto se publiquen los datos, los carga y luego se salta ese día / país.

En un día X se debe hacer:

Por cada país:

Descargar página con datos
Extraer html de la página
Poblar la paridad para ese día




