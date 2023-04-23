package nl.hva.amsserver.models.levels;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.UserSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

/**
 * The LevelsLocked model
 *
 * @author Jordy Mol
 */
@Entity
@Table(name = "LockedLevel")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class LevelsLocked {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = UserSerializer.class)
    private User userId;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "levels")
    private LevelsLockedJSON levels;

    /**
     * Instantiates a new LevelsLocked object
     */
    public LevelsLocked() {}

    /**
     * Instantiates a new LevelsLocked object
     *
     * @param levels    The levels object
     */
    public LevelsLocked(LevelsLockedJSON levels) {
        this.levels = levels;
    }

    /**
     * Instantiates a new LevelsLocked object
     *
     * @param user  The user whose levels are locked
     */
    public LevelsLocked(User user) {
        super();
        this.userId = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public LevelsLockedJSON getLevels() {
        return levels;
    }

    public void setLevels(LevelsLockedJSON levels) {
        this.levels = levels;
    }
}
