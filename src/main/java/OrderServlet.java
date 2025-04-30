import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customer_name");

        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("cart");

        if(cart == null || cart.isEmpty()) {
            response.getWriter().println("Cart is Empty!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            
            for(CartItem item : cart.values()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO orders (customer_name, item_name, quantity, total_price) VALUES (?, ?, ?, ?)");
                ps.setString(1, customerName);
                ps.setString(2, item.getName());
                ps.setInt(3, item.getQuantity());
                ps.setInt(4, item.getQuantity() * item.getPrice());
                ps.executeUpdate();
            }
            
            session.removeAttribute("cart"); // Clear cart after order
            
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Order Placed Successfully!</h1>");
            response.getWriter().println("<a href='FoodServlet'>Order More</a>");
            response.getWriter().println("</body></html>");
            
            con.close();
        } catch(Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}