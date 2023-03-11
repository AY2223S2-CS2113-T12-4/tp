package seedu.duke.atomichabit.feature;

import java.util.ArrayList;

public class AtomicHabitList {
    private ArrayList<AtomicHabit> allAtomicHabits;

    public AtomicHabitList() {
        allAtomicHabits = new ArrayList<>();
    }

    /**
     * Method to add atomicHabit to list containing all habits
     *
     * @param atomicHabit
     */

    public void addAtomicHabit(AtomicHabit atomicHabit) {
        allAtomicHabits.add(atomicHabit);
    }

    /**
     * Method to get list containing all habits
     *
     * @return allAtomicHabits which is an arraylist containing all habits
     */
    public ArrayList<AtomicHabit> getAllHabits() {
        return allAtomicHabits;
    }
}

