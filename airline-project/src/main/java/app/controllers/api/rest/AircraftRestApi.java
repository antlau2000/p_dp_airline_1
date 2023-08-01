package app.controllers.api.rest;

import app.dto.AircraftDTO;
import app.entities.Aircraft;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Api(tags = "Aircraft REST")
@Tag(name = "Aircraft REST", description = "API для операций с самолётом")
@RequestMapping("/api/aircrafts")
public interface AircraftRestApi {

    @GetMapping
    @ApiOperation(value = "Get list of all Aircrafts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aircrafts found"),
            @ApiResponse(code = 204, message = "Aircrafts not found")})
    ResponseEntity<Page<AircraftDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(10) Integer size);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Aircraft by it's \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aircraft found"),
            @ApiResponse(code = 404, message = "Aircraft not found")})
    ResponseEntity<AircraftDTO> getById(
            @ApiParam(name = "id", value = "Aircraft.id")
            @PathVariable("id") Long id);

    @PostMapping
    @ApiOperation(value = "Create new Aircraft")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Aircraft created")})
    ResponseEntity<Aircraft> create(
            @ApiParam(
                    name = "Aircraft",
                    value = "Aircraft model"
            )
            @RequestBody @Valid AircraftDTO aircraftDTO);

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Aircraft by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aircraft updated"),
            @ApiResponse(code = 404, message = "Aircraft not found")})
    ResponseEntity<Aircraft> update(
            @ApiParam(
                    name = "id",
                    value = "Aircraft.id"
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "Aircraft",
                    value = "Aircraft model"
            )
            @RequestBody @Valid AircraftDTO aircraftDTO);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Aircraft by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Aircraft deleted"),
            @ApiResponse(code = 404, message = "Aircraft not found")})
    ResponseEntity<HttpStatus> delete(
            @ApiParam(
                    name = "id",
                    value = "Aircraft.id"
            )
            @PathVariable("id") Long id);
}