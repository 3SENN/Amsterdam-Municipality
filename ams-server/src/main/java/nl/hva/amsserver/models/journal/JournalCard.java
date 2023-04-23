package nl.hva.amsserver.models.journal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.hva.amsserver.models.dto.JournalDTO;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.serializers.UserSerializer;

import javax.persistence.*;

/**
 * This class contains all information about journal cards.
 *
 * @author Ryan Koning
 */
@Entity
@Table(name = "journal")
public class JournalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = UserSerializer.class)
    private User user;
    @Column(name = "title")
    private String title;

    @Column(name = "card_date")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private String cardDate;

    @Column(name = "content")
    private String content;



    public JournalCard(int id, String title, String cardDate, String content){
        this.id = id;
        this.title = title;
        this.cardDate = cardDate;
        this.content = content;

    }

    public JournalCard(int id, User user, String title, String cardDate, String content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.cardDate = cardDate;
        this.content = content;
    }

    public JournalCard(JournalDTO journal, User user) {
        this.id = journal.getId();
        this.user = user;
        this.title = journal.getTitle();
        this.cardDate = journal.getCardDate();
        this.content = journal.getContent();
    }

    public JournalCard() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCardDate() {
        return cardDate;
    }



    public static JournalCard createJournalCard(int id){
        return new JournalCard(id, cardName(), createCardDate(), createCardContent());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public static String cardName(){
        return "Level 1";
    }

    public static String createCardDate(){
        return "27/09/2022 5:48 PM";
    }

    public static String createCardContent(){
        return "Wij gaan lekker samen delen en spelen";
    }

    @Override
    public String toString() {
        return "JournalCard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cardDate=" + cardDate +
                ", cardContent='" + content + '\'' +
                '}';
    }
}
