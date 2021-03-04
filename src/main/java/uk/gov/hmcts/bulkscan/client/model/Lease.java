package uk.gov.hmcts.bulkscan.client.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Lease
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-02T14:18:17.645232Z[Europe/London]")

public class Lease   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("expiresAt")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private Date expiresAt;

  @JsonProperty("blobSasUrl")
  private String blobSasUrl;

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

  public Lease expiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

  /**
   * Date an time at which the lease will auto expire. Defaults to 1 hour from now.
   * @return expiresAt
  */
  @ApiModelProperty(value = "Date an time at which the lease will auto expire. Defaults to 1 hour from now.")
  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Lease blobSasUrl(String blobSasUrl) {
    this.blobSasUrl = blobSasUrl;
    return this;
  }

  /**
   * Readonly url with SAS token to access the blob
   * @return blobSasUrl
  */
  @ApiModelProperty(value = "Readonly url with SAS token to access the blob")
  public String getBlobSasUrl() {
    return blobSasUrl;
  }

  public void setBlobSasUrl(String blobSasUrl) {
    this.blobSasUrl = blobSasUrl;
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
        Objects.equals(this.expiresAt, lease.expiresAt) &&
        Objects.equals(this.blobSasUrl, lease.blobSasUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, expiresAt, blobSasUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Lease {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
    sb.append("    blobSasUrl: ").append(toIndentedString(blobSasUrl)).append("\n");
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

