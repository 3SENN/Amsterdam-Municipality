package nl.hva.amsserver.controller;

import nl.hva.amsserver.DataInserter;
import nl.hva.amsserver.models.journal.JournalCard;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for JournalsController class.
 * NOTE:
 * In the DataInserter class is data provided for the /journal api
 *
 * @author Ryan Koning
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class JournalsControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataInserter dataInserter;

    private JournalCard journalCard, journalCard0, journalCard1, journalCard2;


    @BeforeEach
    public void setup() {
        journalCard = new JournalCard(6, "Level 1", "Monday 16 January 2023", "Nice game");
        journalCard0 = this.dataInserter.getJournalCardList().get(0);
        journalCard1 = this.dataInserter.getJournalCardList().get(1);
        journalCard2 = this.dataInserter.getJournalCardList().get(2);

    }

    /**
     * Test if correct status code is shown when a card was not found.
     */
    @Test
    public void T01_ACorrectStatusShouldBeReturnedWhenCardWasNotFound() {
        //GET request for journal card with id:0
        ResponseEntity<JournalCard> response = this.restTemplate.getForEntity("/journal/0", JournalCard.class);

        //Status code check
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned when card with id: [0] was not found");
    }

    /**
     * Test if a correct status code was shown when a card is found.
     */
    @Test
    public void T02_ACorrectStatusShouldBeReturnedWhenCardWasFound() {
        // GET request for journal card with id
        ResponseEntity<JournalCard> response = this.restTemplate.getForEntity(
                "/journal/" + this.journalCard0.getId(), JournalCard.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                String.format("StatusCode: [200] should be returned when card with id: [%s] was found", this.journalCard0.getId()));
    }

    /**
     * Test if the journal page has cards.
     * NOTE: when back-end runs the DataInserter class automatically adds data to the journal page.
     */
    @Test
    public void T03_JournalPageHasCards() {
        ResponseEntity<JournalCard[]> response = this.restTemplate.getForEntity("/journal", JournalCard[].class);

        // Checking status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "StatusCode: [200] should be returned when all cards where found");

        JournalCard[] cards = response.getBody();

        assert cards != null;

        assertThat(cards.length, is(greaterThan(0)));
    }

    /**
     * Test if a card can be deleted.
     */
    @Test
    public void T04_CanDeleteCard() {
        //GET request to get card with id
        ResponseEntity<JournalCard> card = this.restTemplate.getForEntity(
                "/journal/" + this.journalCard1.getId(), JournalCard.class);

        //Checking status code to make sure card exists
        assertEquals(HttpStatus.OK, card.getStatusCode(),
                "StatusCode: [200] should be returned when all cards where found");

        //Deleting card by id: 27
        this.restTemplate.delete("/journal/27", JournalCard.class);

        //Another GET request to check that card is not there anymore
        ResponseEntity<JournalCard> deletedCard = this.restTemplate.getForEntity("/journal/27", JournalCard.class);

        //Check that card is deleted and not found
        assertEquals(HttpStatus.NOT_FOUND, deletedCard.getStatusCode(),
                "StatusCode: [404] should be returned because card cannot be found after it is deleted");

    }

    /**
     * Test if a card can be updated.
     */
    @Test
    public void T05_CanUpdateCard() {
        //GET request to get card with id
        ResponseEntity<JournalCard> card = this.restTemplate.getForEntity(
                "/journal/" + this.journalCard2.getId(), JournalCard.class);

        //Checking status code to make sure the card exists
        assertEquals(HttpStatus.OK, card.getStatusCode(),
                "StatusCode: [200] should be returned when all cards where found");

        //Assign ResponseEntity to JournalCard
        JournalCard journalCard = card.getBody();
        assert journalCard != null;

        //Changing title of the card
        journalCard.setTitle("Quizzzzzzzzz");

        //Updating card by using a PUT request
        this.restTemplate.put("/journal/" + journalCard.getId(), journalCard, JournalCard.class);

        //Another GET request to get the card after updating it
        ResponseEntity<JournalCard> updatedCard = this.restTemplate.getForEntity(
                "/journal/" + this.journalCard2.getId(), JournalCard.class);

        //Checking that the cards with samer id: 30, are not the same anymore after the update
        assertNotEquals(card, updatedCard);
    }

    /**
     * Test if a card that does not exist can be deleted.
     */
    @Test
    public void T06_CannotDeleteCardThatNotExists() {
        //Making journal card of type HttpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<JournalCard> entity = new HttpEntity<>(headers);

        //GET request on card that does not exist
        ResponseEntity<JournalCard> response =
                this.restTemplate.getForEntity("/journal/20", JournalCard.class);

        //Checking that card does not exist
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "StatusCode: [404] should be returned because the card with id: 20, does not exist");

        //Doing a DELETE request on the card that das not exist
        ResponseEntity<JournalCard> responseFalseDelete =
                this.restTemplate.exchange("/journal/20", HttpMethod.DELETE, entity, JournalCard.class);

        //Checking that the status code is indeed: [404] NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseFalseDelete.getStatusCode(),
                "StatusCode: [404] should be returned because you can't delete a card that does not exist");
    }
}

