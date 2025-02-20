package my.com.scicom.esd.fabricgatewayservice.dto;

import my.com.scicom.esd.fabricgatewayservice.constant.IConstants;

import java.io.Serializable;

/**
 * Copyright (c) 2018. scicom.com.my - All Rights Reserved
 * Created by dharshana.d on 9/13/2018.
 */
public class ESResponse<T> implements Serializable
{
	private int status;
	private T data;
	private String message;

	public ESResponse( int status, String message )
	{
		this.status = status;
		this.message = message;
	}

	public ESResponse( int status, T data, String message )
	{
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public ESResponse()
	{
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public T getData()
	{
		return data;
	}

	public void setData( T data )
	{
		this.data = data;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage( String message )
	{
		this.message = message;
	}

}
