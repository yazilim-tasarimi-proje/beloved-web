package beloved.beloved.controller;

import beloved.beloved.entity.RelationType;
import beloved.beloved.repository.RelationTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relation-types")
public class RelationTypeController {

    private final RelationTypeRepository relationTypeRepository;

    public RelationTypeController(RelationTypeRepository relationTypeRepository) {
        this.relationTypeRepository = relationTypeRepository;
    }

    @GetMapping
    public List<RelationType> getAllRelationTypes() {
        return relationTypeRepository.findAll();
    }
}
