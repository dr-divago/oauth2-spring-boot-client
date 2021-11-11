package in.neuw.oauth2.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.neuw.oauth2.model.BarrierLevel;
import in.neuw.oauth2.model.BarrierType;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class BarrierResponseDto {
  @JsonProperty("responses")
  public List<Responses> responses;

  @Value
  @Jacksonized
  @Builder
  public static class Responses {
    @JsonProperty("status")
    public int status;
    @JsonProperty("barrierOperation")
    public BarrierOperation barrierOperation;
    @JsonProperty("errors")
    List<String> errors;
  }

  @Value
  @Jacksonized
  @Builder
  public static class BarrierOperation {
    @JsonProperty("operation")
    public Operation operation;
    @JsonProperty("level")
    public BarrierLevel level;
    @JsonProperty("identifier")
    public String identifier;
    @JsonProperty("barrierTypeKey")
    public BarrierType barrierTypeKey;
  }
}

