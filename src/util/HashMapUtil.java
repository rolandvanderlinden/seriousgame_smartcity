package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.data.ProductOffer;

public class HashMapUtil
{
	public static HashMap<ProductOffer, Integer> deepClone(HashMap<ProductOffer, Integer> map)
	{
		HashMap<ProductOffer, Integer> result = new HashMap<ProductOffer, Integer>(map.size());
		
		for(Entry<ProductOffer, Integer> entry : map.entrySet())
		{
			ProductOffer po = entry.getKey();
			int i = entry.getValue();

			result.put(po.clone(), new Integer(i));
		}
		
		return result;
	}
	
	public static ArrayList<Entry<ProductOffer, Integer>> sortEntriesOnAmountOfImprovements(HashMap<ProductOffer, Integer> map)
	{
		ArrayList<Entry<ProductOffer, Integer>> result = new ArrayList<Entry<ProductOffer, Integer>>(map.size());
		
		while(!map.isEmpty())
		{
			Entry<ProductOffer, Integer> entry = getEntryWithLeastImprovements(map);
			map.remove(entry.getKey());
			result.add(entry);
		}
		
		return result;
	}
	
	private static Entry<ProductOffer, Integer> getEntryWithLeastImprovements(HashMap<ProductOffer, Integer> map)
	{
		Entry<ProductOffer, Integer> result = null;
		int lowest = Integer.MAX_VALUE;
		
		for(Entry<ProductOffer, Integer> entry : map.entrySet())
		{
			int entrycount = entry.getKey().getProduct().getImprovements().length;
			if(entrycount < lowest)
			{
				result = entry;
				lowest = entrycount;
			}
		}
		
		return result;
	}
	
	public static void addToHashMap(HashMap<ProductOffer, Integer> map, ProductOffer offer, int amount)
	{
		if(map.containsKey(offer))
		{
			int oldAmount = map.get(offer);
			map.put(offer,  amount + oldAmount);
		}
		else
			map.put(offer, amount);
	}
}
