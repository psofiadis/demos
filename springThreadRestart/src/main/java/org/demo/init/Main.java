package org.demo.init;

import org.demo.control.AppCtrlImpl;
import org.demo.generator.EntityGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class Main {

  public static void main(String[] args){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
    AppCtrlImpl appCtrl = (AppCtrlImpl)applicationContext.getBean("appCtrl");
    appCtrl.activate();

    EntityGenerator generator = new EntityGenerator(appCtrl);
    generator.start();

  }


}
