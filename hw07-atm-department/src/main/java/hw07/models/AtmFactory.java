package hw07.models;

import java.util.ArrayList;
import java.util.TreeSet;
import hw07.components.NotesBankFactory;
import hw07.components.ParDictionary;
import hw07.components.ParDictionary.ValidPar;

public class AtmFactory {
  public static Atm createAtmWithRandomNoteBanksContent() {
    ArrayList<NotesBank> notesBanks = new ArrayList<NotesBank>(ValidPar.values().length);

    for(ValidPar eachPar : ValidPar.values()) {
      notesBanks.add(NotesBankFactory.createWithRandomAmountByPar(eachPar));
    }

    return new Atm(notesBanks);
  }
}
