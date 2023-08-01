package app.controllers.api.rest;

import app.dto.account.AccountDTO;
import app.entities.account.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MethodNotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Api(tags = "Account REST")
@Tag(name = "Account REST", description = "API для операций с пользователем")
@RequestMapping("/api/accounts")
public interface AccountRestApi {

    @GetMapping
    @ApiOperation(value = "Get list of all Accounts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Accounts found"),
            @ApiResponse(code = 204, message = "Accounts not found")})
    ResponseEntity<Page> getAll(@PageableDefault(sort = {"id"})
                                @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
                                @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(10) Integer size);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Account by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account found"),
            @ApiResponse(code = 404, message = "Account not found")})
    ResponseEntity<AccountDTO> getById(
            @ApiParam(
                    name = "id",
                    value = "Account.id"
            )
            @PathVariable Long id);

    @GetMapping("/auth")
    @ApiOperation(value = "Get authenticated Account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Account found")})
    ResponseEntity<AccountDTO> getAuthenticatedAccount();

    @PostMapping
    @ApiOperation(value = "Create Account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Account created")})
    ResponseEntity<AccountDTO> create(
            @ApiParam(
                    name = "account",
                    value = "Account model"
            )
            @RequestBody @Valid AccountDTO accountDTO) throws MethodNotSupportedException;

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update Account by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account updated"),
            @ApiResponse(code = 404, message = "Account not found")})
    ResponseEntity<AccountDTO> update(
            @ApiParam(
                    name = "id",
                    value = "Account.id"
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "accountDTO",
                    value = "Account model"
            )
            @RequestBody AccountDTO accountDTO) throws MethodNotSupportedException;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Account by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account deleted"),
            @ApiResponse(code = 404, message = "Account not found")})
    ResponseEntity<Void> delete(
            @ApiParam(
                    name = "id",
                    value = "Account.id"
            )
            @PathVariable Long id);

    @GetMapping("/all-roles")
    @ApiOperation(value = "Get all existing roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles found"),
            @ApiResponse(code = 204, message = "No Role saved")})
    ResponseEntity<List<Role>> getAllRoles();
}