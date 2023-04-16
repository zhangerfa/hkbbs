package site.zhangerfa.util;

import site.zhangerfa.pojo.Page;

/**
 * 分页数据处理工具类
 */
public class PageUtil {
    /**
     * 构造分页信息对象
     * @param currentPage 当前页的页号
     * @param pageSize 当前页的大小，即包含的实体数
     * @param numOfEntity 总的实体数
     * @return
     */
    public static Page generatePage(int currentPage, int pageSize, int numOfEntity){
        return new Page(currentPage, pageSize,
                getNumOfPages(numOfEntity, pageSize),
                getNumOfEntityOnCurrentPage(currentPage, pageSize, numOfEntity));
    }

    /**
     * 获取当前页实体的起始和最后一个的序号
     * @param currentPage 当前页的页号
     * @param pageSize 当前页的大小，即包含的实体数
     * @param numOfEntity 总的实体数
     * @return
     */
    public static int[] getFromTo(int currentPage, int pageSize, int numOfEntity){
        int[] fromTo = new int[2];
        fromTo[0] = Math.min((currentPage - 1) * pageSize, numOfEntity);
        fromTo[1] = Math.min(fromTo[0] + pageSize + 1, numOfEntity);
        return fromTo;
    }

    /**
     * 获取总的页数
     * @param numOfEntity 实体总数量
     * @param pageSize 每页大小
     * @return
     */
    public static int getNumOfPages(int numOfEntity, int pageSize){
        int numOfPages = numOfEntity / pageSize;
        return (numOfEntity % pageSize) == 0? numOfPages: numOfPages + 1;
    }

    /**
     * 获取当前页的实体数
     * @param currentPage 当前页页码
     * @param pageSize 当前页大小
     * @param numOfEntity 实体总数
     * @return
     */
    public static int getNumOfEntityOnCurrentPage(int currentPage, int pageSize, int numOfEntity){
        // 当前页可以放满
        if ((currentPage + 1) * pageSize <= numOfEntity) return pageSize;
        // 当前页放不满
        return numOfEntity - (currentPage - 1) * pageSize;
    }
}
