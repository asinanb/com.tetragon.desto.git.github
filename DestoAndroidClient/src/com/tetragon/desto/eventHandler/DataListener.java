package com.tetragon.desto.eventHandler; 

import java.util.EventListener;

public interface DataListener extends EventListener {

	public <T> void dataChanged(DataChangedEvent<T> e) ;
}

