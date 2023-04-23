package nl.hva.amsserver.repositories.journals;

import nl.hva.amsserver.models.journal.JournalCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepositoryJPA extends JpaRepository<JournalCard, Integer> {
    List<JournalCard> getJournalCardById(int id);

    List<JournalCard> findByUserId(long id);
}
