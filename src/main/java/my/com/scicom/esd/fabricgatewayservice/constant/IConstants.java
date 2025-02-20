package my.com.scicom.esd.fabricgatewayservice.constant;

public interface IConstants
{
	int RESPONSE_STATUS_OK = 1;
	int RESPONSE_STATUS_ERROR = -1;

	enum ResponseType
	{
		SUCCESS, ERROR, WARNING, INFO
	}
}
