package it.francescofiora.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * EventLog Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "eventlog")
@ToString(includeFieldNames = true)
public class EventLog implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "estart")
  private Long estart;

  @Column(name = "eend")
  private Long eend;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "etype")
  private String etype;

  @Column(name = "host")
  private String host;

  @Column(name = "alert")
  private String alert;
}
