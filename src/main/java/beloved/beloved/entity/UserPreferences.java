package beloved.beloved.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

   @ManyToMany(mappedBy = "userPreferences")
   private Set<Category> categorySet= new HashSet<>();

   @OneToOne
   @JoinColumn(name = "relation_type_id")
   private RelationType relationType;

   @ManyToMany(mappedBy = "userPreferences" )
   private Set<SpecialDay> specialDays=new HashSet<>();


    public UserPreferences(Long id, User user, Set<Category> categorySet, RelationType relationType, Set<SpecialDay> specialDays) {
        this.id = id;
        this.user = user;
        this.categorySet = categorySet;
        this.relationType = relationType;
        this.specialDays = specialDays;
    }

    public UserPreferences() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }
    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Set<SpecialDay> getSpecialDays() {
        return specialDays;
    }

    public void setSpecialDays(Set<SpecialDay> specialDays) {
        this.specialDays = specialDays;
    }
}
