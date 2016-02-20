package org.demo.process;

import org.apache.log4j.Logger;
import org.demo.dto.EntityDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class Processor extends Thread {

  Logger log = Logger.getLogger(Processor.class);

  private boolean started = false;
  private boolean stopped = true;
  private volatile boolean hasSubscribers = true;
  private static List<Listener> listeners;
  List<EntityDTO> events = new ArrayList<>();


  static {
    listeners = new ArrayList<>();
  }

  final LinkedBlockingQueue<EntityDTO> eventQueue;

  public Processor() {
    setName("MtosiEventProcessorThread");
    eventQueue = new LinkedBlockingQueue<>();
  }

  @Override
  public void run() {
    while (started && !stopped) {
      log.debug(new StringBuilder().append("Starting event processor"));
      synchronized (eventQueue) {
        processEvents();
      }
    }
    log.debug(new StringBuilder().append("Exiting event processor"));
  }

  private void processEvents() {
    try {
      while (eventQueue.size() > 0) {
        events.add(eventQueue.take());
      }
      for (EntityDTO event : events) {
        notifyListeners(event);
      }
      events.clear();
      eventQueue.wait();
    } catch (Exception e) {
      log.error(new StringBuilder().append("Error processing event list with reason ").append(e).toString(),e);
    }
  }

  private void notifyListeners(EntityDTO event) {
    for (Listener listener : filterListeners(event)) {
      listener.processEntity(event);
    }
  }

  private List<Listener> filterListeners(EntityDTO event) {
    List<Listener> filteredListeners = new ArrayList<>();
    for (Listener listener : listeners) {
      if (listener.appliesForEntity(event)) {
        filteredListeners.add(listener);
      }
    }
    return filteredListeners;
  }

  public void startProcessor(boolean hasSubscribers) {
    if(stopped || !started ) {
      stopped = !hasSubscribers;
      started = hasSubscribers;
      this.hasSubscribers = hasSubscribers;
      this.start();
    }
  }


  public void stopProcessor() {
    stopped = true;
    started = false;
    hasSubscribers = false;
  }

  public boolean isStarted() {
    return started;
  }


  public boolean isStopped() {
    return stopped;
  }

  public void addListener(Listener listener) {
    synchronized (listener) {
      listeners.add(listener);
    }
  }

  public void addToMtosiProcessingQueue(Collection<EntityDTO> eventDTOList) {
    if(isHasSubscribers()) {
      try {
        List<EntityDTO> eventDTOs = new ArrayList<>(eventDTOList);
        for (EntityDTO EntityDTO : eventDTOs) {
          if (EntityDTO != null) {
            synchronized (eventQueue) {
              eventQueue.put(EntityDTO);
              eventQueue.notifyAll();
            }
          }
        }
      } catch (InterruptedException e) {
        log.error(new StringBuilder().append("Error adding event ").append(" to queue with reason:").append(e).toString(), e);
      }
    }
  }

  public synchronized boolean isHasSubscribers() {
    return this.hasSubscribers;
  }

  public synchronized void setHasSubscribers(boolean hasSubscribers) {
    this.hasSubscribers = hasSubscribers;
  }

}
