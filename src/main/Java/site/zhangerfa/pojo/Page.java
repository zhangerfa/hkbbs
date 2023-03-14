package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "分页", description = "记录分页信息，并封装当前页的数据")
public class Page {
    @Schema(description = "当前页码")
    private int currentPage = 1;
    @Schema(description = "每页大小")
    private int pageSize = 10;
    @Schema(description = "当前页上的帖子数")
    private int numOfPostsOnPage = 10;
    @Schema(description = "总页数")
    private int totalPage;
    @Schema(description = "总帖子数")
    private int numOfPosts;

    /**
     * 返回当前页首行
     * @return
     */
    @JsonIgnore
    public int getOffset(){
        return (currentPage - 1) * numOfPostsOnPage;
    }

    /**
     * 返回当前页最后一行
     * @return
     */
    @JsonIgnore
    public int getLimit(){
        return getOffset() + numOfPostsOnPage;
    }

    /**
     * 获取当前总的页数
     * @return
     */
    public int getTotalPage() {
        totalPage = numOfPosts / pageSize;
        totalPage = (numOfPosts % numOfPostsOnPage) == 0? totalPage: totalPage + 1;
        return totalPage;
    }

    public int getNumOfPosts() {
        return numOfPosts;
    }

    public void setNumOfPosts(int numOfPosts) {
        this.numOfPosts = numOfPosts;
    }

    /**
     * 获取起始页码，当前页的前两页，不会小于 1
     * @return
     */
    @JsonIgnore
    public int getFrom(){
        return Math.max(currentPage - 2, 1);
    }

    /**
     * 获取终止页码，当前页后两页，不会大于最大页数
     * @return
     */
    @JsonIgnore
    public int getTo() {
        int total = getTotalPage();
        return Math.min(currentPage + 2, total);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 改变当前页码，但页码必须 ≥1，≤最大页码
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage >= 1){
            this.currentPage = currentPage;
        }
    }

    public int getNumOfPostsOnPage() {
        return numOfPostsOnPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setNumOfPostsOnPage(int numOfPostsOnPage) {
        this.numOfPostsOnPage = numOfPostsOnPage;
    }
}
