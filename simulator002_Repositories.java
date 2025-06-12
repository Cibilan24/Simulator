 package com.hdsoft.hdpay.Repositories;

public class Simulator002
{
	public Simulator002(String sUBORGCODE, String cHCODE, String pAYTYPE, String fLOW, String rESCODE, String rESTYPE,
			String rESNAME, String rESDESC, String rESPONSE_DATA, String rANGE_FROM, String rANGE_TO,
			String sERVICEID) {
		super();
		SUBORGCODE = sUBORGCODE;
		CHCODE = cHCODE;
		PAYTYPE = pAYTYPE;
		FLOW = fLOW;
		RESCODE = rESCODE;
		RESTYPE = rESTYPE;
		RESNAME = rESNAME;
		RESDESC = rESDESC;
		RESPONSE_DATA = rESPONSE_DATA;
		RANGE_FROM = rANGE_FROM;
		RANGE_TO = rANGE_TO;
		SERVICEID = sERVICEID;
	}
	public Simulator002() {
		// TODO Auto-generated constructor stub
	}
	private String SUBORGCODE;
	private String CHCODE;
	private String PAYTYPE;
	private String FLOW;
	private String RESCODE;
	private String RESTYPE;
	private String RESNAME;
	private String RESDESC;
	private String RESPONSE_DATA;
	private String RANGE_FROM;
	private String RANGE_TO;
	private String SERVICEID;
	
	public String getRANGE_FROM() {
		return RANGE_FROM;
	}
	public void setRANGE_FROM(String rANGE_FROM) {
		RANGE_FROM = rANGE_FROM;
	}
	public String getRANGE_TO() {
		return RANGE_TO;
	}
	public void setRANGE_TO(String rANGE_TO) {
		RANGE_TO = rANGE_TO;
	}
	public String getSERVICEID() {
		return SERVICEID;
	}
	public void setSERVICEID(String sERVICEID) {
		SERVICEID = sERVICEID;
	}
	
	
	
	public String getSUBORGCODE() {
		return SUBORGCODE;
	}
	public void setSUBORGCODE(String sUBORGCODE) {
		SUBORGCODE = sUBORGCODE;
	}
	public String getCHCODE() {
		return CHCODE;
	}
	public void setCHCODE(String cHCODE) {
		CHCODE = cHCODE;
	}
	public String getPAYTYPE() {
		return PAYTYPE;
	}
	public void setPAYTYPE(String pAYTYPE) {
		PAYTYPE = pAYTYPE;
	}
	public String getFLOW() {
		return FLOW;
	}
	public void setFLOW(String fLOW) {
		FLOW = fLOW;
	}
	public String getRESCODE() {
		return RESCODE;
	}
	public void setRESCODE(String rESCODE) {
		RESCODE = rESCODE;
	}
	public String getRESTYPE() {
		return RESTYPE;
	}
	public void setRESTYPE(String rESTYPE) {
		RESTYPE = rESTYPE;
	}
	public String getRESNAME() {
		return RESNAME;
	}
	public void setRESNAME(String rESNAME) {
		RESNAME = rESNAME;
	}
	public String getRESDESC() {
		return RESDESC;
	}
	public void setRESDESC(String rESDESC) {
		RESDESC = rESDESC;
	}
	public String getRESPONSE_DATA() {
		return RESPONSE_DATA;
	}
	public void setRESPONSE_DATA(String rESPONSE_DATA) {
		RESPONSE_DATA = rESPONSE_DATA;
	}
}
