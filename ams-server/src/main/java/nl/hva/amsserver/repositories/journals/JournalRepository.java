package nl.hva.amsserver.repositories.journals;

import nl.hva.amsserver.models.journal.JournalCard;

import java.util.List;

public interface JournalRepository {
    List<JournalCard> findAll();

    JournalCard findById(int id);

    List<JournalCard> findByUserId(long id);

    JournalCard save(JournalCard journalCard);

    boolean deleteById(int id);

}
