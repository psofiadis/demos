package org.demo.control;

import org.demo.process.HanlderImpl;
import org.demo.process.MonitorHelperImpl;
import org.demo.process.Processor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class AppCtrlImpl extends AbstractController implements AppCtrl {

  @Autowired
  private HanlderImpl handler;

  public AppCtrlImpl() {
  }

  @Override
  public void activate(){
    handler.postInit();
  }

  @Override
  public Processor getMonitorAsyncProcessor() {
    return handler.getEventProcessor();
  }
}
