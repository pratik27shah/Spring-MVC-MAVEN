/**
 * 
 */
package com.binfer.parameters;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author PRATIK SHAH
 *
 */
public class Blobparameters {
	private String containername;
	private String property;
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getContainername() {
		return containername;
	}

	public void setContainername(String containername) {
		this.containername = containername;
	}
}
