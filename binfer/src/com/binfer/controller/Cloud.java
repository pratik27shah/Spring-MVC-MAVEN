package com.binfer.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.binfer.model.BlobOperations;
import org.springframework.ui.ModelMap;
@Controller

public class Cloud {

	private static final Logger logger = LoggerFactory.getLogger(Cloud.class);
	@RequestMapping(value="/",method = {RequestMethod.GET})
	public ModelAndView  displayCloudList(ModelMap model) throws IOException {
		List<String> filelist=new ArrayList<String>();
		BlobOperations bloboperations=new BlobOperations();
		filelist=bloboperations.showfilelist();
		model.addAttribute("filelist", filelist);     
		return new ModelAndView("welcome");
	}



	@RequestMapping(value="/update", method = {RequestMethod.GET,RequestMethod.POST})

	public ModelAndView getfileurl(@RequestParam ("files") String files,@RequestParam(value="operation", required=false) String operation,
			ModelMap model,HttpServletRequest request) throws IOException{


		String urlparameters;	String url;
		BlobOperations bloboperations=new BlobOperations();
		if(operation!=null){

			model.addAttribute("filename",files);
			String fileurl=bloboperations.getfileurl(files);
			if(fileurl==null){model.addAttribute("filename","File has been removed");}
			model.addAttribute("fileurl","file:///"+fileurl);
		
			return new ModelAndView("view");
		}
		else{

			String status=bloboperations.add(files);
			model.addAttribute("status",status);
			if(status=="False")
			{model.addAttribute("status", "File Not Found");  }
			else{
			if(files=="")
			{model.addAttribute("status", "File Name cannot be blank");}
			else
			{
			
				url=request.getRequestURL().toString()+"?files="+files+"&operation=view";
				model.addAttribute("url",url);
				model.addAttribute("status", "");
			}}
			List<String> filelist=new ArrayList<String>();
			filelist=bloboperations.showfilelist();
			model.addAttribute("filelist", filelist);  
			return new ModelAndView("welcome");
		}

	}



}