package com.sk83rsplace.arkane.utils.lists;

import java.util.Comparator;

import com.sk83rsplace.arkane.client.interfaces.IWorld;

public class IndexComparator implements Comparator<Object> {
	public int compare(Object o1, Object o2) {
		if(((IWorld) o1).getY() > ((IWorld) o2).getY())
			return 1;
		else if(((IWorld) o1).getY() == ((IWorld) o2).getY())
			return  0;
		else
			return -1;
	}
}
