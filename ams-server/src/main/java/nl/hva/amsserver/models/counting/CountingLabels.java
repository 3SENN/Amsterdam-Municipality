package nl.hva.amsserver.models.counting;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.hva.amsserver.serializers.CountingSerializer;

import javax.persistence.*;

/**
 * The CountingLabels model
 *
 * @author Jordy Mol
 */
@Entity
@Table(name="CountingLabels")
public class CountingLabels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ov")
    private int ov;

    @Column(name = "public_spaces")
    private int publicSpaces;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false, name = "counting_id")
    @JsonSerialize(using = CountingSerializer.class)
    private Counting counting;

    /**
     * Instantiates a new Counting label
     */
    public CountingLabels() {}

    /**
     * Instantiates a new Counting label
     *
     * @param name          The name of the label
     * @param ov            The counted people in public transport
     * @param publicSpaces  The counted people in public spaces
     */
    public CountingLabels(String name, int ov, int publicSpaces) {
        this.name = name;
        this.ov = ov;
        this.publicSpaces = publicSpaces;
    }

    /**
     * Method to associate the counting level to the label
     *
     * @param counting  The counting object to be associated
     * @return          boolean to confirm that the association was successful
     */
    public boolean associateCounting(Counting counting) {
        if (counting == null) return false;

        this.counting = counting;
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOv() {
        return ov;
    }

    public void setOv(int ov) {
        this.ov = ov;
    }

    public int getPublicSpaces() {
        return publicSpaces;
    }

    public void setPublicSpaces(int publicSpaces) {
        this.publicSpaces = publicSpaces;
    }

    public Counting getCounting() {
        return counting;
    }

    public void setCounting(Counting counting) {
        this.counting = counting;
    }
}
