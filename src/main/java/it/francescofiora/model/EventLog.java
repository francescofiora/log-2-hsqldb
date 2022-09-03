package it.francescofiora.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EventLog Entity.
 */
@Entity
@Table(name = "eventlog")
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getEstart() {
    return estart;
  }

  public void setEstart(Long estart) {
    this.estart = estart;
  }

  public Long getEend() {
    return eend;
  }

  public void setEend(Long eend) {
    this.eend = eend;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  public String getEtype() {
    return etype;
  }

  public void setEtype(String etype) {
    this.etype = etype;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getAlert() {
    return alert;
  }

  public void setAlert(String alert) {
    this.alert = alert;
  }
}
