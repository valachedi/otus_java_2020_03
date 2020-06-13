package hw07.models;

import java.util.ArrayList;
import java.util.List;
import hw07.models.NotesBank;

public class AtmMemento {
  private List<NotesBank> notesBanksState;

  public AtmMemento(List<NotesBank> notesBanks) {
    notesBanksState = new ArrayList<NotesBank>();
    notesBanks.forEach(notesBank -> notesBanksState.add(notesBank.clone()));
  }

  public ArrayList<NotesBank> getNotesBanks() {
    return new ArrayList<NotesBank>(notesBanksState);
  }
}
