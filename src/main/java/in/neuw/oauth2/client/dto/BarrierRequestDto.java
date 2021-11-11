package in.neuw.oauth2.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.neuw.oauth2.model.BarrierLevel;
import in.neuw.oauth2.model.BarrierType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarrierRequestDto {

  @JsonProperty("operations")
  List<Operations> operations;

  @Builder
  public static class Operations {
    @JsonProperty("operation")
    private Operation operation;
    @JsonProperty("level")
    private BarrierLevel level;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("barrierTypeKey")
    private BarrierType barrierLevelKey;
  }
}


