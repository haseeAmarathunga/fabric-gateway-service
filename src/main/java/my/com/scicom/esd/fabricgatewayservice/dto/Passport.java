package my.com.scicom.esd.fabricgatewayservice.dto;

public class Passport
{
	private String passportId;
	private String nationality;
	private String name;
	private String dob;
	private String issueDate;
	private String expiryDate;

	public String getPassportId()
	{
		return passportId;
	}

	public void setPassportId( String passportId )
	{
		this.passportId = passportId;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality( String nationality )
	{
		this.nationality = nationality;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDob()
	{
		return dob;
	}

	public void setDob( String dob )
	{
		this.dob = dob;
	}

	public String getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate( String issueDate )
	{
		this.issueDate = issueDate;
	}

	public String getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate( String expiryDate )
	{
		this.expiryDate = expiryDate;
	}
}
