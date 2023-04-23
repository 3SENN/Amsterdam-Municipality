package nl.hva.amsserver.repositories.levels;

import java.util.List;

public interface LevelsRepository<E> {

    List<E> findAll();

}
