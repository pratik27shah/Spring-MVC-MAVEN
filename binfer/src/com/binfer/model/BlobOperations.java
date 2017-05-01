/**
 * 
 */
package com.binfer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.catalina.core.ApplicationContext;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.filesystem.reference.FilesystemConstants;
import org.jclouds.openstack.swift.v1.features.ObjectApi;
import org.jclouds.rackspace.cloudfiles.v1.CloudFilesApi;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.binfer.controller.Cloud;
import com.binfer.parameters.Blobparameters;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;

/**
 * @author PRATIK SHAH
 *
 */
public class BlobOperations {

	private static final Logger logger = Logger.getLogger(BlobOperations.class);
	@SuppressWarnings("finally")
	public String add(String filename) throws IOException
	{
		String success="True";
	
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext contextval = new ClassPathXmlApplicationContext("spring.xml");
		Blobparameters blobparameter = (Blobparameters)contextval.getBean("blobparameters");
		String property=blobparameter.getProperty();
		try{
		Properties properties = new Properties();
		properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property);
	
		String containerName = blobparameter.getContainername();
		BlobStoreContext context = ContextBuilder.newBuilder("filesystem")
				.overrides(properties)
				.buildView(BlobStoreContext.class);

		// create a container in the default location
		BlobStore blobStore = context.getBlobStore();
		blobStore.createContainerInLocation(null, containerName);
		String name=removeextraspaces(filename);    
		ByteSource payload = Files.asByteSource(new File(filename));
		Blob blob = blobStore.blobBuilder(name) // you can use folders via blobBuilder(folderName + "/sushi.jpg")
				.payload(payload)
				.contentLength(payload.size())
				.build();

		blobStore.putBlob(containerName, blob) ;
		File folder = new File(   (String) properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property));
		String url=folder.getAbsolutePath()+"\\"+containerName+"\\"+name;
		success=url;

		context.close();
		}
		catch(Exception ex){logger.error(ex.toString());success="False";}
finally{return success;}

	}

	public List<String> showfilelist()
	{
		List<String> filelist=new ArrayList<String>();
		ClassPathXmlApplicationContext contextval = new ClassPathXmlApplicationContext("spring.xml");
		Blobparameters blobparameter = (Blobparameters)contextval.getBean("blobparameters");
		String property=blobparameter.getProperty();
		Properties properties = new Properties();
		properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property);
		File folder = new File(   (String) properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property));
				if(folder.listFiles()!=null){
		File[] listOfFiles1 = folder.listFiles();
		File[] listOfFiles = listOfFiles1[0].listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			filelist.add(listOfFiles[i].getName());
		}
if(filelist.size()==0)filelist.add("No File Found");}
		return filelist;
	}

	@SuppressWarnings("finally")
	public String getfileurl(String files) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext contextval = new ClassPathXmlApplicationContext("spring.xml");
		Blobparameters blobparameter = (Blobparameters)contextval.getBean("blobparameters");
		String url=null;
		try{
		String property=blobparameter.getProperty();
		Properties properties = new Properties();
		properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property);
		String name=removeextraspaces(files);
		String containerName = blobparameter.getContainername();
		File folder = new File(   (String) properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, property));
		url=folder.getAbsolutePath()+"\\"+containerName+"\\"+name;
		}
		catch(Exception ex){url=null;logger.error(ex.toString());}
		finally{return url;}
	}
	
	
	public String removeextraspaces(String filename)
	{
		String[] nameval=filename.split("[^a-zA-Z0-9-.]");
		String name=nameval[nameval.length-1];
		return name;
		
		
	}
}
