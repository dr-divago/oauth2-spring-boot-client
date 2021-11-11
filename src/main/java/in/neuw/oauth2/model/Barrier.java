package in.neuw.oauth2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Barrier {

  BarrierLevel level;

  boolean isActive;

  BarrierType type;

  String identifier;
}
