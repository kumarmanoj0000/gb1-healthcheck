package com.gb1.commons.pagination;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.support.PropertyComparator;

/**
 * A provider using an underlying list as its source.
 * 
 * @author Guillaume Bilodeau
 */
public class StaticPaginatedList<E> implements PaginatedList<E> {
	private List<E> source;

	public StaticPaginatedList(List<E> source) {
		this.source = source;
	}

	public List<E> items(int fromIndex, int toIndex) {
		return source.subList(fromIndex, toIndex);
	}

	public int size() {
		return source.size();
	}

	@SuppressWarnings("unchecked")
	public void sort(String sortProperty, boolean sortAscending) {
		PropertyComparator comp = new PropertyComparator(sortProperty, true, sortAscending);
		Collections.sort(source, comp);
	}
}
