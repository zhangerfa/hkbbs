package site.zhangerfa.util;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Page;

/**
 * 分页数据处理工具类
 */
public class PageUtil {
    @Schema(description = "当前页的页号")
    private int currentPage;
    @Schema(description = "当前页的大小，即包含的实体数")
    private int pageSize;
    @Schema(description = "总的实体数")
    private int numOfEntity;

    public PageUtil(int currentPage, int pageSize, int numOfEntity) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.numOfEntity = numOfEntity;
    }

    /**
     * 构造分页信息对象
     * @return
     */
    public Page generatePage(){
        return new Page(currentPage, pageSize,
                getNumOfPages(),
                getNumOfEntityOnCurrentPage());
    }

    /**
     * 获取当前页实体的起始和最后一个的序号
     * @return
     */
    public int[] getFromTo(){
        int[] fromTo = new int[2];
        fromTo[0] = Math.min((currentPage - 1) * pageSize, numOfEntity);
        fromTo[1] = Math.min(fromTo[0] + pageSize, numOfEntity);
        return fromTo;
    }

    /**
     * 获取总的页数
     * @return
     */
    public int getNumOfPages(){
        int numOfPages = numOfEntity / pageSize;
        return (numOfEntity % pageSize) == 0? numOfPages: numOfPages + 1;
    }

    /**
     * 获取当前页的实体数
     * @return
     */
    public int getNumOfEntityOnCurrentPage(){
        // 当前页可以放满
        if ((currentPage + 1) * pageSize <= numOfEntity) return pageSize;
        // 当前页放不满
        return numOfEntity - (currentPage - 1) * pageSize;
    }
}
