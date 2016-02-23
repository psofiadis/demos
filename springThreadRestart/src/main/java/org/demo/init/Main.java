package org.demo.init;

import org.apache.log4j.Logger;
import org.demo.control.app.AppCtrlImpl;
import org.demo.control.thread.StartStop;
import org.demo.generator.EntityGenerator;
import org.demo.monitor.MonitoringThread;
import org.demo.process.HanlderImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class Main {
  private static Logger log = Logger.getLogger(Main.class);

  public static void main(String[] args){

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
    AppCtrlImpl appCtrl = (AppCtrlImpl)applicationContext.getBean("appCtrl");
    appCtrl.activate();

    EntityGenerator generator = new EntityGenerator(appCtrl);

    generator.start();

    StartStop startStop = new StartStop(appCtrl);
    startStop.start();
    HanlderImpl hanlder = (HanlderImpl)applicationContext.getBean("handler");
    MonitoringThread monitoringThread = new MonitoringThread(hanlder);
    hanlder.getEventProcessor().setMonitoringThread(monitoringThread);
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    try {
      mbs.registerMBean(monitoringThread, new ObjectName(" org.demo:type=org.demo.monitor.MonitoringThread"));
    }catch (Exception e) {
      log.warn("Cannot initialize ProcessorInfo", e);
    }
  }


}
