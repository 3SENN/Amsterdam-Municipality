package nl.hva.amsserver.repositories.levels;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hva.amsserver.models.levels.Level;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("LEVELS.MOCK")
public class LevelsRepositoryMock implements LevelsRepository<Level> {
    private final List<Level> levels;

    public LevelsRepositoryMock() {
        this.levels = new ArrayList<>();
    }

    @Override
    public List<Level> findAll() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Resource resource = new ClassPathResource("/json/levels.json");
            Level[] result = mapper.readValue(resource.getFile(), Level[].class);
            if(levels.size() == 0) {
                levels.addAll(List.of(result));
            }
            return levels;
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return null;
    }

}
