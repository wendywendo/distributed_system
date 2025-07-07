

🥤 Drinks Ordering System (RMI-Based)
This system consists of a central Headquarters server (master project) and a branch client (ft-Nakuru_Branch_testing project) that communicates via Java RMI (Remote Method Invocation).

📁 Project Structure
master/ — contains RMI server code and service implementations.

ft-Nakuru_Branch_testing/ — JavaFX client app for branch users (e.g., Nakuru).

Both projects share the rmi.shared package for common RMI interfaces and DTOs.

🛠️ Prerequisites
Ensure the following are installed and configured on both client and server machines:

Java 17+

JavaFX SDK (configured in --module-path)

MySQL (database already set up and working)

Network allows RMI port (default 1099) to be accessible

🌐 Configuration
1. Edit the RMI Config
In both master and ft-Nakuru_Branch_testing, locate the file:

java
Copy
Edit
// rmi.shared.RMIConfig.java
public class RMIConfig {
    public static final String HOST = "192.168.1.100"; // Replace with actual server IP
    public static final int PORT = 1099;

    public static String getURL(String serviceName) {
        return String.format("rmi://%s:%d/%s", HOST, PORT, serviceName);
    }
}
✅ Replace 192.168.1.100 with the actual IP address of the server machine.

🧩 How to Run
🖥️ 1. On the Server (master)
Open the master project in your IDE or terminal.

Ensure MySQL is running and credentials in DBConnection.java are correct.

Compile and run:

bash
Copy
Edit
# Inside master/src directory (or your source root)
javac com/example/drinksproject/rmi/server/RMIServer.java
java com.example.drinksproject.rmi.server.RMIServer
✅ You should see messages like:

arduino
Copy
Edit
✅ CustomerService bound.
✅ OrderService bound.
✅ DrinkService bound.
✅ LoginService bound.
🚀 RMI Server running successfully.
🧑‍💻 2. On the Client (ft-Nakuru_Branch_testing)
Open the ft-Nakuru_Branch_testing project.

Make sure RMIConfig.java points to the server IP.

Run the JavaFX application:

bash
Copy
Edit
# With JavaFX SDK in module path
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml \
    com.example.drinksproject.HelloApplication
✅ The login window should appear, and remote services will load from the HQ server.
