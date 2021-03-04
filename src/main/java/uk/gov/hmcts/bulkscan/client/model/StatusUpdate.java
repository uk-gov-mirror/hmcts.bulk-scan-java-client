package uk.gov.hmcts.bulkscan.client.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * StatusUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-02-26T14:51:24.148474Z[Europe/London]")

public class StatusUpdate   {
  /**
   * Status to set the envelope to
   */
  public enum StatusEnum {
    PENDING("pending"),
    
    PROCESSED("processed"),
    
    ERRORED("errored");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("reason")
  private String reason;

  public StatusUpdate status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Status to set the envelope to
   * @return status
  */
  @ApiModelProperty(required = true, value = "Status to set the envelope to")
  @NotNull
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public StatusUpdate reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Optionally describes the reason for the status being changed.
   * @return reason
  */
  @ApiModelProperty(value = "Optionally describes the reason for the status being changed.")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatusUpdate statusUpdate = (StatusUpdate) o;
    return Objects.equals(this.status, statusUpdate.status) &&
        Objects.equals(this.reason, statusUpdate.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatusUpdate {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

