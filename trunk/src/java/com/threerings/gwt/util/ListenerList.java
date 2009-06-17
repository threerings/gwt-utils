//
// $Id$

package com.threerings.gwt.util;

import java.util.ArrayList;
import java.util.Map;

/**
 * A handy class for dispatching notifications to listeners.
 */
public class ListenerList<T> extends ArrayList<T>
{
    /** Used by {@link ListenerList#notify}. */
    public static interface Op<L>
    {
        /** Delivers a notification to the supplied listener. */
        void notify (L listener);
    }

    /**
     * Adds the supplied listener to the supplied list. If the list is null, a new listener list
     * will be created. The supplied or newly created list as appropriate will be returned.
     */
    public static <L> ListenerList<L> addListener (ListenerList<L> list, L listener)
    {
        if (list == null) {
            list = new ListenerList<L>();
        }
        list.add(listener);
        return list;
    }

    /**
     * Adds a listener to the listener list in the supplied map. If no list exists, one will be
     * created and mapped to the supplied key.
     */
    public static <L, K> void addListener (Map<K, ListenerList<L>> map, K key, L listener)
    {
        ListenerList<L> list = map.get(key);
        if (list == null) {
            map.put(key, list = new ListenerList<L>());
        }
        list.add(listener);
    }

    /**
     * Removes a listener from the supplied list in the supplied map.
     */
    public static <L, K> void removeListener (Map<K, ListenerList<L>> map, K key, L listener)
    {
        ListenerList<L> list = map.get(key);
        if (list != null) {
            list.remove(listener);
        }
    }

    /**
     * Applies a notification to all listeners in this list.
     */
    public void notify (Op<T> op)
    {
        for (T listener : this) {
            try {
                op.notify(listener);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
    }
}
