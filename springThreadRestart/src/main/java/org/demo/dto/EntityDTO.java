package org.demo.dto;

/**
 * Created by Panagiotis on 2/20/2016.
 */
public class EntityDTO {

  private long id;
  private String name;
  private String text;

  public EntityDTO() {
  }

  public EntityDTO(long id, String name, String text) {
    this.id = id;
    this.name = name;
    this.text = text;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EntityDTO entityDTO = (EntityDTO) o;

    if (id != entityDTO.id) return false;
    if (name != null ? !name.equals(entityDTO.name) : entityDTO.name != null) return false;
    return text != null ? text.equals(entityDTO.text) : entityDTO.text == null;

  }

  @Override
  public String toString() {
    return "EntityDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", text='" + text + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (text != null ? text.hashCode() : 0);
    return result;
  }
}
