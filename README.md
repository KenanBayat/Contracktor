# Contracktor

The purpose of the software is to be used in the construction industry for the efficient management of contract procedures and the construction progress of an organization's projects.
The software includes account management to add new users and admins to the organizations. Users are assigned roles to obtain permission to edit and read the organization's projects. This is intended to save organizations bureaucratic effort by giving authorized users immediate access to all data relevant to them. 
The software system should be able to allow users to read and modify the projects of their assigned organization depending on their role. Consequently, only authorized users are able of changing the service items and thus the projects, which ensures security and data protection. The system also allows projects, contracts, and service items to be searched and enables this data to be displayed in the form of diagrams. The purpose of this function is to obtain clear information about the status of projects, contracts, or individual service items. 

The website was developed for the company Adesso SE and is therefore in German.

### Prerequisites

- Java Development 11.0.12
- Spring Framework 2.5.4
- Gradle (included by Spring or 7.3.3)
- Spring Tool Suite 4 4.11.1
- Bootstrap 5.1.0
- Docker 20.10.7
- Git 2.33.0

### Installation

1. Clone the repository:
    - git clone https://github.com/KenanBayat/Contracktor.git
2. Move into contracktor folder:
    - cd .\contracktor\
3. Build the project:
   - gradle build   
4. Move into libs folder:
   - cd .\build\libs\ 
5. Run the application:
   - java -jar contracktor-0.0.1-SNAPSHOT.jar
6. Open browser and type:
     - http://localhost:8080
7. Login with test credentials:
     - Username: Test
     - Password: Test

After login this home site should be visible:

![home](.\images\home.png)

### Importing  Data 

The software can transfer contract data from a REST interface. To import test data do following steps:

1. Move into adessoAPI folder:
   - cd .\docker\adessoAPI\
2. Build Image of Dockerfile:
   - docker build -t contracktor .
3. Run Docker Image: 
   - docker run -p 3000:3000 contracktor

The URL of the API can be changed via the admin console (see features). 

### Features

- Account management system:
  - Administrators of the organization can add and remove additional users and configure their roles.
  - The application administrators can create new organizations and their administrators.
  - URL of API for data acquisition can be changed
  - States and state transitions of billing units can be added or removed
  - ![admin_console](.\images\admin_console.png)
- Management for projects, contracts and billing unit management:
  -  ![Billing_Unit_Management](.\images\Billing_Unit_Management.png)
- The software provides the option of creating your own diagrams from the service items, contracts and projects in order to obtain an overview of progress.
  - ![Statistics](.\images\Statistics.png)
