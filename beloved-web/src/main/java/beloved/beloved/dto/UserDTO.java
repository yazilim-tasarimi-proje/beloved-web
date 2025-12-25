package beloved.beloved.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    private String role; // Enum string olarak taşınır
    private Boolean enabled;
    private LocalDateTime createdAt;

    private Long parentUserId; // Composite yapısındaki üst kullanıcı ID

    private List<UserDTO> subUsers = new ArrayList<>(); // Alt kullanıcılar (recursive DTO)

    public UserDTO() {}

    public UserDTO(Long id, String firstName, String lastName, String email,
                   String role, Boolean enabled, LocalDateTime createdAt,
                   Long parentUserId, List<UserDTO> subUsers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.parentUserId = parentUserId;
        this.subUsers = subUsers;
    }

    // ---------------- GETTER - SETTER ---------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public List<UserDTO> getSubUsers() {
        return subUsers;
    }

    public void setSubUsers(List<UserDTO> subUsers) {
        this.subUsers = subUsers;
    }
}
