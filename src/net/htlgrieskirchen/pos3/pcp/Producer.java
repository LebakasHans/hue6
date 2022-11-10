/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Producer implements Runnable {
    private final String name;
    private final Storage storage;
    private final int sleepTime;
    
    private final List<Integer> sent;
    private final int numberOfItems;
    
    public Producer(String name, Storage storage, int sleepTime, int numberOfItems) {
        sent = new ArrayList<Integer>();

        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.numberOfItems = numberOfItems;
        System.out.println("Producer initialized");
    }
 
    // implement this

    public List<Integer> getSent() {
        return sent;
    }

    @Override
    public void run() {
        while(!storage.isProductionComplete()){
            for (int i = 0; i < numberOfItems; i++) {
                try {
                    boolean isAdded;
                    do {
                        isAdded = storage.put(i);
                        if (isAdded) {
                            sent.add(i);
                        } else {
                            sleep(sleepTime);
                        }
                    }while(!isAdded);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            storage.setProductionComplete();
        }
        return;
    }
}
