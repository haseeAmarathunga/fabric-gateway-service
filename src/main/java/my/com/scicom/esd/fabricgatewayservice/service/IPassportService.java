package my.com.scicom.esd.fabricgatewayservice.service;

import my.com.scicom.esd.fabricgatewayservice.dto.ESResponse;
import my.com.scicom.esd.fabricgatewayservice.dto.Passport;

import java.util.List;

public interface IPassportService
{
	ESResponse<List<Passport>> getAllPassports();

	ESResponse<Passport> createPassport( Passport passport );

	ESResponse<Boolean> validatePassport( Passport passport );
}
