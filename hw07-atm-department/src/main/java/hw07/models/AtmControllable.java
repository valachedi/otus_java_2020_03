package hw07.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import hw06.models.Atm;
import hw06.models.NotesBank;
import hw07.models.interfaces.memento.OriginatorInterface;
import hw07.models.interfaces.observer.MoneyStorageObservableInterface;
import hw07.models.interfaces.observer.StorageListenerInterface;

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
  public void releaseMoney(int sum) throws Exception {
    super.releaseMoney(sum);
    onWithdraw(sum);
  }

  @Override
  public void addMoneyItemByPar(int par) throws Exception {
    super.addMoneyItemByPar(par);
    onDeposit(par);
  }

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
