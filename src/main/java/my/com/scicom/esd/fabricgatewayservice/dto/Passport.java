package my.com.scicom.esd.fabricgatewayservice.dto;

public class Passport
{
	private String passportNo;
	private String nationality;
	private String name;
	private String dob;
	private String gender;
	private String issueDate;
	private String expiryDate;

	public String getPassportNo()
	{
		return passportNo;
	}

	public void setPassportNo( String passportNo )
	{
		this.passportNo = passportNo;
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

	public String getGender()
	{
		return gender;
	}

	public void setGender( String gender )
	{
		this.gender = gender;
	}
}
