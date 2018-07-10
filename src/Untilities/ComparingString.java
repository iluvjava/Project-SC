package Untilities;


/**
 * Let's write some recursive methods for comparing strings! 
 * @author victo
 *
 */
public class ComparingString 
{
	public static void main(String[] whatever)
	{
		String s1 = "I think I have something there that may level your frustration.";
		String s2 = "I thinkg I have something that can clear you mind.";
		System.out.println("s1="+s1+" \ns2="+s2);
		System.out.println(commonSubString(s1,  s2));
		System.out.println(commonStringPercentage( s1,  s2));
	}
	
	/**
	 * O(N^2), not dynamically programmed. Spaces are included.
	 * @param s1
	 * @param s2
	 * @return
	 * Int that is the length is the longest commons substring.
	 */
	public static int commonSubString(String s1, String s2)
	{
		if(s1==null||s2==null)return -1;
		return commonSubString(0,0,s1,s2,new int[s1.length()][s2.length()]);
	}
	
	
	private static int commonSubString(int index1, int index2,String s1,String s2,int[][] mem)
	{
		//base case
		if(index1 == s1.length()||index2==s2.length())return 0;
		if(mem[index1][index2]!=0)return mem[index1][index2];
		if(s1.charAt(index1)==s2.charAt(index2))
		{
			int result = 1+commonSubString(index1+1,index2+1,s1,s2,mem);
			mem[index1][index2]=result;
			return result;
		}
		else
		{
			int result = commonSubString(index1+1,index2,s1,s2,mem);
			int result2 = commonSubString(index1,index2+1,s1,s2,mem);
			result = Math.max(result, result2);
			mem[index1][index2] = result;
			return result;
		}
	}
	
	/**
	 * A percentage to represent how similiar two strings are. 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static double commonStringPercentage(String s1, String s2)
	{
		int common = commonSubString(s1, s2);
		int merged = s1.length()+s2.length()-common;
		return (common+0.0)/merged;
	}
	

}
