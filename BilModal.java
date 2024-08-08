/*
 * InSilico Solutions LLP
 * www.insilicoss.com
 */
package com.insilicoss.cab.saleBill;

import com.insilicoss.App;
import com.insilicoss.database.DBManager;
import com.insilicoss.exception.PresentableException;
import com.insilicoss.exception.SystemException;
import com.insilicoss.validation.CoreValidator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.TrackChanges;
import com.insilicoss.cab.saleBill.BilLine;
/**
 *
 * @author Alaknanda
 */
@TrackChanges
public class BilModal {
    private String svsBilActv;

    private String svsBilType = "invoi";

    private int sviBizNtyOnrLocD;

    private int sviTxctgPartyLocD;

    private int sviBizNtyOnrLocF;

    private int sviTxctgPartyLocF;

    private int sviBizNtyOnrShipLocD;

    private int sviBizNtyOnrShipLocF;

    private int sviTxctgPartyShipLocD;

    private int sviTxctgPartyShipLocF;

    private String svsBnfcrOnr = " ";

    private int  sviBnfcrOnrD;

    private int  sviBnfcrOnrF;

    private String svsBilSts = " ";

    private String svsBilIdy;

    private LocalDate  svdBilDate;

    private String svsBilAckDocIdy = " ";

    private LocalDate svdBilAckDocDate = App.DEFAULT_MIN_DATE;

    private String   svsPchsOrdrIdy = " ";

    private LocalDate svdPchsOrdrDate = App.DEFAULT_MIN_DATE;

    private LocalDate svdBilAccDate = App.DEFAULT_MIN_DATE;

    private LocalDate svdBilDueDate = App.DEFAULT_MIN_DATE;

    private String  svsBilCrncIdy = " ";

    private int  svnBilXchngRate = 1;

    private String svsBillRCMAplbl = " ";

    private int sviBilPrprdUsrD = 1;

    private int sviBilAprvdUsrD = 1;

    private int sviTaxTmpltD = 1;

    private String  svsBilNotes = " ";

    public int sviBilD;

    private DBManager cvoDBManager;

    private List<BilLine> svoBilLine = new ArrayList<>();

    public BilModal(DBManager pvoDBManager){
        cvoDBManager = pvoDBManager;
    }

    public BilModal(int pviBilD, DBManager pvoDBManager){
        cviBilD      = pviBilD;
        cvoDBManager = pvoDBManager;
        _loadBilWithD();
    }

    private void _loadBilWithD(){
        try {
            if(cviBilD < 0) {
                throw new PresentableException("Invalid voucher reference. ");
            }
            cvoDBManager.addContextParam("rviBilD", cviBilD);
            ResultSet lvoBilRs = cvoDBManager.selectResultSet("cab\\saleBill\\sarBil");
            if(lvoBilRs.next()){
                cvsBilType           = lvoBilRs.getString("svsBilType");
                cviBizNtyOnrLocD     = lvoBilRs.getInt("sviBizNtyOnrLocD");
                cviTxctgPartyLocD    = lvoBilRs.getInt("sviTxctgPartyLocD");
                cviBizNtyOnrLocF     = lvoBilRs.getInt("sviBizNtyOnrLocF");
                cviTxctgPartyLocF    = lvoBilRs.getInt("sviTxctgPartyLocF");
                cviBizNtyOnrShipLocD = lvoBilRs.getInt("sviBizNtyOnrShipLocD");
                cviBizNtyOnrShipLocF = lvoBilRs.getInt("sviBizNtyOnrShipLocF");
                cvsBnfcrOnr          = lvoBilRs.getString("svsBnfcrOnr");
                cviBnfcrOnrD         = lvoBilRs.getInt("sviBnfcrOnrD");
                cvsBilSts            = lvoBilRs.getString("svsBilSts");
                cvsBilIdy            = lvoBilRs.getString("svsBilIdy");
                cvdBilDate           = lvoBilRs.getDate("svdBilDate").toLocalDate();
                cvsBilAckDocIdy      = lvoBilRs.getString("svsBilAckDocIdy");
                cvdBilAckDocDate     = lvoBilRs.getDate("svdBilAckDocDate").toLocalDate();
                cvsPchsOrdrIdy       = lvoBilRs.getString("svsPchsOrdrIdy");
                cvdPchsOrdrDate      = lvoBilRs.getDate("svdPchsOrdrDate").toLocalDate();
                cvdBilAccDate        = lvoBilRs.getDate("svdBilAccDate").toLocalDate();
                cvdBilDueDate        = lvoBilRs.getDate("svdBilDueDate").toLocalDate();
                cvsBilCrncIdy        = lvoBilRs.getString("svsBilCrncIdy");
                cvnBilXchngRate      = lvoBilRs.getInt("svnBilXchngRate");
                cvsBillRCMAplbl      = lvoBilRs.getString("svsBillRCMAplbl");
                cviBilPrprdUsrD      = lvoBilRs.getInt("sviBilPrprdUsrD");
                cviBilAprvdUsrD      = lvoBilRs.getInt("sviBilAprvdUsrD");
                cviTaxTmpltD         = lvoBilRs.getInt("sviTaxTmpltD");
                cvsBilNotes          = lvoBilRs.getString("svsBilNotes");
                cviBilD              = lvoBilRs.getInt("sviBilD");
            }
            else {
                throw new PresentableException("Invalid voucher reference. ");
            }

            _loadBilLinesD();
        }
        catch(SQLException se){
            throw new SystemException(se.getMessage(), se);
        }
    }

    private void _loadBilLinesD()throws SQLException{
        cvoDBManager.addContextParam("rviBilD",cviBilD);
        ResultSet lvoBilLineRs = cvoDBManager.selectResultSet("cab\\saleBill\\sarBilLine");

        while(lvoBilLineRs.next()){
            String lvsBilLineActv         = lvoBilLineRs.getString("svsBilLineSts");
            int    lviBilD                = lvoBilLineRs.getInt("sviBilD");
            int    lviItemD               = lvoBilLineRs.getInt("sviItemD");
            int    lviItemF               = lvoBilLineRs.getInt("sviItemF");
            String lvsBilLineDesc         = lvoBilLineRs.getString("svsBilLineDesc");
            String lvsBilLineUom          = lvoBilLineRs.getString("svsBilLineUom");
            int    lvnBilLineQty          = lvoBilLineRs.getInt("svnBilLineQty");
            int    lvnBilLineUnitPrice    = lvoBilLineRs.getInt("svnBilLineUnitPrice");
            int    lvnBilLineDscnt        = lvoBilLineRs.getInt("svnBilLineDscnt");
            int    lvnBilLineVal          = lvoBilLineRs.getInt("svnBilLineVal");
            String lvbBilLineHasSupotDoc  = lvoBilLineRs.getString("svbBilLineHasSupotDoc");
            String lvsBilLineDspsl        = lvoBilLineRs.getString("svsBilLineDspsl");
            String lvsCntxt               = lvoBilLineRs.getString("svsCntxt");
            int    lviCntxtBizNtyD        = lvoBilLineRs.getInt("sviCntxtBizNtyD");
            int    lviCntxtNgmtD          = lvoBilLineRs.getInt("sviCntxtNgmtD");
            String lvsBilLineHSN          = lvoBilLineRs.getString("svsBilLineHSN");
            int    lviLnkdBilLineD        = lvoBilLineRs.getInt("sviLnkdBilLineD");
            String lvsBilLineNotes        = lvoBilLineRs.getString("svsBilLineNotes");
            int    lviBilLineD            = lvoBilLineRs.getInt("sviBilLineD");
        }
    }

    public void save(){
        CoreValidator.validateAutoFlag(this);
        if(isChanged){
            cvoDBManager.addContextParam("rvsBilType",cvsBilType);
            cvoDBManager.addContextParam("rviBizNtyOnrLocD",cviBizNtyOnrLocD);
            cvoDBManager.addContextParam("rviTxctgPartyLocD",cviTxctgPartyLocD);
            cvoDBManager.addContextParam("rviBizNtyOnrLocF",cviBizNtyOnrLocF);
            cvoDBManager.addContextParam("rviTxctgPartyLocF",cviTxctgPartyLocF);
            cvoDBManager.addContextParam("rviBizNtyOnrShipLocD",cviBizNtyOnrShipLocD);
            cvoDBManager.addContextParam("rviBizNtyOnrShipLocF",cviBizNtyOnrShipLocF);
            cvoDBManager.addContextParam("rviTxctgPartyShipLocD",cviTxctgPartyShipLocD);
            cvoDBManager.addContextParam("rviTxctgPartyShipLocF",cviTxctgPartyShipLocF);
            cvoDBManager.addContextParam("rvsBnfcrOnr",cvsBnfcrOnr);
            cvoDBManager.addContextParam("rviBnfcrOnrD",cviBnfcrOnrD);
            cvoDBManager.addContextParam("rviBnfcrOnrF",cviBnfcrOnrF);
            cvoDBManager.addContextParam("rvsBilSts",cvsBilSts);
            cvoDBManager.addContextParam("rvsBilIdy",cvsBilIdy);
            cvoDBManager.addContextParam("rvdBilDate",cvdBilDate);
            cvoDBManager.addContextParam("rvsBilAckDocIdy",cvsBilAckDocIdy);
            cvoDBManager.addContextParam("rvdBilAckDocDate",cvdBilAckDocDate);
            cvoDBManager.addContextParam("rvsPchsOrdrIdy",cvsPchsOrdrIdy);
            cvoDBManager.addContextParam("rvdPchsOrdrDate",cvdPchsOrdrDate);
            cvoDBManager.addContextParam("rvdBilAccDate",cvdBilAccDate);
            cvoDBManager.addContextParam("rvdBilDueDate",cvdBilDueDate);
            cvoDBManager.addContextParam("rvsBilCrncIdy",cvsBilCrncIdy);
            cvoDBManager.addContextParam("rvnBilXchngRate",cvnBilXchngRate);
            cvoDBManager.addContextParam("rvsBillRCMAplbl",cvsBillRCMAplbl);
            cvoDBManager.addContextParam("rviBilPrprdUsrD",cviBilPrprdUsrD);
            cvoDBManager.addContextParam("rviBilAprvdUsrD",cviBilAprvdUsrD);
            cvoDBManager.addContextParam("rviTaxTmpltD",cviTaxTmpltD);
            cvoDBManager.addContextParam("rvsBilNotes",cvsBilNotes);

            if(cviBilD == -1){
                cvoDBManager.update("cab\\saleBill\\iarBil", true);
                cviBilD = cvoDBManager.cviRecentAutoGenKey;
            } else {
                cvoDBManager.addContextParam("rviBilD",cviBilD);
                cvoDBManager.update("cab\\saleBill\\uarBil");

            }
        }

        for(BilLine lvoBilLine : svoBilLine){
            BilLine bilLine = new BilLine();
            if(lvoBilLine.isChanged){
                cvoDBManager.addContextParam("rviBilD",cviBilD);
                cvoDBManager.addContextParam("rviItemD",bilLine.cviItemD);
                cvoDBManager.addContextParam("rviItemF",bilLine.cviItemF);
                cvoDBManager.addContextParam("rvsBilLineDesc",bilLine.cvsBilLineDesc);
                cvoDBManager.addContextParam("rvsBilLineUom",bilLine.cvsBilLineUom);
                cvoDBManager.addContextParam("rvnBilLineQty",bilLine.cvnBilLineQty);
                cvoDBManager.addContextParam("rvnBilLineUnitPrice",bilLine.cvnBilLineUnitPrice);
                cvoDBManager.addContextParam("rvnBilLineDscnt",bilLine.cvnBilLineDscnt);
                cvoDBManager.addContextParam("rvnBilLineVal",bilLine.cvnBilLineVal);
                cvoDBManager.addContextParam("rvbBilLineHasSupotDoc",bilLine.cvbBilLineHasSupotDoc);
                cvoDBManager.addContextParam("rvsBilLineDspsl",bilLine.cvsBilLineDspsl);
                cvoDBManager.addContextParam("rvsCntxt",bilLine.cvsCntxt);
                cvoDBManager.addContextParam("rviCntxtBizNtyD",bilLine.cviCntxtBizNtyD);
                cvoDBManager.addContextParam("rviCntxtNgmtD",bilLine.cviCntxtNgmtD);
                cvoDBManager.addContextParam("rvsBilLineHSN",bilLine.cvsBilLineHSN);
                cvoDBManager.addContextParam("rviLnkdBilLineD",bilLine.cviLnkdBilLineD);
                cvoDBManager.addContextParam("rvsBilLineNotes",bilLine.cvsBilLineNotes);

                if(bilLine.cviBilLineD > 0){
                    cvoDBManager.addContextParam("rviBilLineD",bilLine.cviBilLineD);
                    cvoDBManager.update("cab\\saleBill\\uarBilLine");
                } else {
                    bilLine.cviBilLineD = cvoDBManager.getNextTxnD("BilLineD");
                    cvoDBManager.addContextParam("rviBilLineD",bilLine.cviBilLineD);
                    cvoDBManager.update("cab\\saleBill\\iarBilLine");
                }
            }
        }


    }

    public BilLine createNewBilLine(){
        BilLine lvoBilLine = new BilLine();
        svoBilLine.add(lvoBilLine);
        return lvoBilLine;
    }

    public Billine getBilLineWithD(int pviBilLineD){
        if(pviBilLineD == -1){
            return null;
        }
        for (BilLine lvoBilLine : svoBilLine) {
            if(lvoBilLine.sviBilLineD == pviBilLineD){
                return lvoBilLine;
            }
        }
        return null;
    }

    @TrackChanges
    class BilLine {
        private String svsBilLineActv;

        private int sviBilD;

        private int sviItemD;

        private int sviItemF;

        private String svsBilLineDesc;

        private String svsBilLineUom;

        private int svnBilLineQty;

        private int svnBilLineUnitPrice;

        private int svnBilLineDscnt;

        private int svnBilLineVal;

        private String svbBilLineHasSupotDoc = "1";

        private String svsBilLineDspsl = " ";

        private String svsCntxt = " ";

        private int sviCntxtBizNtyD = -1;

        private int sviCntxtNgmtD = -1;

        private String svsBilLineHSN;

        private int sviLnkdBilLineD = -1;

        private String svsBilLineNotes = " ";

        public int sviBilLineD;
    }

}


