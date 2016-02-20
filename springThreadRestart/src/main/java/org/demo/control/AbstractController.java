package org.demo.control;


import org.apache.log4j.Logger;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public abstract class AbstractController {

  Logger log = Logger.getLogger(AbstractController.class);

  public void activate()
  {
    log.info("AbstractModule: No activation!");
  }
}
