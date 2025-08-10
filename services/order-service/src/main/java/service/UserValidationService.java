import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
public class UserValidationService {
    
    private final RestTemplate restTemplate;
    
    @Value("${user-service.url:http://user-service:8080}")
    private String userServiceUrl;
    
    public UserValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public boolean validateUser(UUID userId) {
        try {
            String url = userServiceUrl + "/api/v1/users/" + userId;
            ResponseEntity<UserValidationResponse> response = restTemplate.getForEntity(
                url, UserValidationResponse.class);
            
            return response.getStatusCode().is2xxSuccessful() && 
                   response.getBody() != null && 
                   response.getBody().isActive();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static class UserValidationResponse {
        private UUID id;
        private String email;
        private boolean active;
        
        public UserValidationResponse() {}
        
        public UUID getId() { return id; }
        public String getEmail() { return email; }
        public boolean isActive() { return active; }
        
        public void setId(UUID id) { this.id = id; }
        public void setEmail(String email) { this.email = email; }
        public void setActive(boolean active) { this.active = active; }
    }
}