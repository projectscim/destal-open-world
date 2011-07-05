/*******************************************************************************
 * destal open world, an open source java multiplayer game
 * Copyright (C) 2011 Alexander Belke, Dennis Sternberg, Steffen Schneider
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package destal.shared.net;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: rework this to save bandwidth

public class Packet implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4501785697817222078L;
	private byte _type;
    private ArrayList<Object> _data;
    
    private int _current;
    
    public Packet(byte type)
    {
    	_type = type;
    	_data = new ArrayList<Object>();
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
