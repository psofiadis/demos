/**
 * Created by Panagiotis on 2/20/2016.
 */

package org.demo.monitor;

import org.apache.log4j.Logger;
import org.demo.process.HanlderImpl;
import org.demo.process.Processor;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

public class  MonitoringThread extends NotificationBroadcasterSupport implements MonitoringThreadMBean{

  Logger log = Logger.getLogger(MonitoringThread.class);

  private long sequenceNumber = 1;
  private HanlderImpl hanlder;

  public MonitoringThread(HanlderImpl hanlder){
    addNotificationListener(new NotificationListener() {
      @Override
      public void handleNotification(Notification notification, Object handback) {
        log.info("*** Handling new notification ***\nMessage: " + notification.getMessage()+
            "\nSeq: " + notification.getSequenceNumber()+"\n*********************************");
      }
    }, null, null);
    this.hanlder = hanlder;
  }

  @Override
  public int getQueueSize() {
    return Processor.getEventQueueSize();
  }

  @Override
  public int getNumberOfThreads() {
    return 0;
  }

  @Override
  public String getThreadState() {
    return hanlder.getEventProcessor().getState().name();
  }

  @Override
  public boolean restartThread(){
    hanlder.postInit(false);
    return true;
  }

  @Override
  public   boolean restartThreadClean(){
    hanlder.postInit(true);
    return true;
  }

  public void setMessage(int oldQueuSize, int queueSizeAlarm) {
    Notification n = new AttributeChangeNotification(this, sequenceNumber++,

        System.currentTimeMillis(), "Old queue size:"+oldQueuSize+", New queue size:"+queueSizeAlarm, "Queue Size", "Integer",

        oldQueuSize, queueSizeAlarm);
    sendNotification(n);
  }
}
