package beloved.beloved.entity;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class RelationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "relationType")
    private UserPreferences userPreferencesSet;

    public RelationType() {
    }

    public RelationType(Long id, String name, UserPreferences userPreferences) {
        this.id = id;
        this.name = name;
        this.userPreferencesSet = userPreferences;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public UserPreferences getUserPreferences() {
        return userPreferencesSet;
    }
    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferencesSet = userPreferences;
    }
}
