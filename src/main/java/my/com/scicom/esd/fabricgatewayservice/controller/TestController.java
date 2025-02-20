package my.com.scicom.esd.fabricgatewayservice.controller;

import my.com.scicom.esd.fabricgatewayservice.config.ConnectionConfig;
import my.com.scicom.esd.fabricgatewayservice.constant.IConstants;
import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements ITestController
{
	@Override
	public ResponseEntity<ESResponse<String>> testService( @PathVariable String version )
	{
		return ResponseEntity.ok( new ESResponse<>( IConstants.RESPONSE_STATUS_OK, "Hyperledger Fabric Gateway Service is working" ) );
	}
}
