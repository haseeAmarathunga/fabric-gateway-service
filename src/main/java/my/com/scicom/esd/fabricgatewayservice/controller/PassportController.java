package my.com.scicom.esd.fabricgatewayservice.controller;

import my.com.scicom.esd.fabricgatewayservice.constant.IConstants;
import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import my.com.scicom.esd.fabricgatewayservice.dto.Passport;
import my.com.scicom.esd.fabricgatewayservice.service.IPassportService;
import my.com.scicom.esd.fabricgatewayservice.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PassportController implements IPassportController
{
	@Autowired
	private Environment environment;

	@Autowired
	private IPassportService passportService;

	@Override
	public ResponseEntity<ESResponse<List<Passport>>> getAllPassports( @PathVariable String version )
	{
		if ( Utility.isNull( version ) )
		{
			return ResponseEntity.badRequest().body( new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, null, environment.getProperty( "version.missing" ) ) );
		}
		return ResponseEntity.ok( passportService.getAllPassports() );
	}

	@Override
	public ResponseEntity<ESResponse<Passport>> createPassport( @PathVariable String version, @RequestBody Passport passport )
	{
		if ( Utility.isNull( version ) )
		{
			return ResponseEntity.badRequest().body( new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, null, environment.getProperty( "version.missing" ) ) );
		}
		if ( passport == null )
		{

			return ResponseEntity.badRequest().body( new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, null, environment.getProperty( "req.body.missing" ) ) );
		}
		return ResponseEntity.ok( passportService.createPassport( passport ) );
	}

	@Override
	public ResponseEntity<ESResponse<Boolean>> validatePassport( @PathVariable String version, @RequestBody Passport passport )
	{
		if ( Utility.isNull( version ) )
		{
			return ResponseEntity.badRequest().body( new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, null, environment.getProperty( "version.missing" ) ) );
		}
		if ( passport == null )
		{

			return ResponseEntity.badRequest().body( new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, null, environment.getProperty( "req.body.missing" ) ) );
		}
		return ResponseEntity.ok( passportService.validatePassport( passport ) );
	}
}
