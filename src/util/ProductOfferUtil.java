package util;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import model.data.ProductOffer;

public class ProductOfferUtil
{
	public static ArrayList<Entry<ProductOffer, Integer>> sortEntrySetOnHashCode(Set<Entry<ProductOffer, Integer>> entrySet)
	{
		ArrayList<Entry<ProductOffer, Integer>> result = new ArrayList<Entry<ProductOffer, Integer>>(entrySet.size());
		
		while(true)
		{
			if(entrySet.isEmpty())
				break;
			
			Entry<ProductOffer, Integer> lowestHashCodeEntry = getLowestHashCodeEntry(entrySet);
			entrySet.remove(lowestHashCodeEntry);
			result.add(lowestHashCodeEntry);
		}
		
		return result;
	}
	
	public static Entry<ProductOffer, Integer> getLowestHashCodeEntry(Set<Entry<ProductOffer, Integer>> entrySet)
	{
		Entry<ProductOffer, Integer> result = null;
		int lowestHashCode = Integer.MAX_VALUE;
		
		for(Entry<ProductOffer, Integer> entry : entrySet)
		{
			int hashcode = entry.getKey().hashCode();
			if(hashcode < lowestHashCode)
			{
				lowestHashCode = hashcode;
				result = entry;
			}
		}
		
		return result;
	}
}
