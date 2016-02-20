package org.demo.process;

import org.demo.dto.EntityDTO;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class HanlderImpl implements Listener {

  private final MonitorHelperImpl monitorHelper;
  private final Processor processor;
  public HanlderImpl(MonitorHelperImpl monitorHelper) {
    processor = new Processor();
    processor.addListener(this);
    this.monitorHelper = monitorHelper;
  }

  public void postInit(){
    boolean isSubscriberListEmpty = false;
    processor.setHasSubscribers(!isSubscriberListEmpty);
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
