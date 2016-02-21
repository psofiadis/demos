package org.demo.generator;

import org.demo.control.app.AppCtrl;
import org.demo.dto.EntityDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class EntityGenerator extends Thread{


  AppCtrl appCtrl;

  private long id;
  private String name;
  private String text;

  public EntityGenerator(AppCtrl appCtrl){
    this.appCtrl = appCtrl;
  }



  private List<EntityDTO> entityDTOs;
//  static{
//    entityDTOs = new ArrayList(){{
//      add(new EntityDTO())
//    }};
//  }
  @Override
  public void run(){
    while(true) {

      appCtrl.getMonitorAsyncProcessor().addToMtosiProcessingQueue(new ArrayList(){{
        add(new EntityDTO(++id,"EntityDTO:"+id, "I am an Entity with id: "+ id));
        add(new EntityDTO(++id,"EntityDTO:"+id, "I am an Entity with id: "+ id));
        add(new EntityDTO(++id,"EntityDTO:"+id, "I am an Entity with id: "+ id));
      }});
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
