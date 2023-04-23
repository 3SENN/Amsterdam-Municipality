package nl.hva.amsserver.repositories.journals;

import nl.hva.amsserver.exceptions.ResourceNotFound;
import nl.hva.amsserver.models.journal.JournalCard;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.users.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalRepositoryMock implements JournalRepository {

    @Autowired
    UserRepositoryJPA userRepositoryJPA;
    private List<JournalCard> cards;
    private int lastId = 0;

    @Autowired
    public JournalRepositoryMock() {
        cards = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            this.cards.add(JournalCard.createJournalCard(this.nextID()));
        }
    }

    public int nextID() {
        this.lastId += 1;
        return lastId;
    }

    @Override
    public List<JournalCard> findAll() {
        return this.cards;
    }

    @Override
    public JournalCard findById(int id) {
        return this.cards.get(id);
    }


    @Override
    public List<JournalCard> findByUserId(long id) {
        Optional<User> user = userRepositoryJPA.findById(id);

        if (user.isEmpty()) throw new ResourceNotFound(String.format("User with id:[%d] is not found", id));

        System.out.println(user.get());

        return this.cards.stream().filter(c -> c.getUser() == user.get()).toList();
    }

    @Override
    public JournalCard save(JournalCard journalCard) {
        this.cards.add(journalCard);
        return journalCard;
    }

    @Override
    public boolean deleteById(int id) {
        this.cards.remove(id);
        return true;
    }
}
