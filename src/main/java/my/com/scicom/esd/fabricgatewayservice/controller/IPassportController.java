package my.com.scicom.esd.fabricgatewayservice.controller;

import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import my.com.scicom.esd.fabricgatewayservice.dto.Passport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("passports")
@CrossOrigin(origins = { "*" })
public interface IPassportController
{
	@RequestMapping(method = RequestMethod.GET, path = "/v{version}", produces = "application/json")
	ResponseEntity<ESResponse<List<Passport>>> getAllPassports( @PathVariable String version );

	@RequestMapping(method = RequestMethod.POST, path = "/v{version}", produces = "application/json")
	ResponseEntity<ESResponse<Passport>> createPassport( @PathVariable String version, @RequestBody Passport passport );

	@RequestMapping(method = RequestMethod.PATCH, path = "/v{version}/validate", produces = "application/json")
	ResponseEntity<ESResponse<Boolean>> validatePassport( @PathVariable String version, @RequestBody Passport passport );
}
