package de.thb.paf.scrabblefactory.gameplay.sort;


import java.util.Comparator;
import java.util.List;

import de.thb.paf.scrabblefactory.models.entities.Cheese;

import static java.util.Arrays.asList;

/**
 * Chained Cheese item comparator used to sort cheese items by their
 * activation state as well as their x-position.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class CheeseComparator implements Comparator<Cheese> {

    /**
     * List of comparators to apply
     */
    List<Comparator<Cheese>> comparators;

    /**
     * Constructor.
     */
    public CheeseComparator() {
        this.comparators = asList(
                new CheeseIsActiveComparator(),
                new CheesePositionComparator()
        );
    }

    @Override
    public int compare(Cheese cheese1, Cheese cheese2) {
        for(Comparator<Cheese> comparator : this.comparators) {
            int result = comparator.compare(cheese1, cheese2);

            if(result != 0) {
                return result;
            }
        }

        return 0;
    }

    /**
     * Cheese item comparator used to sort cheese items by their
     * activation state in ascending order from isActive to isInactive.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private static class CheeseIsActiveComparator implements Comparator<Cheese> {

        @Override
        public int compare(Cheese cheese1, Cheese cheese2) {
            return Boolean.valueOf(cheese1.isActive()).compareTo(cheese2.isActive());
        }
    }

    /**
     * Cheese item comparator used to sort cheese items by
     * their x-position in ascending order from left to right.
     *
     * @author Dominic Schiller - Technische Hochschule Brandenburg
     * @version 1.0
     * @since 1.0
     */
    private static class CheesePositionComparator implements Comparator<Cheese> {

        @Override
        public int compare(Cheese cheese1, Cheese cheese2) {
            return (cheese1.getPosition().x < cheese2.getPosition().x ? -1 : 1);
        }
    }

}
