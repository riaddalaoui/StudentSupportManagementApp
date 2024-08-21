<h1>DASI - Student Support Management Web App</h1>

<h2>Overview</h2>
The Student Support Management project is a web-based application designed to facilitate and manage student support activities within an academic institution. It includes several key functionalities such as user authentication, session management, and the generation of statistics and reports. The project follows the Model-View-Controller (MVC) architecture and is developed primarily using Java.

<h2>Features</h2>

- <b>User Authentication</b>: The application provides secure authentication mechanisms for both students and staff (intervenants).
- <b>Session Management</b>: Users can create, manage, and track support sessions (soutiens) efficiently. This includes starting, terminating, and evaluating sessions.
- <b>Data Serialization</b>: The project implements various serialization methods to handle data related to user profiles, session histories, and statistical reports.
- <b>Dynamic Statistics</b>: The application generates real-time statistics and visualizations that provide insights into the performance of students and the overall effectiveness of the support sessions.
- <b>Action Handling</b>: A robust action-based framework allows for the execution of various tasks such as user registration, session management, and report generation.

<h2>Project Structure</h2>

- <b>Servlets</b>: Core controllers that handle HTTP requests and responses, serving as the entry point for the application logic.
  - `ActionServlet.java`
  
- <b>Actions</b>: These classes encapsulate the logic for various operations, such as user authentication, session management, and data retrieval.
  - Example actions include `AuthentifierUtilisateurAction.java`, `CreateSoutienAction.java`, `GetStatistiquesAccueilAction.java`, etc.
  
- <b>Serialization</b>: Classes responsible for converting complex data structures into formats suitable for web communication and storage.
  - Example serializations include `ProfilUtilisateurSerialisation.java`, `StatistiqueAccueilSerialisation.java`, `TableauDeBordSerialisation.java`, etc.

<h2>Technologies Used</h2>

- <b>Java</b>: Core programming language used for developing the application.
- <b>Servlets</b>: For handling client requests and directing them to the appropriate actions.
- <b>JSP (JavaServer Pages)</b>: For rendering dynamic web content on the client side.
- <b>Maven</b>: Project management and build automation tool used to manage dependencies and compile the project.

