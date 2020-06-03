package hw06.models;

import java.util.ArrayList;
import hw06.components.ParDictionary;

public class AtmFactory {
  public static Atm createAtmWithRandomNoteBanksContent() throws Exception {
    ArrayList<Integer> parsListAvailable = ParDictionary.getParsList();
    ArrayList<NotesBank> notesBanks = new ArrayList<NotesBank>(parsListAvailable.size());

    for(int eachPar : parsListAvailable) {
      notesBanks.add(NotesBankFactory.createWithRandomAmountByPar(eachPar));
    }

    return new Atm(notesBanks);
  }
}
