import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");  // Get email from form
        String password = request.getParameter("password");
        response.setContentType("text/html");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, username);
            stmt.setString(2, email);  // Insert email
            stmt.setString(3, password);
            
            int rowsInserted = stmt.executeUpdate();
            PrintWriter out = response.getWriter();
            
            if (rowsInserted > 0) {
                out.println("<h3>Registration Successful</h3>");
                out.println("<a href='login.html'>Go to Login</a>");
            } else {
                out.println("<h3>Registration Failed</h3>");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                response.getWriter().println("<h3>Error: Username or Email already exists. Choose a different one.</h3>");
            } else {
                e.printStackTrace();
                response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
            }
        }
    }
}