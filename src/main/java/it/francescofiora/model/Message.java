package it.francescofiora.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO Message.
 */
@Getter
@Setter
@ToString(includeFieldNames = true)
public class Message {
  private String id;
  private String state;
  private String type;
  private String host;
  private long timestamp;
  private boolean skip = false;
}
