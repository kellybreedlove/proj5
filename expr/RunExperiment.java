package expr;

/**
 * RunExperiment
 * 
 * @author Kelly Breedlove
 * CSCI 345, Wheaton College
 * 23 February 2015
 * 
 * Very similar to RunExperiment from proj3, with some design improvements.
 */
public class RunExperiment {

	/**
	 * The different map experiments to run.
	 */
	private static HashMapExperiment[] maps = { new BHMapExperiment(), new LPHMapExperiment(), new PHMapExperiment() };
	
	/**
	 * The maximum number of pairs to test.
	 */
	private static int maxPairs;
	
	/**
	 * An array of testable sizes with maxPairs as its largest value.
	 */
	private static int[] sizes;
	
	/**
	 * A buffer line to print for easier reading.
	 */
	private static String line = "=======================================================";
	
	/**
	 * Using maxPairs, test put runtimes on each map.
	 */
	private static void putRuntimeComparison() {
		for (int i = 0; i < maps.length; i++)
			System.out.println(maps[i].getType() + " put runtime: " + maps[i].putRuntimes(maxPairs));
		System.out.println(line);
	}
	
	/**
	 * Using maxPairs, test get runtimes on each map.
	 */
	private static void getRuntimeComparison() {
		for (int i = 0; i < maps.length; i++)
			System.out.println(maps[i].getType() + " get runtime: " + maps[i].getRuntimes(maxPairs));
		System.out.println(line);
	}
	
	/**
	 * Using maxPairs, test containsKey runtimes on each map.
	 */
	private static void containsKeyRuntimeComparison() {
		for (int i = 0; i < maps.length; i++)
			System.out.println(maps[i].getType() + " containsKey runtime: " + maps[i].containsKeyRuntimes(maxPairs));
		System.out.println(line);
	}
	
	/**
	 * Using sizes, test put runtimes on the given map for each size
	 * @param map The map to test on
	 */
	private static void putRuntimeBySize(HashMapExperiment map) {
		for (int i = 0; i < sizes.length; i++)
			System.out.println(map.getType() + " put runtime " + sizes[i] + ": " + map.putRuntimes(sizes[i]));
		System.out.println(line);
	}
	
	/**
	 * Using sizes, test get runtimes on the given map for each size
	 * @param map The map to test on
	 */
	private static void getRuntimeBySize(HashMapExperiment map) {
		for (int i = 0; i < sizes.length; i++)
			System.out.println(map.getType() + " put runtime " + sizes[i] + ": " + map.getRuntimes(sizes[i]));
		System.out.println(line);
	}
	
	/**
	 * Using sizes, test containsKey runtimes on the given map for each size
	 * @param map The map to test on
	 */
	private static void containsKeyRuntimeBySize(HashMapExperiment map) {
		for (int i = 0; i < sizes.length; i++)
			System.out.println(map.getType() + " put runtime " + sizes[i] + ": " + map.containsKeyRuntimes(sizes[i]));
		System.out.println(line);
	}	
	
	/**
	 * 
	 * @param args The max number of pairs to run test on
	 */
	public static void main(String[] args) {
		
		maxPairs = Integer.parseInt(args[0]);
		sizes = new int[maxPairs/10];
		int j = 0;
		for (int i = 10; i < sizes.length; i+= 10) {
			sizes[j] = maxPairs/i;
			j++;
		}
		
		putRuntimeComparison();
		getRuntimeComparison();
		containsKeyRuntimeComparison();
		for (int i = 0; i < maps.length; i++)
			putRuntimeBySize(maps[i]);
		for (int i = 0; i < maps.length; i++)
			getRuntimeBySize(maps[i]);
		for (int i = 0; i < maps.length; i++)
			containsKeyRuntimeBySize(maps[i]);		
	}
}
