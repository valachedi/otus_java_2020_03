package hw07.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import hw07.models.Atm;
import hw07.models.NotesBank;
import hw07.models.interfaces.memento.OriginatorInterface;
import hw07.models.interfaces.observer.MoneyStorageObservableInterface;
import hw07.models.interfaces.observer.StorageListenerInterface;

/*
  Atm class is from homework #6
  Controllable = Observerable + StateReversable
*/
public class AtmControllable extends Atm implements MoneyStorageObservableInterface,OriginatorInterface {
  private final List<StorageListenerInterface> listeners = new ArrayList<>();
  private UUID uuid = UUID.randomUUID();

  public AtmControllable(Atm atm) {
    super(new ArrayList<NotesBank>(atm.getNotesBanks()));
  }

  public String toString() {
    return uuid.toString();
  }

  @Override
  public void releaseMoney(int sum) {
    super.releaseMoney(sum);
    onWithdraw(sum);
  }

  @Override
  public void addMoneyItemByPar(int par) {
    super.addMoneyItemByPar(par);
    onDeposit(par);
  }

  /*
    observer pattern
  */
  @Override
  public void addListener(StorageListenerInterface listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(StorageListenerInterface listener) {
    listeners.remove(listener);
  }

  void onDeposit(int amount) {
    listeners.forEach(listener -> listener.onStorageDeposit(this, amount));
  }

  void onWithdraw(int sum) {
    listeners.forEach(listener -> listener.onStorageWithdraw(this, sum));
  }

  /*
    memento pattern
  */
  @Override
  public AtmMemento saveState() {
    return new AtmMemento(notesBanks);
  }

  @Override
  public void restoreState(AtmMemento memento) {
    notesBanks.clear();
    memento.getNotesBanks().forEach(notesBank -> this.notesBanks.add(notesBank.clone()));
  }
}
