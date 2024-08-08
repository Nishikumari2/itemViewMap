public class VoucherController { 
  CoreRequest cvoCoreReq; 
  CoreResponse cvoCoreRes; 
  Logger cvoLogger; 
  DBManager cvoDBManager; 

  public VoucherController(CoreRequest coreReq, CoreResponse coreRes) { 
    cvoCoreReq   = coreReq; 
    cvoCoreRes   = coreRes; 
    cvoLogger    = LogManager.getLogger(); 
    cvoDBManager = cvoCoreReq.getDBManager(); 
    cvoDBManager.addContextParamFromClass(CabCnstntDbVar.class); 
  }  
  
  public void saveBil() throws SQLException{
	int lviBizNtyOnrLocD  = cvoCoreReq.getIntVal("sviBizNtyOnrLocD");
	int lviTxctgPartyLocD = cvoCoreReq.getIntVal("sviTxctgPartyLocD"); 
	int lviBizNtyOnrLocF  = cvoCoreReq.getIntVal("sviBizNtyOnrLocF"); 
	int lviTxctgPartyLocF = cvoCoreReq.getIntVal("sviTxctgPartyLocF"); 
	int lviBizNtyOnrShipLocD = cvoCoreReq.getIntVal("sviBizNtyOnrLocD"); 
	int lviBizNtyOnrShipLocF = cvoCoreReq.getIntVal("sviBizNtyOnrLocF"); 
	int lviTxctgPartyShipLocD = cvoCoreReq.getIntVal("sviTxctgPartyLocD");
	int lviTxctgPartyShipLocF = cvoCoreReq.getIntVal("sviTxctgPartyLocF"); 
	String lvsBnfcrOnr = cvoCoreReq.getVal("svsBizNtyOnrLocDesc"); 
	int lviBnfcrOnrD   = cvoCoreReq.getIntVal("sviBizNtyOnrLocD"); 
	int lviBnfcrOnrF   = cvoCoreReq.getIntVal("sviBizNtyOnrLocF"); 
	String lvsBilIdy   = cvoCoreReq.getIntVal("svsBilIdy"); 
	LocalDate lvdBilDate = cvoCoreReq.getDateVal("svdBilDate"); 
	LocalDate lvdBilDueDate = cvoCoreReq.getDateVal("svdBilDueDate");
    String lvsPchsOrdrIdy = cvoCoreReq.getVal("svsPchsOrdrIdy"); 
	LocalDate lvdPchsOrdrDate = cvoCoreReq.getDateVal("svdPchsOrdrDate");
	String lvsBilCrncIdy = cvoCoreReq.getVal("svsBilCrncIdy"); 
	int lviBilXchngRate  = cvoCoreReq.getIntVal("svnBilXchngRate"); 
	int lviTaxTmpltD     = cvoCoreReq.getIntVal("svsTaxTmpltDesc",-1); 
	int lviBilPrprdUsrD  = cvoCoreRes.getIntVal("svsBilPrprdUsrDesc",-1); 
	int lviBilAprvdUsrD  = cvoCoreReq.getIntVal("svsBilAprvdUsrDesc",-1); 
	
	RowSet    lvoBilLines   = cvoCoreReq.getRowSet("svoBilLine"); 
    BilModal  lvoBil = new BilModal(lvsBilIdy, cvoDBManager, true); 
    lvoBil.setBizNtyOnrLocD(lviBizNtyOnrLocD);
    lvoBil.setTxctgPartyLocD(lviTxctgPartyLocD);
    lvoBil.setBizNtyOnrLocF(lviBizNtyOnrLocF);
    lvoBil.setTxctgPartyLocF(lviTxctgPartyLocF);
    lvoBil.setBizNtyOnrShipLocD(lviBizNtyOnrShipLocD);
    lvoBil.setBizNtyOnrShipLocF(lviBizNtyOnrShipLocF);
    lvoBil.setTxctgPartyShipLocD(lviTxctgPartyShipLocD); 
    lvoBil.setTxctgPartyShipLocF(lviTxctgPartyShipLocF);
    lvoBil.setBnfcrOnr(lvsBnfcrOnr);
    lvoBil.setBnfcrOnrD(lviBnfcrOnrD); 
    lvoBil.setBnfcrOnrF(lviBnfcrOnrF); 
    lvoBil.setBilIdy(lvsBilIdy);
    lvoBil.setBilDate(lvdBilDate);
    lvoBil.setBilDueDate(lvdBilDueDate);
    lvoBil.setPchsOrdrIdy(lvsPchsOrdrIdy);
    lvoBil.setPchsOrdrDate(lvdPchsOrdrDate);
    lvoBil.setBilCrncIdy(lvsBilCrncIdy);
    lvoBil.setBilXchngRate(cvoCoreRequest.getIntVal("svnBilXchngRate"));
    lvoBil.setTaxTmpltD(cvoCoreRequest.getIntVal("svsTaxTmpltDesc",-1));
    lvoBil.setBilPrprdUsrD(cvoCoreRequest.getIntVal("svsBilPrprdUsrDesc",-1));
    lvoBil.setBilAprvdUsrD(cvoCoreRequest.getIntVal("svsBilAprvdUsrDesc",-1));  
	
	BilModal.BilLine lvoBilLine;  
	
	  while(lvoBilLine.hasNext()){
      Row lvoRowBilLine = lvoBilLine.moveNext();
      int lviBilLineD = lvoRowBilLine.getIntVal("sviBilLineD", -1); 

      lvoBilLine = lviBilLineD == -1? lvoBilLine.createNewBilLine() : lvoBilLine.getBilLineWithD(lviBilLineD); 

      
      lvoBilLine.setItemD(lvoRowBilLine.getIntVal("sviItemD"));
      lvoBilLine.setItemF(lvoRowBilLine.getIntVal("sviItemF"));
      lvoBilLine.setBilLineDesc(lvoRowBilLine.getVal("svsBilLineDesc"));
      lvoBilLine.setBilLineUom(lvoRowBilLine.getVal("svsBilLineUom"));
      lvoBilLine.setBilLineQty(lvoRowBilLine.getIntVal("svnBilLineQty"));
      lvoBilLine.setBilLineUnitPrice(lvoRowBilLine.getIntVal("svnBilLineUnitPrice"));
      lvoBilLine.setBilLineDscnt(lvoRowBilLine.getIntVal("svnBilLineDscnt"));
      lvoBilLine.setBilLineVal(lvoRowBilLine.getIntVal("svnBilLineVal"));
      lvoBilLine.setBilLineHSN(lvoRowBilLine.getVal("svsBilLineHSN"));
      lvoBil.save();
  }