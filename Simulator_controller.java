package com.hdsoft.hdpay.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hdsoft.common.Common_Utils;
import com.hdsoft.hdpay.Repositories.Simulator001;
import com.hdsoft.hdpay.Repositories.Simulator002;
import com.hdsoft.hdpay.Repositories.web_service_001;
import com.hdsoft.hdpay.models.Model;
import com.hdsoft.hdpay.models.ODEX_Simulator_Model;

import com.itextpdf.io.exceptions.IOException;

@Controller
public class ODEX_Simulator {

	      @Autowired
	      private ODEX_Simulator_Model model;

	      Common_Utils cb = new Common_Utils();
	
	      private static final Logger logger = LogManager.getLogger(ODEX_Simulator.class);

	   @RequestMapping(value = { "/simulator001" }, method = RequestMethod.GET)
	   public ModelAndView m11(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException 
	   {
		  
		  ModelAndView mv = new ModelAndView();
		  mv.setViewName("HDPAY/Configuration/API_Configuration_old2");

		  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		  response.setHeader("Pragma", "no-cache");
		  response.setHeader("Expires", "0");

	   return mv;
	   }

	   @RequestMapping(value = { "/simulator002" }, method = RequestMethod.GET)
	   public ModelAndView m16(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException 
	   {
		  
		   ModelAndView mv = new ModelAndView();
		   mv.setViewName("HDPAY/Configuration/simulator002");

		   response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		   response.setHeader("Pragma", "no-cache");
		   response.setHeader("Expires", "0");

		return mv;
	   }

	   @RequestMapping(value = {"/form/simulator002" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String simulator002(@RequestParam("SUBORGCODE") String SUBORGCODE,@RequestParam("PAYTYPE") String PAYTYPE, @RequestParam("RESID") String RESID,@RequestParam("SERVICECD") String SERVICECD, @RequestParam("CHCODE") String CHCODE,
	   @RequestParam("FLOW") String FLOW, @RequestParam("RESDESC") String RESDESC,@RequestParam("RESCODE") String RESCODE, @RequestParam("RESCATEGORY") String RESCATEGORY,
	   @RequestParam("RESDATA") String RESDATA, @RequestParam("FROM") String FROM, @RequestParam("TO") String TO,HttpServletRequest request, HttpServletResponse response) 
	   {
		  
		   JsonObject details = new JsonObject();

		   details = model.simulator002(SUBORGCODE, CHCODE, PAYTYPE, FLOW, RESCODE, RESCATEGORY, RESID, RESDESC, RESDATA,FROM, TO, SERVICECD);

		   return details.toString();
	   }

	   @RequestMapping(value = {"/form/simulator001" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String simulator001(@RequestParam("SUBORGCODE") String SUBORGCODE,@RequestParam("SYSCODE") String SYSCODE, @RequestParam("CHCODE") String CHCODE,@RequestParam("SERVNAME") String SERVNAME, @RequestParam("SERVICECD") String SERVICECD,
	   @RequestParam("FLOW") String FLOW, @RequestParam("FORMAT") String FORMAT,@RequestParam("PROTOCOL") String PROTOCOL, @RequestParam("URI") String URI,@RequestParam("METHOD") String METHOD, @RequestParam("CHTYPE") String CHTYPE,
	   @RequestParam("PAYLOAD") String PAYLOAD, @RequestParam("HEADERID") String HEADERID,@RequestParam("SIMIDPATH") String SIMIDPATH, HttpServletRequest request, HttpServletResponse response) 
	   {

		   JsonObject details = new JsonObject();

		   details = model.simulator001(SUBORGCODE, SYSCODE, CHCODE, SERVICECD, SERVNAME, FORMAT, PROTOCOL, METHOD, CHTYPE,URI, PAYLOAD, HEADERID, FLOW, SIMIDPATH);

		   return details.toString();
	   }

	   @RequestMapping(value = {"/sugges/sim002" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String Suggestions_APIcode_Retrieve(@RequestParam("term") String term,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException 
	   {
		
		   JsonArray details = new JsonArray();

		   details = model.Get_API_Codes(term);
		   
		   System.out.println(details);

		   return details.toString();
	   }

	   @RequestMapping(value = {"/sugges/sim001" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String Suggestions_APIcode_Retrieve1(@RequestParam("term") String term,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException 
	   {
		   
		   JsonArray details = new JsonArray();

		   details = model.Get_API_Codes1(term);

		   return details.toString();
	    }

	   @RequestMapping(value = {"/Find/sim002_Service" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String Find_Api_Information(@ModelAttribute Simulator002 Info, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException 
	   {
		   JsonObject details = new JsonObject();

		   details = model.Find_sim_Service(Info.getSERVICEID(), Info.getRESDESC(), Info.getCHCODE());

		   return details.toString();
       }

	   @RequestMapping(value = {"/Find/sim001_Service" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String Find_Api_Information1(@ModelAttribute Simulator001 Info, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException 
	   {
		   JsonObject details = new JsonObject();

		   details = model.Find_sim_Service1(Info.getSERVICECD(), Info.getSERVNAME(), Info.getCHCODE());

		   return details.toString();

	   }

	  

	   @RequestMapping(value = {"/api/payment-gateway/v1/bankCollection/getPymtDetails/{pymtNo}" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody ResponseEntity<String> Generate_Muse_Recon(@PathVariable(name = "pymtNo", required = false) String pymtNo, @RequestBody String Body_MSG,HttpServletRequest request, HttpServletResponse response) 
	   {

		  JsonObject details = new JsonObject();
		  HttpStatus c = HttpStatus.OK;
		 JsonObject cd= model.Read_sim2(pymtNo);

		  String Request = cd.toString();

		  JsonObject Request2 = cb.StringToJsonObject(Request);
		  details=Request2.get("RESPONSE_DATA").getAsJsonObject();
		  String RESCODE = Request2.get("RESCODE").getAsString();

		  if (RESCODE.equals("400")) {c = HttpStatus.BAD_REQUEST;	}
          else if (RESCODE.equals("200")) {c = HttpStatus.OK;}
		  else {c = HttpStatus.INTERNAL_SERVER_ERROR;}
		
		  return new ResponseEntity<>(details.toString(), c);
	   }

	   @RequestMapping(value = {"/api/payment-gateway/v1/bankCollection/approvePymtDetails" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody ResponseEntity<String> Generate_Muse_Recon2(@PathVariable(name = "pymtNo", required = false) String pymtNo, @RequestBody String Body_MSG,HttpServletRequest request, HttpServletResponse response) 
	   {
		   JsonObject details = new JsonObject();

		   JsonObject cd = model.Read_sim(Body_MSG);
		   String Request =cd.toString();
		   HttpStatus c = HttpStatus.OK;

		   JsonObject Request2 = cb.StringToJsonObject(Request);
		   details=Request2.get("RESPONSE_DATA").getAsJsonObject();
		   String RESCODE = Request2.get("RESCODE").getAsString();

		   if (RESCODE.equals("400")) {c = HttpStatus.BAD_REQUEST;}
	       else if (RESCODE.equals("200")) {c = HttpStatus.OK;} 
		   else {c = HttpStatus.INTERNAL_SERVER_ERROR;}
		
		   return new ResponseEntity<>(details.toString(), c);
	    }
	   
	   @RequestMapping(value = {"/TEST/Document/{chcode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   public @ResponseBody String Find_Api_Information2(@PathVariable(name = "chcode", required = false) String chcode, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException 
	   {
		   JsonObject details = new JsonObject();

		   details = model.Get_document(chcode);

		   return details.toString();

	   }

}

