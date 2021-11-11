package in.neuw.oauth2.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.neuw.oauth2.client.dto.BarrierResponseDto;
import in.neuw.oauth2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karanbir Singh on 07/18/2020
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public BarrierResponseDto response(@RequestParam(required = false, defaultValue = "29445650") final Integer name)
        throws JsonProcessingException {
        return testService.getTestContent(name).block();
    }
}
