package in.neuw.oauth2.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.neuw.oauth2.client.dto.BarrierRequestDto;
import in.neuw.oauth2.client.dto.BarrierResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Karanbir Singh on 07/18/2020
 */
@Slf4j
@AllArgsConstructor
public class TestClientService {

    private WebClient testWebClient;

    public Mono<BarrierResponseDto> getTestMessage(BarrierRequestDto supplierPartIds) throws JsonProcessingException {

      ObjectMapper mapper = new ObjectMapper();
      String ss =  mapper.writeValueAsString(supplierPartIds);
      System.out.println(ss);

        BarrierResponseDto resp = testWebClient.put()
            .uri("/barriers/")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Accept", "*/*")
            .body(Mono.just(supplierPartIds), BarrierRequestDto.class)
            .retrieve()
            .bodyToMono(BarrierResponseDto.class)
            .log()
            .block();

        return Mono.empty();
    }
}
