![Record Store API Banner.png](src%2Fmain%2Fresources%2Fstatic%2FRecord%20Store%20API%20Banner.png)

# The Brief

The Kettle Record store is in dire need of digitization; the current inventory system is
not cutting it in the 21st century. The store has asked for a robust, well-tested back-end API that allows them
to manage their large inventory of records. This should leverage the power of Spring Boot to create
RESTful endpoints.

# API

## Accessing the API

The API can be accessed at base URL:<br>```http://localhost:8080/api/v1/albums```

## Endpoints

### GET ```/albums```

Returns a list of all albums

```json
[
    {
        "id": 1,
        "artist": "Jon Hopkins",
        "genre": "ELECTRONIC",
        "name": "Immunity",
        "releaseYear": 2013,
        "stockQuantity": 10
    },
    {
        "id": 2,
        "artist": "Nothing But Thieves",
        "genre": "ROCK",
        "name": "Moral Panic",
        "releaseYear": 2020,
        "stockQuantity": 5
    },
    {
        "id": 3,
        "artist": "Miles Davis",
        "genre": "JAZZ",
        "name": "Kind of Blue",
        "releaseYear": 1959,
        "stockQuantity": 0
    }
    ... etc.
]
```

### GET ```/albums/:id```

Returns a single album with given specified ID. Invalid ID will return an error and 404.

```json
{
    "id": 3,
    "artist": "Miles Davis",
    "genre": "JAZZ",
    "name": "Kind of Blue",
    "releaseYear": 1959,
    "stockQuantity": 0
}
```

```
Album with ID 10 not found.
```

### GET ```/albums/``` with queries

The GET endpoint accepts 4 query parameters. <br>

| Key    | Description                                  |
|--------|----------------------------------------------|
| name   | Returns all albums of specified name         |
| genre  | Returns all albums of specified genre        |
| artist | Returns all albums of specified artist       |
| year   | Returns all albums of specified release date |

This API handles one parameter request at a time.<br>

#### Permitted Genres
ROCK, POP, ELECTRONIC, JAZZ, METAL, ALTERNATIVE, CLASSICAL

### GET ```/albums/instock```

Returns a list of all albums with stock.

```json
[
    {
        "id": 1,
        "artist": "Jon Hopkins",
        "genre": "ELECTRONIC",
        "name": "Immunity",
        "releaseYear": 2013,
        "stockQuantity": 10
    },
    {
        "id": 2,
        "artist": "Nothing But Thieves",
        "genre": "ROCK",
        "name": "Moral Panic",
        "releaseYear": 2020,
        "stockQuantity": 5
    },
    {
        "id": 4,
        "artist": "Nothing But Thieves",
        "genre": "ROCK",
        "name": "Welcome to the DCC",
        "releaseYear": 2023,
        "stockQuantity": 6
    }
]
```

### POST ```/albums```

Allows addition of new albums to the inventory. 

```json
{
    "name": "Long Way Down",
    "artist": "Tom Odell",
    "releaseYear": 2013,
    "genre": "ALTERNATIVE",
    "stockQuantity": 4
}
```
returns response

```json
{
    "id": 5,
    "artist": "Tom Odell",
    "genre": "ALTERNATIVE",
    "name": "Long Way Down",
    "releaseYear": 2013,
    "stockQuantity": 4
}
```

### PUT ```/albums/:id```

Updates album of selected ID with album given as body

```json
{
  "id": 5,
  "name": "Black Friday",
  "artist": "Tom Odell",
  "releaseYear": 2024,
  "genre": "ALTERNATIVE",
  "stockQuantity": 14
}
```
returns response

```json
{
    "id": 5,
    "artist": "Tom Odell",
    "genre": "ALTERNATIVE",
    "name": "Black Friday",
    "releaseYear": 2024,
    "stockQuantity": 14
}
```
or returns an error if the ID does not exist
```
Album with ID 10 not found.
```

### DELETE ```/albums/:id```

Deletes album at the specified ID, and returns the deleted album.

```json
{
    "id": 3,
    "artist": "Miles Davis",
    "genre": "JAZZ",
    "name": "Kind of Blue",
    "releaseYear": 1959,
    "stockQuantity": 0
}
```
or returns an error if the album is not found

```
Album with ID 10 not found.
```




# Database Connections

## Using the API in dev mode

This API is configured to run using a H2 in-memory database connection by default. 
To utilise this, clone this repository and set ```spring.profiles.active=dev``` in the ```application.properties``` file.

## Connection to a PostgreSQL database

To persist data, a PostgreSQL database connection is recommended.<br>
Set ```spring.profiles.active=prod``` in the ```application.properties``` file. <br>
Connection information will need to be configured in a new ```application-prid.properties``` file as follows: <br>
```
spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DB_NAME>
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>
spring.jpa.hibernate.ddl-auto=< create | create-drop | validate | update >
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

# Future considerations
- [ ] CLI for terminal-based interaction with API
- [ ] Further error handling and messaging to handle edge cases
- [ ] Cloud deployment
- [ ] Integration with mobile front-end