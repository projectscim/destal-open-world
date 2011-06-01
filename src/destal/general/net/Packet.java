package destal.general.net;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: rework this to save bandwidth

public class Packet implements Serializable
{
    private byte _type;
    private ArrayList _data;
    
    private int _current;
    
    public Packet(byte type)
    {
    	_type = type;
    	_data = new ArrayList();
    	_current = 0;
    }
    
    public byte getType()
    {
    	return _type;
    }
    
    public boolean set(Object data)
    {
        return _data.add(data);
    }
    
    public Object get()
    {
    	if(_current < _data.size())
    		return _data.get(_current++);
    	else
    		return null;
    }
}
