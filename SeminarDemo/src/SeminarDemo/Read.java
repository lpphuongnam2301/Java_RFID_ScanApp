package SeminarDemo;

import com.caen.RFIDLibrary.CAENRFIDLogicalSource;
import com.caen.RFIDLibrary.CAENRFIDLogicalSourceConstants;
import com.caen.RFIDLibrary.CAENRFIDPort;
import com.caen.RFIDLibrary.CAENRFIDReader;
import com.caen.RFIDLibrary.CAENRFIDReaderInfo;
import com.caen.RFIDLibrary.CAENRFIDTag;

public class Read {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CAENRFIDReader MyReader = new CAENRFIDReader();
		try {
			MyReader.Connect(CAENRFIDPort.CAENRFID_TCP, "192.168.1.2"); 
	        CAENRFIDLogicalSource MySource = MyReader.GetSource("Source_0");
	        
	      //get Reader Infor
	      CAENRFIDReaderInfo Info = MyReader.GetReaderInfo();
	      String Model = Info.GetModel();
	      String SerialNumber = Info.GetSerialNumber();
	      String FWRelease = MyReader.GetFirmwareRelease();
	      int power = MyReader.GetPower();

	      System.out.println("Model: "+Model);
	      System.out.println("SerialNumber: "+SerialNumber);
	      System.out.println("FWRelease: "+FWRelease);
	      System.out.println("power: "+power);
	      
	      System.out.println("");
	      	MySource.SetSession_EPC_C1G2(CAENRFIDLogicalSourceConstants.EPC_C1G2_SESSION_S1);

	        CAENRFIDTag[] MyTags = MySource.InventoryTag();
	       
	        if (MyTags.length > 0)
	        {
	            for (int i = 0; i < MyTags.length; i++)
	            {
	                System.out.println("EPC: "+ hex(MyTags[i].GetId())  + 
                                " | Antenna : " +MyTags[i].GetAntenna() +
                                " | TID:"+ (MyTags[i].GetTID()) +" | RSSI : "
                                +Integer.valueOf(MyTags[i].GetRSSI()));
	            }
	        }
	        
	    
	        MyReader.Disconnect();
		}catch(Exception ex) {
			if(MyReader != null)MyReader.Disconnect();
		}
        
	}
	
	public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString().toUpperCase();
    }

}
