package com.hdsoft.hdpay.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.zip.CRC32;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hdsoft.common.Common_Utils;
import com.hdsoft.hdpay.Repositories.Simulator001;
import com.hdsoft.hdpay.Repositories.Simulator002;
import com.hdsoft.hdpay.Repositories.document_link3;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ODEX_Simulator_Model {
	
	   public JdbcTemplate Jdbctemplate;

	   @Autowired
	   public void setJdbctqemplate(HikariDataSource Datasource) 
	   {
          Jdbctemplate = new JdbcTemplate(Datasource);
	   }

	   private static final Logger logger = LogManager.getLogger(Configuration_Modal.class);
	
	   Common_Utils cb = new Common_Utils();
	
	   public JsonObject simulator002(String SUBORGCODE, String CHCODE, String PAYTYPE, String FLOW, String RESCODE,String RESCATEGORY, String RESID, String RESDESC, String RESDATA, String FROM, String TO,String SERVICECD) 
	    {
		    JsonObject details = new JsonObject();
		 
            try 
             {
        	
			   if (RESCATEGORY.equalsIgnoreCase("Success")) {
				   RESCATEGORY = "S";
			   } else if (RESCATEGORY.equalsIgnoreCase("Failure")) {
				   RESCATEGORY = "F";
			   } else if (RESCATEGORY.equalsIgnoreCase("Timeout")) {
				   RESCATEGORY = "T";
			   }

		       FLOW = FLOW.equalsIgnoreCase("inward") ? "I" : "O";

		       String sql = "INSERT INTO simulator002 (SUBORGCODE, CHCODE, PAYTYPE, FLOW,RESCODE, RESTYPE, RESNAME, RESDESC, RESPONSE_DATA, RANGE_FROM, RANGE_TO, SERVICEID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		       int status = Jdbctemplate.update(sql, new Object[] { SUBORGCODE, CHCODE, PAYTYPE, String.valueOf(FLOW), RESCODE,RESCATEGORY, RESID, RESDESC, RESDATA, FROM, TO, SERVICECD });

		       System.out.println("inserted successfully" + status);
              
            }catch (Exception e) {
            	  
			    details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());

			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		        }

		  return details;
	    }

	   public JsonObject simulator001(String SUBORGCODE, String SYSCODE, String CHCODE, String SERVICECD, String SERVNAME,String FORMAT, String PROTOCOL, String METHOD, String CHTYPE, String URI, String PAYLOAD, String HEADERID,String FLOW, String SIMIDPATH) {
		
	    	JsonObject details = new JsonObject();
	    	
		    try
		      {

		        FLOW = FLOW.equalsIgnoreCase("inward") ? "I" : "O";

		        String sql = "INSERT INTO simulator001 (SUBORGCODE,SYSCODE, CHCODE,SERVICECD,SERVNAME, FORMAT,PROTOCOL, METHOD,CHTYPE,URI,PAYLOAD,HEADERID,FLOW, SIMIDPATH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		        int status = Jdbctemplate.update(sql, new Object[] { SUBORGCODE, SYSCODE, CHCODE, SERVICECD, SERVNAME, FORMAT,PROTOCOL, METHOD, CHTYPE, URI, PAYLOAD, HEADERID, FLOW, SIMIDPATH });

		        System.out.println("inserted successfully" + status);
		    
	        }catch (Exception e) {
	    	   
			    details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());

			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		    }

		  return details;
	     }

	   public JsonArray Get_API_Codes(String term) 
	   {
		
		      JsonArray Event_Codes = new JsonArray();
		
		      JsonObject Informations2 = new JsonObject();
		
		   try 
		   {

			   String sql = "select * from simulator002 where CHCODE LIKE upper(?) or CHCODE LIKE lower(?)  ORDER BY TO_NUMBER(SERVICEID) ASC";

			   List<Simulator002> API_Info = Jdbctemplate.query(sql, new Object[] { "%" + term + "%", "%" + term + "%" },new sim002_Mapper());

			   for (int i = 0; i < API_Info.size(); i++) {
				   JsonObject Informations = new JsonObject(); 
				 String CHCODE = API_Info.get(i).getCHCODE();
				 String RESDESC = API_Info.get(i).getRESDESC();
				 String SERVICEID = API_Info.get(i).getSERVICEID();
				 String RESTYPE = API_Info.get(i).getRESTYPE();
				 
				if (RESTYPE.equalsIgnoreCase("S")) {
					RESTYPE = "Success";
				} else if (RESTYPE.equalsIgnoreCase("F")) {
					RESTYPE = "Failure";
				} else if (RESTYPE.equalsIgnoreCase("T")) {
					RESTYPE = "Timeout";
				}

				Informations.addProperty("label", CHCODE + " (" + SERVICEID + "-" + RESTYPE + "-" + RESDESC + ")");
				
				Informations.addProperty("id", CHCODE + "|" + SERVICEID + "|" + RESTYPE + "|" + RESDESC);
				
				Event_Codes.add(Informations);
			   
			   }

		    } catch (Exception e) {
			
		    	Informations2.addProperty("Result", "Failed");
			
		    	Informations2.addProperty("Message", e.getLocalizedMessage());
			
		    	Event_Codes.add(Informations2);

			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		     }

		   return Event_Codes;
	    }

	    
	   public JsonObject Find_sim_Service(String Service_Id, String RESDESC, String Channel_Id) {
		
	    	  JsonObject details = new JsonObject();

		    try 
		     {
			    String sql = "SELECT * FROM simulator002 WHERE SERVICEID =? AND RESDESC =? AND CHCODE =?";

			    logger.debug("Exception in Get_API_Codes :::: " + sql);
			    
			    List<Simulator002> API_Info = Jdbctemplate.query(sql, new Object[] { Service_Id, RESDESC, Channel_Id },new sim002_Mapper());

			    logger.debug("Exception in Get_API_Codes :::: " + API_Info);
			
			    if (API_Info.size() != 0) {
				    String cd = API_Info.get(0).getRESTYPE();
				
				   if (cd.equalsIgnoreCase("S")) {
					   cd = "Success";
				   } else if (cd.equalsIgnoreCase("F")) {
					   cd = "Failure";
				   } else if (cd.equalsIgnoreCase("T")) {
					   cd = "Timeout";
				   }
				

				details.addProperty("SUBORGCODE", API_Info.get(0).getSUBORGCODE());
				details.addProperty("CHCODE", API_Info.get(0).getCHCODE());
				details.addProperty("SERVICEID", API_Info.get(0).getSERVICEID());
				details.addProperty("PAYTYPE", API_Info.get(0).getPAYTYPE());
				details.addProperty("RANGE_FROM", API_Info.get(0).getRANGE_FROM());
				details.addProperty("RANGE_TO", API_Info.get(0).getRANGE_TO());
				details.addProperty("RESCODE", API_Info.get(0).getRESCODE());
				details.addProperty("RESDESC", API_Info.get(0).getRESDESC());
				String cb = API_Info.get(0).getFLOW().equalsIgnoreCase("o") ? "Outward" : "Inward";
				details.addProperty("RESNAME", API_Info.get(0).getRESNAME());
				details.addProperty("RESPONSE_DATA", API_Info.get(0).getRESPONSE_DATA());
				details.addProperty("RESTYPE", cd);
				details.addProperty("FLOW", cb);

			    }

			    details.addProperty("Result", API_Info.size() != 0 ? "Success" : "Failed");
			    details.addProperty("Message", API_Info.size() != 0 ? "API Details Found !!" : "API Details Not Found");
		
		    } catch (Exception e) {
			
			    details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());

			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		    }

		 return details;
	   }

	   public JsonObject Find_sim_Service1(String Service_Id, String RESDESC, String Channel_Id) {
		
		     JsonObject details = new JsonObject();
		     
		     System.out.println("Service_Id  ="+Service_Id +",RESDESC ="+RESDESC+",Channel_Id ="+Channel_Id);

		     try 
		      {
			    String sql = "SELECT * FROM simulator001 WHERE SERVICECD =? AND SERVNAME =? AND CHCODE =?";

			    List<Simulator001> API_Info = Jdbctemplate.query(sql, new Object[] { RESDESC, Channel_Id, Service_Id },new sim001_Mapper());

			    System.out.println("API>info" + API_Info.size());
			    
			    
			

			    if (API_Info.size() != 0) {

				   details.addProperty("SUBORGCODE", API_Info.get(0).getSUBORGCODE());
				   details.addProperty("CHCODE", API_Info.get(0).getCHCODE());
				   details.addProperty("SERVICECD", API_Info.get(0).getSERVICECD());
				   details.addProperty("SERVNAME", API_Info.get(0).getSERVNAME());
				   details.addProperty("FORMAT", API_Info.get(0).getFORMAT());
				   details.addProperty("PROTOCOL", API_Info.get(0).getPROTOCOL());
				   details.addProperty("METHOD", API_Info.get(0).getMETHOD());
				   details.addProperty("SYSCODE", API_Info.get(0).getSYSCODE());
			       String cb = API_Info.get(0).getFLOW().equalsIgnoreCase("o") ? "Outward" : "Inward";
			       details.addProperty("FLOW", cb);
				   details.addProperty("CHTYPE", API_Info.get(0).getCHTYPE());
				   details.addProperty("URI", API_Info.get(0).getURI());
				   details.addProperty("PAYLOAD", API_Info.get(0).getPAYLOAD());
				   details.addProperty("SIMIDPATH", API_Info.get(0).getSIMIDPATH());
				   details.addProperty("HEADERID", API_Info.get(0).getHEADERID());
				
			     }

			    details.addProperty("Result", API_Info.size() != 0 ? "Success" : "Failed");
			    details.addProperty("Message", API_Info.size() != 0 ? "API Details Found !!" : "API Details Not Found");
		
		    } catch (Exception e) {
			    
		    	details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());

			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		    }

		 return details;
	    
	   }

	    
	   public JsonArray Get_API_Codes1(String term) {
		
	    	 JsonArray Event_Codes = new JsonArray();
		
	    	 JsonObject Informations2 = new JsonObject();
		
	    	try {

			    String sql = "select * from simulator001 where CHCODE LIKE upper(?) or CHCODE LIKE lower(?) ORDER BY TO_NUMBER(SERVICECD) ASC";

			    List<Simulator001> API_Info = Jdbctemplate.query(sql, new Object[] { "%" + term + "%", "%" + term + "%" },new sim001_Mapper());

			    for (int i = 0; i < API_Info.size(); i++) {
			    	
			       JsonObject Informations = new JsonObject();	
				
			       String CHCODE = API_Info.get(i).getCHCODE();
				   String RESDESC = API_Info.get(i).getSERVNAME();
				   String SERVICEID = API_Info.get(i).getSERVICECD();

				   Informations.addProperty("label", CHCODE + " (" + SERVICEID + "-" + RESDESC + ")");
				   Informations.addProperty("id", CHCODE + "|" + SERVICEID + "|" + RESDESC);

				   Event_Codes.add(Informations);
			     }

		    } catch (Exception e) {
			
		    	Informations2.addProperty("Result", "Failed");
			    Informations2.addProperty("Message", e.getLocalizedMessage());
			    Event_Codes.add(Informations2);
			    
			    logger.debug("Exception in Find_API_Service :::: " + e.getLocalizedMessage());
		      }

		 return Event_Codes;
	   }

	   public JsonObject Api_Service2(String from, String to) {
		 
	    	 JsonObject details = new JsonObject();
		
	    	 Common_Utils util = new Common_Utils();

	    	 try {
			 
	    		 int TO = Integer.parseInt(to);
			
	    		 int From = Integer.parseInt(from);
			

				 if (From > 100 && TO <= 200) {
						to = "200";
				} else if (From > 200 && TO <= 250) {
						to = "250";
				} else if (From > 250 && TO <= 300) {
						to = "300";
				} else if (From > 300 && TO <= 350) {
						to = "350";
				} else if (From > 350 && TO <= 400) {
						to = "400";
				} else if (TO >= 401) {
						to = "1045234";
				}

			
			    String sql = "SELECT * FROM simulator002 WHERE RANGE_TO = ?";
			    logger.debug("Executing query with from=" + from + " and to=" + to);

			    List<Simulator002> API_Info = Jdbctemplate.query(sql, new Object[] { to }, new sim002_Mapper());
			    logger.debug("Query result: " + API_Info.size());

			if (!API_Info.isEmpty()) {
				
				JsonObject ad = util.StringToJsonObject(API_Info.get(0).getRESPONSE_DATA());
				details.add("RESPONSE_DATA", ad);

				logger.debug("RESPONSE_DATA returned: " + API_Info.get(0).getRESPONSE_DATA());

			} else {
				
				details.addProperty("Result", "Failed");
				details.addProperty("Message", "No data found for the given range.");
			   }

		    } catch (Exception e) {
			
			    details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());
			    logger.error("Exception in Api_Service2: ", e.getLocalizedMessage());
		     }

		return details;
	   }

	   public JsonObject Read_sim(String body_MSG) {
		
	    	JsonObject details = new JsonObject();
      
	    	try
              {
		
	    		JsonObject Request = cb.StringToJsonObject(body_MSG);

		        String odexPymtRefNo = Request.get("odexPymtRefNo").getAsString();
		        String range = odexPymtRefNo.length() >= 3 ? odexPymtRefNo.substring(odexPymtRefNo.length() - 3): odexPymtRefNo;

		        int TO = Integer.parseInt(range);

				if (TO >= 100 && TO <= 200) {
					range = "200";
				} else if (TO >= 201 && TO <= 250) {
					range = "250";
				} else if (TO >= 251 && TO <= 300) {
					range = "300";
				} else if (TO >= 301 && TO <= 330) {
					range = "330";
				} else if (TO >= 331 && TO <= 350) {
					range = "350";
				} else if (TO >= 351 && TO <= 370) {
					range = "370";
				} else if (TO >= 371 && TO <= 400) {
					range = "400";
				} else if (TO >= 401 && TO <= 430) {
					range = "430";
				} else if (TO >= 431 && TO <= 460) {
					range = "460";
				} else {
					range = "600";
				}

		        String header = "003";
		        String sql = "SELECT * FROM simulator002 WHERE SERVICEID=? and RANGE_TO = ?";
		
		        logger.debug("Executing query with from=" + header + " and to=" + range);

		        List<Simulator002> API_Info = Jdbctemplate.query(sql, new Object[] { header, range }, new sim002_Mapper());

		        JsonObject cd = cb.StringToJsonObject(API_Info.get(0).getRESPONSE_DATA());

		        details.add("RESPONSE_DATA", cd);
			    details.addProperty("RESCODE", API_Info.get(0).getRESCODE());

		
             }catch (Exception e) {
			
            	details.addProperty("Result", "Failed");
			    details.addProperty("Message", e.getLocalizedMessage());
			
			    logger.error("Exception in Api_Service2: ", e.getLocalizedMessage());
		      }
		return details;
	     
	   }

	   public JsonObject Read_sim2(String pymtNo) {
		
	    	JsonObject details = new JsonObject();
       
	    	try 
	    	 {
		
	    		if (pymtNo != null) {
			
	    		String last = pymtNo.length() >= 3 ? pymtNo.substring(pymtNo.length() - 3) : pymtNo;

			          if (last.matches("\\d{3}")) {

								String header = "002";
								int TO = Integer.parseInt(last);

								if (TO >= 100 && TO <= 150) {
									last = "150";
								} else if (TO >= 151 && TO <= 200) {
									last = "200";
								} else if (TO >= 201 && TO <= 250) {
									last = "250";
								} else if (TO >= 251 && TO <= 300) {
									last = "300";
								} else if (TO >= 301 && TO <= 350) {
									last = "350";
								} else if (TO >= 351 && TO <= 400) {
									last = "400";
								} else {
									last = "600";
								}

				     String sql = "SELECT * FROM simulator002 WHERE SERVICEID=? and RANGE_TO = ?";
				     logger.debug("Executing query with from=" + header + " and to=" + last);
 
				     List<Simulator002> API_Info = Jdbctemplate.query(sql, new Object[] { header, last },new sim002_Mapper());

				     JsonObject cd = cb.StringToJsonObject(API_Info.get(0).getRESPONSE_DATA());

				     details.add("RESPONSE_DATA", cd);
				     details.addProperty("RESCODE", API_Info.get(0).getRESCODE());
				
			        }
		          
	    		 }
            
	    	 }catch (Exception e) {
			
	    		 details.addProperty("Result", "Failed");
			     details.addProperty("Message", e.getLocalizedMessage());
			
			     logger.error("Exception in Api_Service2: ", e.getLocalizedMessage());
		     }
		
	    	 return details;
	
	    }

	    public JsonObject Get_document(String chcode) {
		
		         JsonObject details =new JsonObject();
		     try
		     {
		         String sql = "SELECT * FROM document_link3 WHERE CHCODE=?";
			     logger.debug("Executing query with " + chcode);
			     
			     String upperchcode = chcode.toUpperCase();
			   
			     List<document_link3> API_Info = Jdbctemplate.query(sql, new Object[] { upperchcode},new DOCUMENT_Mapper());

			     if(API_Info.size()!=0){
			   
			     details.addProperty("Document link", API_Info.get(0).getURL());
			      
			     }
			    
		     }catch (Exception e) {
					
	    		 details.addProperty("Result", "Failed");
			     details.addProperty("Message", e.getLocalizedMessage());
			
			     logger.error("Exception in Api_Service2: ", e.getLocalizedMessage());
		     }
		
		return details;
	    }
	    
	    public class sim001_Mapper implements RowMapper<Simulator001> {
			  public Simulator001 mapRow(ResultSet rs, int rowNum) throws SQLException {
				 
				  Simulator001 API = new Simulator001();

					API.setSUBORGCODE(rs.getString("SUBORGCODE"));
					API.setSYSCODE(rs.getString("SYSCODE"));
					API.setCHCODE(rs.getString("CHCODE"));
					API.setSERVICECD(rs.getString("SERVICECD"));
					API.setSERVNAME(rs.getString("SERVNAME"));
					API.setFORMAT(rs.getString("FORMAT"));
					API.setPROTOCOL(rs.getString("PROTOCOL"));
					API.setMETHOD(rs.getString("METHOD"));
					API.setCHTYPE(rs.getString("CHTYPE"));
					API.setURI(rs.getString("URI"));
					API.setPAYLOAD(rs.getString("PAYLOAD"));
					API.setHEADERID(rs.getString("HEADERID"));
					API.setFLOW(rs.getString("FLOW"));
					API.setSIMIDPATH(rs.getString("SIMIDPATH"));
			
				  return API;
			  }
		    }

		   public class sim002_Mapper implements RowMapper<Simulator002> {
			   public Simulator002 mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					Simulator002 API = new Simulator002();

					API.setSUBORGCODE(rs.getString("SUBORGCODE"));
					API.setCHCODE(rs.getString("CHCODE"));
					API.setPAYTYPE(rs.getString("PAYTYPE"));
					API.setRESCODE(rs.getString("RESCODE"));
					API.setRESTYPE(rs.getString("RESTYPE"));
					API.setRESNAME(rs.getString("RESNAME"));
					API.setRESDESC(rs.getString("RESDESC"));
					API.setRANGE_FROM(rs.getString("RANGE_FROM"));
					API.setRANGE_TO(rs.getString("RANGE_TO"));
					API.setRESPONSE_DATA(rs.getString("RESPONSE_DATA"));
					API.setSERVICEID(rs.getString("SERVICEID"));
					API.setFLOW(rs.getString("FLOW"));

				   return API;

			     }
		    }

	    public class DOCUMENT_Mapper implements RowMapper<document_link3> {
			   public document_link3 mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				  document_link3 API = new document_link3();

				  API.setURL(rs.getString("URL"));
				  API.setCHCODE(rs.getString("CHCODE"));
					
				   return API;

			     }
		    }

}



