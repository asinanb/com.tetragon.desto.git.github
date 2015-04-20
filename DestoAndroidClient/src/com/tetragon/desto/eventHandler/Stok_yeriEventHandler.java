package com.tetragon.desto.eventHandler;

import java.util.List;

import com.tetragon.desto.model.Stok_yeri;

public class Stok_yeriEventHandler {

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
    public void fireStok_yeriChanged(List<Stok_yeri> dataList) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<Stok_yeri> event = new DataChangedEvent<Stok_yeri>(this,
    					dataList);
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
    }   
    public void fireStok_yeriChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DataListener.class) {
            	DataChangedEvent<Stok_yeri> event = new DataChangedEvent<Stok_yeri>(this);
                ((DataListener)listeners[i+1]).dataChanged(event);
               
            }          
        }
    }   
}
