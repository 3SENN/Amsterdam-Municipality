package nl.hva.amsserver.models.counting;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.UserSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The Counting model
 *
 * @author Jordy Mol
 */
@Entity
@Table(name = "counting")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Counting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = UserSerializer.class)
    private User userId;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name = "starting_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startingTime;

    @Column(name = "boarding_stop")
    private String boardingStop;

    @Column(name = "transfer")
    private String transfer;

    @Column(name = "exit_stop")
    private String exitStop;

    @Column(name = "destination")
    private String destination;

    @Column(name = "destination_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime destinationTime;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "public_spaces")
    private CountingPublicSpaces publicSpaces;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "ov")
    private CountingOV ov;

    @Column(name = "score")
    private int score;

    @OneToMany(mappedBy = "counting")
    private Set<CountingLabels> countingLabels = new HashSet<>();

    /**
     * Instantiates a new Counting level
     */
    public Counting() {
    }

    /**
     * Instantiates a new Counting level
     *
     * @param startingPoint     The starting location of the counting level
     * @param startingTime      The starting time and date of the counting level
     * @param boardingStop      The optional boarding stop of the counting level
     * @param transfer          The optional transfer location of the counting level
     * @param exitStop          The optional exit stop of the counting level
     * @param destination       The destination of the counting level
     * @param destinationTime   The destination time and date of the counting level
     * @param publicSpaces      The counted people in public spaces
     * @param ov                The counted people in public transport
     * @param score             The score achieved for the counting level
     */
    public Counting(String startingPoint, LocalDateTime startingTime, String boardingStop,
                    String transfer, String exitStop, String destination, LocalDateTime destinationTime,
                    CountingPublicSpaces publicSpaces, CountingOV ov, int score) {
        this.startingPoint = startingPoint;
        this.startingTime = startingTime;
        this.boardingStop = boardingStop;
        this.transfer = transfer;
        this.exitStop = exitStop;
        this.destination = destination;
        this.destinationTime = destinationTime;
        this.publicSpaces = publicSpaces;
        this.ov = ov;
        this.score = score;
    }

    /**
     * Instantiates a new Counting level
     *
     * @param playedBy  The user that played the counting game
     */
    public Counting(User playedBy) {
        super();
        this.userId = playedBy;
    }

    /**
     * Method to associate the label to the counting level
     *
     * @param countingLabel The counting label to be associated
     * @return              boolean to confirm that the association was successful
     */
    public boolean associateLabel(CountingLabels countingLabel) {
        if (countingLabel == null || this.countingLabels.contains(countingLabel)) return false;

        this.countingLabels.add(countingLabel);
        countingLabel.associateCounting(this);
        return true;
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

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public String getBoardingStop() {
        return boardingStop;
    }

    public void setBoardingStop(String boardingStop) {
        this.boardingStop = boardingStop;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getExitStop() {
        return exitStop;
    }

    public void setExitStop(String exitStop) {
        this.exitStop = exitStop;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(LocalDateTime destinationTime) {
        this.destinationTime = destinationTime;
    }

    public CountingPublicSpaces getPublicSpaces() {
        return publicSpaces;
    }

    public void setPublicSpaces(CountingPublicSpaces publicSpaces) {
        this.publicSpaces = publicSpaces;
    }

    public CountingOV getOv() {
        return ov;
    }

    public void setOv(CountingOV ov) {
        this.ov = ov;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<CountingLabels> getCountingLabels() {
        return countingLabels;
    }

    public void setCountingLabels(Set<CountingLabels> countingLabels) {
        this.countingLabels = countingLabels;
    }
}
