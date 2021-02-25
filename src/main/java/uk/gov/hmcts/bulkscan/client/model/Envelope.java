package uk.gov.hmcts.bulkscan.client.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Envelope
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-02-25T22:45:18.182988Z[Etc/UTC]")

public class Envelope   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("dateReceived")
  private String dateReceived;

  @JsonProperty("dateProcessed")
  private String dateProcessed;

  /**
   * Gets or Sets status
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

  @JsonProperty("isLeased")
  private Boolean isLeased;

  @JsonProperty("auditTrail")
  private String auditTrail;

  public Envelope id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Envelope dateReceived(String dateReceived) {
    this.dateReceived = dateReceived;
    return this;
  }

  /**
   * Get dateReceived
   * @return dateReceived
  */
  @ApiModelProperty(value = "")


  public String getDateReceived() {
    return dateReceived;
  }

  public void setDateReceived(String dateReceived) {
    this.dateReceived = dateReceived;
  }

  public Envelope dateProcessed(String dateProcessed) {
    this.dateProcessed = dateProcessed;
    return this;
  }

  /**
   * Get dateProcessed
   * @return dateProcessed
  */
  @ApiModelProperty(value = "")


  public String getDateProcessed() {
    return dateProcessed;
  }

  public void setDateProcessed(String dateProcessed) {
    this.dateProcessed = dateProcessed;
  }

  public Envelope status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Envelope isLeased(Boolean isLeased) {
    this.isLeased = isLeased;
    return this;
  }

  /**
   * Get isLeased
   * @return isLeased
  */
  @ApiModelProperty(value = "")


  public Boolean getIsLeased() {
    return isLeased;
  }

  public void setIsLeased(Boolean isLeased) {
    this.isLeased = isLeased;
  }

  public Envelope auditTrail(String auditTrail) {
    this.auditTrail = auditTrail;
    return this;
  }

  /**
   * Get auditTrail
   * @return auditTrail
  */
  @ApiModelProperty(value = "")


  public String getAuditTrail() {
    return auditTrail;
  }

  public void setAuditTrail(String auditTrail) {
    this.auditTrail = auditTrail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Envelope envelope = (Envelope) o;
    return Objects.equals(this.id, envelope.id) &&
        Objects.equals(this.dateReceived, envelope.dateReceived) &&
        Objects.equals(this.dateProcessed, envelope.dateProcessed) &&
        Objects.equals(this.status, envelope.status) &&
        Objects.equals(this.isLeased, envelope.isLeased) &&
        Objects.equals(this.auditTrail, envelope.auditTrail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dateReceived, dateProcessed, status, isLeased, auditTrail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Envelope {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dateReceived: ").append(toIndentedString(dateReceived)).append("\n");
    sb.append("    dateProcessed: ").append(toIndentedString(dateProcessed)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isLeased: ").append(toIndentedString(isLeased)).append("\n");
    sb.append("    auditTrail: ").append(toIndentedString(auditTrail)).append("\n");
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

