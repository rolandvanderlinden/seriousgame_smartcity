package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListUtil
{
	/**
	 * Calculates the powerset of the given collection.
	 * Taken from: http://rosettacode.org/wiki/Power_Set#Java
	 * @param list
	 * @return
	 */
	public static <T> List<List<T>> powerset(Collection<T> list) 
	{
		List<List<T>> powerset = new ArrayList<List<T>>();
		ArrayList<T> emptySet = new ArrayList<T>();
		powerset.add(emptySet);   // add the empty set
		 
		// for every item in the original list
		for (T item : list)
		{
			List<List<T>> newPs = new ArrayList<List<T>>();
		
			for (List<T> subset : powerset) 
			{
				// copy all of the current powerset's subsets
				newPs.add(subset);
				 
				// plus the subsets appended with the current item
				List<T> newSubset = new ArrayList<T>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}
			 
			// powerset is now powerset of list.subList(0, list.indexOf(item)+1)
			powerset = newPs;
		}
		  
		//Remove the empty set
		powerset.remove(emptySet);
		return powerset;
	}
}
