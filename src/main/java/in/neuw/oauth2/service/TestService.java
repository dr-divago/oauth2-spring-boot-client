package in.neuw.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.neuw.oauth2.client.TestClientService;
import in.neuw.oauth2.client.dto.BarrierRequestDto;
import in.neuw.oauth2.client.dto.BarrierResponseDto;
import in.neuw.oauth2.client.dto.Operation;
import in.neuw.oauth2.model.BarrierLevel;
import in.neuw.oauth2.model.BarrierType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Karanbir Singh on 07/18/2020
 */
@Service
public class TestService {

    @Autowired
    private TestClientService testClientService;

    public Mono<BarrierResponseDto> getTestContent(Integer name) throws JsonProcessingException {
        List<BarrierRequestDto.Operations> operationsList = new ArrayList<>();
        BarrierRequestDto.Operations operations = BarrierRequestDto
            .Operations
            .builder()
            .operation(Operation.IMPOSE)
            .identifier("ABCDEF")
            .level(BarrierLevel.SKU)
            .barrierLevelKey(BarrierType.pricing)
            .build();
        operationsList.add(operations);
        BarrierRequestDto dto = BarrierRequestDto.builder().operations(operationsList).build();

        return testClientService.getTestMessage(dto);
    }

}
