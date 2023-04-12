package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "接收分页信息的对象")
public class InPage {
    @Schema(description = "当前页码，默认为1")
    private int currentPage = 1;

    @Schema(description = "每页大小，默认为10")
    private int pageSize = 10;

    public InPage(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
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
}
