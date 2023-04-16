package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "记录分页信息，并封装当前页的数据")
public class Page {
    @Schema(description = "当前页码")
    private int currentPage = 1;
    @Schema(description = "每页大小")
    private int pageSize = 10;
    @Schema(description = "当前页上的帖子数")
    private int numOfPostsOnCurrentPage;
    @Schema(description = "总页数")
    private int numOfPages;

    public Page(int currentPage, int pageSize, int numOfPages, int numOfPostsOnCurrentPage){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.numOfPages = numOfPages;
        this.numOfPostsOnCurrentPage = numOfPostsOnCurrentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNumOfPostsOnCurrentPage() {
        return numOfPostsOnCurrentPage;
    }

    public void setNumOfPostsOnCurrentPage(int numOfPostsOnCurrentPage) {
        this.numOfPostsOnCurrentPage = numOfPostsOnCurrentPage;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}
