/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package uk.gov.hmcts.bulkscan.client.api;

import uk.gov.hmcts.bulkscan.client.model.Envelope;
import uk.gov.hmcts.bulkscan.client.model.InlineObject;
import uk.gov.hmcts.bulkscan.client.model.InlineObject1;
import java.util.UUID;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-02-25T22:45:18.182988Z[Etc/UTC]")

@Validated
@Api(value = "Default", description = "the Default API")
public interface DefaultApi {

    /**
     * DELETE /envelopes/{team}/envelope/{envelopeId}/lease/{leaseId} : Delete this lease for the envelope
     *
     * @param team Team name who the envelopes belong to. Supplied S2S token will be validated against this path (required)
     * @param envelopeId UUID for the envelope to remove the lease from (required)
     * @param leaseId UUID for a lease to be deleted (required)
     * @return Deleted Successfully (status code 204)
     *         or Requires S2S authentication token (status code 401)
     *         or You do not have permission to access this teams envelopes (status code 403)
     *         or Team or envelope not found not found (status code 404)
     *         or Lease already exists (status code 409)
     */
    @ApiOperation(value = "Delete this lease for the envelope", nickname = "envelopesTeamEnvelopeEnvelopeIdLeaseLeaseIdDelete", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Deleted Successfully"),
        @ApiResponse(code = 401, message = "Requires S2S authentication token"),
        @ApiResponse(code = 403, message = "You do not have permission to access this teams envelopes"),
        @ApiResponse(code = 404, message = "Team or envelope not found not found"),
        @ApiResponse(code = 409, message = "Lease already exists") })
    @RequestMapping(value = "/envelopes/{team}/envelope/{envelopeId}/lease/{leaseId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> envelopesTeamEnvelopeEnvelopeIdLeaseLeaseIdDelete(@ApiParam(value = "Team name who the envelopes belong to. Supplied S2S token will be validated against this path",required=true) @PathVariable("team") String team,@ApiParam(value = "UUID for the envelope to remove the lease from",required=true) @PathVariable("envelopeId") UUID envelopeId,@ApiParam(value = "UUID for a lease to be deleted",required=true) @PathVariable("leaseId") UUID leaseId);


    /**
     * PUT /envelopes/{team}/envelope/{envelopeId}/lease/{leaseId} : Lease this envelope for processing
     *
     * @param team Team name who the envelopes belong to. Supplied S2S token will be validated against this path (required)
     * @param envelopeId UUID for the envelope to be leased (required)
     * @param leaseId UUID for a lease to be created (required)
     * @param lease  (optional)
     * @return Created (status code 201)
     *         or Requires S2S authentication token (status code 401)
     *         or You do not have permission to access this teams envelopes (status code 403)
     *         or Team or envelope not found not found (status code 404)
     *         or Lease already exists (status code 409)
     */
    @ApiOperation(value = "Lease this envelope for processing", nickname = "envelopesTeamEnvelopeEnvelopeIdLeaseLeaseIdPut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Requires S2S authentication token"),
        @ApiResponse(code = 403, message = "You do not have permission to access this teams envelopes"),
        @ApiResponse(code = 404, message = "Team or envelope not found not found"),
        @ApiResponse(code = 409, message = "Lease already exists") })
    @RequestMapping(value = "/envelopes/{team}/envelope/{envelopeId}/lease/{leaseId}",
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<Void> envelopesTeamEnvelopeEnvelopeIdLeaseLeaseIdPut(@ApiParam(value = "Team name who the envelopes belong to. Supplied S2S token will be validated against this path",required=true) @PathVariable("team") String team,@ApiParam(value = "UUID for the envelope to be leased",required=true) @PathVariable("envelopeId") UUID envelopeId,@ApiParam(value = "UUID for a lease to be created",required=true) @PathVariable("leaseId") UUID leaseId,@ApiParam(value = ""  )  @Valid @RequestBody(required = false) InlineObject lease);


    /**
     * PATCH /envelopes/{team}/envelope/{envelopeId} : Updates the status of this envelope
     *
     * @param team Team name who the envelopes belong to. Supplied S2S token will be validated against this path (required)
     * @param envelopeId UUID for the envelope to remove the lease from (required)
     * @param leaseId leaseId for the lease which has been aquired for this envelope (required)
     * @param processingStatus  (optional)
     * @return OK (status code 200)
     */
    @ApiOperation(value = "Updates the status of this envelope", nickname = "envelopesTeamEnvelopeEnvelopeIdPatch", notes = "", response = Envelope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Envelope.class) })
    @RequestMapping(value = "/envelopes/{team}/envelope/{envelopeId}",
        produces = "*/*", 
        method = RequestMethod.PATCH)
    ResponseEntity<Envelope> envelopesTeamEnvelopeEnvelopeIdPatch(@ApiParam(value = "Team name who the envelopes belong to. Supplied S2S token will be validated against this path",required=true) @PathVariable("team") String team,@ApiParam(value = "UUID for the envelope to remove the lease from",required=true) @PathVariable("envelopeId") UUID envelopeId,@ApiParam(value = "leaseId for the lease which has been aquired for this envelope" ,required=true) @RequestHeader(value="leaseId", required=true) UUID leaseId,@ApiParam(value = ""  )  @Valid @RequestBody(required = false) InlineObject1 processingStatus);


    /**
     * GET /envelopes/{team} : Gets a list of envelopes in a pending state
     *
     * @param team Team name who the envelopes belong to. Supplied S2S token will be validated against this path (required)
     * @return OK (status code 200)
     *         or Requires S2S authentication token (status code 401)
     *         or You do not have permission to access this teams envelopes (status code 403)
     *         or Team not found (status code 404)
     */
    @ApiOperation(value = "Gets a list of envelopes in a pending state", nickname = "envelopesTeamGet", notes = "", response = Envelope.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Envelope.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Requires S2S authentication token"),
        @ApiResponse(code = 403, message = "You do not have permission to access this teams envelopes"),
        @ApiResponse(code = 404, message = "Team not found") })
    @RequestMapping(value = "/envelopes/{team}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<Envelope>> envelopesTeamGet(@ApiParam(value = "Team name who the envelopes belong to. Supplied S2S token will be validated against this path",required=true) @PathVariable("team") String team);

}
