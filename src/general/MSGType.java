package general;

public class MSGType
{
	// Note: Client messages 0x0...
	//		 Server messages 0x1...
	static byte MSG_CL_TEST = 0x01;
	static byte MSG_CL_UPDATE_CHUNKBUFFER = 0x02;
	
	static byte MSG_SV_TEST = 0x11;
	static byte MSG_SV_SEND_CHUNKBUFFER = 0x12;
	
}
