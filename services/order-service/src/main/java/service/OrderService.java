import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserValidationService userValidationService;
    
    public OrderService(OrderRepository orderRepository, UserValidationService userValidationService) {
        this.orderRepository = orderRepository;
        this.userValidationService = userValidationService;
    }
    
    public Order createOrder(CreateOrderRequest request) {
        if (!userValidationService.validateUser(request.getUserId())) {
            throw new UserValidationException("Invalid or inactive user: " + request.getUserId());
        }
        
        List<OrderItem> items = request.getItems().stream()
            .map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getUnitPrice()))
            .collect(Collectors.toList());
            
        Order order = new Order(request.getUserId(), items);
        items.forEach(item -> item.setOrder(order));
        
        return orderRepository.save(order);
    }
    
    public static class CreateOrderRequest {
        private UUID userId;
        private List<OrderItemRequest> items;
        
        public CreateOrderRequest() {}
        
        public UUID getUserId() { return userId; }
        public List<OrderItemRequest> getItems() { return items; }
        
        public void setUserId(UUID userId) { this.userId = userId; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }
    
    public static class OrderItemRequest {
        private UUID productId;
        private Integer quantity;
        private BigDecimal unitPrice;
        
        public OrderItemRequest() {}
        
        public UUID getProductId() { return productId; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        
        public void setProductId(UUID productId) { this.productId = productId; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }
}