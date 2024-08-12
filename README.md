# Bookstore

Bookstore backend service demo project.
Currently just support movie related APIs. 
You may need to run it with bookstore-client. 

## Env Setup
Need JDK 22

 * build: ```./gradlew build```
 * test: ```./gradlew test```
 * run: ```./gradlew bootRun```

## Design Detail
### APIs
All APIs come from "MovieController":
 * HTTP GET /api/movies: Fetch movie list with average rating and pagination
 * HTTP GET /api/movies/{id}: Fetch one movie detail data
 * HTTP PUT /api/movies/{id}/rate: Submit movie rating

### Data Management
As this is a simple demo, so I use HashMaps for data persistence.
But they can be replaced with real database if needed.
 * MovieRepository: save the movies detail data, use movie id as key.
 * MovieRateRepository: save the movies rating data, use movie id as key.

### Security
This demo project used no authentications or access check.