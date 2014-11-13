package edu.sjsu.cmpe.cache.client;


import java.util.ArrayList;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        ArrayList<DistributedCacheService> caches = new ArrayList<DistributedCacheService>();
        caches.add(new DistributedCacheService("http://localhost:3000"));
        caches.add(new DistributedCacheService("http://localhost:3001"));
        caches.add(new DistributedCacheService("http://localhost:3002"));

        String[] toPut = {"a","b","c","d","e","f","g","h","i","j"};
        for(int i=1;i<11;i++){
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString("someId"), caches.size());
        	caches.get(bucket).put(i,toPut[i]);
        	System.out.println("put("+bucket+") => "+toPut[i]);
        }
        
        for(int i=1;i<11;i++){
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString("someId"), caches.size());
        	String value = caches.get(bucket).get(i);
        	System.out.println("get("+bucket+") => "+value);
        }
        
        //CacheServiceInterface cache = new DistributedCacheService("http://localhost:3000");
        //cache.put(1, "foo");
        //System.out.println("put(1 => foo)");
        //String value = cache.get(1);
        //System.out.println("get(1) => " + value);

        System.out.println("Exiting Cache Client...");
    }

}
