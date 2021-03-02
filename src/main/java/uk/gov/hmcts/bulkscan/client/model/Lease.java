package uk.gov.hmcts.bulkscan.client.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Lease
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-02-26T14:51:24.148474Z[Europe/London]")

public class Lease   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("expiresAt")
  private String expiresAt;

  public Lease(UUID id) {
    this.id = id;
  }

  public Lease() {
    this.id = UUID.randomUUID();
  }

  public Lease id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Lease expiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

  /**
   * Date an time at which the lease will auto expire. Defaults to 1 hour from now.
   * @return expiresAt
  */
  @ApiModelProperty(value = "Date an time at which the lease will auto expire. Defaults to 1 hour from now.")
  public String getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Lease lease = (Lease) o;
    return Objects.equals(this.id, lease.id) &&
        Objects.equals(this.expiresAt, lease.expiresAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, expiresAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Lease {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
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

