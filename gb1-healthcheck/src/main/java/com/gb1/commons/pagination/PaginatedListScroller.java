package com.gb1.commons.pagination;

import java.util.List;

/**
 * A paginated list scroller that allows stateful navigation through all available pages.
 * <p/>
 * Instances are stateful and must be properly initialized before usage. The first call should be to
 * one of the following methods:
 * <ul>
 * <li><code>gotoPage(int)</code></li>
 * <li><code>gotoFirstPage()</code></li>
 * <li><code>gotoLastPage()</code></li>
 * </ul>
 * Unless one of these methods is called first, an <code>IllegalStateException</code> will be
 * thrown.
 * 
 * @author Guillaume Bilodeau
 */
public class PaginatedListScroller<E> {
	private PaginatedList<E> provider;
	private int pageSize;
	private int currentPageIndex = -1;

	public PaginatedListScroller(PaginatedList<E> provider) {
		this(provider, provider.size());
	}

	public PaginatedListScroller(PaginatedList<E> provider, int pageSize) {
		this.provider = provider;
		this.pageSize = pageSize;
	}

	/**
	 * Returns the items on the current page.
	 * <p/>
	 * This call has no effect on the current page.
	 * 
	 * @return The items on the current page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public List<E> itemsForCurrentPage() {
		checkCursorOpen();
		List<E> currentPageItems = getItemsForPage(currentPageIndex);

		return currentPageItems;
	}

	/**
	 * Scrolls the list to the page with the given index. After this call, this page will be the
	 * list's current page.
	 * 
	 * @param pageIndex The page index to go to, zero-based
	 * @return The scrollable list, positioned on the first available page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public PaginatedListScroller<E> gotoPage(int pageIndex) {
		if (pageIndex < 0) {
			throw new IllegalArgumentException("Page index cannot be lower than 0");
		}
		if (pageIndex >= getNumberOfPages()) {
			throw new IllegalArgumentException("Page index must be lower than last page index ("
					+ getNumberOfPages() + ")");
		}

		currentPageIndex = pageIndex;
		return this;
	}

	/**
	 * Scrolls the list to the first available page. After this call, the list's first page will be
	 * the current page.
	 * 
	 * @return The scrollable list, positioned on the first available page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public PaginatedListScroller<E> gotoFirstPage() {
		return gotoPage(0);
	}

	/**
	 * Scrolls the list to the last available page from this list. After this call, the list's last
	 * page will be the current page.
	 * 
	 * @return The scrollable list, positioned on the last available page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public PaginatedListScroller<E> gotoLastPage() {
		return gotoPage(getNumberOfPages() - 1);
	}

	/**
	 * Scrolls the list to the next available page from this list. After this call, the list's next
	 * page will be the current page.
	 * 
	 * @return The scrollable list, positioned on the next available page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public PaginatedListScroller<E> gotoNextPage() {
		checkCursorOpen();
		if (!hasNextPage()) {
			throw new IllegalStateException("No available next page");
		}

		return gotoPage(currentPageIndex + 1);
	}

	/**
	 * Scrolls the list to the previous available page from this list. After this call, the list's
	 * previous page will be the current page.
	 * 
	 * @return The scrollable list, positioned on the previous available page
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public PaginatedListScroller<E> gotoPreviousPage() {
		checkCursorOpen();
		if (!hasPreviousPage()) {
			throw new IllegalStateException("No available previous page");
		}

		return gotoPage(currentPageIndex - 1);
	}

	/**
	 * Verifies whether another page is available following the current one.
	 * <p/>
	 * This call has no effect on the current page.
	 * 
	 * @return true if a following page is available; false otherwise
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public boolean hasNextPage() {
		int lastPageIndex = getNumberOfPages() - 1;
		boolean nextPageExists = (currentPageIndex < lastPageIndex);

		return nextPageExists;
	}

	/**
	 * Verifies whether another page is available preceding the current one.
	 * <p/>
	 * This call has no effect on the current page.
	 * 
	 * @return true if a preceding page is available; false otherwise
	 * @throws IllegalStateException When the list has not yet been initialized
	 */
	public boolean hasPreviousPage() {
		boolean previousPageExists = (currentPageIndex > 0);
		return previousPageExists;
	}

	/**
	 * Returns the number of pages that are available in this list.
	 * 
	 * @return The number of available pages
	 */
	public int getNumberOfPages() {
		int nbPages;
		int cachedSize = provider.size();

		if (cachedSize == 0) {
			nbPages = 0;
		}
		else if (cachedSize % pageSize == 0) {
			nbPages = cachedSize / pageSize;
		}
		else {
			nbPages = (cachedSize / pageSize) + 1;
		}

		return nbPages;
	}

	/**
	 * Returns the items in the page of the given index. This call does not scroll the list and so
	 * does not change the current page.
	 * 
	 * @param index The index of the page to return
	 * @return The items in the page of the given index
	 * @throws IllegalArgumentException When the requested page doesn't exist
	 */
	public List<E> itemsForPage(int index) throws IllegalArgumentException {
		List<E> pageItems = getItemsForPage(index);
		currentPageIndex = index;

		return pageItems;
	}

	/**
	 * Returns the number of items to be contained by each page.
	 * 
	 * @return The size of each page
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the new page size for the paginated list. After this change, the current page will be
	 * the first one.
	 * 
	 * @param newPageSize The new page size
	 * @throws IllegalArgumentException If the new page size is lower than 1
	 */
	public void setPageSize(int newPageSize) throws IllegalArgumentException {
		if (newPageSize < 1) {
			throw new IllegalArgumentException("Page size must be at least 1");
		}

		pageSize = newPageSize;
		currentPageIndex = 0;
	}

	/**
	 * Returns the size of the entire list.
	 * 
	 * @return The size of the list
	 */
	public int size() {
		return provider.size();
	}

	/**
	 * Sorts the entire list using the given property and direction.
	 * 
	 * @param sortProperty The property name to sort on
	 * @param sortAscending The direction (true = ascending, false = descending) in which to sort
	 */
	public void sort(String sortProperty, boolean sortAscending) {
		provider.sort(sortProperty, sortAscending);
	}

	// helper methods

	private void checkCursorOpen() {
		if (currentPageIndex == -1) {
			throw new IllegalStateException(
					"Cursor not yet open.  You need to call gotoFirstPage(), gotoLastPage() or gotoPage(int) first.");
		}
	}

	private List<E> getItemsForPage(int pageIndex) {
		int firstItemIndex = pageIndex * pageSize;
		int lastItemIndex = firstItemIndex + pageSize;
		int cachedSize = size();

		if (lastItemIndex > cachedSize) {
			lastItemIndex = cachedSize;
		}

		return provider.items(firstItemIndex, lastItemIndex);
	}
}
