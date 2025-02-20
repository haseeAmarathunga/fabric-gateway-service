package my.com.scicom.esd.fabricgatewayservice.controller;


import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("tests")
@CrossOrigin(origins = { "*" })
public interface ITestController
{
	@RequestMapping(method = RequestMethod.GET, path = "/v{version}", produces = "application/json")
	ResponseEntity<ESResponse<String>> testService( @PathVariable String version );
}
