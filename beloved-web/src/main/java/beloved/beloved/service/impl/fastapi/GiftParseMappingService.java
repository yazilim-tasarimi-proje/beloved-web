package beloved.beloved.service.impl.fastapi;

import beloved.beloved.dto.AiRecommendationRequestDto;
import beloved.beloved.dto.ParsedGiftResponseDto;
import beloved.beloved.entity.RelationType;
import beloved.beloved.entity.SpecialDay;
import beloved.beloved.repository.RelationTypeRepository;
import beloved.beloved.repository.SpecialDayRepository;
import org.springframework.stereotype.Service;

@Service
public class GiftParseMappingService {

    private final RelationTypeRepository relationTypeRepository;
    private final SpecialDayRepository specialDayRepository;

    public GiftParseMappingService(RelationTypeRepository relationTypeRepository,
                                   SpecialDayRepository specialDayRepository) {
        this.relationTypeRepository = relationTypeRepository;
        this.specialDayRepository = specialDayRepository;
    }

    public Long mapRelationTypeToId(String relationTypeName) {
        if (relationTypeName == null) return null;

        return relationTypeRepository.findByName(relationTypeName)
                .map(RelationType::getId)
                .orElse(null);
    }

    public Long mapSpecialDayToId(String specialDayName) {
        if (specialDayName == null) return null;

        return specialDayRepository.findByName(specialDayName)
                .map(SpecialDay::getId)
                .orElse(null);
    }
    public AiRecommendationRequestDto toRecommendationRequest(
            ParsedGiftResponseDto parsed
    ) {
        AiRecommendationRequestDto dto = new AiRecommendationRequestDto();

        dto.setRelationTypeId(
                mapRelationTypeToId(parsed.getRelationType())
        );

        dto.setSpecialDayId(
                mapSpecialDayToId(parsed.getSpecialDay())
        );

        return dto;
    }

}
