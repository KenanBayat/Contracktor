# Contracktor

The purpose of the software is to be used in the construction industry for the efficient management of contract procedures and the construction progress of an organization's projects.
The software includes account management to add new users and admins to the organizations. Users are assigned roles to obtain permission to edit and read the organization's projects. This is intended to save organizations bureaucratic effort by giving authorized users immediate access to all data relevant to them. 
The software system should be able to allow users to read and modify the projects of their assigned organization depending on their role. Consequently, only authorized users are able of changing the service items and thus the projects, which ensures security and data protection. The system also allows projects, contracts, and service items to be searched and enables this data to be displayed in the form of diagrams. The purpose of this function is to obtain clear information about the status of projects, contracts, or individual service items.

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
    git clone https://github.com/KenanBayat/Contracktor.git

2. Move into contracktor folder:
    cd .\contracktor\

3. Build the project:
   gradle build   

4. Move into libs folder:
   cd .\build\libs\ 

6. Run the application:
   java -jar contracktor-0.0.1-SNAPSHOT.jar

7. Open browser and http://localhost:8080

8. Login with test credentials:
     Username: Test
     Password: Test

