# Gift Certificate System
This is a web service for managing gift certificates and tags, providing CRUD operations for gift certificates, 
CRUD operations for tags, and the ability to get certificates by tags.
The system is designed with a RESTful architecture, and is built using Java 17, Spring MVC, Spring JDBC template
and a PostgreSQL database.

[//]: # (## Database schema)

[//]: # ()
[//]: # (<img src="https://user-images.githubusercontent.com/96039201/237025446-f8dd8912-a760-445c-958e-86e362c13722.jpg">)

## Installation
1. Clone the project.
2. Create a new Postgresql database using data.sql from the repository/resources/sql folder.
3. Choose one between two configurations: dev and prod. 
4. Change the properties file, located in the repository/resources/ folder, based on your database configurations.
5. Build the project using maven.
6. Add new Tomcat 10.0.27 configuration to the project.
7. Run Tomcat and open http://localhost:8080/ on the browser.

## CRUD operations for gift certificate
* POST /gift-certificates - Create a new gift certificate. Tags will also be created If passed.
* GET /gift-certificates/{id} - Get a gift certificate with tags by ID.
* GET /gift-certificates?tag={tag}&search={search}&sort={sort}
  - Retrieve gift certificates with tags that match the specified search and filter criteria.
      - tag (Optional) a tag name to filter the gift certificates by.
        If null, no filtering by tag will be applied.
      - search (Optional) a part of the name or description of the gift certificates
        to search for. If null, no search filtering will be applied.
      - sort (Optional) a type of sorting to be applied to the results. If null,
        no sorting will be applied. The value of this parameter should be a string composed of two parts:
        a field to sort by (name or date), and a sort direction (asc or desc), separated by an underscore.
        e.g. "date_desc" would sort the results by date in descending order.
* PATCH /certificates/{id} - Update an existing gift certificate by ID.  Tags will also be created If passed.
* DELETE /certificates/{id} - Delete a gift certificate by ID.

## CRUD operations for tag
* POST /tags - Create a new tag.
* GET /tags/{id} - Get a tag by ID.
* GET /tags - Get all tags.
* PATCH /tags/{id} - Update an existing tag.
* DELETE /tags/{id} - Delete a tag by ID.


