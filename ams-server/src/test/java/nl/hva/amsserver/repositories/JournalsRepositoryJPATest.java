package nl.hva.amsserver.repositories;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.journal.JournalCard;
import nl.hva.amsserver.repositories.journals.JournalRepositoryJPA;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for journal repository class.
 * NOTE: when back-end runs the DataInserter class automatically adds data to the journal page.
 * @author Ryan Koning
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@Import(DataInserter.class)
public class JournalsRepositoryJPATest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JournalRepositoryJPA journalRepositoryJPA;

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    private final int TEST_CARD_ID = 28;
    private final int DELETE_CARD_ID = 29;

    /**
     * Test if repositories are loaded.
     */
    @Test
    void T00_InjectedComponentsAreNotNull(){
        assertNotNull(journalRepositoryJPA);
        assertNotNull(userRepositoryJPA);
    }

    /**
     * Test if card can be found by id.
     */
    @Test
    void T01_CardCanBeFoundById(){
        //Find card with id: 28
        JournalCard card = this.journalRepositoryJPA.getReferenceById(this.TEST_CARD_ID);

        //Check that the constant value used as id is the same as the id of the card
        assertThat(this.TEST_CARD_ID).isEqualTo(card.getId());
    }

    /**
     * Test if card can be deleted by id.
     */
    @Test
    void T02_CardCanBeDeletedById(){
        //Find the card with id: 29
        JournalCard card = this.journalRepositoryJPA.getReferenceById(this.DELETE_CARD_ID);

        //Delete card from database
        this.journalRepositoryJPA.deleteById(card.getId());

        //Try to get the card with id: 29 again
        Optional<JournalCard> deletedCard = this.journalRepositoryJPA.findById(this.DELETE_CARD_ID);

        //Check that card doesn't exist anymore
        assertTrue(deletedCard.isEmpty());
    }
}
