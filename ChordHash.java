package chord;

import java.lang.Math;
import java.util.Random;

public class ChordHash {
    public static int chord_hash(String s) {
        int hash =0;
        for(int i=0; i<s.length(); i++){
            hash = hash*31 + (int)s.charAt(i);
        }
        hash = Math.abs(hash);
        hash = hash% (int)Math.pow(2, 21);
        return hash;
    }
    
    public static String ip_create() {
    	Random r = new Random();
    	StringBuilder builder = new StringBuilder();
    	builder.append(r.nextInt(255));
    	builder.append('.');
    	builder.append(r.nextInt(255));
    	builder.append('.');
    	builder.append(r.nextInt(255));
    	builder.append('.');
    	builder.append(r.nextInt(255));
    	return builder.toString();
    }
}
