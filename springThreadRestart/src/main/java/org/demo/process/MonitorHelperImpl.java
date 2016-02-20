package org.demo.process;

import org.demo.dto.EntityDTO;

import org.apache.log4j.Logger;
/**
 * Created by Panagiotis on 2/20/2016.
 */
public class MonitorHelperImpl {

  Logger log = Logger.getLogger(MonitorHelperImpl.class);

  public void sendEntity(EntityDTO entityDTO){
    log.info("Sending entity: "+ entityDTO.toString());
    System.out.println(entityDTO.toString());
  }


}
