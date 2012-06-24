package util;

public class ArrayUtil
{
	public static boolean containEqualContent(Object[] array1, Object[] array2)
	{
		return containsAll(array1, array2) && containsAll(array2, array1);
	}
	
	public static boolean containsAll(Object[] array1, Object[] array2)
	{
		for(Object o2 : array2)
			if(!contains(array1, o2))
				return false;
		
		return true;
	}
	
	public static boolean contains(Object[] array, Object o)
	{
		for(Object arrayObject : array)
			if(arrayObject.equals(o))
				return true;
		
		return false;
	}
}
