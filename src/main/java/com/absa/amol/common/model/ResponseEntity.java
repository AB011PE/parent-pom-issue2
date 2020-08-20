package com.absa.amol.common.model;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  @JsonbProperty(nillable = true)
  private String code;
  @JsonbProperty(nillable = true)
  private String message;
  @JsonbProperty(nillable = true)
  private String status;
  @JsonbProperty(nillable = true)
  private T data;

}
