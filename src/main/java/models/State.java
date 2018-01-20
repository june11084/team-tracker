package models;

import java.util.Objects;

public class State {

  private String name;
  private int id;

  public State(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    State state = (State) o;
    return id == state.id &&
            Objects.equals(name, state.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, id);
  }
}
