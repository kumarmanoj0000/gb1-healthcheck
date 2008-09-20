package com.gb1.commons.pagination;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StaticPaginatedListTest {
	@Test
	public void testBasics() {
		final int SIZE = 13;
		final int FROM_INDEX = 2;
		final int TO_INDEX = 8;

		List<String> source = prepareStringList(SIZE);
		StaticPaginatedList<String> provider = new StaticPaginatedList<String>(source);

		assertEquals(SIZE, provider.size());
		assertEquals(source.subList(FROM_INDEX, TO_INDEX), provider.items(FROM_INDEX, TO_INDEX));
	}

	private List<String> prepareStringList(int size) {
		List<String> list = new ArrayList<String>(size);
		for (int i = 0; i < size; i++) {
			list.add(Integer.toString(i));
		}

		return list;
	}
}
