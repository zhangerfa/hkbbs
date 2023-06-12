package site.zhangerfa.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "记录分页信息")
@Data
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

    public Page(IPage<?> page){
        this.currentPage = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
        this.numOfPages = (int) page.getPages();
        this.numOfPostsOnCurrentPage = (int) page.getTotal();
    }
}
