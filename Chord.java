package chord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Chord{
    
    public static void main(String[] args) throws IOException {
        System.out.println(Parameter.KEY_BIT);
        int num_servers=10000*4, num_key=1000000;
        Ring r = new Ring();
        String[] servers = new String[num_servers];
//        String[] servers = {"192.168.0.1", "15.12.0.9", "172.10.0.7", "199.45.2.13", "52.10.63.4"};
        for(int i=0; i<servers.length; i++) {
        	servers[i]=ChordHash.ip_create();
        }
        for(String s: servers){
            int idx = ChordHash.chord_hash(s);
            r.insert_node_ip(idx, s);
        }
        r.print_ring();
        r.update_finger_table();
        
        int[] paths = new int[num_key];
        String[] files = new String[num_key];
        Map<Integer, Integer> times= new HashMap<>();
        String path_dir = "path40000.txt";
        File pathfile = new File(path_dir);
        FileWriter pathwriter = new FileWriter(pathfile);
        for(int i=0; i<files.length; i++) {
        	String key = "File"+Integer.toString(i)+"_"+Double.toString(new Random().nextDouble());
        	files[i] = key;
        	int idx = ChordHash.chord_hash(key);
        	boolean sa = r.save_node_key(idx, key);
        	paths[i] = r.search_node_path(idx, key);
        	if(times.containsKey(paths[i])) {
        		times.put(paths[i], times.get(paths[i])+1);
        	}else {
        		times.put(paths[i], 1);
        	}
//        	pathwriter.write(Integer.toString(paths[i])+"\n");
        	pathwriter.write(Integer.toString(paths[i])+"\r\n");
//        	pathwriter.write(2556);
        	pathwriter.flush();
        	System.out.println(key+":\t "+Integer.toString(idx)+" "+sa +" \t "+ Integer.toString(paths[i]));
        }
        pathwriter.close();
//        r.print_node_filenum();
        
        for(int i : times.keySet()) {
        	System.out.println("times "+i+": "+times.get(i));
        }
        
        int[] keys = r.get_node_key();
        for(int i=0; i<keys.length; i++) {
        	System.out.println("Node "+(i+1)+" : "+Integer.toString(keys[i]));
        }
        if(keys!=null) {
        	Arrays.sort(keys);
        }
        
        String dir = "keys40000.txt";
        File ff = new File(dir);
        FileWriter writer = new FileWriter(ff);
        for(int i=0; i<keys.length; i++) {
        	writer.write(Integer.toString(keys[i])+"\r\n");
        	if(i==(int)(0.01*num_servers)) {
        		System.out.println("1 percent node key "+keys[i]);
        	}else if(i==(int)(0.99*num_servers)) {
        		System.out.println("99 percent node key "+keys[i]);
        	}
        	writer.flush();
        }
        writer.close();
        
        String real_servers = "servers40000.txt";
        File rs = new File(real_servers);
        FileWriter rw = new FileWriter(rs);
        int[] real_key = new int[num_servers];
        for(int i=0; i<num_servers; i++) {
        	int idx = ChordHash.chord_hash(servers[i]);
        	real_key[i] = r.get_node_key_num(idx);
        	rw.write(Integer.toString(real_key[i])+"\r\n");
        	rw.flush();
        }
        rw.close();
    }

}
