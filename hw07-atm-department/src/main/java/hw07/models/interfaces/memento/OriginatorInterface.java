package hw07.models.interfaces.memento;

import hw07.models.AtmMemento;

public interface OriginatorInterface {
  AtmMemento saveState();
  void restoreState(AtmMemento memento);
}
