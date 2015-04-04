package impl;

import java.util.Random;



public class UniversalHashFactory {

    private static Random randy = new Random();
    
    public static HashFunction<Object> makeHashFunction(final int p, final int m) {
        final int a = m <= 1 ? 0 : randy.nextInt(p-1) + 1;
        final int b = m <= 1 ? 0 : randy.nextInt(p);
        return new HashFunction<Object>() {
            public int hash(Object key) {
                return ((a * (key.hashCode() & 0x7fffffff) + b) % p) % m;
            }
        };
    }
    
    public static HashFunction<Object> makeHashFunction(final int p, final int m,
            final int f) {
        final int a = m <= 1 ? 0 : randy.nextInt(p-1) + 1;
        final int b = m <= 1 ? 0 : randy.nextInt(p);
        return new HashFunction<Object>() {
            public int hash(Object key) {
                return ((a * ((key.hashCode() & 0x7fffffff) % f) + b) % p) % m;
            }
        };
    }
}
