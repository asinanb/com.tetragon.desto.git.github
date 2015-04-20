package com.tetragon.desto.eventHandler;

import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.StokItem;

public class SatisItemEventHandler {

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
    public void fireSatisChanged(SatisItem satisItem) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<StokItem> event = new DataChangedEvent<StokItem>(this,
            			satisItem.getStokItemList());
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
    }   
    public void fireSatisChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<SatisItem> event = new DataChangedEvent<SatisItem>(this);
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
    }   
}
