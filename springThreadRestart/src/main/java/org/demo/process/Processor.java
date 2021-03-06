package org.demo.process;

import org.apache.log4j.Logger;
import org.demo.dto.EntityDTO;
import org.demo.monitor.MonitoringThread;

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
  private MonitoringThread monitoringThread;
  List<EntityDTO> events= new ArrayList<>();

  private static int queueSize;


  static {
    listeners = new ArrayList<>();
  }

  final LinkedBlockingQueue<EntityDTO> eventQueue;

  public Processor(LinkedBlockingQueue<EntityDTO> eventQueue) {
    setName("ProcessorThread");
    this.eventQueue = eventQueue;
  }

  @Override
  public void run() {

    while (started && !stopped) {
      log.debug(new StringBuilder().append("Starting event processor"));
      synchronized (eventQueue) {
        try {
          processEvents();
        }catch(RuntimeException ex){
          log.error(ex.getMessage());
        }
      }
    }
    log.debug(new StringBuilder().append("Exiting event processor"));
  }

  private void processEvents() {
    try {
      while (eventQueue.size() > 0) {
        events.add(eventQueue.take());
        queueSize = eventQueue.size();
      }
      for (EntityDTO event : events) {
        try {
          notifyListeners(event);
        }catch (Throwable e){
          log.error(new StringBuilder().append("Error processing event with reason ").append(e).toString(),e);
        }
      }
      events.clear();
      eventQueue.wait();
    } catch (Throwable e) {
      log.error(new StringBuilder().append("Error processing event list with reason ").append(e).toString(),e);
      events.clear();
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
//    hasSubscribers = false;
  }

  public void shutDown( boolean clean) {
    stopProcessor();
    if(clean) {
      synchronized (eventQueue) {
        eventQueue.clear();
        eventQueue.notifyAll();
      }
    }
    synchronized (listeners) {
      listeners.clear();
    }
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
        int currentQueueSize = eventQueue.size();
        for (EntityDTO entityDTO : eventDTOs) {
          log.info("Adding to eventQueue entity: "+ entityDTO.toString());
          if (entityDTO != null) {
            synchronized (eventQueue) {
              eventQueue.put(entityDTO);
              queueSize = eventQueue.size();
              eventQueue.notifyAll();
            }
          }
        }
        if(currentQueueSize < eventQueue.size() && monitoringThread != null) monitoringThread.setMessage(currentQueueSize, eventQueue.size());
      } catch (InterruptedException e) {
        log.error(new StringBuilder().append("Error adding event ").append(" to queue with reason:").append(e).toString(), e);
      }
    }
  }

  public void setMonitoringThread(MonitoringThread monitoringThread){
    this.monitoringThread = monitoringThread;
  }

  public synchronized boolean isHasSubscribers() {
    return this.hasSubscribers;
  }

  public synchronized void setHasSubscribers(boolean hasSubscribers) {
    this.hasSubscribers = hasSubscribers;
  }

  public static int getEventQueueSize() {
    return queueSize;
  }
}
