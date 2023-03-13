package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "分页", description = "记录分页信息，并封装当前页的数据")
public class Page<T> {
    @Schema(description = "当前页码")
    private int current = 1;
    @Schema(description = "每页大小")
    private int pageSize = 10;
    @Schema(description = "当前页上的帖子数")
    private int numOfPostsOnPage = 10;
    @Schema(description = "总页数")
    private int totalPage;
    @Schema(description = "当前页帖子集合")
    private List<T> posts;
    @Schema(description = "分页页码开始页")
    private int from;
    @Schema(description = "分页页码结束页")
    private int to;
    @Schema(description = "总帖子数")
    private int numOfPosts;
    // 查询路径
    private String path;

    /**
     * 返回当前页首行
     * @return
     */
    public int getOffset(){
        return (current - 1) * numOfPostsOnPage;
    }

    /**
     * 返回当前页最后一行
     * @return
     */
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
    public int getFrom(){
        from = Math.max(current - 2, 1);
        return from;
    }

    /**
     * 获取终止页码，当前页后两页，不会大于最大页数
     * @return
     */
    public int getTo() {
        int total = getTotalPage();
        to = Math.min(current + 2, total);
        return to;
    }

    public int getCurrent() {
        return current;
    }

    /**
     * 改变当前页码，但页码必须 ≥1，≤最大页码
     * @param current
     */
    public void setCurrent(int current) {
        if (current >= 1){
            this.current = current;
        }
    }

    public int getNumOfPostsOnPage() {
        return numOfPostsOnPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setNumOfPostsOnPage(int numOfPostsOnPage) {
        this.numOfPostsOnPage = numOfPostsOnPage;
    }

    public List<T> getPosts() {
        return posts;
    }

    public void setPosts(List<T> posts) {
        this.posts = posts;
    }
}
