package nl.hva.amsserver.controller;

import nl.hva.amsserver.models.levels.Level;
import nl.hva.amsserver.repositories.levels.LevelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelsController {

    @Autowired
    LevelsRepository<Level> levelsRepository;

    @GetMapping(path = "", produces = "application/json")
    public List<Level> getAllLevels() {
        return levelsRepository.findAll();
    }
}
