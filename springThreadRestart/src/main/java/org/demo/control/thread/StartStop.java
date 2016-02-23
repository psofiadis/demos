package org.demo.control.thread;

import org.apache.log4j.Logger;
import org.demo.control.app.AppCtrl;
import org.demo.control.app.AppCtrlImpl;

/**
 * Created by Panagiotis on 2/21/2016.
 */
public class StartStop extends Thread {

  Logger log = Logger.getLogger(StartStop.class);

  private Boolean isRunning = true;
  private final AppCtrl appCtrl;

  public StartStop(AppCtrl appCtrl){
    this.appCtrl = appCtrl;
  }


  @Override
  public void run() {
    while (true) {
      synchronized (isRunning) {
        try {
          sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (isRunning) {
          log.info("Stopping processor...");
          appCtrl.getMonitorAsyncProcessor().stopProcessor();
          isRunning = false;
        }
//        else {
//          log.info("Starting processor...");
//          if(appCtrl instanceof AppCtrlImpl)
//            ((AppCtrlImpl)appCtrl).activate();
//          isRunning = true;
//        }
      }

    }
  }
}
