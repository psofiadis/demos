package org.demo.generator;

import org.demo.control.AppCtrl;
import org.demo.dto.EntityDTO;
import org.springframework.beans.factory.annotation.Autowired;

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
        add(new EntityDTO(id+1,"EntityDTO:"+id, "I am an Entity with id: "+ id));
        add(new EntityDTO(id+2,"EntityDTO:"+id, "I am an Entity with id: "+ id));
        add(new EntityDTO(id+3,"EntityDTO:"+id, "I am an Entity with id: "+ id));
      }});
      try {
        sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
