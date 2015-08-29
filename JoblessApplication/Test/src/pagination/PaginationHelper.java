package pagination;

import javax.faces.model.DataModel;

/**
 * Abstract Class needet for JSF Pagination
 * @author Sava Savov
 *
 */
public abstract class PaginationHelper {

	private int pageSize;
	private int page;

	public PaginationHelper(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * abstract method
	 * @return Integer - Items Count
	 */
	public abstract int getItemsCount();

	/**
	 * abstract method
	 * @return DataModel - Page Data Model
	 */
	@SuppressWarnings("rawtypes")
	public abstract DataModel createPageDataModel();

	public int getPageFirstItem() {

		return page * pageSize;
	}

	public int getPageLastItem() {
		int i = getPageFirstItem() + pageSize - 1;
		int count = getItemsCount() - 1;
		if (i > count) {
			i = count;
		}
		if (i < 0) {
			i = 0;
		}
		return i;
	}

	public boolean isHasNextPage() {
		return (page + 1) * pageSize + 1 <= getItemsCount();
	}

	public void nextPage() {
		if (isHasNextPage()) {
			page++;
		}
	}

	public boolean isHasPreviousPage() {
		return page > 0;
	}

	public void previousPage() {
		if (isHasPreviousPage()) {
			page--;
		}
	}

	public int getPageSize() {
		return pageSize;
	}
}
