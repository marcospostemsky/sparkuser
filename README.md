## Spark API Items

Para poder consumir correctamente la API de Items, debera disponer de lo siguiente:

* Elastic Search 6.5.4 [Descargar](https://www.elastic.co/es/downloads/past-releases/elasticsearch-6-5-4).
* API Spark Sites [Git API Sites](https://github.com/marcospostemsky/sparksite).
* API Spark User [Git API Users](https://github.com/marcospostemsky/sparkuser).
* API Spark Item [Git API Items](https://github.com/ferrariagustin93/sparkItem).

# Caracteristicas de API Items

La API items se encuentra escuchando en el puerto :4567, y cualquier pedido que se haga al endpoint /sites/* requiere 
de un logeo previo.

Para logear el usuario, debe enviar un POST a http://localhost:4567/user, que contenga por headers lo siguiente:

* username : <"nombreUsuario">
* password : <"passUsuario">

Si el usuario y contraseña existen en la base de datos de Elastic, por body se devuelve un id y un token, estos deben
ser enviados por headers en los siguientes GETs para recibir respuesta de los siguientes endpoints:

* GET
    * /sites
    * /sites/:idsite/categories
    * /sites/:idsite/categories/:idCategory/:idItem
    


# Ejecutar Elastic y cargar usuarios

Para poder logear usuarios desde Items, primero debe almancenarlos en el indice "users" de tipo "user". El ES debe estar
escuchando en el puerto :9200.