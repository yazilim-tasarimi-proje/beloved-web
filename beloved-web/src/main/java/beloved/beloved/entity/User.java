// ----------------------------------------------------------
// 2️⃣ User Entity: Composite Pattern'in "Composite" + "Leaf" birleşimi
// ----------------------------------------------------------
// Her User hem tekil olabilir (leaf) hem de alt kullanıcıları barındırabilir (composite)

package beloved.beloved.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserComponent {

    // ------------------ Temel Alanlar ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;   // ⭐ BURASI VAR AMA GETTER/SETTER EKSİKTİ

    private LocalDateTime createdAt;  // ⭐ BURASI VAR AMA GETTER/SETTER EKSİKTİ

    // ------------------ Composite Yapı ------------------
    @OneToMany(mappedBy = "parentUser", cascade = CascadeType.ALL)
    private List<User> subUsers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_user_id")
    private User parentUser;

    // ------------------ Enum (Role) ------------------
    public enum Role {
        USER,
        ADMIN
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    // ------------------ Durum Bilgisi ------------------
    @Column(nullable = false)
    private Boolean enabled = true;

    // ------------------ Constructor ------------------
    public User() {}

    // ------------------ Getter - Setter ------------------
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getSubUsers() {
        return subUsers;
    }

    public void setSubUsers(List<User> subUsers) {
        this.subUsers = subUsers;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐
    // 🟩 EKSİK GETTER/SETTER METOTLARI EKLENDİ
    // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ----------------------------------------------------------
    // Composite Pattern Davranışı
    // ----------------------------------------------------------
    @Override
    public void showDetails() {
        System.out.println("👤 Kullanıcı: " + firstName + " " + lastName + " [" + role + "]");
        for (User subUser : subUsers) {
            subUser.showDetails();
        }
    }

    // Alt kullanıcı ekleme
    public void addSubUser(User user) {
        user.setParentUser(this);
        subUsers.add(user);
    }

    // Alt kullanıcı kaldırma
    public void removeSubUser(User user) {
        subUsers.remove(user);
        user.setParentUser(null);
    }
}
