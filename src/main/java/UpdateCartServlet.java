import java.io.IOException;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemName = request.getParameter("item_name");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("cart");

        if(cart != null && cart.containsKey(itemName)) {
            CartItem item = cart.get(itemName);

            if("increase".equals(action)) {
                item.setQuantity(item.getQuantity() + 1);
            } else if("decrease".equals(action)) {
                if(item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cart.remove(itemName); // If quantity becomes 0, remove item
                }
            } else if("remove".equals(action)) {
                cart.remove(itemName);
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("ViewCartServlet");
    }
}