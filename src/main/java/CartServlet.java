import java.io.IOException;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemParam = request.getParameter("item");
        String[] parts = itemParam.split(",");
        String itemName = parts[0];
        int price = Integer.parseInt(parts[1]);
        
        int quantity = Integer.parseInt(request.getParameter("qty_" + itemName));

        HttpSession session = request.getSession();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new HashMap<>();
        }
        
        if (cart.containsKey(itemName)) {
            CartItem existing = cart.get(itemName);
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            cart.put(itemName, new CartItem(itemName, price, quantity));
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("FoodServlet");
    }
}