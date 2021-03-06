package com.tetragon.desto.eventHandler;

import com.tetragon.desto.model.Urun_tipItem;
import com.tetragon.desto.model.Urun_tipItemList;

public class Urun_tipEventHandler {

	protected ListenerList listenerList = new ListenerList();
	/**
	 * Adds a <code>AlarmListener</code> to the button.
	 * @param l the listener to be added
	 */
	public void addListener(DataListener l) {
		listenerList.add(DataListener.class, l);
	}

	/**
	 * Removes a AlarmListener from the button.
	 * @param l the listener to be removed
	 */
	public void removeListener(DataListener l) {
		listenerList.remove(DataListener.class, l);
	}
	/**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this AbstractButton with addChangeListener().
     *
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public DataListener[] getChangeListeners() {
        return (DataListener[])(listenerList.getListeners(
        		DataListener.class));
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created.
     * @see ListenerList
     */
    public void fireUrun_tipChanged(Urun_tipItemList urun_tipItemList) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<Urun_tipItem> event = new DataChangedEvent<Urun_tipItem>(this,
    					urun_tipItemList);
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
    }

	public void fireUrunChanged() {
		// Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<Urun_tipItem> event = new DataChangedEvent<Urun_tipItem>(this);
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
	}   
}
