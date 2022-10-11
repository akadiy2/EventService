# EventService
Service to handle event operations

# Installation

Prerequisites: Gradle and Java 8 or above

1. Install MongoDB
    - Follow the instructions to install MongoDB Community Edition (or a version of your choosing) from the official webpage: https://www.mongodb.com/docs/manual/administration/install-community/
    - Set the path variable to the installation directory of mongodb/bin on the system explorer (for Windows users)
    
2. Run the application locally
   - Navigate to the root of "EventService"
   - Run "./gradlew build" or update the project's dependencies using the respective IDE's gradle perspective.
   - Run EventService application by right clicking on the file and select "Run Application"

# Usage

1.  On a rest client (such as postman), add an event object to the following url: "http://localhost:8102/events"
```{"id": 1, "name": "Event1", "description": "Description of event1"}```


- curl --location --request POST 'localhost:8102/events' \
   --header 'Content-Type: application/json' \
   --data-raw '{"id": 1, "name": "Event1", "description": "Description of event1"}'
   
2. To view data in mongodb (events) run GET on "http://localhost:8102/events"

       
