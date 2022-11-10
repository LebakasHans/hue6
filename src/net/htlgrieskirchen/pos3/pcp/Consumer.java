/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;


public class Consumer implements Runnable {
    private final String name;
    private final Storage storage;
    private final int sleepTime;
    
    private final List<Integer> received;
    private boolean running;
    
    public Consumer(String name, Storage storage, int sleepTime) {
        received = new ArrayList<Integer>();

        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.running = true;
        System.out.println(name + " initialized");
    }

    public List<Integer> getReceived() {
        return received;
    }

    @Override
    public void run() {
        while(running){
            boolean productionComplete;
            boolean noItem;
            productionComplete = storage.isProductionComplete();
            Integer test = storage.get();

            if( test != null ) {
                    received.add(test);
                    noItem = false;
            }else{
                noItem = true;
            }

                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(productionComplete && noItem){
                    running = false;
                }
        }
    }
}

