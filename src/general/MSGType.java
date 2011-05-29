package general;

public class MSGType
{
	// Note: Client messages 0x0...
	//		 Server messages 0x1...
	
	public final static int PROTOCOL_VERSION = 1;
	
	/*
	 * == Protocol ==
	 * 
	 * -- Connecting --
	 * Client -> Server (MSG_CL_INIT: PROTOCOL_VERSION, name)
	 * Server -> Client (MSG_SV_INIT: accepted?, MOTD)
	 * Client -> Server (MSG_CL_REQUEST_CHUNKBUFFER)
	 * Server -> Client (MSG_SV_RESPONSE_CHUNKBUFFER: Chunkbuffer/Array)
	 */
	
	public final static byte MSG_CL_INIT= 0x01;
	public final static byte MSG_CL_REQUEST_CHUNKBUFFER = 0x02;
	
	public final static byte MSG_SV_INIT = 0x11;
	public final static byte MSG_SV_RESPONSE_CHUNKBUFFER = 0x12;
}
