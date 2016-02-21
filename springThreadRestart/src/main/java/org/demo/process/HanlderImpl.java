package org.demo.process;

import org.demo.dto.EntityDTO;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class HanlderImpl implements Listener {

  private final MonitorHelperImpl monitorHelper;
  private Processor processor;
  static final LinkedBlockingQueue<EntityDTO> eventQueue = new LinkedBlockingQueue<>();
  public HanlderImpl(MonitorHelperImpl monitorHelper) {
    this.monitorHelper = monitorHelper;
  }

  public void postInit(){
    if(processor != null) processor.shutDown();
    boolean isSubscriberListEmpty = false;
    processor = new Processor(eventQueue);
    processor.setHasSubscribers(!isSubscriberListEmpty);
    processor.addListener(this);
    processor.startProcessor(!isSubscriberListEmpty);
  }


  @Override
  public boolean processEntity(EntityDTO dto) {
    monitorHelper.sendEntity(dto);
    return true;
  }

  @Override
  public boolean appliesForEntity(EntityDTO dto) {
    return true;
  }

  public Processor getEventProcessor(){
    return processor;
  }
}
