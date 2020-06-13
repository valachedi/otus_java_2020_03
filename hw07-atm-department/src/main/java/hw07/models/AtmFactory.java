package hw07.models;

import java.util.ArrayList;
import java.util.TreeSet;
import hw07.components.NotesBankFactory;
import hw07.components.ParDictionary;

public class AtmFactory {
  public static Atm createAtmWithRandomNoteBanksContent() throws Exception {
    TreeSet<Integer> parsListAvailable = ParDictionary.getParsList();
    ArrayList<NotesBank> notesBanks = new ArrayList<NotesBank>(parsListAvailable.size());

    for(int eachPar : parsListAvailable) {
      notesBanks.add(NotesBankFactory.createWithRandomAmountByPar(eachPar));
    }

    return new Atm(notesBanks);
  }
}
