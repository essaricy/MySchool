package com.myschool.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class UserActivity.
 */
public class UserActivity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The module. */
	private String module;

	/** The request url. */
	private String requestUrl;

	/** The requested time. */
	private Date requestedTime;

	/** The service latency. */
	private long serviceLatency;

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Sets the module.
	 *
	 * @param module the new module
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Sets the request url.
	 *
	 * @param requestUrl the new request url
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Gets the requested time.
	 *
	 * @return the requested time
	 */
	public Date getRequestedTime() {
		return requestedTime;
	}

	/**
	 * Sets the requested time.
	 *
	 * @param requestedTime the new requested time
	 */
	public void setRequestedTime(Date requestedTime) {
		this.requestedTime = requestedTime;
	}

	/**
	 * Gets the service latency.
	 *
	 * @return the service latency
	 */
	public long getServiceLatency() {
		return serviceLatency;
	}

	/**
	 * Sets the service latency.
	 *
	 * @param serviceLatency the new service latency
	 */
	public void setServiceLatency(long serviceLatency) {
		this.serviceLatency = serviceLatency;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString() {
	    final String SEPARATOR = ", ";
	    StringBuilder retValue = new StringBuilder();
	    retValue.append("UserActivity ( ")
	        .append(super.toString()).append(SEPARATOR)
	        .append("module = ").append(this.module).append(SEPARATOR)
	        .append("requestUrl = ").append(this.requestUrl).append(SEPARATOR)
	        .append("requestedTime = ").append(this.requestedTime).append(SEPARATOR)
	        .append("serviceLatency = ").append(this.serviceLatency).append(SEPARATOR)
	        .append(" )\n");
	    return retValue.toString();
	}

}
