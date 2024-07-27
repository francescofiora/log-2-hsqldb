package it.francescofiora.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
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

  @Serial
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
