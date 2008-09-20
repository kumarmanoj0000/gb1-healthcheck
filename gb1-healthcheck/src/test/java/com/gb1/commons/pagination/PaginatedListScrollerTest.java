package com.gb1.commons.pagination;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

public class PaginatedListScrollerTest {
	private final int PAGE_SIZE = 20;

	@Test
	public void testEmptyList() {
		final int listSize = 0;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		assertEquals(listSize, paginatedList.size());
		assertEquals(0, paginatedList.getNumberOfPages());
	}

	@Test
	public void testListOneResult() {
		final int listSize = 1;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		assertEquals(listSize, paginatedList.size());
		assertEquals(1, paginatedList.getNumberOfPages());

		paginatedList.gotoFirstPage();
		assertEquals(1, paginatedList.itemsForCurrentPage().size());
		assertEquals(source, paginatedList.itemsForCurrentPage());
	}

	@Test
	public void testListOneFullPage() {
		final int listSize = PAGE_SIZE;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		assertEquals(listSize, paginatedList.size());
		assertEquals(1, paginatedList.getNumberOfPages());

		paginatedList.gotoFirstPage();
		List<String> onlyPage = paginatedList.itemsForCurrentPage();
		assertEquals(PAGE_SIZE, onlyPage.size());
		assertEquals(source, paginatedList.itemsForCurrentPage());
	}

	@Test
	public void testListMultiplePagesLastPartial() {
		final int listSize = 43;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		// there should be 3 pages: 2 full and 1 partial
		assertEquals(listSize, paginatedList.size());
		assertEquals(3, paginatedList.getNumberOfPages());

		List<String> firstFullPage = paginatedList.gotoFirstPage().itemsForCurrentPage();
		assertEquals(PAGE_SIZE, firstFullPage.size());
		assertEquals(prepareStringList(0, 19), firstFullPage);

		List<String> secondFullPage = paginatedList.gotoNextPage().itemsForCurrentPage();
		assertEquals(PAGE_SIZE, secondFullPage.size());
		assertEquals(prepareStringList(20, 39), secondFullPage);

		List<String> lastPartialPage = paginatedList.gotoNextPage().itemsForCurrentPage();
		assertEquals(3, lastPartialPage.size());
		assertEquals(prepareStringList(40, 42), lastPartialPage);
	}

	@Test
	public void testListMultiplePagesLastFull() {
		final int nbPages = 3;
		final int listSize = nbPages * PAGE_SIZE;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		// there should be 3 full pages
		assertEquals(listSize, paginatedList.size());
		assertEquals(nbPages, paginatedList.getNumberOfPages());

		List<String> firstFullPage = paginatedList.gotoFirstPage().itemsForCurrentPage();
		assertEquals(PAGE_SIZE, firstFullPage.size());
		assertEquals(prepareStringList(0, 19), firstFullPage);

		List<String> secondFullPage = paginatedList.gotoNextPage().itemsForCurrentPage();
		assertEquals(PAGE_SIZE, secondFullPage.size());
		assertEquals(prepareStringList(20, 39), secondFullPage);

		List<String> lastFullPage = paginatedList.gotoNextPage().itemsForCurrentPage();
		assertEquals(PAGE_SIZE, lastFullPage.size());
		assertEquals(prepareStringList(40, 59), lastFullPage);
	}

	@Test
	public void testScrolling() {
		// prepare a list with 43 items: 2 full pages of 20 items and 1 partial of 3 items
		final int listSize = 43;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		try {
			paginatedList.gotoNextPage();
			fail("Cursor was not yet open");
		}
		catch (IllegalStateException e) {
			// ok
		}

		paginatedList.gotoFirstPage();
		assertEquals(prepareStringList(0, 19), paginatedList.itemsForCurrentPage());
		assertTrue(paginatedList.hasNextPage());
		assertTrue(!paginatedList.hasPreviousPage());

		try {
			paginatedList.gotoPreviousPage();
			fail("Current page was the first one: no available previous");
		}
		catch (IllegalStateException e) {
			// ok
		}

		paginatedList.gotoNextPage();
		assertEquals(prepareStringList(20, 39), paginatedList.itemsForCurrentPage());
		assertTrue(paginatedList.hasNextPage());
		assertTrue(paginatedList.hasPreviousPage());

		paginatedList.gotoNextPage();
		assertEquals(prepareStringList(40, 42), paginatedList.itemsForCurrentPage());
		assertTrue(!paginatedList.hasNextPage());
		assertTrue(paginatedList.hasPreviousPage());

		try {
			paginatedList.gotoNextPage();
			fail("Current page was the last one: no available next");
		}
		catch (IllegalStateException e) {
			// ok
		}
	}

	@Test
	public void testDirectPageAccess() {
		// prepare a list with 63 items: 3 full pages of 20 items and 1 partial of 3 items
		final int listSize = 63;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), PAGE_SIZE);

		assertEquals(prepareStringList(0, 19), paginatedList.gotoPage(0).itemsForCurrentPage());
		assertEquals(prepareStringList(20, 39), paginatedList.gotoPage(1).itemsForCurrentPage());
		assertEquals(prepareStringList(40, 59), paginatedList.gotoPage(2).itemsForCurrentPage());
		assertEquals(prepareStringList(60, 62), paginatedList.gotoPage(3).itemsForCurrentPage());

		try {
			paginatedList.gotoPage(-1);
			fail("Page index cannot be lower than 0");
		}
		catch (IllegalArgumentException e) {
			// ok
		}

		try {
			paginatedList.gotoPage(4);
			fail("Page index is higher than last page index");
		}
		catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testChangePageSizeFirstCall() {
		final int listSize = 5;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source));

		assertEquals(listSize, paginatedList.getPageSize());
		assertEquals(1, paginatedList.getNumberOfPages());

		final int newPageSize = 3;
		paginatedList.setPageSize(newPageSize);
		assertEquals(newPageSize, paginatedList.getPageSize());
		assertEquals(2, paginatedList.getNumberOfPages());
	}

	@Test
	public void testChangePageSizeInvalid() {
		final int listSize = 5;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source));

		try {
			paginatedList.setPageSize(0);
			fail("Cannot set page size to 0");
		}
		catch (IllegalArgumentException iae) {
			// ok
		}

		try {
			paginatedList.setPageSize(-1);
			fail("Cannot set page size to a negative value");
		}
		catch (IllegalArgumentException iae) {
			// ok
		}
	}

	@Test
	public void testChangePageSizeDuringScrolling() {
		final int listSize = 43;
		final int originalPageSize = 10;
		List<String> source = prepareStringList(listSize);
		PaginatedListScroller<String> paginatedList = new PaginatedListScroller<String>(
				new StaticPaginatedList<String>(source), originalPageSize);

		// scroll through a few pages and discard results
		paginatedList.gotoFirstPage();
		paginatedList.gotoNextPage();
		paginatedList.gotoNextPage();

		// change page size; after this change, the paginated list should reset itself and start
		// over from the first page
		final int newPageSize = 20;
		final List<String> firstPageItems = prepareStringList(newPageSize);

		paginatedList.setPageSize(newPageSize);
		assertEquals(newPageSize, paginatedList.getPageSize());
		assertEquals(firstPageItems, paginatedList.itemsForCurrentPage());
		assertEquals(firstPageItems, paginatedList.gotoFirstPage().itemsForCurrentPage());
	}

	@Test
	public void testSortEmptyList() {
		List<TestObject> source = Collections.emptyList();
		PaginatedListScroller<TestObject> paginatedList = new PaginatedListScroller<TestObject>(
				new StaticPaginatedList<TestObject>(source), 10);
		paginatedList.sort("stringProperty", true);

		assertEquals(0, paginatedList.size());
	}

	@Test
	public void testSortNonEmptyList() {
		final int listSize = 43;
		final int pageSize = 20;

		List<TestObject> source = prepareTestList(listSize);
		List<TestObject> shuffledSource = new ArrayList<TestObject>(source);
		Collections.shuffle(shuffledSource);

		PaginatedListScroller<TestObject> paginatedList = new PaginatedListScroller<TestObject>(
				new StaticPaginatedList<TestObject>(shuffledSource), pageSize);
		paginatedList.sort("intProperty", true);

		assertEquals(source.subList(0, pageSize), paginatedList.itemsForPage(0));
	}

	// helpers

	private List<String> prepareStringList(int size) {
		return prepareStringList(0, size - 1);
	}

	private List<String> prepareStringList(int min, int max) {
		List<String> list = new ArrayList<String>();
		for (int i = min; i <= max; i++) {
			list.add(Integer.toString(i));
		}

		return list;
	}

	private List<TestObject> prepareTestList(int size) {
		return prepareTestList(0, size - 1);
	}

	private List<TestObject> prepareTestList(int min, int max) {
		List<TestObject> list = new ArrayList<TestObject>();
		for (int i = min; i <= max; i++) {
			list.add(new TestObject(Integer.toString(i), i, new Date()));
		}

		return list;
	}

	private class TestObject {
		private String stringProperty;
		private int intProperty;
		private Date dateProperty;

		public TestObject(String stringProperty, int intProperty, Date dateProperty) {
			this.stringProperty = stringProperty;
			this.intProperty = intProperty;
			this.dateProperty = dateProperty;
		}

		public String getStringProperty() {
			return stringProperty;
		}

		public int getIntProperty() {
			return intProperty;
		}

		public Date getDateProperty() {
			return dateProperty;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof TestObject)) {
				return false;
			}

			TestObject that = (TestObject) o;
			EqualsBuilder builder = new EqualsBuilder().append(this.stringProperty,
					that.stringProperty).append(this.intProperty, that.intProperty).append(
					this.dateProperty, that.dateProperty);

			return builder.isEquals();
		}

		@Override
		public int hashCode() {
			HashCodeBuilder builder = new HashCodeBuilder().append(stringProperty).append(
					intProperty).append(dateProperty);
			return builder.toHashCode();
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
