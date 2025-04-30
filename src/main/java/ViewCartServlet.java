import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("cart");

        out.println("<html><body>");
        out.println("<h1>Your Cart</h1>");

        if(cart == null || cart.isEmpty()) {
            out.println("Cart is Empty!");
        } else {
            int total = 0;
            out.println("<table border='1'><tr><th>Item</th><th>Quantity</th><th>Price</th><th>Actions</th></tr>");
            for(CartItem item : cart.values()) {
                int itemTotal = item.getQuantity() * item.getPrice();
                out.println("<tr>");
                out.println("<td>" + item.getName() + "</td>");
                out.println("<td>" + item.getQuantity() + "</td>");
                out.println("<td>Rs. " + itemTotal + "</td>");
                out.println("<td>");
                out.println("<form action='UpdateCartServlet' method='post' style='display:inline;'>");
                out.println("<input type='hidden' name='item_name' value='" + item.getName() + "'>");
                out.println("<button type='submit' name='action' value='increase'>+</button>");
                out.println("<button type='submit' name='action' value='decrease'>-</button>");
                out.println("<button type='submit' name='action' value='remove'>Remove</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
                total += itemTotal;
            }
            out.println("</table>");
            out.println("<h3>Total: Rs. " + total + "</h3>");
            
            out.println("<form action='OrderServlet' method='post'>");
            out.println("Your Name: <input type='text' name='customer_name' required><br><br>");
            out.println("<input type='submit' value='Place Order'>");
            out.println("</form>");
        }

        out.println("<br><a href='FoodServlet'>Back to Menu</a>");
        out.println("</body></html>");
    }
}