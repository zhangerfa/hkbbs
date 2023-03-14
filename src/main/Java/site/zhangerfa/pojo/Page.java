package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "分页", description = "记录分页信息，并封装当前页的数据")
public class Page {
    @Schema(description = "当前页码")
    private int currentPage = 1;
    @Schema(description = "每页大小")
    private int pageSize = 10;
    @Schema(description = "当前页上的帖子数")
    private int numOfPostsOnPage;
    @Schema(description = "总页数")
    private int totalPage;
    @Schema(description = "总帖子数")
    private int numOfPosts;

    public Page(int currentPage, int pageSize){
        if (pageSize < 1) throw new RuntimeException("每页显示帖子数量至少为1");
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * 补全page的信息
     */
    public void completePage(int numOfPosts){
        this.numOfPosts = numOfPosts;

        totalPage = numOfPosts / pageSize;
        totalPage = (numOfPosts % pageSize) == 0? totalPage: totalPage + 1;

        if (currentPage < totalPage)  numOfPostsOnPage = pageSize;
        else numOfPostsOnPage = numOfPosts - (totalPage - 1) * pageSize;
    }

    /**
     * 返回当前页首行
     * @return
     */
    @JsonIgnore
    public int getOffset(){
        return (currentPage - 1) * pageSize;
    }

    /**
     * 返回当前页最后一行
     * @return
     */
    @JsonIgnore
    public int getLimit(){
        return getOffset() + pageSize + 1;
    }

    /**
     * 获取当前总的页数
     * @return
     */
    public int getTotalPage() {
        return totalPage;
    }

    public int getNumOfPosts() {
        return numOfPosts;
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

    public int getNumOfPostsOnPage() {
        return numOfPostsOnPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
