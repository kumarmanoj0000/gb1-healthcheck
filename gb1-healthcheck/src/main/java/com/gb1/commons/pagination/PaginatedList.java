package com.gb1.commons.pagination;

import java.util.List;

/**
 * An item provider for paginated lists.
 * 
 * @author Guillaume Bilodeau
 */
public interface PaginatedList<E> {
	/**
	 * Returns the number of items in the entire list.
	 * 
	 * @return The number of items in the list
	 */
	int size();

	/**
	 * Returns the items located between the given indexes, inclusively.
	 * 
	 * @param fromIndex The beginning index (inclusive)
	 * @param toIndex The end index (inclusive)
	 * @return The requested items
	 */
	List<E> items(int fromIndex, int toIndex);

	/**
	 * Sorts the underlying list using the given property and direction.
	 * 
	 * @param sortProperty The property name to sort on
	 * @param sortAscending The direction (true = ascending, false = descending) in which to sort
	 */
	void sort(String sortProperty, boolean sortAscending);
}
