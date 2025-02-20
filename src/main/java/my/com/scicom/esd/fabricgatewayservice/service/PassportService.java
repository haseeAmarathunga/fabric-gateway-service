package my.com.scicom.esd.fabricgatewayservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import my.com.scicom.esd.fabricgatewayservice.config.ConnectionConfig;
import my.com.scicom.esd.fabricgatewayservice.constant.IConstants;
import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import my.com.scicom.esd.fabricgatewayservice.dto.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PassportService implements IPassportService
{
	@Autowired
	private Environment environment;

	@Autowired
	private ConnectionConfig connectionConfig;

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public ESResponse<List<Passport>> getAllPassports()
	{
		try
		{
			System.out.println( "\n--> Evaluate Transaction: getAllPassports, function returns all the current passports on the ledger" );
			byte[] result = connectionConfig.contract.evaluateTransaction( "GetAllPassports" );
			// String resultStr = prettyJson( result );
			// System.out.println( "Result => " + resultStr );
			ObjectMapper mapper = getObjectMapper();
			List<Passport> passports = mapper.readValue( result, new TypeReference<>()
			{
			} );
			return new ESResponse<>( IConstants.RESPONSE_STATUS_OK, passports, environment.getProperty( "passport.found" ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, environment.getProperty( "passport.search.failed" ) );
		}
	}

	@Override
	public ESResponse<Passport> createPassport( Passport passport )
	{
		try
		{
			System.out.println( "\n--> Submit Transaction: CreatePassport, creates new passport with ID, Nationality, Name, DOB, Issue Date and Expiry Date arguments" );
			byte[] result = connectionConfig.contract.submitTransaction( "CreatePassport", passport.getPassportId(), passport.getNationality(), passport.getName(), passport.getDob(), passport.getIssueDate(), passport.getExpiryDate() );
			ObjectMapper mapper = getObjectMapper();
			Passport passportSaved = mapper.readValue( result, Passport.class );
			return new ESResponse<>( IConstants.RESPONSE_STATUS_OK, passportSaved, environment.getProperty( "passport.create.success" ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, environment.getProperty( "passport.create.failed" ) );
		}
	}

	@Override
	public ESResponse<Boolean> validatePassport( Passport passport )
	{
		try
		{
			System.out.println("\n--> Submit Transaction: ValidatePassport, validate passport with Passport ID, Nationality, Name, DOB, Issue Date and Expiry Date arguments");
			byte[] result = connectionConfig.contract.submitTransaction( "ValidatePassport", passport.getPassportId(), passport.getNationality(), passport.getName(), passport.getDob(), passport.getIssueDate(), passport.getExpiryDate() );
			ObjectMapper mapper = getObjectMapper();
			Boolean resultBool = mapper.readValue( result, Boolean.class );
			return new ESResponse<>( IConstants.RESPONSE_STATUS_OK, resultBool, environment.getProperty( "passport.validation.success" ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return new ESResponse<>( IConstants.RESPONSE_STATUS_ERROR, environment.getProperty( "passport.validation.failed" ) );
		}
	}

	private String prettyJson( final byte[] json )
	{
		return prettyJson( new String( json, StandardCharsets.UTF_8 ) );
	}

	private String prettyJson( final String json )
	{
		var parsedJson = JsonParser.parseString( json );
		return gson.toJson( parsedJson );
	}

	private ObjectMapper getObjectMapper()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule( new JavaTimeModule() );
		mapper.configure( DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false );
		mapper.configure( DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true );
		mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		return mapper;
	}
}
