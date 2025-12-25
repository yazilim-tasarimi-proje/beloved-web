package beloved.beloved.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.JoinTable;

@Entity
public class SpecialDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "userpreferences_specialday" , joinColumns = @JoinColumn(name = "userpreferences_id")
            , inverseJoinColumns = @JoinColumn(name = "specialday_id"))
    private Set<UserPreferences> userPreferencesSet= new HashSet<>();

    public SpecialDay(Long id, String name, Set<UserPreferences> userPreferencesSet) {
        this.id = id;
        this.name = name;
        this.userPreferencesSet = userPreferencesSet;
    }

    public SpecialDay() {
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

    public Set<UserPreferences> getUserPreferencesSet() {
        return userPreferencesSet;
    }

    public void setUserPreferencesSet(Set<UserPreferences> userPreferencesSet) {
        this.userPreferencesSet = userPreferencesSet;
    }
}
