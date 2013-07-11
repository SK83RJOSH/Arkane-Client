package com.sk83rsplace.arkane.utils.lists;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

public class RenderedList<Entity> extends AbstractList<Entity> {
    private ArrayList<Entity> internalList = new ArrayList<Entity>();

    public void add(int position, Entity e) {
        internalList.add(e);
        sort();
    }

    public Entity get(int i) {
        return internalList.get(i);
    }

    public int size() {
        return internalList.size();
    }
    
    public void drop(Entity e) {
    	if(internalList.contains(e))
    		internalList.remove(e);
    }
    
    public void sort() {
    	Collections.sort(internalList, new IndexComparator());
    }
}