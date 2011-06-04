package destal.general.net;

public class MSGType
{
	// Note: Client messages 0x0...
	//		 Server messages 0x1...
	
	public final static int PROTOCOL_VERSION = 3;
	
	/*
	 * == Protocol ==
	 * 
	 * -- Connecting --
	 * Client -> Server (MSG_CL_INIT: PROTOCOL_VERSION, name)
	 * Server -> Client (MSG_SV_INIT: accepted?, MOTD, ID)
	 * Client -> Server (MSG_CL_REQUEST_ENTER)
	 * Server -> Client (MSG_SV_RESPONSE_ENTER: xPos (Player), yPos (Player), Chunkbuffer/Array) // TODO: add client list
	 * TODO: replace by snapshot system?
	 * Server -> Client (all) (MSG_SV_NEW_CLIENT_CONNECTED: ID, xPos, yPos)
	 * 
	 * -- Chunk Request --
	 * Client -> Server (MSG_CL_REQUEST_CHUNK: xPos, yPos)
	 * Server -> Client (MSG_SV_RESPONSE_CHUNK: Chunk)
	 * 
	 * TODO: replace by snapshot system?
	 * -- Player Position --
	 * Client -> Server (MSG_CL_PLAYER_INPUT: xPos, yPos)
	 * Server -> Client (all) (MSG_SV_RESPONSE_PLAYER_POSITIONS ID, xPos, yPos)
	 */
	
	public final static byte MSG_CL_INIT = 0x01;
	public final static byte MSG_CL_REQUEST_ENTER = 0x02;
	public final static byte MSG_CL_REQUEST_CHUNK = 0x03;
	public final static byte MSG_CL_PLAYER_INPUT = 0x04;
	
	public final static byte MSG_SV_INIT = 0x11;
	public final static byte MSG_SV_RESPONSE_ENTER = 0x12;
	public final static byte MSG_SV_RESPONSE_CHUNK = 0x13;
	public final static byte MSG_SV_RESPONSE_PLAYER_POSITIONS = 0x14;
	public final static byte MSG_SV_NEW_CLIENT_CONNECTED = 0x15;
}
