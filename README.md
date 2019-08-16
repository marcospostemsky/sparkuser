## Spark API Items

Para poder consumir correctamente la API de Items, debera disponer de lo siguiente:

* Elastic Search (ES) 6.5.4 [Descargar](https://www.elastic.co/es/downloads/past-releases/elasticsearch-6-5-4): escucha 
en el puerto :9200.
* API Spark Sites [Git API Sites](https://github.com/marcospostemsky/sparksite): escucha 
                                                                                en el puerto :8084.
* API Spark User [Git API Users](https://github.com/marcospostemsky/sparkuser): escucha 
                                                                                en el puerto :8086.
* API Spark Item [Git API Items](https://github.com/ferrariagustin93/sparkItem): escucha 
                                                                                 en el puerto :4567.

# Caracteristicas de API Items

La API items se encuentra escuchando en el puerto :4567, y cualquier pedido que se haga al endpoint /sites/* requiere 
de un logeo previo.

Para logear el usuario, debe enviar un POST a http://localhost:4567/users, que contenga por headers lo siguiente:


->
| Key| Value|
| ----- | ---- |
| username |"nombreUsuario" |
| password| "passwordUsuario" | 
<-


* username : <"nombreUsuario">
* password : <"passUsuario">

Si el usuario y contraseña existen en la base de datos de Elastic, por body se devuelve un id y un token, estos deben
ser enviados por headers en los siguientes GETs para recibir respuesta de los siguientes endpoints:

| Key| Value|
| ----- | ---- |
| id |"bodyPOSTToken" |
| token| "bodyIDToken" |


* GET
    * /sites
    * /sites/:idsite/categories
    * /sites/:idsite/categories/:idCategory/:idItem
    


# Ejecutar Elastic y cargar usuarios

Para poder logear usuarios desde Items, primero debe almancenarlos en el indice "users" de tipo "user". El ES debe estar
escuchando en el puerto :9200.

Para almacenar nuevos usuarios, debe enviar un POST, de la siguiente manera.

* POST http://localhost:9200/users/user/_bulk

    * Header:
        * Content-Type: application/json
    
    * Body;
        
            ```json
            {"index":{}}
            {"username":"marcos","password":"1234"}
            {"index":{}}
            {"username":"agustin","password":"123456"}
            {"index":{}}
            {"username":"agustinF","password":"mercadolibre"}
            {"index":{}}
            {"username":"marcosP","password":"mercadolibre!"}
            ```