package org.demo.process;

import org.demo.dto.EntityDTO;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public interface Listener {

  boolean processEntity(EntityDTO dto);

  boolean appliesForEntity(EntityDTO dto);

}
