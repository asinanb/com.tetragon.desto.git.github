package com.tetragon.desto.eventHandler;

import java.util.EventObject;
import java.util.List;

/**
 * An event that represents new data set is read
 * @param <T>
 */
public class DataChangedEvent<T> extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3977811727680280008L;
	
	private List<T> list;
	
	public DataChangedEvent(Object source, List<T> dataList) {
		super(source);
		this.setList(dataList);
	}

	public DataChangedEvent(Object source) {
		super(source);
	}
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> dataList) {
		this.list = dataList;
	}

	
}
